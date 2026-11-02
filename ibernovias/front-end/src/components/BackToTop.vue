<template>
  <button
    v-show="isVisible"
    type="button"
    class="back-to-top"
    @click="scrollToTop"
    aria-label="Volver arriba"
  >
    Volver arriba ↑
  </button>
</template>

<script setup>
import { ref, onMounted, onBeforeUnmount } from 'vue'

const isVisible = ref(false)

const handleScroll = () => {
  isVisible.value = window.scrollY > 420
}

const scrollToTop = () => {
  window.scrollTo({ top: 0, behavior: window.matchMedia('(prefers-reduced-motion: reduce)').matches ? 'auto' : 'smooth' })
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
.back-to-top {display:block;position:static;padding:.75rem 0;min-height:44px;color:#514b43;font-size:.85rem;}
.back-to-top:hover {text-decoration:underline;text-underline-offset:4px;}
</style>
