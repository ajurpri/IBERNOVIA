<template>
  <section class="min-h-screen bg-luxury-gray py-10">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <header class="mb-8 flex flex-col md:flex-row md:items-center md:justify-between gap-4">
        <div>
          <h1 class="font-serif text-3xl md:text-4xl text-luxury-black">Panel de Administración</h1>
          <p class="text-gray-600 text-sm">Gestiona productos, stock y usuarios.</p>
        </div>
        <div class="flex items-center gap-3">
          <button
            class="px-4 py-2 uppercase text-xs tracking-widest rounded"
            :class="activeTab === 'products' ? 'bg-luxury-black text-white' : 'border border-gray-200 text-gray-600'"
            @click="activeTab = 'products'"
          >
            Productos
          </button>
          <button
            class="px-4 py-2 uppercase text-xs tracking-widest rounded"
            :class="activeTab === 'users' ? 'bg-luxury-black text-white' : 'border border-gray-200 text-gray-600'"
            @click="loadUsers()"
          >
            Usuarios
          </button>
        </div>
      </header>

      <div v-if="activeTab === 'products'" class="grid grid-cols-1 lg:grid-cols-[2fr_1fr] gap-8">
        <div class="bg-white rounded-2xl shadow-sm border border-gray-100 p-6">
          <div class="flex flex-col md:flex-row gap-4 mb-6">
            <input
              v-model="search"
              type="text"
              placeholder="Buscar por nombre o categoría"
              class="w-full px-4 py-2 border border-gray-200 rounded focus:outline-none focus:border-luxury-gold"
            >
            <select v-model="statusFilter" class="px-3 py-2 border border-gray-200 rounded text-sm">
              <option value="all">Todos</option>
              <option value="active">Activos</option>
              <option value="inactive">Inactivos</option>
            </select>
          </div>

          <div v-if="loading" class="py-10 text-center text-gray-500">Cargando productos...</div>
          <div v-else-if="filteredProducts.length === 0" class="py-10 text-center text-gray-500">Sin resultados.</div>

          <div v-else class="space-y-4">
            <article
              v-for="producto in filteredProducts"
              :key="producto.id"
              class="border border-gray-100 rounded-xl p-4 flex flex-col md:flex-row md:items-center gap-4"
            >
              <div class="w-20 h-24 bg-gray-100 rounded overflow-hidden flex-shrink-0">
                <img
                  v-if="producto.imagen"
                  :src="producto.imagen"
                  :alt="producto.nombre"
                  class="w-full h-full object-cover"
                  loading="lazy"
                  decoding="async"
                >
              </div>
              <div class="flex-1">
                <h3 class="font-semibold text-luxury-black">{{ producto.nombre }}</h3>
                <p class="text-xs uppercase tracking-widest text-gray-400">{{ producto.categoria }}</p>
                <p class="text-sm text-gray-600 mt-1">€{{ producto.precio }} · Stock {{ producto.stock }}</p>
                <span
                  :class="producto.activo ? 'text-green-600' : 'text-red-500'"
                  class="text-xs font-semibold uppercase tracking-widest"
                >
                  {{ producto.activo ? 'Activo' : 'Inactivo' }}
                </span>
              </div>
              <div class="flex items-center gap-2">
                <button
                  class="px-3 py-2 text-xs uppercase tracking-widest border border-gray-200 rounded hover:border-luxury-gold"
                  @click="editProduct(producto)"
                >
                  Editar
                </button>
                <button
                  class="px-3 py-2 text-xs uppercase tracking-widest border border-red-200 text-red-600 rounded hover:bg-red-50"
                  @click="removeProduct(producto.id)"
                >
                  Eliminar
                </button>
              </div>
            </article>
          </div>
        </div>

        <div class="bg-white rounded-2xl shadow-sm border border-gray-100 p-6">
          <h2 class="font-serif text-2xl text-luxury-black mb-4">{{ form.id ? 'Editar producto' : 'Nuevo producto' }}</h2>

          <form class="space-y-4" @submit.prevent="saveProduct">
            <div>
              <label class="text-xs uppercase tracking-widest text-gray-500">Nombre</label>
              <input v-model="form.nombre" required type="text" class="w-full px-4 py-2 border border-gray-200 rounded">
            </div>

            <div>
              <label class="text-xs uppercase tracking-widest text-gray-500">Categoría</label>
              <input v-model="form.categoria" required type="text" class="w-full px-4 py-2 border border-gray-200 rounded">
            </div>

            <div class="grid grid-cols-2 gap-4">
              <div>
                <label class="text-xs uppercase tracking-widest text-gray-500">Precio (€)</label>
                <input v-model.number="form.precio" required type="number" min="0" step="0.01" class="w-full px-4 py-2 border border-gray-200 rounded">
              </div>
              <div>
                <label class="text-xs uppercase tracking-widest text-gray-500">Stock</label>
                <input v-model.number="form.stock" required type="number" min="0" class="w-full px-4 py-2 border border-gray-200 rounded">
              </div>
            </div>

            <div>
              <label class="text-xs uppercase tracking-widest text-gray-500">Imagen (URL o /images/...)</label>
              <input v-model="form.imagen" type="text" class="w-full px-4 py-2 border border-gray-200 rounded">
            </div>

            <div>
              <label class="text-xs uppercase tracking-widest text-gray-500">Descripción</label>
              <textarea v-model="form.descripcion" rows="3" class="w-full px-4 py-2 border border-gray-200 rounded"></textarea>
            </div>

            <label class="flex items-center gap-2 text-sm text-gray-600">
              <input v-model="form.activo" type="checkbox" class="rounded border-gray-300">
              Producto activo
            </label>

            <div class="flex items-center gap-3">
              <button
                type="submit"
                class="flex-1 bg-luxury-black text-white py-2 uppercase text-xs tracking-widest rounded disabled:opacity-50"
                :disabled="saving"
              >
                {{ saving ? 'Guardando...' : 'Guardar cambios' }}
              </button>
              <button
                type="button"
                class="px-4 py-2 border border-gray-200 rounded text-xs uppercase tracking-widest"
                @click="resetForm"
              >
                Limpiar
              </button>
            </div>

            <p v-if="message" :class="messageOk ? 'text-green-600' : 'text-red-600'" class="text-sm">
              {{ message }}
            </p>
          </form>
        </div>
      </div>

      <div v-else class="bg-white rounded-2xl shadow-sm border border-gray-100 p-6">
        <div class="flex flex-col md:flex-row gap-4 mb-6">
          <input
            v-model="userSearch"
            type="text"
            placeholder="Buscar por nombre o email"
            class="w-full px-4 py-2 border border-gray-200 rounded focus:outline-none focus:border-luxury-gold"
          >
          <select v-model="userFilter" class="px-3 py-2 border border-gray-200 rounded text-sm">
            <option value="all">Todos</option>
            <option value="admin">Admin</option>
            <option value="active">Activos</option>
            <option value="inactive">Inactivos</option>
          </select>
        </div>

        <div v-if="loadingUsers" class="py-10 text-center text-gray-500">Cargando usuarios...</div>
        <div v-else-if="filteredUsers.length === 0" class="py-10 text-center text-gray-500">Sin resultados.</div>

        <div v-else class="space-y-4">
          <article
            v-for="user in filteredUsers"
            :key="user.id"
            class="border border-gray-100 rounded-xl p-4 flex flex-col md:flex-row md:items-center gap-4"
          >
            <div class="flex-1">
              <p class="font-semibold text-luxury-black">{{ user.nombre }} {{ user.apellido }}</p>
              <p class="text-xs text-gray-500">{{ user.email }}</p>
              <div class="flex gap-2 mt-2 text-xs uppercase tracking-widest">
                <span :class="user.activo ? 'text-green-600' : 'text-red-500'">{{ user.activo ? 'Activo' : 'Inactivo' }}</span>
                <span :class="user.isAdmin ? 'text-luxury-gold' : 'text-gray-400'">{{ user.isAdmin ? 'Admin' : 'Usuario' }}</span>
              </div>
            </div>
            <div class="flex items-center gap-2">
              <button
                class="px-3 py-2 text-xs uppercase tracking-widest border border-gray-200 rounded hover:border-luxury-gold"
                @click="toggleAdmin(user)"
              >
                {{ user.isAdmin ? 'Quitar admin' : 'Hacer admin' }}
              </button>
              <button
                class="px-3 py-2 text-xs uppercase tracking-widest border border-gray-200 rounded hover:border-luxury-gold"
                @click="toggleActive(user)"
              >
                {{ user.activo ? 'Desactivar' : 'Activar' }}
              </button>
            </div>
          </article>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue'
