<template>
  <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
    <h1 class="font-serif text-4xl text-luxury-black mb-12">Carrito de Compras</h1>

    <div v-if="cartStore.items.length === 0" class="text-center py-20">
      <p class="text-gray-500 mb-4 text-lg">Tu carrito está vacío</p>
      <router-link to="/" class="text-luxury-gold hover:underline">← Continuar comprando</router-link>
    </div>

    <div v-else class="grid grid-cols-1 lg:grid-cols-3 gap-8">
      <!-- Tabla de productos -->
      <div class="lg:col-span-2">
        <div class="space-y-4">
          <div v-for="item in cartStore.items" :key="item.id" class="border p-6 rounded-lg flex gap-4">
            <!-- Imagen -->
            <img 
              :src="item.imagen || 'https://placehold.co/100x100'" 
              :alt="item.nombre"
              class="w-24 h-24 object-cover rounded"
            >

            <!-- Info -->
            <div class="flex-1">
              <h3 class="font-serif text-lg text-luxury-black">{{ item.nombre }}</h3>
              <p class="text-sm text-gray-500 mb-2">{{ item.categoria }}</p>
              <p class="text-lg text-luxury-gold font-bold">{{ item.precio }}€</p>
            </div>

            <!-- Cantidad y total -->
            <div class="flex flex-col items-end gap-4">
              <div class="flex items-center border border-gray-300 rounded">
                <button @click="cartStore.updateCantidad(item.id, item.cantidad - 1)" class="px-2 py-1 hover:bg-gray-100">-</button>
                <span class="w-8 text-center">{{ item.cantidad }}</span>
                <button @click="cartStore.updateCantidad(item.id, item.cantidad + 1)" class="px-2 py-1 hover:bg-gray-100">+</button>
              </div>
              <p class="font-bold">{{ (item.precio * item.cantidad).toFixed(2) }}€</p>
              <button 
                @click="cartStore.removeItem(item.id)"
                class="text-red-500 hover:text-red-700 text-sm">
                Eliminar
              </button>
            </div>
          </div>
        </div>
      </div>

      <!-- Resumen de compra -->
      <div class="lg:col-span-1">
        <div class="border-2 border-luxury-gold p-6 rounded-lg sticky top-20">
          <h2 class="font-serif text-2xl text-luxury-black mb-6">Resumen</h2>
          
          <div class="space-y-3 mb-6 pb-6 border-b">
            <div class="flex justify-between">
              <span class="text-gray-600">Subtotal:</span>
              <span>{{ cartStore.total }}€</span>
            </div>
            <div class="flex justify-between">
              <span class="text-gray-600">Envío:</span>
              <span>Gratis</span>
            </div>
            <div class="flex justify-between">
              <span class="text-gray-600">Impuestos:</span>
              <span>{{ (cartStore.total * 0.21).toFixed(2) }}€</span>
            </div>
          </div>

          <div class="flex justify-between mb-8 text-lg font-bold">
            <span>Total:</span>
            <span class="text-luxury-gold">{{ (parseFloat(cartStore.total) * 1.21).toFixed(2) }}€</span>
          </div>

          <button class="w-full bg-luxury-gold text-white px-6 py-3 font-bold uppercase tracking-widest hover:bg-opacity-80 transition mb-3">
            Proceder al Pago
          </button>
          
          <router-link to="/" class="block text-center text-luxury-gold hover:underline text-sm">
            Continuar Comprando
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useCartStore } from '../stores/cart'

const cartStore = useCartStore()
</script>
