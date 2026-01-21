import { createRouter, createWebHistory } from 'vue-router'
import HelloWorld from '../components/HelloWorld.vue'
import ProductoView from '../views/ProductoView.vue'
import CarritoView from '../views/CarritoView.vue'
import AuthView from '../views/AuthView.vue'
import { useAuthStore } from '../stores/auth'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: HelloWorld
  },
  {
    path: '/productos',
    name: 'Productos',
    component: HelloWorld
  },
  {
    path: '/producto/:id',
    name: 'Producto',
    component: ProductoView
  },
  {
    path: '/auth',
    name: 'Auth',
    component: AuthView
  },
  {
    path: '/carrito',
    name: 'Carrito',
    component: CarritoView,
    meta: { requiresAuth: true }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

// Guard para proteger rutas autenticadas
router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()
  
  if (to.meta.requiresAuth) {
    if (authStore.isAuthenticated) {
      next()
    } else {
      // Redirigir a login
      next('/auth')
    }
  } else {
    next()
  }
})

export default router
