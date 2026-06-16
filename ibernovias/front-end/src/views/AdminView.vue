<template>
  <section class="admin-shell min-h-screen py-8 lg:py-10">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <header class="admin-hero mb-8 lg:mb-10">
        <div class="flex flex-col gap-6 lg:flex-row lg:items-end lg:justify-between">
          <div class="max-w-3xl">
            <p class="admin-kicker">Gestión privada</p>
            <h1 class="admin-title">Panel Administrativo</h1>
            <p class="admin-subtitle">Una vista limpia para gestionar catálogo, usuarios, eventos y solicitudes con más claridad y menos ruido visual.</p>
          </div>
          <button
            @click="authStore.logout()"
            class="admin-logout"
          >
            Cerrar sesión
          </button>
        </div>

        <!-- Tabs de navegación -->
        <div class="admin-tabs">
          <button
            v-for="tab in tabs"
            :key="tab.id"
            @click="activeTab = tab.id"
            :class="activeTab === tab.id ? 'admin-tab-active' : 'admin-tab'"
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
          <div class="admin-stat">
            <p class="text-gray-500 text-sm uppercase tracking-widest mb-2">Productos totales</p>
            <p class="text-4xl font-bold text-luxury-black">{{ products.length }}</p>
            <p class="text-xs text-gray-400 mt-2">{{ activeProducts }} activos</p>
          </div>
          <div class="admin-stat">
            <p class="text-gray-500 text-sm uppercase tracking-widest mb-2">Stock bajo (&lt;5)</p>
            <p class="text-4xl font-bold text-gray-900">{{ lowStockProducts }}</p>
            <p class="text-xs text-gray-400 mt-2">Requieren atención</p>
          </div>
          <div class="admin-stat">
            <p class="text-gray-500 text-sm uppercase tracking-widest mb-2">Usuarios registrados</p>
            <p class="text-4xl font-bold text-gray-900">{{ users.length }}</p>
            <p class="text-xs text-gray-400 mt-2">{{ adminUsers }} administradores</p>
          </div>
          <div class="admin-stat">
            <p class="text-gray-500 text-sm uppercase tracking-widest mb-2">Categorías</p>
            <p class="text-4xl font-bold text-gray-900">{{ categories.length }}</p>
            <p class="text-xs text-gray-400 mt-2">Tipos disponibles</p>
          </div>
        </div>

        <!-- Productos con bajo stock -->
        <div class="admin-card">
          <h2 class="admin-section-title">Productos con bajo stock</h2>
          <div v-if="lowStockList.length === 0" class="text-gray-500 text-sm py-4">Todos los productos tienen stock adecuado</div>
          <div v-else class="space-y-2">
            <div v-for="prod in lowStockList" :key="prod.id" class="admin-list-row">
              <div>
                <p class="font-semibold text-luxury-black">{{ prod.nombre }}</p>
                <p class="text-xs text-gray-500">{{ prod.categoria }}</p>
              </div>
              <div class="text-right">
                <p class="text-2xl font-bold text-gray-900">{{ prod.stock }}</p>
                <p class="text-xs text-gray-500">unidades</p>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- PRODUCTOS -->
      <div v-if="activeTab === 'products'" class="grid grid-cols-1 lg:grid-cols-[2fr_1fr] gap-8">
        <div class="admin-card">
          <div class="flex flex-col md:flex-row gap-4 mb-6">
            <input
              v-model="search"
              type="text"
              placeholder="Buscar por nombre o categoría"
              class="admin-input w-full"
            >
            <select v-model="statusFilter" class="admin-input px-3 py-2 text-sm">
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
              class="admin-list-row cursor-pointer"
              @click="editProduct(producto)"
            >
              <div class="flex gap-4">
                <div class="w-20 h-24 bg-gray-100 rounded overflow-hidden flex-shrink-0">
                  <img
                    v-if="producto.imagen"
                    :src="getImageUrl(producto.imagen)"
                    :alt="producto.nombre"
                    class="w-full h-full object-cover"
                    loading="lazy"
                  >
                </div>
                <div class="flex-1">
                  <h3 class="font-semibold text-luxury-black">{{ producto.nombre }}</h3>
                  <p class="text-xs uppercase tracking-widest text-gray-400">{{ [producto.familia, producto.categoria].filter(Boolean).join(' · ') }}</p>
                  <div class="flex items-center gap-4 mt-2">
                    <span class="font-bold text-gray-900">€{{ producto.precio }}</span>
                    <span class="text-sm text-gray-600">Stock: {{ producto.stock }}</span>
                    <span :class="producto.activo ? 'text-gray-700 bg-gray-100' : 'text-gray-500 bg-gray-50'" class="text-xs font-semibold px-2 py-1 rounded">
                      {{ producto.activo ? 'Activo' : 'Inactivo' }}
                    </span>
                  </div>
                </div>
              </div>
            </article>
          </div>
        </div>

        <div class="admin-card h-fit">
          <h2 class="admin-section-title">{{ form.id ? 'Editar producto' : 'Nuevo producto' }}</h2>

          <form class="space-y-4" @submit.prevent="saveProduct">
            <div>
              <label class="text-xs uppercase tracking-widest text-gray-500 block mb-1">Nombre</label>
              <input v-model="form.nombre" required type="text" class="admin-input w-full">
            </div>

            <div>
              <label class="text-xs uppercase tracking-widest text-gray-500 block mb-1">Familia</label>
              <select
                v-model="form.familia"
                class="admin-input w-full bg-white"
              >
                <option value="">(Sin familia)</option>
                <option v-for="fam in selectableFamilies" :key="fam" :value="fam">{{ fam }}</option>
              </select>
            </div>

            <div>
              <label class="text-xs uppercase tracking-widest text-gray-500 block mb-1">Categoría</label>
              <select
                v-model="form.categoria"
                required
                class="admin-input w-full bg-white"
              >
                <option disabled value="">Selecciona una categoría</option>
                <option v-for="cat in selectableCategories" :key="cat" :value="cat">{{ cat }}</option>
              </select>
            </div>

            <div class="grid grid-cols-2 gap-3">
              <div>
                <label class="text-xs uppercase tracking-widest text-gray-500 block mb-1">Precio (€)</label>
                <input v-model.number="form.precio" required type="number" min="0" step="0.01" class="admin-input w-full">
              </div>
              <div>
                <label class="text-xs uppercase tracking-widest text-gray-500 block mb-1">Stock</label>
                <input v-model.number="form.stock" required type="number" min="0" class="admin-input w-full">
              </div>
            </div>

            <div>
              <label class="text-xs uppercase tracking-widest text-gray-500 block mb-1">Imagen (Subir archivo o pegar URL)</label>
              <div class="space-y-2">
                <input 
                  type="file" 
                  ref="fileInput"
                  @change="handleImageUpload" 
                  accept="image/*" 
                  class="admin-input w-full text-sm"
                >
                <div class="text-center text-xs text-gray-400">— O —</div>
                <input 
                  v-model="form.imagen"
                  type="text" 
                  placeholder="Pegar URL de la imagen (ej: https://ibernovia.es/images/...)" 
                  class="admin-input w-full"
                >
              </div>
              <div v-if="form.imagen && form.imagen.startsWith('data:')" class="mt-2 text-xs text-gray-600">
                ✓ Imagen cargada localmente: {{ form.imagenNombre }}
              </div>
              <div v-else-if="form.imagen" class="mt-2 text-xs text-gray-600 truncate">
                ✓ Ruta/URL de imagen activa: {{ form.imagen }}
              </div>
            </div>

            <div>
              <label class="text-xs uppercase tracking-widest text-gray-500 block mb-1">Descripción</label>
              <textarea v-model="form.descripcion" rows="2" class="admin-input w-full"></textarea>
            </div>

            <label class="flex items-center gap-2 cursor-pointer">
              <input v-model="form.activo" type="checkbox" class="rounded border-gray-300 w-4 h-4">
              <span class="text-sm text-gray-600 uppercase tracking-widest">Producto activo</span>
            </label>

            <div class="flex gap-2">
              <button
                type="submit"
                :disabled="saving"
                class="admin-primary-btn flex-1"
              >
                {{ saving ? '⏳ Guardando...' : '✓ Guardar' }}
              </button>
              <button
                type="button"
                class="admin-secondary-btn px-4 py-2 text-xs uppercase tracking-widest"
                @click="resetForm"
              >
                Limpiar
              </button>
            </div>

            <div v-if="message" :class="messageOk ? 'bg-green-50 text-green-700 border-green-200' : 'bg-red-50 text-red-700 border-red-200'" class="p-3 rounded-xl border text-sm">
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
      <div v-if="activeTab === 'users'" class="admin-card">
        <div class="flex flex-col md:flex-row gap-4 mb-6">
          <input
            v-model="userSearch"
            type="text"
            placeholder="Buscar por nombre o email"
            class="admin-input w-full"
          >
          <select v-model="userFilter" class="admin-input px-3 py-2 text-sm">
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
            class="admin-list-row flex flex-col md:flex-row md:items-center gap-4"
          >
            <div class="flex-1">
              <div class="flex items-center gap-2 mb-1">
                <p class="font-semibold text-luxury-black">{{ user.nombre || user.email }}</p>
                <span v-if="user.isAdmin" class="text-xs bg-gray-100 text-gray-700 px-2 py-1 rounded font-bold">ADMIN</span>
                <span v-if="user.isBusiness" class="text-xs bg-amber-100 text-amber-800 px-2 py-1 rounded font-bold">EMPRESA</span>
              </div>
              <p class="text-xs text-gray-500">{{ user.email }}</p>
              <p class="text-xs text-gray-400">Unido: {{ new Date(user.createdAt || Date.now()).toLocaleDateString() }}</p>
            </div>
            <div class="flex items-center gap-2">
              <button
                class="admin-secondary-btn px-3 py-2 text-xs uppercase tracking-widest border transition rounded"
                :class="user.isBusiness ? 'border-amber-200 text-amber-600 hover:bg-amber-50' : 'border-gray-300 text-gray-700 hover:bg-gray-50'"
                @click="toggleBusiness(user)"
              >
                {{ user.isBusiness ? 'Quitar Empresa' : 'Hacer Empresa' }}
              </button>
              <button
                class="admin-secondary-btn px-3 py-2 text-xs uppercase tracking-widest border transition rounded"
                :class="user.isAdmin ? 'border-red-200 text-red-600 hover:bg-red-50' : 'border-gray-300 text-gray-700 hover:bg-gray-50'"
                @click="toggleAdmin(user)"
              >
                {{ user.isAdmin ? 'Quitar admin' : 'Hacer admin' }}
              </button>
              <button
                class="admin-secondary-btn px-3 py-2 text-xs uppercase tracking-widest border transition rounded"
                :class="user.activo ? 'border-gray-300 text-gray-700 hover:bg-gray-50' : 'border-gray-200 text-gray-500 hover:bg-gray-50'"
                @click="toggleActive(user)"
              >
                {{ user.activo ? '✓ Activo' : '✗ Inactivo' }}
              </button>
            </div>
          </article>
        </div>
      </div>

      <!-- EVENTOS -->
      <div v-if="activeTab === 'events'" class="grid grid-cols-1 lg:grid-cols-[2fr_1fr] gap-8">
        <div class="admin-card">
          <div class="flex items-center justify-between mb-6">
            <div>
              <h2 class="admin-section-title">Agenda de eventos</h2>
              <p class="text-xs text-gray-500 mt-1">Total: {{ eventos.length }} eventos</p>
            </div>
            <button
              @click="resetEventoForm"
              class="admin-secondary-btn px-4 py-2 text-xs uppercase tracking-widest"
            >
              ➕ Nuevo evento
            </button>
          </div>

          <div v-if="loadingEventos" class="py-10 text-center text-gray-500">Cargando eventos...</div>
          <div v-else-if="eventos.length === 0" class="py-10 text-center text-gray-500">Sin eventos registrados</div>

          <div v-else class="space-y-3 max-h-[600px] overflow-y-auto">
              <article
              v-for="evento in eventos"
              :key="evento.id"
              @click="editEvento(evento)"
              :class="eventoForm.id === evento.id ? 'bg-gray-100 border-gray-300' : 'hover:shadow-md'"
              class="admin-list-row cursor-pointer transition"
            >
              <div class="flex items-start justify-between gap-4">
                <div class="flex-1">
                  <p class="text-xs uppercase tracking-widest text-gray-500 font-semibold mb-1">{{ formatDateForDisplay(evento.fecha) }}</p>
                  <h3 class="font-semibold text-luxury-black text-sm">{{ evento.titulo }}</h3>
                  <p class="text-xs text-gray-600 mt-1">{{ evento.descripcion }}</p>
                  <p class="text-xs text-gray-500 mt-2 uppercase">📍 {{ evento.lugar }}</p>
                </div>
              </div>
            </article>
          </div>
        </div>

        <!-- Formulario de eventos -->
        <div class="admin-card h-fit">
          <h2 class="admin-section-title">{{ eventoForm.id ? 'Editar evento' : 'Nuevo evento' }}</h2>

          <form class="space-y-4" @submit.prevent="saveEvento">
            <div>
              <label class="text-xs uppercase tracking-widest text-gray-500 block mb-1">Título *</label>
              <input 
                v-model="eventoForm.titulo" 
                required 
                type="text" 
                placeholder="Ej: Presentación Colección" 
                class="admin-input w-full"
              >
            </div>

            <div>
              <label class="text-xs uppercase tracking-widest text-gray-500 block mb-1">Fecha y Hora *</label>
              <input 
                v-model="eventoForm.fecha" 
                required 
                type="datetime-local" 
                class="admin-input w-full"
              >
            </div>

            <div>
              <label class="text-xs uppercase tracking-widest text-gray-500 block mb-1">Lugar *</label>
              <input 
                v-model="eventoForm.lugar" 
                required 
                type="text" 
                placeholder="Ej: Atelier Ibernovia · Andújar" 
                class="admin-input w-full"
              >
            </div>

            <div>
              <label class="text-xs uppercase tracking-widest text-gray-500 block mb-1">Descripción</label>
              <textarea 
                v-model="eventoForm.descripcion" 
                rows="3" 
                placeholder="Detalles del evento..."
                class="admin-input w-full"
              ></textarea>
            </div>

            <div class="flex gap-2">
              <button
                type="submit"
                :disabled="savingEvento"
                class="admin-primary-btn flex-1"
              >
                {{ savingEvento ? '⏳ Guardando...' : '✓ Guardar' }}
              </button>
              <button
                type="button"
                class="admin-secondary-btn px-4 py-2 text-xs uppercase tracking-widest"
                @click="resetEventoForm"
              >
                Limpiar
              </button>
            </div>

            <div v-if="eventoMessage" :class="eventoMessageOk ? 'bg-green-50 text-green-700 border-green-200' : 'bg-red-50 text-red-700 border-red-200'" class="p-3 rounded border text-sm">
              {{ eventoMessageOk ? '✓' : '✗' }} {{ eventoMessage }}
            </div>
          </form>

          <div v-if="eventoForm.id" class="mt-6 pt-6 border-t border-gray-200">
            <button
              @click="removeEvento(eventoForm.id)"
              class="w-full px-4 py-2 border border-red-200 text-red-600 rounded text-xs uppercase tracking-widest hover:bg-red-50"
            >
              Eliminar evento
            </button>
          </div>
        </div>
      </div>

      <!-- SOLICITUDES DE PRESUPUESTO -->
      <div v-if="activeTab === 'quote-requests'" class="grid grid-cols-1 lg:grid-cols-[2fr_1fr] gap-8">
        <div class="admin-card">
          <div class="flex items-center justify-between mb-6">
            <div>
              <h2 class="admin-section-title">Solicitudes de presupuesto</h2>
              <p class="text-xs text-gray-500 mt-1">Total: {{ solicitudes.length }} | Pendientes: {{ solicitudes.filter(s => s.estado === 'pendiente').length }}</p>
            </div>
            <button
              @click="loadSolicitudes"
              class="admin-secondary-btn px-4 py-2 text-xs uppercase tracking-widest"
            >
              🔄 Actualizar
            </button>
          </div>

          <div class="flex gap-2 mb-6">
            <button
              @click="solicitudFilter = 'all'; loadSolicitudes()"
              :class="solicitudFilter === 'all' ? 'bg-luxury-black text-white' : 'border border-gray-200 text-gray-600 hover:bg-gray-50'"
              class="admin-secondary-btn px-4 py-2 text-xs uppercase tracking-widest"
            >
              Todas
            </button>
            <button
              @click="solicitudFilter = 'pendiente'; loadSolicitudes()"
              :class="solicitudFilter === 'pendiente' ? 'bg-luxury-black text-white' : 'border border-gray-200 text-gray-600 hover:bg-gray-50'"
                class="admin-secondary-btn px-4 py-2 text-xs uppercase tracking-widest"
            >
              Pendientes
            </button>
            <button
              @click="solicitudFilter = 'respondida'; loadSolicitudes()"
              :class="solicitudFilter === 'respondida' ? 'bg-luxury-black text-white' : 'border border-gray-200 text-gray-600 hover:bg-gray-50'"
                class="admin-secondary-btn px-4 py-2 text-xs uppercase tracking-widest"
            >
              Respondidas
            </button>
          </div>

          <div v-if="loadingSolicitudes" class="py-10 text-center text-gray-500">Cargando solicitudes...</div>
          <div v-else-if="solicitudes.length === 0" class="py-10 text-center text-gray-500">Sin solicitudes</div>

          <div v-else class="space-y-3 max-h-[600px] overflow-y-auto">
            <article
              v-for="sol in solicitudes"
              :key="sol.id"
              @click="selectedSolicitud = sol"
              :class="selectedSolicitud?.id === sol.id ? 'bg-gray-100 border-gray-300' : 'hover:shadow-md'"
              class="admin-list-row cursor-pointer transition"
            >
              <div class="flex items-start justify-between gap-4">
                <div class="flex-1">
                  <div class="flex items-center gap-2 mb-1">
                    <p class="font-semibold text-luxury-black">{{ sol.empresaNombre }}</p>
                    <span :class="sol.estado === 'pendiente' ? 'bg-gray-100 text-gray-700' : 'bg-gray-200 text-gray-800'" class="text-xs font-semibold px-2 py-1 rounded">
                      {{ sol.estado === 'pendiente' ? 'Pendiente' : 'Respondida' }}
                    </span>
                  </div>
                  <p class="text-xs text-gray-600">{{ sol.personaContacto || 'Sin contacto' }}</p>
                  <p class="text-xs text-gray-500 mt-1">{{ sol.email }}</p>
                  <p class="text-xs text-gray-400 mt-1">{{ new Date(sol.createdAt).toLocaleString('es-ES') }}</p>
                </div>
              </div>
            </article>
          </div>
        </div>

        <!-- Detalle solicitud -->
        <div v-if="selectedSolicitud" class="admin-card h-fit sticky top-6">
          <div class="flex items-center justify-between mb-4">
            <h2 class="admin-section-title text-xl">Detalle</h2>
            <button
              @click="selectedSolicitud = null"
              class="text-gray-400 hover:text-gray-600 text-xl"
            >
              ✕
            </button>
          </div>

          <div class="space-y-4 pb-4 border-b border-gray-200">
            <div>
              <p class="text-xs uppercase tracking-widest text-gray-500 mb-1">Empresa</p>
              <p class="font-semibold text-luxury-black">{{ selectedSolicitud.empresaNombre }}</p>
            </div>

            <div>
              <p class="text-xs uppercase tracking-widest text-gray-500 mb-1">Contacto</p>
              <p class="text-gray-700">{{ selectedSolicitud.personaContacto || '-' }}</p>
            </div>

            <div>
              <p class="text-xs uppercase tracking-widest text-gray-500 mb-1">Email</p>
              <a :href="`mailto:${selectedSolicitud.email}`" class="text-gray-900 hover:text-gray-600 transition">
                {{ selectedSolicitud.email }}
              </a>
            </div>

            <div>
              <p class="text-xs uppercase tracking-widest text-gray-500 mb-1">Teléfono</p>
              <a :href="`tel:${selectedSolicitud.telefono}`" class="text-gray-900 hover:text-gray-600 transition">
                {{ selectedSolicitud.telefono }}
              </a>
            </div>

            <div>
              <p class="text-xs uppercase tracking-widest text-gray-500 mb-1">Productos Solicitados</p>
              <div class="bg-gray-50 p-3 rounded text-sm text-gray-700 max-h-40 overflow-y-auto">
                {{ selectedSolicitud.productosSolicitados }}
              </div>
            </div>

            <div v-if="selectedSolicitud.notas">
              <p class="text-xs uppercase tracking-widest text-gray-500 mb-1">Mensaje</p>
              <p class="bg-blue-50 text-blue-900 p-3 rounded text-sm">{{ selectedSolicitud.notas }}</p>
            </div>

            <div>
              <p class="text-xs uppercase tracking-widest text-gray-500 mb-1">Fecha de Solicitud</p>
              <p class="text-gray-700">{{ new Date(selectedSolicitud.createdAt).toLocaleString('es-ES') }}</p>
            </div>
          </div>

          <div class="mt-6 space-y-2">
            <div v-if="selectedSolicitud.estado === 'pendiente'">
              <button
                @click="updateEstadoSolicitud(selectedSolicitud.id, 'respondida')"
                class="w-full px-4 py-2 bg-gray-900 text-white rounded text-xs uppercase tracking-widest hover:bg-gray-700 transition font-bold"
              >
                ✓ Marcar como Respondida
              </button>
            </div>
            <div v-else>
              <button
                @click="updateEstadoSolicitud(selectedSolicitud.id, 'pendiente')"
                class="w-full px-4 py-2 border border-gray-300 text-gray-700 rounded text-xs uppercase tracking-widest hover:bg-gray-50 transition"
              >
                ⏳ Volver a Pendiente
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- MENSAJES DE CONTACTO -->
      <div v-if="activeTab === 'messages'" class="grid grid-cols-1 lg:grid-cols-[2fr_1fr] gap-8">
        <div class="admin-card">
          <div class="flex items-center justify-between mb-6">
            <div>
              <h2 class="admin-section-title">Solicitudes de acceso empresarial</h2>
              <p class="text-xs text-gray-500 mt-1">Total: {{ messages.length }} | Sin leer: {{ filteredMessages.filter(m => !m.leido).length }}</p>
            </div>
            <button
              @click="loadMessages"
              class="admin-secondary-btn px-4 py-2 text-xs uppercase tracking-widest"
            >
              🔄 Actualizar
            </button>
          </div>

          <div class="flex gap-2 mb-6">
            <button
              @click="messageFilter = 'all'"
              :class="messageFilter === 'all' ? 'bg-luxury-black text-white' : 'border border-gray-200 text-gray-600 hover:bg-gray-50'"
              class="admin-secondary-btn px-4 py-2 text-xs uppercase tracking-widest"
            >
              Todos
            </button>
            <button
              @click="messageFilter = 'unread'"
              :class="messageFilter === 'unread' ? 'bg-luxury-black text-white' : 'border border-gray-200 text-gray-600 hover:bg-gray-50'"
              class="admin-secondary-btn px-4 py-2 text-xs uppercase tracking-widest"
            >
              Sin leer
            </button>
          </div>

          <div v-if="loadingMessages" class="py-10 text-center text-gray-500">Cargando mensajes...</div>
          <div v-else-if="filteredMessages.length === 0" class="py-10 text-center text-gray-500">Sin mensajes</div>

          <div v-else class="space-y-3 max-h-[600px] overflow-y-auto">
            <article
              v-for="msg in filteredMessages"
              :key="msg.id"
              @click="selectedMessage = msg"
              :class="selectedMessage?.id === msg.id ? 'bg-gray-100 border-gray-300' : 'hover:shadow-md'"
              class="admin-list-row cursor-pointer transition flex items-start justify-between"
            >
              <div class="flex-1">
                <div class="flex items-center gap-2 mb-1">
                  <p class="font-semibold text-luxury-black">{{ msg.nombre }}</p>
                  <span v-if="msg.respondido" class="text-xs bg-gray-100 text-gray-700 px-2 py-0.5 rounded">Respondido</span>
                </div>
                <p class="text-xs text-gray-500">{{ msg.email }}</p>
                <p class="text-sm text-gray-700 mt-2">{{ msg.asunto }}</p>
                <p class="text-xs text-gray-400 mt-2">{{ new Date(msg.fechaCreacion).toLocaleString('es-ES') }}</p>
              </div>
              <span v-if="msg.empresa" class="text-xs bg-gray-100 text-gray-700 px-2 py-1 rounded ml-2 flex-shrink-0">{{ msg.empresa }}</span>
            </article>
          </div>
        </div>

        <!-- Detalle del mensaje seleccionado -->
        <div v-if="selectedMessage" class="admin-card h-fit sticky top-6">
          <div class="flex items-center justify-between mb-4">
            <h2 class="admin-section-title text-xl">Detalle</h2>
            <button
              @click="selectedMessage = null"
              class="text-gray-400 hover:text-gray-600 text-xl"
            >
              ✕
            </button>
          </div>

          <div class="space-y-4 pb-4 border-b border-gray-200">
            <div>
              <p class="text-xs uppercase tracking-widest text-gray-500 mb-1">Nombre</p>
              <p class="font-semibold text-luxury-black">{{ selectedMessage.nombre }}</p>
            </div>

            <div>
              <p class="text-xs uppercase tracking-widest text-gray-500 mb-1">Email</p>
              <a :href="`mailto:${selectedMessage.email}`" class="text-gray-900 hover:underline text-sm font-semibold">{{ selectedMessage.email }}</a>
            </div>

            <div v-if="selectedMessage.empresa">
              <p class="text-xs uppercase tracking-widest text-gray-500 mb-1">Empresa</p>
              <p class="text-sm">{{ selectedMessage.empresa }}</p>
            </div>

            <div v-if="selectedMessage.cif">
              <p class="text-xs uppercase tracking-widest text-gray-500 mb-1">CIF</p>
              <p class="font-mono text-sm">{{ selectedMessage.cif }}</p>
            </div>

            <div v-if="selectedMessage.telefono">
              <p class="text-xs uppercase tracking-widest text-gray-500 mb-1">Teléfono</p>
              <a :href="`tel:${selectedMessage.telefono}`" class="text-gray-900 hover:underline text-sm font-semibold">{{ selectedMessage.telefono }}</a>
            </div>

            <div v-if="selectedMessage.provincia">
              <p class="text-xs uppercase tracking-widest text-gray-500 mb-1">Provincia</p>
              <p class="text-sm">{{ selectedMessage.provincia }}</p>
            </div>

            <div>
              <p class="text-xs uppercase tracking-widest text-gray-500 mb-1">Asunto</p>
              <p class="text-sm font-semibold">{{ selectedMessage.asunto }}</p>
            </div>

            <div>
              <p class="text-xs uppercase tracking-widest text-gray-500 mb-1">Mensaje</p>
              <p class="text-sm whitespace-pre-wrap text-gray-700 max-h-[200px] overflow-y-auto">{{ selectedMessage.mensaje }}</p>
            </div>

            <div>
              <p class="text-xs uppercase tracking-widest text-gray-500 mb-1">Fecha</p>
              <p class="text-xs text-gray-500">{{ new Date(selectedMessage.fechaCreacion).toLocaleString('es-ES') }}</p>
            </div>
          </div>

          <div class="mt-4 space-y-3">
            <button
              v-if="!selectedMessage.leido"
              @click="markAsRead(selectedMessage.id)"
              class="w-full px-4 py-2 bg-gray-100 text-gray-700 text-xs uppercase tracking-widest rounded hover:bg-gray-200 transition font-bold"
            >
              ✓ Marcar como leído
            </button>

            <div>
              <label class="text-xs uppercase tracking-widest text-gray-500 block mb-2">Agregar nota interna</label>
              <textarea
                v-model="noteText"
                placeholder="Escribir nota..."
                rows="3"
                class="admin-input w-full text-sm resize-none"
              ></textarea>
              <button
                @click="saveNote"
                :disabled="!noteText.trim()"
                class="admin-primary-btn w-full mt-2 text-xs uppercase tracking-widest"
              >
                💾 Guardar nota
              </button>
            </div>

            <div v-if="selectedMessage.notas" class="bg-amber-50 border border-amber-200 rounded p-3">
              <p class="text-xs uppercase tracking-widest text-amber-700 font-bold mb-2">Notas internas:</p>
              <p class="text-sm text-amber-900 whitespace-pre-wrap">{{ selectedMessage.notas }}</p>
            </div>
          </div>
        </div>

        <div v-else class="admin-card flex items-center justify-center text-gray-400 text-sm min-h-[320px]">
          Selecciona un mensaje para ver detalles
        </div>
      </div>
    </div>
  </section>
