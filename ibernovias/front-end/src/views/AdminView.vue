<template>
  <section class="min-h-screen bg-luxury-gray py-10">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <header class="mb-8">
        <div class="flex flex-col md:flex-row md:items-center md:justify-between gap-4 mb-8">
          <div>
            <h1 class="font-serif text-4xl md:text-5xl text-luxury-black">Panel Administrativo</h1>
            <p class="text-gray-600 text-sm mt-2">Gestiona la tienda, productos, stock y usuarios</p>
          </div>
          <button
            @click="authStore.logout()"
            class="px-6 py-2 uppercase text-xs tracking-widest bg-red-500/10 text-red-600 rounded hover:bg-red-500/20 transition"
          >
            Cerrar sesión
          </button>
        </div>

        <!-- Tabs de navegación -->
        <div class="flex gap-2 border-b border-gray-200">
          <button
            v-for="tab in tabs"
            :key="tab.id"
            @click="activeTab = tab.id"
            :class="activeTab === tab.id ? 'border-b-2 border-luxury-black text-luxury-black' : 'text-gray-600'"
            class="px-4 py-3 font-bold uppercase text-xs tracking-widest transition"
          >
            {{ tab.label }}
          </button>
        </div>
      </header>

      <!-- DASHBOARD -->
      <div v-if="activeTab === 'dashboard'" class="space-y-8">
        <!-- Estadísticas Principales -->
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
          <div class="bg-white rounded-2xl p-6 border border-gray-100 shadow-sm">
            <p class="text-gray-500 text-sm uppercase tracking-widest mb-2">Productos totales</p>
            <p class="text-4xl font-bold text-luxury-black">{{ products.length }}</p>
            <p class="text-xs text-gray-400 mt-2">{{ activeProducts }} activos</p>
          </div>
          <div class="bg-white rounded-2xl p-6 border border-gray-100 shadow-sm">
            <p class="text-gray-500 text-sm uppercase tracking-widest mb-2">Stock bajo (&lt;5)</p>
            <p class="text-4xl font-bold text-orange-600">{{ lowStockProducts }}</p>
            <p class="text-xs text-gray-400 mt-2">Requieren atención</p>
          </div>
          <div class="bg-white rounded-2xl p-6 border border-gray-100 shadow-sm">
            <p class="text-gray-500 text-sm uppercase tracking-widest mb-2">Usuarios registrados</p>
            <p class="text-4xl font-bold text-blue-600">{{ users.length }}</p>
            <p class="text-xs text-gray-400 mt-2">{{ adminUsers }} administradores</p>
          </div>
          <div class="bg-white rounded-2xl p-6 border border-gray-100 shadow-sm">
            <p class="text-gray-500 text-sm uppercase tracking-widest mb-2">Categorías</p>
            <p class="text-4xl font-bold text-purple-600">{{ categories.length }}</p>
            <p class="text-xs text-gray-400 mt-2">Tipos disponibles</p>
          </div>
        </div>

        <!-- Productos con bajo stock -->
        <div class="bg-white rounded-2xl p-6 border border-gray-100 shadow-sm">
          <h2 class="font-bold text-lg text-luxury-black mb-4">⚠️ Productos con bajo stock</h2>
          <div v-if="lowStockList.length === 0" class="text-gray-500 text-sm py-4">Todos los productos tienen stock adecuado</div>
          <div v-else class="space-y-2">
            <div v-for="prod in lowStockList" :key="prod.id" class="flex items-center justify-between p-3 bg-orange-50 rounded-lg border border-orange-100">
              <div>
                <p class="font-semibold text-luxury-black">{{ prod.nombre }}</p>
                <p class="text-xs text-gray-500">{{ prod.categoria }}</p>
              </div>
              <div class="text-right">
                <p class="text-2xl font-bold text-orange-600">{{ prod.stock }}</p>
                <p class="text-xs text-gray-500">unidades</p>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- PRODUCTOS -->
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

          <div v-else class="space-y-4 max-h-[600px] overflow-y-auto">
            <article
              v-for="producto in filteredProducts"
              :key="producto.id"
              class="border border-gray-100 rounded-xl p-4 hover:shadow-md transition cursor-pointer"
              @click="editProduct(producto)"
            >
              <div class="flex gap-4">
                <div class="w-20 h-24 bg-gray-100 rounded overflow-hidden flex-shrink-0">
                  <img
                    v-if="producto.imagen"
                    :src="producto.imagen"
                    :alt="producto.nombre"
                    class="w-full h-full object-cover"
                    loading="lazy"
                  >
                </div>
                <div class="flex-1">
                  <h3 class="font-semibold text-luxury-black">{{ producto.nombre }}</h3>
                  <p class="text-xs uppercase tracking-widest text-gray-400">{{ producto.categoria }}</p>
                  <div class="flex items-center gap-4 mt-2">
                    <span class="font-bold text-luxury-gold">€{{ producto.precio }}</span>
                    <span class="text-sm" :class="producto.stock > 5 ? 'text-green-600' : 'text-orange-600'">Stock: {{ producto.stock }}</span>
                    <span :class="producto.activo ? 'text-green-600 bg-green-50' : 'text-red-600 bg-red-50'" class="text-xs font-semibold px-2 py-1 rounded">
                      {{ producto.activo ? '✓ Activo' : '✗ Inactivo' }}
                    </span>
                  </div>
                </div>
              </div>
            </article>
          </div>
        </div>

        <div class="bg-white rounded-2xl shadow-sm border border-gray-100 p-6 h-fit">
          <h2 class="font-serif text-2xl text-luxury-black mb-4">{{ form.id ? '✏️ Editar' : '➕ Nuevo producto' }}</h2>

          <form class="space-y-4" @submit.prevent="saveProduct">
            <div>
              <label class="text-xs uppercase tracking-widest text-gray-500 block mb-1">Nombre</label>
              <input v-model="form.nombre" required type="text" class="w-full px-4 py-2 border border-gray-200 rounded focus:outline-none focus:border-luxury-gold">
            </div>

            <div>
              <label class="text-xs uppercase tracking-widest text-gray-500 block mb-1">Categoría</label>
              <input v-model="form.categoria" required type="text" class="w-full px-4 py-2 border border-gray-200 rounded focus:outline-none focus:border-luxury-gold">
            </div>

            <div class="grid grid-cols-2 gap-3">
              <div>
                <label class="text-xs uppercase tracking-widest text-gray-500 block mb-1">Precio (€)</label>
                <input v-model.number="form.precio" required type="number" min="0" step="0.01" class="w-full px-4 py-2 border border-gray-200 rounded">
              </div>
              <div>
                <label class="text-xs uppercase tracking-widest text-gray-500 block mb-1">Stock</label>
                <input v-model.number="form.stock" required type="number" min="0" class="w-full px-4 py-2 border border-gray-200 rounded">
              </div>
            </div>

            <div>
              <label class="text-xs uppercase tracking-widest text-gray-500 block mb-1">Imagen URL</label>
              <input v-model="form.imagen" type="text" class="w-full px-4 py-2 border border-gray-200 rounded focus:outline-none focus:border-luxury-gold" placeholder="/images/producto.jpg">
            </div>

            <div>
              <label class="text-xs uppercase tracking-widest text-gray-500 block mb-1">Descripción</label>
              <textarea v-model="form.descripcion" rows="2" class="w-full px-4 py-2 border border-gray-200 rounded"></textarea>
            </div>

            <label class="flex items-center gap-2 cursor-pointer">
              <input v-model="form.activo" type="checkbox" class="rounded border-gray-300 w-4 h-4">
              <span class="text-sm text-gray-600 uppercase tracking-widest">Producto activo</span>
            </label>

            <div class="flex gap-2">
              <button
                type="submit"
                :disabled="saving"
                class="flex-1 bg-luxury-black text-white py-2 uppercase text-xs tracking-widest rounded disabled:opacity-50 hover:bg-luxury-gold hover:text-luxury-black transition font-bold"
              >
                {{ saving ? '⏳ Guardando...' : '✓ Guardar' }}
              </button>
              <button
                type="button"
                class="px-4 py-2 border border-gray-200 rounded text-xs uppercase tracking-widest hover:bg-gray-50"
                @click="resetForm"
              >
                Limpiar
              </button>
            </div>

            <div v-if="message" :class="messageOk ? 'bg-green-50 text-green-700 border-green-200' : 'bg-red-50 text-red-700 border-red-200'" class="p-3 rounded border text-sm">
              {{ messageOk ? '✓' : '✗' }} {{ message }}
            </div>
          </form>

          <div v-if="form.id" class="mt-6 pt-6 border-t border-gray-200">
            <button
              @click="removeProduct(form.id)"
              class="w-full px-4 py-2 border border-red-200 text-red-600 rounded text-xs uppercase tracking-widest hover:bg-red-50"
            >
              Eliminar producto
            </button>
          </div>
        </div>
      </div>

      <!-- USUARIOS -->
      <div v-if="activeTab === 'users'" class="bg-white rounded-2xl shadow-sm border border-gray-100 p-6">
        <div class="flex flex-col md:flex-row gap-4 mb-6">
          <input
            v-model="userSearch"
            type="text"
            placeholder="Buscar por nombre o email"
            class="w-full px-4 py-2 border border-gray-200 rounded focus:outline-none focus:border-luxury-gold"
          >
          <select v-model="userFilter" class="px-3 py-2 border border-gray-200 rounded text-sm">
            <option value="all">Todos ({{ users.length }})</option>
            <option value="admin">Admin ({{ adminUsers }})</option>
            <option value="active">Activos</option>
          </select>
        </div>

        <div v-if="loadingUsers" class="py-10 text-center text-gray-500">Cargando usuarios...</div>
        <div v-else-if="filteredUsers.length === 0" class="py-10 text-center text-gray-500">Sin resultados.</div>

        <div v-else class="space-y-3">
          <article
            v-for="user in filteredUsers"
            :key="user.id"
            class="border border-gray-100 rounded-xl p-4 flex flex-col md:flex-row md:items-center gap-4 hover:shadow-md transition"
          >
            <div class="flex-1">
              <div class="flex items-center gap-2 mb-1">
                <p class="font-semibold text-luxury-black">{{ user.nombre || user.email }}</p>
                <span v-if="user.isAdmin" class="text-xs bg-luxury-gold/10 text-luxury-gold px-2 py-1 rounded font-bold">👑 ADMIN</span>
              </div>
              <p class="text-xs text-gray-500">{{ user.email }}</p>
              <p class="text-xs text-gray-400">Unido: {{ new Date(user.createdAt || Date.now()).toLocaleDateString() }}</p>
            </div>
            <div class="flex items-center gap-2">
              <button
                class="px-3 py-2 text-xs uppercase tracking-widest border transition rounded"
                :class="user.isAdmin ? 'border-red-200 text-red-600 hover:bg-red-50' : 'border-luxury-gold/30 text-luxury-gold hover:bg-luxury-gold/5'"
                @click="toggleAdmin(user)"
              >
                {{ user.isAdmin ? 'Quitar admin' : 'Hacer admin' }}
              </button>
              <button
                class="px-3 py-2 text-xs uppercase tracking-widest border transition rounded"
                :class="user.activo ? 'border-green-200 text-green-600 hover:bg-green-50' : 'border-gray-200 text-gray-600 hover:bg-gray-50'"
                @click="toggleActive(user)"
              >
                {{ user.activo ? '✓ Activo' : '✗ Inactivo' }}
              </button>
            </div>
          </article>
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, computed, onMounted, inject } from 'vue'
import { apiClient } from '../lib/api'
import { useAuthStore } from '../stores/auth'

