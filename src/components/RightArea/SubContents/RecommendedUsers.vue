<script setup>
import { ref, onMounted } from 'vue'
import { accounts } from '@/consts/accounts.js'

const randomUsers = ref([])

// ランダムに10件のユーザを選択する
function pickRandom10Users() {
  const users = accounts.value.slice(1)
  const shuffledUserList = users.sort(() => Math.random() - 0.5)
  randomUsers.value = shuffledUserList.slice(0, 10)
}
onMounted(() => {
  pickRandom10Users()
})
</script>
<template>
  <div>
    <h3 style="padding-top: 10px">おすすめユーザー</h3>
    <div v-for="(user, index) in randomUsers" :key="index" class="users">
      <img class="user-icon" :src="user.icon" width="50" height="50" />
      <div class="user-info">
        <span>{{ user.userName }}</span>
        <br />
        <span style="font-size: small; color: gray">@{{ user.userId }}</span>
      </div>
    </div>
  </div>
</template>
<style scoped>
.recommended-users h3 {
  margin: 10px 10px;
}
.users {
  display: flex;
  align-items: center;
  margin-left: 10px;
}
.user-icon {
  margin-right: 10px;
}
.user-info {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  line-height: 10px;
}
</style>
