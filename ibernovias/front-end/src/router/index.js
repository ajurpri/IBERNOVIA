import { createRouter, createWebHistory } from 'vue-router'
import HelloWorld from '../components/HelloWorld.vue'
import ProductoView from '../views/ProductoView.vue'
import CarritoView from '../views/CarritoView.vue'

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
    path: '/carrito',
    name: 'Carrito',
    component: CarritoView
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

export default router
