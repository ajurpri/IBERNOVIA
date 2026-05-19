import { createRouter, createWebHistory } from 'vue-router'
import { defineAsyncComponent } from 'vue'
import ProductoView from '../views/ProductoView.vue'
import { useAuthStore } from '../stores/auth'

// Lazy load para vistas que se usan menos frecuentemente
const CarritoView = defineAsyncComponent(() => import('../views/CarritoView.vue'))
const CheckoutView = defineAsyncComponent(() => import('../views/CheckoutView.vue'))
const ConfirmacionView = defineAsyncComponent(() => import('../views/ConfirmacionView.vue'))
const AuthView = defineAsyncComponent(() => import('../views/AuthView.old.vue'))
const TiendaView = defineAsyncComponent(() => import('../views/TiendaView.vue'))
const HomeView = defineAsyncComponent(() => import('../views/HomeView.vue'))
const CuentaView = defineAsyncComponent(() => import('../views/CuentaView.vue'))
const ContactoView = defineAsyncComponent(() => import('../views/ContactoView.vue'))
const AccesoEmpresarialView = defineAsyncComponent(() => import('../views/AccesoEmpresarialView.vue'))
const PrivacidadView = defineAsyncComponent(() => import('../views/PrivacidadView.vue'))
const CookiesView = defineAsyncComponent(() => import('../views/CookiesView.vue'))
const LegalView = defineAsyncComponent(() => import('../views/LegalView.vue'))
const AdminView = defineAsyncComponent(() => import('../views/AdminView.vue'))

const routes = [
  {
    path: '/',
    name: 'Home',
    component: HomeView,
    meta: {
      title: 'IBERNOVIA | Inicio',
      description: 'IBERNOVIA Atelier: diseño nupcial y complementos profesionales.'
    }
  },
  {
    path: '/tienda',
    name: 'Tienda',
    component: TiendaView,
    meta: {
      title: 'Catálogo profesional | IBERNOVIA',
      description: 'Explora el catálogo. Los precios y solicitudes de presupuesto son exclusivos para clientes profesionales registrados.'
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
      requiresBusiness: true,
      title: 'Carrito | IBERNOVIA'
    }
  },
  {
    path: '/checkout',
    name: 'Checkout',
    component: CheckoutView,
    meta: {
      requiresBusiness: true,
      title: 'Checkout | IBERNOVIA'
    }
  },
  {
    path: '/confirmacion',
    name: 'Confirmacion',
    component: ConfirmacionView,
    meta: {
      requiresBusiness: true,
      title: 'Confirmación de Compra | IBERNOVIA'
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
    path: '/acceso-empresarial',
    name: 'AccesoEmpresarial',
    component: AccesoEmpresarialView,
    meta: {
      title: 'Alta empresarial | IBERNOVIA',
      description: 'Solicita el alta profesional para acceder a tarifas y pedidos mayoristas.'
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
router.beforeEach(async (to, from, next) => {
  const authStore = useAuthStore()

  if (to.meta.requiresBusiness) {
    if (authStore.canRequestQuote) {
      next()
    } else {
      next('/acceso-empresarial')
    }
    return
  }

  if (to.meta.requiresAdmin) {
    const verifiedAdmin = await authStore.verifyAdminSession()
    if (verifiedAdmin) {
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
