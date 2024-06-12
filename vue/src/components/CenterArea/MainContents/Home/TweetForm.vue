<script setup>
import { ref, onBeforeMount } from 'vue'
import { format } from 'date-fns'
import { useCurrentUserStore } from '@/stores/currentUser.js'
import axios from 'axios'
import { formatDateTime } from '@/utils/formatDateTime.js'

const newTweetContent = ref('')
const scheduledTweetContent = ref('')
const scheduledDatetime = ref('')
const currentUser = useCurrentUserStore()
const account = ref(null)
const response = ref(null)
const error = ref(null)
const isScheduledMode = ref(false)

const createTweet = async () => {
  if (newTweetContent.value.trim() !== '') {
    error.value = null
    response.value = null

    const tweet = {
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
    }

    try {
      await axios.post('http://localhost:8081/api/tweets', tweet)
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

const createScheduledTweet = async () => {
  if (scheduledTweetContent.value.trim() !== '' && scheduledDatetime.value) {
    error.value = null
    response.value = null

    const scheduledTweet = {
      accountId: currentUser.userId,
      text: scheduledTweetContent.value,
      image: '',
      location: 'somewhere',
      scheduledDatetime: formatDateTime(scheduledDatetime.value, 'update'),
      createdDatetime: formatDateTime(new Date().toISOString(), 'update'),
      deleteFlag: false
    }

    try {
      await axios.post('http://localhost:8081/api/schedule', scheduledTweet)
      window.location.reload()
    } catch (err) {
      error.value = err.response
        ? `${err.response.status}: ${err.response.statusText}`
        : err.message
    } finally {
      scheduledTweetContent.value = ''
      scheduledDatetime.value = ''
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
      <div>
        <BButton
          pill
          variant="primary"
          :disabled="!isScheduledMode"
          @click="isScheduledMode = false"
          >通常モード</BButton
        >
        <BButton pill variant="primary" :disabled="isScheduledMode" @click="isScheduledMode = true"
          >予約モード</BButton
        >
      </div>
      <div v-if="!isScheduledMode">
        <b-form-textarea
          id="new-tweet"
          v-model="newTweetContent"
          placeholder="いまどうしてる？"
          rows="3"
          max-rows="6"
          :maxlength="280"
          style="width: 600px"
        />
        <BButton pill variant="primary" :disabled="newTweetContent === ''" @click="createTweet">
          ツイート
        </BButton>
      </div>
      <div v-else>
        <b-form-textarea
          id="scheduled-tweet"
          v-model="scheduledTweetContent"
          placeholder="いまどうしてる？"
          rows="3"
          max-rows="6"
          :maxlength="280"
          style="width: 600px"
        />
        <input
          v-model="scheduledDatetime"
          type="datetime-local"
          :min="new Date().toISOString().slice(0, 16)"
        />
        <BButton
          pill
          variant="primary"
          :disabled="scheduledTweetContent === '' || !scheduledDatetime"
          @click="createScheduledTweet"
        >
          予約ツイート
        </BButton>
      </div>
    </div>
    <div v-if="error" class="error-message">
      <p>{{ error }}</p>
    </div>
  </div>
</template>
<style scoped>
.tweet-form {
  margin-bottom: 10px;
}
</style>