import { apiClient } from '../lib/api'

const activeTab = ref('products')
const products = ref([])
const loading = ref(false)
const saving = ref(false)
const message = ref('')
const messageOk = ref(true)
const search = ref('')
const statusFilter = ref('all')

const users = ref([])
const loadingUsers = ref(false)
const userSearch = ref('')
const userFilter = ref('all')

const form = ref({
  id: null,
  nombre: '',
  categoria: '',
  precio: 0,
  stock: 0,
  imagen: '',
  descripcion: '',
  activo: true
})

const loadProducts = async () => {
  loading.value = true
  try {
    const res = await apiClient.get('/api/productos')
    products.value = Array.isArray(res.data) ? res.data : []
  } catch (e) {
    message.value = 'No se pudieron cargar los productos.'
    messageOk.value = false
  } finally {
    loading.value = false
  }
}

const filteredProducts = computed(() => {
  const term = search.value.toLowerCase().trim()
  return products.value.filter(p => {
    const matchesSearch = !term || p.nombre.toLowerCase().includes(term) || (p.categoria || '').toLowerCase().includes(term)
    const matchesStatus = statusFilter.value === 'all'
      || (statusFilter.value === 'active' && p.activo)
      || (statusFilter.value === 'inactive' && !p.activo)
    return matchesSearch && matchesStatus
  })
})

