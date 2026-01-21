<template>
  <div class="min-h-screen bg-white py-12 md:py-16">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
      <!-- Encabezado -->
      <div class="text-center mb-12">
        <h1 class="font-serif text-4xl md:text-5xl text-luxury-black mb-2">Tu Carrito</h1>
        <div class="h-1 w-20 bg-luxury-gold mx-auto mt-4"></div>
      </div>

      <!-- Carrito Vacío -->
      <div v-if="cartStore.items.length === 0" class="text-center py-20">
        <svg class="w-20 h-20 mx-auto text-gray-300 mb-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
          <path stroke-linecap="round" stroke-linejoin="round" stroke-width="1.5" d="M3 3h2l.4 2M7 13h10l4-8H5.4M7 13L5.4 5M7 13l-2.293 2.293c-.63.63-.184 1.707.707 1.707H17m0 0a2 2 0 100 4 2 2 0 000-4zm-8 2a2 2 0 11-4 0 2 2 0 014 0z" />
        </svg>
        <p class="text-gray-500 text-lg mb-6 font-light">Tu carrito está vacío</p>
        <router-link to="/" class="inline-block bg-luxury-black text-white px-8 py-3 hover:bg-luxury-gold hover:text-luxury-black transition font-bold uppercase tracking-widest text-sm">
          Continuar Comprando
        </router-link>
      </div>

      <!-- Carrito con Productos -->
      <div v-else class="grid grid-cols-1 lg:grid-cols-3 gap-8 lg:gap-12">
        <!-- Tabla de Productos -->
        <div class="lg:col-span-2">
          <div class="border border-gray-200">
            <!-- Encabezado de Tabla -->
            <div class="grid grid-cols-4 gap-4 bg-gray-50 p-4 border-b border-gray-200 text-xs font-bold uppercase tracking-widest text-gray-600">
              <div class="col-span-2">Producto</div>
              <div class="text-center">Cantidad</div>
              <div class="text-right">Subtotal</div>
            </div>

            <!-- Items del Carrito -->
            <div v-for="item in cartStore.items" :key="item.id" class="border-b border-gray-100 p-4 hover:bg-gray-50 transition">
              <div class="grid grid-cols-4 gap-4 items-center">
                <!-- Imagen y Nombre -->
                <div class="col-span-2 flex gap-4">
                  <div class="flex-shrink-0 w-20 h-24 bg-gray-100 rounded overflow-hidden">
                    <img :src="item.imagen" :alt="item.nombre" class="w-full h-full object-cover" @error="e => e.target.style.display='none'">
                  </div>
                  <div class="flex flex-col justify-between">
                    <router-link :to="`/producto/${item.id}`" class="text-sm font-semibold text-luxury-black hover:text-luxury-gold transition line-clamp-2">
                      {{ item.nombre }}
                    </router-link>
                    <p class="text-xs text-gray-400 uppercase tracking-widest">{{ item.categoria }}</p>
                    <p class="text-sm font-bold text-gray-900">{{ item.precio }}€</p>
                  </div>
                </div>

                <!-- Cantidad -->
                <div class="flex justify-center">
                  <div class="flex items-center border border-gray-300 rounded">
                    <button @click="restarCantidad(item.id)" class="px-2 py-1 hover:bg-gray-100 text-gray-600">−</button>
                    <input 
                      :value="item.cantidad" 
                      @change="e => cartStore.updateCantidad(item.id, parseInt(e.target.value))"
                      type="number" 
                      min="1" 
                      class="w-12 text-center border-0 focus:outline-none text-sm"
                    >
                    <button @click="sumarCantidad(item.id)" class="px-2 py-1 hover:bg-gray-100 text-gray-600">+</button>
                  </div>
                </div>

                <!-- Subtotal y Eliminar -->
                <div class="text-right">
                  <p class="text-sm font-bold text-gray-900 mb-2">{{ (item.precio * item.cantidad).toFixed(2) }}€</p>
                  <button 
                    @click="cartStore.removeItem(item.id)"
                    class="text-xs text-red-500 hover:text-red-700 uppercase tracking-widest font-bold transition">
                    Eliminar
                  </button>
                </div>
              </div>
            </div>
          </div>

          <!-- Continuar Comprando -->
          <div class="mt-6">
            <router-link to="/" class="inline-block text-luxury-gold hover:text-luxury-black text-sm font-bold uppercase tracking-widest transition border-b-2 border-luxury-gold hover:border-luxury-black">
              ← Continuar Comprando
            </router-link>
          </div>
        </div>

        <!-- Resumen del Pedido -->
        <div class="lg:col-span-1">
          <div class="bg-gray-50 border border-gray-200 p-6 sticky top-24">
            <h3 class="font-serif text-2xl text-luxury-black mb-6">Resumen</h3>
            
            <!-- Detalles -->
            <div class="space-y-3 mb-6 pb-6 border-b border-gray-200">
              <div class="flex justify-between text-sm">
                <span class="text-gray-600">Subtotal</span>
                <span class="font-semibold">{{ cartStore.total }}€</span>
              </div>
              <div class="flex justify-between text-sm">
                <span class="text-gray-600">Envío</span>
                <span class="font-semibold text-luxury-gold">Gratis</span>
              </div>
              <div class="flex justify-between text-sm">
                <span class="text-gray-600">Impuestos</span>
                <span class="font-semibold">Calculados al finalizar</span>
              </div>
            </div>

            <!-- Total -->
            <div class="mb-6">
              <div class="flex justify-between items-center mb-2">
                <span class="font-serif text-lg text-luxury-black">Total</span>
                <span class="text-2xl font-bold text-luxury-gold">{{ cartStore.total }}€</span>
              </div>
              <p class="text-xs text-gray-400 text-center">{{ cartStore.totalItems }} producto(s)</p>
            </div>

            <!-- Botón Checkout -->
            <button 
              @click="proceedCheckout"
              class="w-full bg-luxury-black text-white py-3 font-bold uppercase tracking-widest text-sm hover:bg-luxury-gold hover:text-luxury-black transition mb-3">
              Proceder a Pagar
            </button>

            <!-- Botón Vaciar -->
            <button 
              @click="cartStore.clear"
              class="w-full border border-red-300 text-red-500 py-2 font-bold uppercase tracking-widest text-xs hover:bg-red-50 transition">
              Vaciar Carrito
            </button>

            <!-- Info de Envío -->
            <div class="mt-6 pt-6 border-t border-gray-200 text-xs text-gray-500 space-y-2">
              <div class="flex items-start gap-2">
                <svg class="w-4 h-4 mt-1 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
                  <path fill-rule="evenodd" d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z" clip-rule="evenodd" />
                </svg>
                <span>Envío gratis a partir de 50€</span>
              </div>
              <div class="flex items-start gap-2">
                <svg class="w-4 h-4 mt-1 flex-shrink-0" fill="currentColor" viewBox="0 0 20 20">
                  <path d="M8.16 5.314l4.897 4.898a.75.75 0 000 1.06l4.898 4.897a.75.75 0 01-1.06 1.061l-4.898-4.897a.75.75 0 00-1.06 0l-4.898 4.897a.75.75 0 01-1.06-1.06l4.897-4.898a.75.75 0 010-1.06L2.041 5.314a.75.75 0 011.06-1.06l4.898 4.898a.75.75 0 001.061 0l4.898-4.898a.75.75 0 011.06 1.06z" />
                </svg>
                <span>Devoluciones sin costo en 30 días</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useCartStore } from '../stores/cart'

const cartStore = useCartStore()

const sumarCantidad = (id) => {
  const item = cartStore.items.find(i => i.id === id)
  if (item) cartStore.updateCantidad(id, item.cantidad + 1)
}

const restarCantidad = (id) => {
  const item = cartStore.items.find(i => i.id === id)
  if (item && item.cantidad > 1) cartStore.updateCantidad(id, item.cantidad - 1)
}

const proceedCheckout = () => {
  // Próximamente: Integración de Stripe/PayPal
  alert('🎉 ¡Orden creada! Próxima fase: Integración de pago. Por ahora, tu carrito está listo.')
}
</script>
