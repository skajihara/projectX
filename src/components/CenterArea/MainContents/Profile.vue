<script setup>
import { ref } from 'vue'
import { defineProps } from 'vue'
import { onBeforeMount, onMounted } from 'vue'
import { accounts } from '@/consts/accounts.js'

//外部ファイルにアカウント情報を用意する
//definePropsでユーザIDを受け取り
//ユーザ情報を検索取得する（該当無しの場合はデフォルト値）
//取得した情報を画面に表示する
const props = defineProps({
  userId: {
    type: String,
    required: true
  }
})

const AccountInfo = ref(null)

onBeforeMount(() => {
  getAccountInfo()
  console.log('beforeMount')
})

onMounted(() => {
  console.log('onMounted')
})

function getAccountInfo() {
  const foundAccount = accounts.value.find((account) => account.userId === props.userId)
  if (foundAccount) {
    AccountInfo.value = foundAccount
    console.log(AccountInfo.value)
  } else {
    AccountInfo.value = { userName: 'noName', userId: 'noId', useBio: '' }
    console.error('Account not found')
  }
}
</script>

<template>
  <h1>プロフィール</h1>
  <div>
    <router-link :to="{ name: 'home' }">🔙戻る</router-link>
    <p>ユーザーID: {{ AccountInfo.userId }}</p>
    <p>ユーザー名: {{ AccountInfo.userName }}</p>
    <p>自己紹介: {{ AccountInfo.userBio }}</p>
    <p>{{ $route }}</p>
  </div>
</template>
