<script setup>
import { ref, onBeforeMount } from 'vue'
import { format } from 'date-fns'
import { useCurrentUserStore } from '@/stores/currentUser.js'
import axios from 'axios'

const newTweetContent = ref('')
const currentUser = useCurrentUserStore()
const account = ref(null)
const response = ref(null)
const error = ref(null)
const createTweet = async () => {
  if (newTweetContent.value.trim() !== '') {
    error.value = null
    response.value = null

    const tweet = ref({
      accountId: currentUser.userId,
      text: newTweetContent.value,
      image: '',
      likes: 0,
      retweets: 0,
      replies: 0,
      views: 0,
      datetime: format(Date.now(), "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"),
      location: 'somewhere',
      deleteFlag: false
    })

    try {
      await axios.post('http://localhost:8081/api/tweets', tweet.value)
      window.location.reload()
    } catch (err) {
      error.value = err.response
        ? `${err.response.status}: ${err.response.statusText}`
        : err.message
    } finally {
      newTweetContent.value = ''
    }
  }
}
async function fetchData() {
  try {
    const response = await axios.get('http://localhost:8081/api/accounts/' + currentUser.userId)
    account.value = response.data
  } catch (err) {
    error.value = err.response ? `${err.response.status}: ${err.response.statusText}` : err.message
  }
}
onBeforeMount(() => {
  fetchData()
})
</script>
<template>
  <div>
    <div v-if="account" class="tweet-form">
      <router-link :to="{ name: 'profile', params: { userId: account.id } }">
        <img class="user-icon" :src="account.icon" width="50" height="50" />
      </router-link>
      <BButton pill variant="primary" :disabled="newTweetContent === ''" @click="createTweet">
        ツイート
      </BButton>
      <b-form-textarea
        id="new-tweet"
        v-model="newTweetContent"
        placeholder="いまどうしてる？"
        rows="3"
        max-rows="6"
        :maxlength="280"
        style="width: 600px"
      />
    </div>
  </div>
</template>
<style scoped>
.tweet-form {
  margin-bottom: 10px;
}
</style>
