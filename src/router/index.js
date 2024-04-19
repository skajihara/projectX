import { createRouter, createWebHistory } from 'vue-router'

// ホーム
import Home from '@/components/CenterArea/MainContents/Home.vue'
// 話題を検索
import Explore from '@/components/CenterArea/MainContents/Explore.vue'
// 通知
import Notifications from '@/components/CenterArea/MainContents/Notifications.vue'
// メッセージ
import Messages from '@/components/CenterArea/MainContents/Messages.vue'
// リスト
import Lists from '@/components/CenterArea/MainContents/Lists.vue'
// ブックマーク
import Bookmarks from '@/components/CenterArea/MainContents/Bookmarks.vue'
// コミュニティ
import Communities from '@/components/CenterArea/MainContents/Communities.vue'
// プレミアム
import Premium from '@/components/CenterArea/MainContents/Premium.vue'
// プロフィール
import Profile from '@/components/CenterArea/MainContents/Profile.vue'
// エラー
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
