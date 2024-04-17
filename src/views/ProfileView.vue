<script setup>
import { ref } from 'vue'
import { useRoute } from 'vue-router'
const route = useRoute()
const myProfile = ref({
  userId: route.params.userId,
  userName: route.params.userName,
  userBio: route.params.userBio
})
</script>

<template>
  <div>
    <h1>プロフィール</h1>
    <p>ユーザーID: {{ myProfile.userId }}</p>
    <p>ユーザー名: {{ myProfile.userName }}</p>
    <p>自己紹介: {{ myProfile.userBio }}</p>
    <router-link :to="{ name: 'timeline' }">タイムラインへ</router-link>
    <p>{{ $route }}</p>
  </div>
</template>
