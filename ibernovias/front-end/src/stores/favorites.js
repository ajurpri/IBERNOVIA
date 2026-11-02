import { ref } from 'vue'

const storageKey = 'ibernovia_favorites'
const readFavorites = () => {
  try {
    const value = JSON.parse(localStorage.getItem(storageKey) || '[]')
    return Array.isArray(value) ? value.filter(id => typeof id === 'string') : []
  } catch { return [] }
}

// Store IDs only, never product objects or professional prices.
export const favoriteIds = ref(readFavorites())
export const toggleFavoriteId = (id) => {
  const key = String(id)
  favoriteIds.value = favoriteIds.value.includes(key)
    ? favoriteIds.value.filter(value => value !== key)
    : [...favoriteIds.value, key]
  try { localStorage.setItem(storageKey, JSON.stringify(favoriteIds.value)) } catch { /* In-memory fallback. */ }
}
