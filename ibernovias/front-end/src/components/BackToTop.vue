<template>
  <button
    v-show="isVisible"
    type="button"
    class="back-to-top"
    @click="scrollToTop"
    aria-label="Volver arriba"
  >
    ↑
  </button>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'

const isVisible = ref(false)

const handleScroll = () => {
  isVisible.value = window.scrollY > 420
}

const scrollToTop = () => {
  window.scrollTo({ top: 0, behavior: 'smooth' })
}

onMounted(() => {
  handleScroll()
  window.addEventListener('scroll', handleScroll, { passive: true })
})

onBeforeUnmount(() => {
  window.removeEventListener('scroll', handleScroll)
})
</script>

<style scoped>
.back-to-top {
  position: fixed;
  right: 20px;
  bottom: 24px;
  z-index: 60;
  height: 44px;
  width: 44px;
  border-radius: 999px;
  border: 1px solid rgba(212, 175, 55, 0.5);
  background: rgba(255, 255, 255, 0.95);
  color: #1a1a1a;
  font-size: 18px;
  font-weight: 700;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.12);
  transition: transform 0.2s ease, box-shadow 0.2s ease, opacity 0.2s ease;
}

.back-to-top:hover {
  transform: translateY(-2px);
  box-shadow: 0 10px 24px rgba(0, 0, 0, 0.16);
}

.back-to-top:focus-visible {
  outline: 2px solid rgba(212, 175, 55, 0.9);
  outline-offset: 2px;
}

@media (max-width: 640px) {
  .back-to-top {
    right: 16px;
    bottom: 16px;
  }
}
</style>
