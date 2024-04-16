<script setup>
import { useRoute } from 'vue-router'
const route = useRoute()
console.log(route)
</script>

<template>
  <div>
    <h1>プロフィール</h1>
    <p>ユーザーID: {{ $route.params.userId }}</p>
    <p>ユーザー名: {{ $route.params.userName }}</p>
    <p>自己紹介: {{ $route.params.userBio }}</p>
    <router-link :to="{ name: 'timeline' }">タイムラインへ</router-link>
    <p>{{ $route }}</p>
  </div>
</template>