const filteredUsers = computed(() => {
  const term = userSearch.value.toLowerCase().trim()
  return users.value.filter(u => {
    const matchesSearch = !term || `${u.nombre} ${u.apellido}`.toLowerCase().includes(term) || u.email.toLowerCase().includes(term)
    const matchesFilter = userFilter.value === 'all'
      || (userFilter.value === 'admin' && u.isAdmin)
      || (userFilter.value === 'active' && u.activo)
      || (userFilter.value === 'inactive' && !u.activo)
    return matchesSearch && matchesFilter
  })
})

const editProduct = (producto) => {
  form.value = { ...producto }
  message.value = ''
}

const resetForm = (keepMessage = false) => {
  form.value = {
    id: null,
    nombre: '',
    categoria: '',
    precio: 0,
    stock: 0,
    imagen: '',
    descripcion: '',
    activo: true
  }
  if (!keepMessage) {
    message.value = ''
  }
}

const loadUsers = async () => {
  activeTab.value = 'users'
  if (users.value.length > 0) return
  loadingUsers.value = true
  try {
    const res = await apiClient.get('/api/admin/users')
    users.value = Array.isArray(res.data) ? res.data : []
  } catch (e) {
    messageOk.value = false
    message.value = 'No se pudieron cargar los usuarios.'
  } finally {
    loadingUsers.value = false
  }
}

const toggleAdmin = async (user) => {
  try {
    const res = await apiClient.put(`/api/admin/users/${user.id}`, {
      isAdmin: !user.isAdmin
    })
    users.value = users.value.map(u => (u.id === user.id ? res.data : u))
  } catch (e) {
    messageOk.value = false
    message.value = 'No se pudo actualizar el usuario.'
  }
}

const toggleActive = async (user) => {
  try {
    const res = await apiClient.put(`/api/admin/users/${user.id}`, {
      activo: !user.activo
    })
    users.value = users.value.map(u => (u.id === user.id ? res.data : u))
  } catch (e) {
    messageOk.value = false
    message.value = 'No se pudo actualizar el usuario.'
  }
}

const saveProduct = async () => {
  saving.value = true
  message.value = ''
  try {
    if (form.value.id) {
      const res = await apiClient.put(`/api/productos/${form.value.id}`, form.value)
      products.value = products.value.map(p => (p.id === form.value.id ? res.data : p))
      message.value = 'Producto actualizado.'
    } else {
      const res = await apiClient.post('/api/productos', form.value)
      products.value = [res.data, ...products.value]
      message.value = 'Producto creado.'
    }
    messageOk.value = true
    resetForm(true)
  } catch (e) {
    messageOk.value = false
    message.value = 'No se pudo guardar el producto.'
  } finally {
    saving.value = false
  }
}

const removeProduct = async (id) => {
  if (!confirm('¿Eliminar este producto?')) return
  try {
    await apiClient.delete(`/api/productos/${id}`)
    products.value = products.value.filter(p => p.id !== id)
  } catch (e) {
    messageOk.value = false
    message.value = 'No se pudo eliminar el producto.'
  }
}

onMounted(loadProducts)
</script>
