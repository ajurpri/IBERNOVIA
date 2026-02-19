import { fileURLToPath, URL } from 'node:url'

import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

// https://vite.dev/config/
export default defineConfig({
  plugins: [vue()],
  server: {
    port: 5174,
    strictPort: false,
    host: 'localhost'
  },
  resolve: {
    alias: {
      '@': fileURLToPath(new URL('./src', import.meta.url))
    }
  },
  build: {
    minify: 'terser',
    terserOptions: {
      compress: {
        drop_console: true
      }
    },
    rollupOptions: {
      output: {
        manualChunks: {
          'vue-core': ['vue', 'vue-router', 'pinia'],
          'axios': ['axios']
        }
      }
    },
    target: 'es2020',
    reportCompressedSize: false,
    chunkSizeWarningLimit: 500
  }
})
