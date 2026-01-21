<template>
  <footer class="bg-luxury-black text-white mt-20">
    <div class="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-16">
      <div class="grid grid-cols-1 md:grid-cols-4 gap-12 mb-12">
        <!-- Sobre -->
        <div>
          <h3 class="font-serif text-xl mb-4">Ibernovia</h3>
          <p class="text-gray-400 text-sm leading-relaxed">
            Complementos nupciales exclusivos con la máxima elegancia y calidad. Cada pieza cuenta una historia de lujo y sofisticación.
          </p>
        </div>

        <!-- Enlaces -->
        <div>
          <h4 class="font-semibold text-lg mb-4">Enlaces</h4>
          <ul class="space-y-2 text-sm text-gray-400">
            <li><router-link to="/" class="hover:text-luxury-gold transition">Inicio</router-link></li>
            <li><router-link to="/productos" class="hover:text-luxury-gold transition">Colección</router-link></li>
            <li><a href="#" class="hover:text-luxury-gold transition">Nosotros</a></li>
            <li><a href="#" class="hover:text-luxury-gold transition">Blog</a></li>
          </ul>
        </div>

        <!-- Servicio -->
        <div>
          <h4 class="font-semibold text-lg mb-4">Servicio</h4>
          <ul class="space-y-2 text-sm text-gray-400">
            <li><a href="#" class="hover:text-luxury-gold transition">Política de Privacidad</a></li>
            <li><a href="#" class="hover:text-luxury-gold transition">Términos de Compra</a></li>
            <li><a href="#" class="hover:text-luxury-gold transition">Contacto</a></li>
            <li><a href="#" class="hover:text-luxury-gold transition">FAQ</a></li>
          </ul>
        </div>

        <!-- Contacto -->
        <div>
          <h4 class="font-semibold text-lg mb-4">Contacto</h4>
          <p class="text-gray-400 text-sm mb-2">
            <strong>Email:</strong> info@ibernovia.com
          </p>
          <p class="text-gray-400 text-sm mb-4">
            <strong>Teléfono:</strong> +34 123 456 789
          </p>
          <div class="flex gap-3">
            <a href="#" class="text-luxury-gold hover:text-white transition">Instagram</a>
            <a href="#" class="text-luxury-gold hover:text-white transition">Facebook</a>
          </div>
        </div>
      </div>

      <div class="border-t border-gray-800 pt-8 text-center text-gray-400 text-sm">
        <p>© 2026 Ibernovia - Todos los derechos reservados | Diseño exclusivo para tu día especial</p>
      </div>
    </div>
  </footer>
</template>

<script setup>
</script>

<style scoped>
:root {
  --luxury-gold: #d4af37;
}

.luxury-gold {
  color: var(--luxury-gold);
}
</style>
