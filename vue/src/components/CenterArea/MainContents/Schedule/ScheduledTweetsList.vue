<script setup>
import { ref, onBeforeMount } from 'vue'
import { useCurrentUserStore } from '@/stores/currentUser.js'
import axios from 'axios'
import ScheduledTweet from './ScheduledTweet.vue'

const currentUser = useCurrentUserStore()
const accounts = ref(null)
const tweets = ref(null)
const loading = ref(true)
const error = ref(null)
const errDtl = ref(null)
async function fetchData() {
  loading.value = true
  error.value = null
  try {
    const resAccounts = await axios.get('http://localhost:8081/api/accounts')
    accounts.value = resAccounts.data
    const resTweets = await axios.get(
      'http://localhost:8081/api/schedule/account/' + currentUser.userId
    )
    tweets.value = resTweets.data
  } catch (err) {
    error.value = 'Failed to fetch data'
    errDtl.value = err.response ? `${err.response.status}: ${err.response.statusText}` : err.message
  } finally {
    loading.value = false
  }
}
async function deleteTweet(id) {
  try {
    await axios.delete('http://localhost:8081/api/schedule/' + id)
    window.location.reload()
  } catch (err) {
    error.value = err.response ? `${err.response.status}: ${err.response.statusText}` : err.message
  } finally {
    fetchData()
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
        <ScheduledTweet
          :schedule-id="tweet.id"
          :account-id="tweet.accountId"
          :text="tweet.text"
          :image="tweet.image"
          :location="tweet.location"
          :scheduled-datetime="tweet.scheduledDatetime"
          :created-datetime="tweet.createdDatetime"
          @delete-tweet="deleteTweet"
        ></ScheduledTweet>
      </div>
    </template>
    <div v-else>No data.</div>
  </div>
</template>
<style scoped></style>
