<template>
  <div v-if="message" class="fixed bottom-6 right-6 z-50 animate-slide-in-up">
    <div 
      class="rounded-lg shadow-2xl px-6 py-4 flex items-center gap-3 border backdrop-blur-sm"
      :class="{
        'bg-green-50 border-green-200 text-green-800': type === 'success',
        'bg-red-50 border-red-200 text-red-800': type === 'error',
        'bg-blue-50 border-blue-200 text-blue-800': type === 'info',
        'bg-amber-50 border-amber-200 text-amber-800': type === 'warning'
      }"
    >
      <!-- Icon -->
      <div v-if="type === 'success'" class="text-xl">✓</div>
      <div v-else-if="type === 'error'" class="text-xl">✕</div>
      <div v-else-if="type === 'warning'" class="text-xl">⚠</div>
      <div v-else class="text-xl">ℹ</div>

      <!-- Message -->
      <span class="font-semibold text-sm">{{ message }}</span>

      <!-- Progress bar -->
      <div 
        class="absolute bottom-0 left-0 h-1 rounded-b-lg"
        :class="{
          'bg-green-400': type === 'success',
          'bg-red-400': type === 'error',
          'bg-blue-400': type === 'info',
          'bg-amber-400': type === 'warning'
        }"
        :style="{ width: progress + '%', transition: 'width 0.1s linear' }"
      ></div>
    </div>
  </div>
</template>

<script setup>
import { ref, watch, onMounted, defineExpose } from 'vue'

const message = ref('')
const type = ref('info')
const progress = ref(100)
let timeoutId = null

const show = (msg, msgType = 'info', duration = 3000) => {
  message.value = msg
  type.value = msgType
  progress.value = 100

  // Clear existing timeout
  if (timeoutId) clearTimeout(timeoutId)

  // Progress animation
  const startTime = Date.now()
  const updateProgress = () => {
    const elapsed = Date.now() - startTime
    progress.value = Math.max(0, 100 - (elapsed / duration) * 100)
    if (progress.value > 0) {
      requestAnimationFrame(updateProgress)
    } else {
      message.value = ''
    }
  }
  updateProgress()

  // Auto hide
  timeoutId = setTimeout(() => {
    message.value = ''
  }, duration)
}

defineExpose({ show })
</script>

<style scoped>
@keyframes slideInUp {
  from {
    transform: translateY(20px);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}

.animate-slide-in-up {
  animation: slideInUp 0.3s ease-out;
}
</style>
