<script setup>
import { ref, onBeforeMount } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'
import Tweet from '../Tweet.vue'

const router = useRouter()
const tweets = ref(null)
const accounts = ref(null)
const loading = ref(true)
const error = ref(null)
const errDtl = ref(null)

function searchAccount(accountId) {
  return accounts.value.find((account) => account.id === accountId)
}
async function fetchData() {
  loading.value = true
  error.value = null
  try {
    const resAccounts = await axios.get('http://localhost:8081/api/accounts')
    accounts.value = resAccounts.data
    const resTweets = await axios.get('http://localhost:8081/api/tweets/recent')
    tweets.value = resTweets.data
  } catch (err) {
    error.value = 'Failed to fetch data'
    errDtl.value = err.response ? `${err.response.status}: ${err.response.statusText}` : err.message
  } finally {
    loading.value = false
  }
}
async function deleteTweet(id) {
  const confirmed = window.confirm('この予約ツイートを削除しますか？')
  if (confirmed) {
    try {
      await axios.delete('http://localhost:8081/api/tweets/' + id)
      router.replace({ name: 'home' }).then(() => {
        fetchData()
      })
    } catch (err) {
      error.value = err.response
        ? `${err.response.status}: ${err.response.statusText}`
        : err.message
    }
  }
}
onBeforeMount(() => {
  fetchData()
})
</script>

<template>
  <div>
    <div v-if="loading">Loading...</div>
    <div v-else-if="error">
      <p>{{ error }}</p>
      <p>{{ errDtl }}</p>
    </div>
    <template v-else-if="tweets">
      <div v-for="(tweet, index) in tweets" :key="index" class="tweet">
        <Tweet
          :id="tweet.id"
          :tweet-content="tweet.text"
          :account-id="tweet.accountId"
          :account-name="searchAccount(tweet.accountId).name"
          :datetime="tweet.datetime"
          :location="tweet.location"
          :likes="tweet.likes"
          :retweet="tweet.retweet"
          :reply="tweet.reply"
          :views="tweet.views"
          :icon="searchAccount(tweet.accountId).icon"
          :image="tweet.image"
          @delete-tweet="deleteTweet"
        ></Tweet>
      </div>
    </template>
    <div v-else>No data.</div>
  </div>
</template>
<style scoped></style>
