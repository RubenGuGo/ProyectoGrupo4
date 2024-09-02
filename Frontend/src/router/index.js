import { createRouter, createWebHistory } from 'vue-router'
import Inicio from '../views/Inicio.vue'
import Tipo from '../views/Tipo.vue'
import Obra from '../views/Obra.vue'
import ObraForm from '../components/ObraForm.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'inicio',
      component: Inicio
    },
    {
      path: '/tipo',
      name: 'tipo',
      component: Tipo
    },
    {
      path: '/obra',
      name: 'obra',
      component: Obra
    },
    {
      path: '/obra/:id',
      name: 'ObraForm',
      component: ObraForm
    }
  ]
})

export default router
