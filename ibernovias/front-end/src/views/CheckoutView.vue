<template>
  <div class="bg-luxury-cream min-h-screen pb-20">
    <!-- Progress Bar -->
    <div class="bg-white border-b border-gray-200 py-6">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex items-center justify-between">
          <!-- Step 1: Carrito -->
          <div class="flex items-center">
            <router-link to="/carrito" class="flex items-center cursor-pointer hover:text-luxury-gold transition">
              <div class="flex items-center justify-center w-10 h-10 rounded-full bg-luxury-gold text-white font-bold text-lg">✓</div>
              <p class="ml-3 text-sm font-semibold text-luxury-gold">Carrito</p>
            </router-link>
          </div>
          <!-- Line 1 -->
          <div class="flex-1 h-1 bg-luxury-gold mx-4"></div>
          
          <!-- Step 2: Checkout -->
          <div class="flex items-center">
            <div class="flex items-center justify-center w-10 h-10 rounded-full bg-luxury-gold text-white font-bold text-lg">✓</div>
            <p class="ml-3 text-sm font-semibold text-luxury-black">Checkout</p>
          </div>
          <!-- Line 2 -->
          <div class="flex-1 h-1 bg-gray-300 mx-4"></div>
          
          <!-- Step 3: Confirmación -->
          <div class="flex items-center">
            <div class="flex items-center justify-center w-10 h-10 rounded-full bg-gray-300 text-white font-bold text-lg">3</div>
            <p class="ml-3 text-sm font-semibold text-gray-500">Confirmación</p>
          </div>
        </div>
      </div>
    </div>

    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <h1 class="font-serif text-4xl text-luxury-black mb-12">Finalizar Compra</h1>

      <div class="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <!-- Formulario de Envío -->
        <div class="lg:col-span-2">
          <div class="bg-white border border-gray-200 rounded-xl p-8 shadow-sm">
            <h2 class="font-serif text-2xl text-luxury-black mb-6 border-b pb-4">Datos de Envío</h2>
            
            <form @submit.prevent="proceedToPay" class="space-y-6">
              <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                <!-- Nombre -->
                <div>
                  <label class="block text-sm font-semibold text-gray-700 mb-2">Nombre *</label>
                  <input 
                    v-model="form.nombre" 
                    type="text" 
                    required
                    class="w-full px-4 py-3 border border-gray-300 rounded focus:outline-none focus:border-luxury-gold focus:ring-1 focus:ring-luxury-gold"
                    placeholder="Tu nombre"
                  >
                </div>

                <!-- Apellido -->
                <div>
                  <label class="block text-sm font-semibold text-gray-700 mb-2">Apellido *</label>
                  <input 
                    v-model="form.apellido" 
                    type="text" 
                    required
                    class="w-full px-4 py-3 border border-gray-300 rounded focus:outline-none focus:border-luxury-gold focus:ring-1 focus:ring-luxury-gold"
                    placeholder="Tu apellido"
                  >
                </div>
              </div>

              <!-- Email -->
              <div>
                <label class="block text-sm font-semibold text-gray-700 mb-2">Email *</label>
                <input 
                  v-model="form.email" 
                  type="email" 
                  required
                  class="w-full px-4 py-3 border border-gray-300 rounded focus:outline-none focus:border-luxury-gold focus:ring-1 focus:ring-luxury-gold"
                  placeholder="tu@email.com"
                >
              </div>

              <!-- Teléfono -->
              <div>
                <label class="block text-sm font-semibold text-gray-700 mb-2">Teléfono *</label>
                <input 
                  v-model="form.telefono" 
                  type="tel" 
                  required
                  class="w-full px-4 py-3 border border-gray-300 rounded focus:outline-none focus:border-luxury-gold focus:ring-1 focus:ring-luxury-gold"
                  placeholder="+34 600 000 000"
                >
              </div>

              <!-- Dirección -->
              <div>
                <label class="block text-sm font-semibold text-gray-700 mb-2">Dirección *</label>
                <input 
                  v-model="form.direccion" 
                  type="text" 
                  required
                  class="w-full px-4 py-3 border border-gray-300 rounded focus:outline-none focus:border-luxury-gold focus:ring-1 focus:ring-luxury-gold"
                  placeholder="Calle, número..."
                >
              </div>

              <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
                <!-- Ciudad -->
                <div class="md:col-span-1">
                  <label class="block text-sm font-semibold text-gray-700 mb-2">Localidad *</label>
                  <input 
                    v-model="form.ciudad" 
                    type="text" 
                    required
                    class="w-full px-4 py-3 border border-gray-300 rounded focus:outline-none focus:border-luxury-gold focus:ring-1 focus:ring-luxury-gold"
                    placeholder="Tu ciudad"
                  >
                </div>

                <!-- CP -->
                <div>
                  <label class="block text-sm font-semibold text-gray-700 mb-2">C.P. *</label>
                  <input 
                    v-model="form.cp" 
                    type="text" 
                    required
                    class="w-full px-4 py-3 border border-gray-300 rounded focus:outline-none focus:border-luxury-gold focus:ring-1 focus:ring-luxury-gold"
                    placeholder="00000"
                  >
                </div>

                <!-- País -->
                <div>
                  <label class="block text-sm font-semibold text-gray-700 mb-2">País *</label>
                  <input 
                    v-model="form.pais" 
                    type="text" 
                    required
                    class="w-full px-4 py-3 border border-gray-300 rounded focus:outline-none focus:border-luxury-gold focus:ring-1 focus:ring-luxury-gold"
                    placeholder="España"
                  >
                </div>
              </div>

              <!-- Términos -->
              <div class="pt-4 border-t border-gray-200">
                <label class="flex items-start gap-3 cursor-pointer group">
                  <input 
                    v-model="form.terminos" 
                    type="checkbox" 
                    required
                    class="mt-1 w-4 h-4 text-luxury-gold border-gray-300 rounded focus:ring-luxury-gold cursor-pointer"
                  >
                  <span class="text-sm text-gray-600 group-hover:text-gray-700">
                    Acepto los 
                    <router-link to="/legal" class="text-luxury-gold hover:underline">términos y condiciones</router-link> 
                    y la 
                    <router-link to="/privacidad" class="text-luxury-gold hover:underline">política de privacidad</router-link>
                  </span>
                </label>
              </div>

              <!-- Botones -->
              <div class="flex gap-4 pt-6">
                <router-link to="/carrito" class="flex-1 border border-luxury-gold text-luxury-gold px-6 py-4 font-bold uppercase tracking-widest hover:bg-luxury-gold hover:text-white transition rounded-lg text-center">
                  ← Volver
                </router-link>
                <button 
                  type="submit"
                  :disabled="!form.terminos"
                  class="flex-1 bg-luxury-gold text-white px-6 py-4 font-bold uppercase tracking-widest hover:bg-opacity-90 transition rounded-lg disabled:opacity-50 disabled:cursor-not-allowed shadow-lg hover:shadow-xl">
                  Ir al Pago →
                </button>
              </div>
            </form>
          </div>
        </div>

        <!-- Resumen Lateral -->
        <div class="lg:col-span-1">
          <div class="bg-white border border-gray-200 rounded-xl p-6 sticky top-32 shadow-sm">
            <h2 class="font-serif text-xl text-luxury-black mb-4 border-b pb-3">Resumen de Compra</h2>
            
            <div class="space-y-3 mb-4">
              <div v-for="item in cartStore.items" :key="item.id" class="flex justify-between text-sm pb-3 border-b">
                <span class="text-gray-600">
                  {{ item.nombre }} <span class="font-semibold text-gray-400">x{{ item.cantidad }}</span>
                </span>
                <span class="font-semibold">{{ (item.precio * item.cantidad).toFixed(2) }}€</span>
              </div>
            </div>

            <div class="space-y-2 pb-4 border-t pt-4">
              <div class="flex justify-between text-sm">
                <span class="text-gray-600">Subtotal:</span>
                <span class="font-semibold">{{ parseFloat(cartStore.total).toFixed(2) }}€</span>
              </div>
              <div class="flex justify-between text-sm">
                <span class="text-gray-600">Envío:</span>
                <span class="font-semibold text-luxury-gold">Gratis</span>
              </div>
              <div class="flex justify-between text-sm">
                <span class="text-gray-600">Impuestos (21%):</span>
                <span class="font-semibold">{{ (parseFloat(cartStore.total) * 0.21).toFixed(2) }}€</span>
              </div>
            </div>

            <div class="flex justify-between pt-4 border-t">
              <span class="font-bold">Total:</span>
              <span class="text-luxury-gold text-xl font-bold">{{ (parseFloat(cartStore.total) * 1.21).toFixed(2) }}€</span>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { useCartStore } from '../stores/cart'

const router = useRouter()
const cartStore = useCartStore()

const form = ref({
  nombre: '',
  apellido: '',
  email: '',
  telefono: '',
  direccion: '',
  ciudad: '',
  cp: '',
  pais: 'España',
  terminos: false
})

const proceedToPay = () => {
  // Aquí se redirige a la pasarela de pago
  // Por ahora, guardamos los datos y vamos a una página de confirmación
  router.push('/confirmacion')
}
</script>
