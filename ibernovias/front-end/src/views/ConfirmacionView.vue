<template>
  <div class="bg-luxury-cream min-h-screen flex items-center justify-center py-20">
    <div class="max-w-2xl mx-auto px-4 sm:px-6 lg:px-8">
      <!-- Progress Bar -->
      <div class="bg-white border border-gray-200 rounded-t-xl p-6 mb-0">
        <div class="flex items-center justify-between">
          <!-- Step 1: Carrito -->
          <div class="flex items-center">
            <router-link to="/carrito" class="flex items-center cursor-pointer hover:text-luxury-gold transition">
              <div class="flex items-center justify-center w-10 h-10 rounded-full bg-luxury-gold text-white font-bold text-lg">✓</div>
            </router-link>
          </div>
          <!-- Line 1 -->
          <div class="flex-1 h-1 bg-luxury-gold mx-2"></div>
          
          <!-- Step 2: Checkout -->
          <div class="flex items-center">
            <router-link to="/checkout" class="flex items-center cursor-pointer hover:text-luxury-gold transition">
              <div class="flex items-center justify-center w-10 h-10 rounded-full bg-luxury-gold text-white font-bold text-lg">✓</div>
            </router-link>
          </div>
          <!-- Line 2 -->
          <div class="flex-1 h-1 bg-luxury-gold mx-2"></div>
          
          <!-- Step 3: Confirmación -->
          <div class="flex items-center">
            <div class="flex items-center justify-center w-10 h-10 rounded-full bg-luxury-gold text-white font-bold text-lg">✓</div>
          </div>
        </div>
      </div>

      <!-- Contenido Principal -->
      <div class="bg-white rounded-b-xl p-12 text-center shadow-lg">
        <!-- Icono de Éxito -->
        <div class="mb-8">
          <div class="inline-flex items-center justify-center w-24 h-24 rounded-full bg-gradient-to-br from-luxury-gold/20 to-luxury-gold/10 mb-6">
            <svg class="w-12 h-12 text-luxury-gold" fill="currentColor" viewBox="0 0 20 20">
              <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd" />
            </svg>
          </div>
          <h1 class="font-serif text-4xl text-luxury-black mb-4">¡Compra Completada!</h1>
          <p class="text-xl text-luxury-gold font-semibold mb-4">Número de pedido: #{{ orderNumber }}</p>
          <p class="text-gray-600 text-lg">Tu compra ha sido procesada correctamente</p>
        </div>

        <!-- Info de Envío -->
        <div class="bg-luxury-cream border border-luxury-gold/30 rounded-lg p-6 mb-8 text-left">
          <h2 class="font-semibold text-luxury-black mb-4 text-lg">Información del Pedido</h2>
          <div class="space-y-3">
            <div class="flex justify-between py-2 border-b border-gray-200">
              <span class="text-gray-600">Cantidad de artículos:</span>
              <span class="font-semibold">{{ totalItems }}</span>
            </div>
            <div class="flex justify-between py-2 border-b border-gray-200">
              <span class="text-gray-600">Subtotal:</span>
              <span class="font-semibold">{{ subtotal }}€</span>
            </div>
            <div class="flex justify-between py-2 border-b border-gray-200">
              <span class="text-gray-600">Envío:</span>
              <span class="font-semibold text-luxury-gold">Gratis</span>
            </div>
            <div class="flex justify-between py-2 border-b border-gray-200">
              <span class="text-gray-600">Impuestos (21%):</span>
              <span class="font-semibold">{{ tax }}€</span>
            </div>
            <div class="flex justify-between py-3 pt-4 border-t-2 border-gray-300">
              <span class="text-lg font-bold text-luxury-black">Total Pagado:</span>
              <span class="text-2xl font-bold text-luxury-gold">{{ total }}€</span>
            </div>
          </div>
        </div>

        <!-- Mensajes -->
        <div class="space-y-4 mb-8">
          <p class="text-gray-600">
            Hemos enviado un email de confirmación a <span class="font-semibold text-luxury-black">tu@email.com</span>
          </p>
          <p class="text-gray-600">
            El número de seguimiento de tu pedido es: <span class="font-mono font-bold text-luxury-black">{{ orderNumber }}</span>
          </p>
          <p class="text-sm text-gray-500 italic">
            Recibirás actualizaciones del envío en los próximos días
          </p>
        </div>

        <!-- Botones de Acción -->
        <div class="flex gap-4 pt-8 border-t border-gray-200">
          <router-link 
            to="/tienda" 
            class="flex-1 border border-luxury-gold text-luxury-gold px-6 py-3 font-bold uppercase text-sm tracking-widest hover:bg-luxury-gold hover:text-white transition rounded-lg">
            Seguir Comprando
          </router-link>
          <router-link 
            to="/cuenta" 
            class="flex-1 bg-luxury-gold text-white px-6 py-3 font-bold uppercase text-sm tracking-widest hover:bg-opacity-90 transition rounded-lg shadow-lg hover:shadow-xl">
            Ver Mi Pedido
          </router-link>
        </div>
      </div>

      <!-- Footer Info -->
      <div class="mt-8 text-center text-sm text-gray-500">
        <p>Si tienes alguna pregunta, contáctanos en <router-link to="/contacto" class="text-luxury-gold hover:underline">support@ibernovia.com</router-link></p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { useCartStore } from '../stores/cart'

const router = useRouter()
const cartStore = useCartStore()

const orderNumber = ref(0)
const totalItems = ref(0)
const subtotal = ref('0.00')
const tax = ref('0.00')
const total = ref('0.00')

onMounted(() => {
  // Generar número de pedido
  orderNumber.value = 'IBV-' + Date.now()
  
  // Calcular totales
  const items = cartStore.items.length
  const sub = parseFloat(cartStore.total)
  const taxes = (sub * 0.21).toFixed(2)
  const tot = (sub * 1.21).toFixed(2)
  
  totalItems.value = items
  subtotal.value = sub.toFixed(2)
  tax.value = taxes
  total.value = tot
  
  // Limpiar carrito después de 3 segundos
  setTimeout(() => {
    cartStore.clearCart()
  }, 3000)
})
</script>
