<script setup>
import { ref, onBeforeMount } from 'vue'
import axios from 'axios'
import { useCurrentUserStore } from '@/stores/currentUser.js'

var isFollowing = ref(true)

const currentUser = useCurrentUserStore()
const error = ref(null)
const errDtl = ref(null)
const account = ref(null)

function toggleFollow() {
  isFollowing.value = !isFollowing.value
}

function formatRegistered(dateString) {
  const date = new Date(dateString)
  const year = date.getFullYear()
  const month = date.getMonth() + 1
  return `${year}年${month}月`
}

async function fetchData() {
  try {
    const response = await axios.get('http://localhost:8081/api/accounts/' + currentUser.userId)
    account.value = response.data
  } catch (err) {
    error.value = 'Failed to fetch data'
    errDtl.value = err.response ? `${err.response.status}: ${err.response.statusText}` : err.message
  }
}

onBeforeMount(() => {
  fetchData()
})
</script>

<template>
  <div>
    <router-link :to="{ name: 'home' }">🔙戻る</router-link>
    <p v-if="error">{{ error }}</p>
    <div v-else-if="account" class="account">
      <div class="account-header">
        <img :src="account.headerPhoto" width="600" height="200" class="header-photo" />
      </div>
      <div class="account-info">
        <div class="account-icon-border">
          <img :src="account.icon" width="150" height="150" class="account-icon" />
        </div>
        <button class="follow-button" @click="toggleFollow">
          {{ isFollowing ? 'フォロー中' : 'フォロー' }}
        </button>
        <div class="account-detail">
          <div>
            <h3 class="account-name">{{ account.name }}</h3>
            <p class="account-id">@{{ account.id }}</p>
          </div>
          <p class="account-bio">{{ account.bio }}</p>
          <p>
            <span>📌場所：{{ account.location }}</span>
            &nbsp;&nbsp;&nbsp;
            <span>🎈誕生日：{{ account.birthday }}</span>
            <br />
            <span>{{ formatRegistered(account.registered) }}からTwitterを利用しています</span>
          </p>
          <p>
            <span style="font-weight: bold">{{ account.following }}</span> フォロー中
            &nbsp;&nbsp;&nbsp;
            <span style="font-weight: bold">{{ account.follower }}</span> フォロワー
          </p>
        </div>
      </div>
    </div>
    <p v-else>Loading...</p>
  </div>
</template>
<style scoped>
.account-icon {
  position: relative;
  z-index: 2;
  margin-top: -10px;
  margin-left: -10px;
}
.account-icon-border {
  position: relative;
  z-index: 1;
  margin-top: -75px;
  margin-left: 10px;
  width: 130px;
  height: 130px;
  border-radius: 50%;
  background: white;
}
.follow-button {
  position: relative;
  top: -30px;
  margin-left: 500px;
  width: 100px;
}
.account-detail {
  margin-top: -20px;
}
</style>
