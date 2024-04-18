import { createRouter, createWebHistory } from 'vue-router'

// ホーム
import Home from '../views/Home.vue'
// 話題を検索
import Explore from '../views/Explore.vue'
// 通知
import Notifications from '../views/Notifications.vue'
// メッセージ
import Messages from '../views/Messages.vue'
// リスト
import Lists from '../views/Lists.vue'
// ブックマーク
import Bookmarks from '../views/Bookmarks.vue'
// コミュニティ
import Communities from '../views/Communities.vue'
// プレミアム
import Premium from '../views/Premium.vue'
// プロフィール
import Profile from '../views/Profile.vue'
// エラー
import NotFound from '../views/NotFound.vue'

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
      component: Profile
    },
    {
      path: '/:catchError(.*)*',
      name: '#',
      component: NotFound
    }
  ]
})

export default router
