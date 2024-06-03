<script setup>
import { ref, onBeforeMount } from 'vue'
import axios from 'axios'
import { useCurrentUserStore } from '@/stores/currentUser.js'

const currentUser = useCurrentUserStore()
const error = ref(null)
const errDtl = ref(null)
const account = ref(null)

async function fetchData() {
  try {
    const response = await axios.get('http://localhost:8081/api/accounts/' + currentUser.userId)
    account.value = response.data
  } catch (err) {
    error.value = 'Failed to fetch data'
    errDtl.value = err.response ? `${err.response.status}: ${err.response.statusText}` : err.message
  } finally {
    // 何か後処理
  }
}

onBeforeMount(() => {
  fetchData()
})
</script>

<template>
  <div>
    <h1>プロフィール</h1>
    <div>
      <router-link :to="{ name: 'home' }">🔙戻る</router-link>
      <p v-if="error">{{ error }}</p>
      <div v-else-if="account">
        <p>ユーザーID: {{ account.id }}</p>
        <p>ユーザー名: {{ account.name }}</p>
        <p>自己紹介: {{ account.bio }}</p>
      </div>
      <p v-else>Loading...</p>
    </div>
  </div>
</template>
