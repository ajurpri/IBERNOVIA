import { createRouter, createWebHistory } from 'vue-router'
import HelloWorld from '../components/HelloWorld.vue'
import ProductoView from '../views/ProductoView.vue'
import CarritoView from '../views/CarritoView.vue'
import AuthView from '../views/AuthView.vue'
import TiendaView from '../views/TiendaView.vue'
import CuentaView from '../views/CuentaView.vue'
import ContactoView from '../views/ContactoView.vue'
import { useAuthStore } from '../stores/auth'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: HelloWorld
  },
  {
    path: '/tienda',
    name: 'Tienda',
    component: TiendaView
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
    component: CarritoView
  },
  {
    path: '/cuenta',
    name: 'Cuenta',
    component: CuentaView,
    meta: { requiresAuth: true }
  },
  {
    path: '/contacto',
    name: 'Contacto',
    component: ContactoView
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
      next('/auth')
    }
  } else {
    next()
  }
})

export default router
