import { defineStore } from 'pinia'
import { ref, computed } from 'vue'

export const useCartStore = defineStore('cart', () => {
  const items = ref([])

  const addItem = (producto, cantidad = 1) => {
    const existente = items.value.find(item => item.id === producto.id)
    if (existente) {
      existente.cantidad += cantidad
    } else {
      items.value.push({
        ...producto,
        cantidad
      })
    }
  }

  const removeItem = (productoId) => {
    items.value = items.value.filter(item => item.id !== productoId)
  }

  const updateCantidad = (productoId, cantidad) => {
    const item = items.value.find(item => item.id === productoId)
    if (item) {
      item.cantidad = Math.max(1, cantidad)
    }
  }

  const total = computed(() => {
    return items.value.reduce((sum, item) => sum + (item.precio * item.cantidad), 0).toFixed(2)
  })

  const totalItems = computed(() => {
    return items.value.reduce((sum, item) => sum + item.cantidad, 0)
  })

  const clear = () => {
    items.value = []
  }

  return {
    items,
    addItem,
    removeItem,
    updateCantidad,
    total,
    totalItems,
    clear
  }
})
