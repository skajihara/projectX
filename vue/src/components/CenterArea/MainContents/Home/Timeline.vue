<script setup>
import { ref, onBeforeMount } from 'vue'
import axios from 'axios'
import Tweet from '../Tweet.vue'
import { accounts } from '@/consts/accounts.js'

const tweets = ref(null)
const loading = ref(true)
const error = ref(null)
const errDtl = ref(null)
const fetchData = async () => {
  loading.value = true
  error.value = null
  try {
    const response = await axios.get('http://localhost:8081/api/tweets/recent')
    tweets.value = response.data
  } catch (err) {
    error.value = 'Failed to fetch data'
    errDtl.value = err.response ? `${err.response.status}: ${err.response.statusText}` : err.message
  } finally {
    loading.value = false
  }
}

function searchIcon(accountId) {
  return accounts.value.find((account) => account.userId === accountId).icon
}
async function deleteTweet(id) {
  try {
    await axios.delete('http://localhost:8081/api/tweets/' + id)
    window.location.reload()
  } catch (err) {
    error.value = err.response ? `${err.response.status}: ${err.response.statusText}` : err.message
  } finally {
    fetchData()
  }
}
onBeforeMount(() => {
  fetchData()
  console.log('timeline mounted.')
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
          :datetime="tweet.datetime"
          :location="tweet.location"
          :likes="tweet.likes"
          :retweet="tweet.retweet"
          :reply="tweet.reply"
          :views="tweet.views"
          :icon="searchIcon(tweet.accountId)"
          :image="tweet.image"
          @delete-tweet="deleteTweet"
        ></Tweet>
      </div>
    </template>
    <div v-else>No data.</div>
  </div>
</template>
<style scoped></style>