const authStore = useAuthStore()
const toast = inject('toast')

const tabs = [
  { id: 'dashboard', label: '📊 Dashboard' },
  { id: 'products', label: '📦 Productos' },
  { id: 'users', label: '👥 Usuarios' }
]

const activeTab = ref('dashboard')
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

// Datos calculados para el dashboard
const activeProducts = computed(() => products.value.filter(p => p.activo).length)
const lowStockProducts = computed(() => products.value.filter(p => p.stock < 5).length)
const lowStockList = computed(() => products.value.filter(p => p.stock < 5).sort((a, b) => a.stock - b.stock))
const categories = computed(() => [...new Set(products.value.map(p => p.categoria))])
const adminUsers = computed(() => users.value.filter(u => u.isAdmin).length)

const loadProducts = async () => {
  loading.value = true
  try {
    const res = await apiClient.get('/api/productos')
    products.value = Array.isArray(res.data) ? res.data : []
  } catch (e) {
    message.value = 'No se pudieron cargar los productos.'
    messageOk.value = false
    if (toast) toast.show('✗ Error al cargar productos', 'error', 2500)
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
    const matchesSearch = !term || `${u.nombre || ''} ${u.apellido || ''}`.toLowerCase().includes(term) || (u.email || '').toLowerCase().includes(term)
    const matchesFilter = userFilter.value === 'all'
      || (userFilter.value === 'admin' && u.isAdmin)
      || (userFilter.value === 'active' && u.activo)
    return matchesSearch && matchesFilter
  })
})

