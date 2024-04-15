<script setup>
defineProps({
  userId: {
    type: Number,
    required: true
  },
  userName: {
    type: String,
    required: true
  },
  bio: {
    type: String,
    required: true
  }
})
</script>

<template>
  <div>
    <h1>プロフィール</h1>
    <p>ユーザー名: {{ userName }}</p>
    <p>ユーザーID: {{ userId }}</p>
    <p>自己紹介: {{ bio }}</p>
    <router-link :to="{ name: 'timeline' }">タイムラインへ</router-link>
  </div>
</template>
