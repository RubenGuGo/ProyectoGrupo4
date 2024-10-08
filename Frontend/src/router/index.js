import { createRouter, createWebHistory } from 'vue-router'
import Inicio from '../views/Inicio.vue'
import Tipo from '../views/Tipo.vue'
import Obra from '../views/Obra.vue'
import ObraForm from '../components/ObraForm.vue'
import TipoForm from '../components/TipoForm.vue'

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
      path: '/obraForm/:id?',
      name: 'ObraForm',
      component: ObraForm
    },
    {
      path: '/tipoForm/:id?',
      name: 'TipoForm',
      component: TipoForm
    }
  ]
})

export default router
