import { createRouter, createWebHistory } from 'vue-router'
import HelloWorld from '../components/HelloWorld.vue'
import ProductoView from '../views/ProductoView.vue'
import CarritoView from '../views/CarritoView.vue'
import AuthView from '../views/AuthView.vue'
import TiendaView from '../views/TiendaView.vue'
import CuentaView from '../views/CuentaView.vue'
import ContactoView from '../views/ContactoView.vue'
import PrivacidadView from '../views/PrivacidadView.vue'
import CookiesView from '../views/CookiesView.vue'
import LegalView from '../views/LegalView.vue'
import AdminView from '../views/AdminView.vue'
import { useAuthStore } from '../stores/auth'

const routes = [
  {
    path: '/',
    name: 'Home',
    component: HelloWorld,
    meta: {
      title: 'IBERNOVIA | Complementos de Alta Costura',
      description: 'Complementos de alta costura, seleccion premium y asesoramiento en tienda en Andujar.'
    }
  },
  {
    path: '/tienda',
    name: 'Tienda',
    component: TiendaView,
    meta: {
      title: 'Tienda | IBERNOVIA',
      description: 'Explora complementos seleccionados y recibe asesoramiento personalizado.'
    }
  },
  {
    path: '/producto/:id',
    name: 'Producto',
    component: ProductoView,
    meta: {
      title: 'Producto | IBERNOVIA'
    }
  },
  {
    path: '/auth',
    name: 'Auth',
    component: AuthView,
    meta: {
      title: 'Acceso | IBERNOVIA'
    }
  },
  {
    path: '/carrito',
    name: 'Carrito',
    component: CarritoView,
    meta: {
      title: 'Carrito | IBERNOVIA'
    }
  },
  {
    path: '/cuenta',
    name: 'Cuenta',
    component: CuentaView,
    meta: {
      requiresAuth: true,
      title: 'Mi cuenta | IBERNOVIA'
    }
  },
  {
    path: '/contacto',
    name: 'Contacto',
    component: ContactoView,
    meta: {
      title: 'Contacto | IBERNOVIA'
    }
  },
  {
    path: '/privacidad',
    name: 'Privacidad',
    component: PrivacidadView,
    meta: {
      title: 'Privacidad | IBERNOVIA'
    }
  },
  {
    path: '/cookies',
    name: 'Cookies',
    component: CookiesView,
    meta: {
      title: 'Cookies | IBERNOVIA'
    }
  },
  {
    path: '/legal',
    name: 'Legal',
    component: LegalView,
    meta: {
      title: 'Legal | IBERNOVIA'
    }
  },
  {
    path: '/admin',
    name: 'Admin',
    component: AdminView,
    meta: {
      requiresAdmin: true,
      title: 'Admin | IBERNOVIA'
    }
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior(to, from, savedPosition) {
    if (savedPosition) {
      return savedPosition
    }
    if (to.hash) {
      return { el: to.hash, behavior: 'smooth' }
    }
    return { top: 0 }
  }
})

router.afterEach((to) => {
  if (to.meta?.title) {
    document.title = to.meta.title
  }
  if (to.meta?.description) {
    const description = document.querySelector('meta[name="description"]')
    if (description) {
      description.setAttribute('content', to.meta.description)
    }
  }
})

// Guard para proteger rutas autenticadas
router.beforeEach((to, from, next) => {
  const authStore = useAuthStore()

  if (to.meta.requiresAdmin) {
    if (authStore.isAuthenticated && authStore.isAdmin) {
      next()
    } else {
      next('/auth')
    }
    return
  }

  if (to.meta.requiresAuth) {
    if (authStore.isAuthenticated) {
      next()
    } else {
      next('/auth')
    }
    return
  }

  next()
})

export default router
