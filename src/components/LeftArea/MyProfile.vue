<script setup>
import { ref } from 'vue'
import { onBeforeMount } from 'vue'

import { accounts } from '@/consts/accounts.js'

const currentUserId = 'q30387'
const myAccount = ref(null)

onBeforeMount(() => {
  searchMyAccount()
})

function searchMyAccount() {
  const foundAccount = accounts.value.find((account) => account.userId === currentUserId)
  if (foundAccount) {
    myAccount.value = foundAccount
  } else {
    myAccount.value = { userName: 'noName', userId: 'noId', useBio: '' }
    console.error('Account not found')
  }
}
</script>
<template>
  <div>
    <img alt="myicon" class="my-icon" src="@/assets/icons/user/myicon.svg" width="70" height="70" />
    <div class="my-info">
      <span>{{ myAccount.userName }}</span>
      <br />
      <span style="font-size: small; color: gray">@{{ myAccount.userId }}</span>
    </div>
  </div>
</template>
<style scoped>
.my-info {
  position: relative;
  left: 10px;
}
</style>