const editProduct = (producto) => {
  form.value = { ...producto }
  message.value = ''
  // Scroll al form
  setTimeout(() => {
    document.querySelector('form')?.scrollIntoView({ behavior: 'smooth' })
  }, 100)
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
    if (toast) toast.show('✗ Error al cargar usuarios', 'error', 2500)
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
    message.value = `Usuario ${res.data.isAdmin ? 'promovido a admin' : 'revertido a usuario'}`
    messageOk.value = true
    if (toast) {
      const msg = res.data.isAdmin ? `✓ Usuario promovido a administrador` : `✓ Usuario revertido a usuario`
      toast.show(msg, 'success', 2500)
    }
  } catch (e) {
    messageOk.value = false
    message.value = 'No se pudo actualizar el usuario.'
    if (toast) toast.show('✗ Error al actualizar usuario', 'error', 2500)
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
      message.value = '✓ Producto actualizado con éxito.'
      if (toast) toast.show(`✓ "${res.data.nombre}" actualizado correctamente`, 'success', 2500)
    } else {
      const res = await apiClient.post('/api/productos', form.value)
      products.value = [res.data, ...products.value]
      message.value = '✓ Producto creado con éxito.'
      if (toast) toast.show(`✓ "${res.data.nombre}" creado correctamente`, 'success', 2500)
    }
    messageOk.value = true
    resetForm(true)
  } catch (e) {
    messageOk.value = false
    message.value = '✗ Error al guardar el producto.'
    if (toast) toast.show('✗ Error al guardar producto', 'error', 2500)
  } finally {
    saving.value = false
  }
}

const removeProduct = async (id) => {
  if (!confirm('¿Estás seguro? Esta acción no se puede deshacer.')) return
  try {
    const productName = products.value.find(p => p.id === id)?.nombre || 'Producto'
    await apiClient.delete(`/api/productos/${id}`)
    products.value = products.value.filter(p => p.id !== id)
    resetForm()
    message.value = '✓ Producto eliminado.'
    messageOk.value = true
    if (toast) toast.show(`✓ "${productName}" eliminado correctamente`, 'warning', 2500)
  } catch (e) {
    messageOk.value = false
    message.value = '✗ No se pudo eliminar el producto.'
    if (toast) toast.show('✗ Error al eliminar producto', 'error', 2500)
  }
}

onMounted(loadProducts)
</script>
