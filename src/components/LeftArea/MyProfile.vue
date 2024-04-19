<script setup>
import { ref } from 'vue'
import { onMounted } from 'vue'

import { accounts } from '@/consts/accounts.js'

const currentUserId = 'q30387'
const myAccount = ref(null)

onMounted(() => {
  searchMyAccount()
})

function searchMyAccount() {
  myAccount.value = accounts.value.find((account) => account.userId === currentUserId)
}
</script>
<template>
  <div>
    <img alt="myicon" class="my-icon" src="@/assets/icons/myicon.svg" width="70" height="70" />
    <div class="my-info">
      <p>{{ myAccount.userName }}</p>
      <p>@{{ myAccount.userId }}</p>
    </div>
  </div>
</template>
