import { createRouter, createWebHistory } from 'vue-router'

import Home from '@/components/CenterArea/MainContents/Home.vue'
import Explore from '@/components/CenterArea/MainContents/Explore.vue'
import Notifications from '@/components/CenterArea/MainContents/Notifications.vue'
import Messages from '@/components/CenterArea/MainContents/Messages.vue'
import Lists from '@/components/CenterArea/MainContents/Lists.vue'
import Bookmarks from '@/components/CenterArea/MainContents/Bookmarks.vue'
import Communities from '@/components/CenterArea/MainContents/Communities.vue'
import Premium from '@/components/CenterArea/MainContents/Premium.vue'
import Profile from '@/components/CenterArea/MainContents/Profile.vue'
import TweetDetail from '@/components/CenterArea/MainContents/TweetDetail.vue'
import NotFound from '@/components/NotFound.vue'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    {
      path: '/',
      redirect: 'home'
    },
    {
      path: '/home',
      name: 'home',
      component: Home
    },
    {
      path: '/explore',
      name: 'explore',
      component: Explore
    },
    {
      path: '/notifications',
      name: 'notifications',
      component: Notifications
    },
    {
      path: '/messages',
      name: 'messages',
      component: Messages
    },
    {
      path: '/:userId/lists',
      name: 'lists',
      component: Lists
    },
    {
      path: '/bookmarks',
      name: 'bookmarks',
      component: Bookmarks
    },
    {
      path: '/:userId/profile',
      name: 'profile',
      component: Profile
    },
    {
      path: '/communities',
      name: 'communities',
      component: Communities
    },
    {
      path: '/premium',
      name: 'premium',
      component: Premium
    },
    {
      path: '/:userId/profile',
      name: 'profile',
      component: Profile,
      props: true
    },
    {
      path: '/tweet/:id',
      name: 'tweet-detail',
      component: TweetDetail,
      props: true
    },
    {
      path: '/:catchError(.*)*',
      name: '#',
      component: NotFound
    }
  ]
})

export default router
