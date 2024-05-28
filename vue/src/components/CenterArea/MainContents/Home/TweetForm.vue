<script setup>
import { ref } from 'vue'
import { format } from 'date-fns'
import { useCurrentUserStore } from '@/stores/currentUser.js'
import axios from 'axios'

const newTweetContent = ref('')
const currentUser = useCurrentUserStore()
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
      const res = await axios.post('http://localhost:8081/api/tweets', tweet.value)
      response.value = res.data
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
</script>
<template>
  <div>
    <div class="tweet-form">
      <img class="user-icon" src="@/assets/icons/user/myicon.svg" width="50" height="50" />
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
