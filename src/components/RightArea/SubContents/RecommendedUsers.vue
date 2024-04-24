<script setup>
import { ref, onMounted } from 'vue'
import { accounts } from '@/consts/accounts.js'

const randomUsers = ref([])

// ランダムに10件のユーザを選択する
function selectRandomUsers() {
  const users = accounts.value.slice()
  const selectedUsers = []
  while (selectedUsers.length < 10 && users.length > 0) {
    const randomIndex = Math.floor(Math.random() * (users.length - 1))
    const selectedUser = users.splice(randomIndex, 1)[0]
    if (selectedUser.userId === 'q30387') {
      // 自分の情報は除外する
      continue
    }
    if (!selectedUser) {
      // undefinedの場合も除外する
      continue
    }
    selectedUsers.push(users.splice(randomIndex, 1)[0])
  }
  randomUsers.value = selectedUsers
}
// 画面の更新時に再度ユーザを選択する
onMounted(() => {
  selectRandomUsers()
  console.log(randomUsers.value)
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
