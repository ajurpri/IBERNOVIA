<template>
  <section class="admin-list-row mb-6 space-y-4" aria-labelledby="password-reset-title">
    <h3 id="password-reset-title" class="font-semibold text-lg">Nueva contraseña</h3>
    <p class="text-sm">Cliente: <strong>{{ user.nombre }} {{ user.apellido }}</strong><br>Correo: {{ user.email }}</p>
    <form v-if="!saved" class="space-y-4" @submit.prevent="save">
      <p class="text-sm text-gray-600">La contraseña anterior dejará de funcionar. Puedes comunicar la nueva directamente al cliente.</p>
      <div>
        <label for="new-client-password" class="block mb-2">Contraseña nueva (mínimo 8 caracteres)</label>
        <input id="new-client-password" v-model="password" :type="visible ? 'text' : 'password'" required minlength="8" maxlength="72" autocomplete="new-password" :disabled="saving" class="admin-input w-full">
      </div>
      <div>
        <label for="confirm-client-password" class="block mb-2">Repetir contraseña</label>
        <input id="confirm-client-password" v-model="confirmation" :type="visible ? 'text' : 'password'" required autocomplete="new-password" :disabled="saving" class="admin-input w-full">
      </div>
      <label class="flex items-center gap-2"><input v-model="visible" type="checkbox"> Mostrar contraseña</label>
      <p v-if="error" role="alert" class="text-red-700">{{ error }}</p>
      <div class="flex flex-wrap gap-3">
        <button type="submit" :disabled="saving" class="admin-secondary-btn px-4 py-3">{{ saving ? 'Guardando…' : 'Guardar nueva contraseña' }}</button>
        <button type="button" :disabled="saving" class="admin-secondary-btn px-4 py-3" @click="$emit('close')">Cancelar</button>
      </div>
    </form>
    <div v-else class="space-y-4">
      <p role="status" class="text-green-800">Contraseña actualizada. Ya puedes comunicársela al cliente.</p>
      <p v-if="!user.activo" class="text-amber-800">Esta cuenta está inactiva. Tendrás que activarla para que pueda iniciar sesión.</p>
      <label for="saved-client-password" class="block">Nueva contraseña</label>
      <input id="saved-client-password" :value="password" readonly class="admin-input w-full font-mono text-lg" aria-describedby="password-close-note">
      <p id="password-close-note" class="text-sm text-gray-600">Al cerrar esta vista, no podrás volver a consultar esta contraseña. Si se pierde, podrás establecer otra.</p>
      <button type="button" class="admin-secondary-btn px-4 py-3" @click="$emit('close')">Cerrar</button>
    </div>
  </section>
</template>

<script setup>
import { ref } from 'vue'
import { apiClient } from '../lib/api'

const props = defineProps({ user: { type: Object, required: true } })
const emit = defineEmits(['close', 'busy'])
const password = ref('')
const confirmation = ref('')
const visible = ref(false)
const saving = ref(false)
const saved = ref(false)
const error = ref('')

const save = async () => {
  if (saving.value) return
  error.value = ''
  if (!password.value.trim() || password.value.length < 8 || new TextEncoder().encode(password.value).length > 72) {
    error.value = 'Introduce al menos 8 caracteres. Si usas símbolos o acentos, reduce la longitud hasta un máximo de 72 bytes.'
    return
  }
  if (password.value !== confirmation.value) {
    error.value = 'Las contraseñas no coinciden.'
    return
  }
  saving.value = true
  emit('busy', true)
  try {
    await apiClient.put(`/api/admin/users/${props.user.id}/password`, { password: password.value })
    confirmation.value = ''
    saved.value = true
  } catch (e) {
    error.value = e.response?.status === 401 || e.response?.status === 403
      ? 'No tienes autorización. Vuelve a iniciar sesión como administrador.'
      : e.response?.status === 404
        ? 'Este usuario ya no existe. Actualiza el listado.'
        : 'No se pudo confirmar el cambio. Vuelve a guardar la contraseña antes de comunicarla al cliente.'
  } finally {
    saving.value = false
    emit('busy', false)
  }
}
</script>
