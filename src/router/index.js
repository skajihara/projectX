import { createRouter, createWebHistory } from 'vue-router'
import TimelineView from '../views/TimelineView.vue'
import ProfileView from '../views/ProfileView.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      name: 'timeline',
      component: TimelineView
    },
    {
      path: '/profile',
      name: 'profile',
      component: ProfileView,
      props: true
    }
  ]
})

export default router
