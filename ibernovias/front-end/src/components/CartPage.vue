<template>
  <div class="bg-luxury-cream min-h-screen pb-20">
    <!-- Progress Bar -->
    <div class="bg-white border-b border-gray-200 py-6">
      <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
        <div class="flex items-center justify-between mb-4">
          <div class="flex items-center flex-1">
            <!-- Step 1: Carrito -->
            <div class="flex items-center">
              <div class="flex items-center justify-center w-10 h-10 rounded-full bg-luxury-gold text-white font-bold text-lg">✓</div>
              <p class="ml-3 text-sm font-semibold text-luxury-black">Carrito</p>
            </div>
            <!-- Line 1 -->
            <div class="flex-1 h-1 bg-luxury-gold mx-4"></div>
            
            <!-- Step 2: Checkout -->
            <div class="flex items-center">
              <div class="flex items-center justify-center w-10 h-10 rounded-full bg-gray-300 text-white font-bold text-lg">2</div>
              <p class="ml-3 text-sm font-semibold text-gray-500">Checkout</p>
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
    </div>

    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      <h1 class="font-serif text-4xl text-luxury-black mb-12">Carrito de Compras</h1>

      <div v-if="cartStore.items.length === 0" class="text-center py-20">
        <div class="mb-6">
          <svg class="w-16 h-16 mx-auto text-gray-300" fill="none" stroke="currentColor" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M16 11V7a4 4 0 00-8 0v4M5 9h14l1 12H4L5 9z" />
          </svg>
        </div>
        <p class="text-gray-500 mb-4 text-lg">Tu carrito está vacío</p>
        <router-link to="/tienda" class="inline-block bg-luxury-gold text-white px-6 py-3 font-bold uppercase tracking-widest hover:bg-opacity-80 transition rounded">
          ← Continuar comprando
        </router-link>
      </div>

      <div v-else class="grid grid-cols-1 lg:grid-cols-3 gap-8">
        <!-- Tabla de productos -->
        <div class="lg:col-span-2">
          <div class="space-y-4">
            <div v-for="item in cartStore.items" :key="item.id" class="border border-gray-200 p-6 rounded-lg flex gap-4 bg-white hover:shadow-md transition">
              <!-- Imagen -->
              <router-link :to="`/producto/${item.id}`" class="flex-shrink-0">
                <img 
                  :src="item.imagen || 'https://placehold.co/100x100'" 
                  :alt="item.nombre"
                  class="w-24 h-24 object-cover rounded hover:scale-105 transition"
                  loading="lazy"
                  decoding="async"
                >
              </router-link>

              <!-- Info -->
              <div class="flex-1">
                <router-link :to="`/producto/${item.id}`" class="block">
                  <h3 class="font-serif text-lg text-luxury-black hover:text-luxury-gold transition">{{ item.nombre }}</h3>
                </router-link>
                <p class="text-sm text-gray-500 mb-2">{{ item.categoria }}</p>
                <p class="text-lg text-luxury-gold font-bold">{{ item.precio }}€</p>
              </div>

              <!-- Cantidad y total -->
              <div class="flex flex-col items-end gap-4">
                <div class="flex items-center border border-gray-300 rounded bg-gray-50">
                  <button @click="handleUpdateCantidad(item.id, item.cantidad - 1)" class="px-3 py-2 hover:bg-gray-100 transition" aria-label="Disminuir cantidad">−</button>
                  <span class="w-8 text-center font-semibold">{{ item.cantidad }}</span>
                  <button @click="handleUpdateCantidad(item.id, item.cantidad + 1)" class="px-3 py-2 hover:bg-gray-100 transition" aria-label="Aumentar cantidad">+</button>
                </div>
                <p class="font-bold text-lg">{{ (item.precio * item.cantidad).toFixed(2) }}€</p>
                <button 
                  @click="removeItemWithToast(item)"
                  class="text-red-500 hover:text-red-700 text-sm font-semibold transition">
                  Eliminar
                </button>
              </div>
            </div>
          </div>
        </div>

        <!-- Resumen de compra -->
        <div class="lg:col-span-1">
          <div class="bg-white border-2 border-luxury-gold p-6 rounded-xl sticky top-32 shadow-lg">
            <h2 class="font-serif text-2xl text-luxury-black mb-6">Resumen</h2>
            
            <div class="space-y-3 mb-6 pb-6 border-b border-gray-200">
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

            <div class="flex justify-between mb-8 text-lg font-bold">
              <span>Total:</span>
              <span class="text-luxury-gold text-2xl">{{ (parseFloat(cartStore.total) * 1.21).toFixed(2) }}€</span>
            </div>

            <router-link 
              to="/checkout" 
              class="w-full bg-luxury-gold text-white px-6 py-4 font-bold uppercase tracking-widest hover:bg-opacity-90 transition mb-3 text-center rounded-lg shadow-md hover:shadow-lg block">
              Proceder al Pago →
            </router-link>
            
            <router-link to="/tienda" class="block text-center text-luxury-gold hover:text-luxury-black font-semibold text-sm transition">
              ← Continuar Comprando
            </router-link>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { inject } from 'vue'
import { useCartStore } from '../stores/cart'

const cartStore = useCartStore()
const toast = inject('toast')

const handleUpdateCantidad = (id, nuevaCantidad) => {
  const item = cartStore.items.find(i => i.id === id)
  if (nuevaCantidad < 1) {
    cartStore.removeItem(id)
    if (toast) toast.show(`✓ ${item?.nombre} eliminado del carrito`, 'info', 2000)
    return
  }
  cartStore.updateCantidad(id, nuevaCantidad)
  if (toast) toast.show(`✓ Cantidad actualizada: ${nuevaCantidad}`, 'success', 2000)
}

const removeItemWithToast = (item) => {
  const itemName = item.nombre
  cartStore.removeItem(item.id)
  if (toast) toast.show(`✓ "${itemName}" eliminado del carrito`, 'warning', 2000)
}
</script>