</template>

<script setup>
import { ref, computed, onMounted, inject } from 'vue'
import { apiClient, getImageUrl } from '../lib/api'
import { useAuthStore } from '../stores/auth'

const authStore = useAuthStore()
const toast = inject('toast')

const tabs = [
  { id: 'dashboard', label: 'Dashboard' },
  { id: 'products', label: 'Productos' },
  { id: 'users', label: 'Usuarios' },
  { id: 'events', label: 'Eventos' },
  { id: 'quote-requests', label: 'Presupuestos' },
  { id: 'messages', label: 'Mensajes' }
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

const messages = ref([])
const loadingMessages = ref(false)
const messageFilter = ref('all')
const selectedMessage = ref(null)
const noteText = ref('')

const eventos = ref([])
const loadingEventos = ref(false)
const savingEvento = ref(false)
const eventoForm = ref({
  id: null,
  titulo: '',
  descripcion: '',
  fecha: '',
  lugar: ''
})
const eventoMessage = ref('')
const eventoMessageOk = ref(true)

const solicitudes = ref([])
const loadingSolicitudes = ref(false)
const solicitudFilter = ref('all')
const selectedSolicitud = ref(null)

const form = ref({
  id: null,
  nombre: '',
  familia: '',
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

const families = computed(() => [...new Set(products.value.map(p => p.familia))])
const predefinedFamilies = ['Novia', 'Novio', 'Fiesta', 'Comunión', 'Arras']
const selectableFamilies = computed(() => {
  const existing = families.value.filter(Boolean)
  return [...new Set([...predefinedFamilies, ...existing])]
})

const categories = computed(() => [...new Set(products.value.map(p => p.categoria))])
const predefinedCategories = []
const selectableCategories = computed(() => {
  const existing = categories.value.filter(Boolean)
  return [...new Set([...predefinedCategories, ...existing])]
})
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
    const matchesSearch = !term
      || p.nombre.toLowerCase().includes(term)
      || (p.familia || '').toLowerCase().includes(term)
      || (p.categoria || '').toLowerCase().includes(term)

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

const handleImageUpload = (event) => {
  const file = event.target.files?.[0]
  if (!file) return
  
  try {
    const reader = new FileReader()
    reader.onload = (e) => {
      const base64 = e.target?.result
      form.value.imagen = base64
      form.value.imagenNombre = file.name
    }
    reader.readAsDataURL(file)
  } catch (error) {
    message.value = '✗ Error al cargar la imagen.'
    messageOk.value = false
  }
}

const resetForm = (keepMessage = false) => {
  form.value = {
    id: null,
    nombre: '',
    familia: '',
    categoria: '',
    precio: 0,
    stock: 0,
    imagen: '',
    imagenNombre: '',
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

const toggleBusiness = async (user) => {
  try {
    const res = await apiClient.put(`/api/admin/users/${user.id}`, {
      isBusiness: !user.isBusiness
    })
    users.value = users.value.map(u => (u.id === user.id ? res.data : u))
    if (toast) {
      const msg = res.data.isBusiness ? `✓ Acceso de empresa activado` : `✓ Acceso de empresa desactivado`
      toast.show(msg, 'success', 2500)
    }
  } catch (e) {
    if (toast) toast.show('✗ Error al actualizar acceso empresarial', 'error', 2500)
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

const loadMessages = async () => {
  loadingMessages.value = true
  try {
    const res = await apiClient.get('/api/contacto/admin/all')
    messages.value = Array.isArray(res.data) ? res.data : []
  } catch (e) {
    messageOk.value = false
    message.value = 'No se pudieron cargar los mensajes.'
    if (toast) toast.show('✗ Error al cargar mensajes', 'error', 2500)
  } finally {
    loadingMessages.value = false
  }
}

const filteredMessages = computed(() => {
  if (messageFilter.value === 'unread') {
    return messages.value.filter(m => !m.leido)
  }
  return messages.value
})

const markAsRead = async (messageId) => {
  try {
    await apiClient.put(`/api/contacto/admin/${messageId}/read`)
    messages.value = messages.value.map(m => 
      m.id === messageId ? { ...m, leido: true } : m
    )
    if (selectedMessage.value?.id === messageId) {
      selectedMessage.value.leido = true
    }
    if (toast) toast.show('✓ Marcado como leído', 'success', 1500)
  } catch (e) {
    if (toast) toast.show('✗ Error al actualizar', 'error', 1500)
  }
}

const saveNote = async () => {
  if (!selectedMessage.value || !noteText.value.trim()) return
  try {
    const res = await apiClient.put(`/api/contacto/admin/${selectedMessage.value.id}/note`, {
      nota: noteText.value
    })
    messages.value = messages.value.map(m => 
      m.id === selectedMessage.value.id ? res.data : m
    )
    selectedMessage.value = res.data
    noteText.value = ''
    if (toast) toast.show('✓ Nota guardada', 'success', 1500)
  } catch (e) {
    if (toast) toast.show('✗ Error al guardar nota', 'error', 1500)
  }
}

// ===== EVENTOS =====
const loadEventos = async () => {
  try {
    loadingEventos.value = true
    const token = localStorage.getItem('token')
    const res = await apiClient.get('/api/admin/eventos', {
      headers: { 'Authorization': `Bearer ${token}` }
    })
    eventos.value = res.data
  } catch (e) {
    if (toast) toast.show('✗ Error al cargar eventos', 'error', 1500)
  } finally {
    loadingEventos.value = false
  }
}

const formatDateForInput = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)

  const year = date.getFullYear()
  const month = String(date.getMonth() + 1).padStart(2, '0')
  const day = String(date.getDate()).padStart(2, '0')
  const hours = String(date.getHours()).padStart(2, '0')
  const minutes = String(date.getMinutes()).padStart(2, '0')

  return `${year}-${month}-${day}T${hours}:${minutes}`
}

const formatDateForDisplay = (dateString) => {
  if (!dateString) return ''
  const date = new Date(dateString)
  return date.toLocaleDateString('es-ES', { 
    day: '2-digit', 
    month: 'short', 
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  }).toUpperCase()
}

const editEvento = (evento) => {
  eventoForm.value = {
    id: evento.id,
    titulo: evento.titulo,
    descripcion: evento.descripcion,
    fecha: formatDateForInput(evento.fecha),
    lugar: evento.lugar
  }
}

const resetEventoForm = () => {
  eventoForm.value = {
    id: null,
    titulo: '',
    descripcion: '',
    fecha: '',
    lugar: ''
  }
  eventoMessage.value = ''
}

const saveEvento = async () => {
  if (!eventoForm.value.titulo || !eventoForm.value.fecha || !eventoForm.value.lugar) {
    eventoMessage.value = 'Título, fecha y lugar son obligatorios'
    eventoMessageOk.value = false
    return
  }

  try {
    savingEvento.value = true
    const token = localStorage.getItem('token')
    const payload = {
      titulo: eventoForm.value.titulo,
      descripcion: eventoForm.value.descripcion,
      fecha: eventoForm.value.fecha,
      lugar: eventoForm.value.lugar
    }

    let res
    if (eventoForm.value.id) {
      res = await apiClient.put(`/api/admin/eventos/${eventoForm.value.id}`, payload, {
        headers: { 'Authorization': `Bearer ${token}` }
      })
      eventos.value = eventos.value.map(e => e.id === res.data.id ? res.data : e)
      eventoMessage.value = '✓ Evento actualizado correctamente'
    } else {
      res = await apiClient.post('/api/admin/eventos', payload, {
        headers: { 'Authorization': `Bearer ${token}` }
      })
      eventos.value.push(res.data)
      eventoMessage.value = '✓ Evento creado correctamente'
    }
    eventoMessageOk.value = true
    setTimeout(() => resetEventoForm(), 1500)
  } catch (e) {
    eventoMessage.value = '✗ Error al guardar evento'
    eventoMessageOk.value = false
    if (toast) toast.show(eventoMessage.value, 'error', 1500)
  } finally {
    savingEvento.value = false
  }
}

const removeEvento = async (id) => {
  if (!confirm('¿Confirmar eliminación del evento?')) return
  try {
    const token = localStorage.getItem('token')
    await apiClient.delete(`/api/admin/eventos/${id}`, {
      headers: { 'Authorization': `Bearer ${token}` }
    })
    eventos.value = eventos.value.filter(e => e.id !== id)
    resetEventoForm()
    if (toast) toast.show('✓ Evento eliminado', 'success', 1500)
  } catch (e) {
    if (toast) toast.show('✗ Error al eliminar evento', 'error', 1500)
  }
}

// ===== SOLICITUDES DE PRESUPUESTO =====
const loadSolicitudes = async () => {
  try {
    loadingSolicitudes.value = true
    const token = localStorage.getItem('token')
    const estado = solicitudFilter.value === 'all' ? 'all' : solicitudFilter.value
    const res = await apiClient.get(`/api/admin/solicitudes?estado=${estado}`, {
      headers: { 'Authorization': `Bearer ${token}` }
    })
    solicitudes.value = res.data
  } catch (e) {
    if (toast) toast.show('✗ Error al cargar solicitudes', 'error', 1500)
  } finally {
    loadingSolicitudes.value = false
  }
}

const updateEstadoSolicitud = async (solicitudId, nuevoEstado) => {
  try {
    const token = localStorage.getItem('token')
    const res = await apiClient.put(`/api/admin/solicitudes/${solicitudId}/estado`, 
      { estado: nuevoEstado },
      { headers: { 'Authorization': `Bearer ${token}` } }
    )
    
    const index = solicitudes.value.findIndex(s => s.id === solicitudId)
    if (index !== -1) {
      solicitudes.value[index] = res.data
      if (selectedSolicitud.value?.id === solicitudId) {
        selectedSolicitud.value = res.data
      }
    }
    
    if (toast) toast.show(`✓ Estado actualizado a "${nuevoEstado}"`, 'success', 1500)
  } catch (e) {
    if (toast) toast.show('✗ Error al actualizar estado', 'error', 1500)
  }
}

onMounted(() => {
  loadProducts()
  loadMessages()
  loadEventos()
  loadSolicitudes()
})

</script>

<style scoped>
.admin-shell {
  background:
    radial-gradient(circle at top left, rgb(0 0 0 / 0.03), transparent 24%),
    radial-gradient(circle at top right, rgb(255 255 255 / 0.65), transparent 18%),
    linear-gradient(180deg, #f6f6f6 0%, #eeeeee 100%);
}

.admin-hero {
  position: relative;
  overflow: hidden;
  border: 1px solid rgb(20 20 20 / 0.08);
  border-radius: 28px;
  background: linear-gradient(135deg, rgb(255 255 255 / 0.96), rgb(246 246 246 / 0.96));
  box-shadow: 0 24px 80px rgb(0 0 0 / 0.08);
  padding: 1.75rem;
}

.admin-kicker {
  text-transform: uppercase;
  letter-spacing: 0.28em;
  font-size: 0.72rem;
  color: rgb(75 70 63 / 0.76);
  margin-bottom: 0.65rem;
}

.admin-title {
  font-family: Georgia, 'Times New Roman', serif;
  font-size: clamp(2rem, 5vw, 3.8rem);
  line-height: 0.98;
  letter-spacing: 0.05em;
  color: #111111;
}

.admin-subtitle {
  margin-top: 1rem;
  max-width: 60ch;
  color: rgb(75 70 63 / 0.78);
  line-height: 1.8;
}

.admin-logout {
  align-self: flex-start;
  border: 1px solid rgb(20 20 20 / 0.12);
  background: #111111;
  color: #ffffff;
  padding: 0.9rem 1.2rem;
  border-radius: 999px;
  font-size: 0.72rem;
  text-transform: uppercase;
  letter-spacing: 0.22em;
  transition: transform 0.2s ease, background 0.2s ease;
}

.admin-logout:hover {
  transform: translateY(-1px);
  background: #1b1b1b;
}

.admin-tabs {
  display: flex;
  gap: 0.55rem;
  flex-wrap: wrap;
  margin-top: 1.5rem;
  padding: 0.4rem;
  border-radius: 999px;
  background: rgb(255 255 255 / 0.92);
  border: 1px solid rgb(20 20 20 / 0.08);
  backdrop-filter: blur(10px);
}

.admin-tab,
.admin-tab-active {
  border-radius: 999px;
  padding: 0.85rem 1rem;
}

.admin-tab {
  color: rgb(70 66 60 / 0.78);
}

.admin-tab-active {
  background: #111111;
  color: #ffffff;
}

.admin-card {
  background: rgba(255, 255, 255, 0.88);
  border: 1px solid rgb(20 20 20 / 0.08);
  border-radius: 28px;
  box-shadow: 0 18px 60px rgb(0 0 0 / 0.06);
  padding: 1.5rem;
}

.admin-section-title {
  font-family: Georgia, 'Times New Roman', serif;
  font-size: 1.55rem;
  line-height: 1.1;
  color: #111111;
  margin-bottom: 1rem;
}

.admin-stat {
  border-radius: 24px;
  padding: 1.35rem;
  border: 1px solid rgb(20 20 20 / 0.08);
  box-shadow: 0 12px 40px rgb(0 0 0 / 0.05);
  background: linear-gradient(180deg, rgba(255,255,255,0.98), rgba(246,246,246,0.96));
  border-top: 3px solid #111111;
}

.admin-list-row {
  border: 1px solid rgb(20 20 20 / 0.08);
  border-radius: 20px;
  padding: 1rem;
  background: rgba(255, 255, 255, 0.85);
  transition: transform 0.2s ease, box-shadow 0.2s ease, border-color 0.2s ease;
}

.admin-list-row:hover {
  transform: translateY(-1px);
  box-shadow: 0 12px 36px rgb(0 0 0 / 0.06);
  border-color: rgb(20 20 20 / 0.14);
}

.admin-input {
  border: 1px solid rgb(20 20 20 / 0.12);
  border-radius: 16px;
  background: rgba(255, 255, 255, 0.95);
  padding: 0.85rem 1rem;
  outline: none;
  transition: border-color 0.2s ease, box-shadow 0.2s ease;
}

.admin-input:focus {
  border-color: rgb(196 160 106 / 0.8);
  box-shadow: 0 0 0 4px rgb(196 160 106 / 0.14);
}

.admin-primary-btn {
  background: #111111;
  color: #ffffff;
  border-radius: 16px;
  padding: 0.8rem 1rem;
  text-transform: uppercase;
  letter-spacing: 0.18em;
  font-size: 0.72rem;
  font-weight: 700;
  transition: transform 0.2s ease, background 0.2s ease, color 0.2s ease;
}

.admin-primary-btn:hover {
  transform: translateY(-1px);
  background: #7a7a7a;
  color: #ffffff;
}

.admin-secondary-btn {
  border: 1px solid rgb(20 20 20 / 0.12);
  border-radius: 16px;
  background: rgba(255, 255, 255, 0.88);
  color: #444;
  transition: transform 0.2s ease, background 0.2s ease, color 0.2s ease;
}

.admin-secondary-btn:hover {
  transform: translateY(-1px);
  background: #111111;
  color: #ffffff;
}
</style>
