<script setup>
import { ref, onBeforeMount } from 'vue'
import { format } from 'date-fns'
import { useCurrentUserStore } from '@/stores/currentUser.js'
import axios from 'axios'
import { formatDateTime } from '@/utils/formatDateTime.js'
import eventBus from '@/utils/eventBus.js'

const newTweetContent = ref('')
const scheduledTweetContent = ref('')
const scheduledDatetime = ref('')
const currentUser = useCurrentUserStore()
const account = ref(null)
const response = ref(null)
const error = ref(null)
const isScheduledMode = ref(false)
const modeText = ref('通常モード')
const dateError = ref(null)

const handleDateTimeInput = (event) => {
  // カレンダーから選択された値が 5 分単位でない場合、最も近い 5 分単位に丸める
  const selectedDatetime = new Date(event.target.value)
  const now = new Date()

  if (selectedDatetime < now) {
    dateError.value = '過去の日時は選択できません。'
  } else {
    dateError.value = null
    const minutes = selectedDatetime.getMinutes()
    const roundedMinutes = Math.round(minutes / 5) * 5
    selectedDatetime.setMinutes(roundedMinutes)
    selectedDatetime.setHours(selectedDatetime.getHours() + 9)
    scheduledDatetime.value = selectedDatetime.toISOString().slice(0, 16)
  }
}
const selectMode = (isScheduled) => {
  isScheduledMode.value = isScheduled
  modeText.value = isScheduled ? '予約モード' : '通常モード'
}

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
      eventBus.value.get('update-timeline')()
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
  if (scheduledTweetContent.value.trim() !== '' && scheduledDatetime.value && !dateError.value) {
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
      // window.location.reload()
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
      <div>
        <router-link :to="{ name: 'profile', params: { userId: account.id } }">
          <img class="user-icon" :src="account.icon" width="50" height="50" />
        </router-link>
        <b-dropdown id="mode-dropdown" :text="modeText" class="m-2">
          <b-dropdown-item @click="selectMode(false)">通常モード</b-dropdown-item>
          <b-dropdown-item @click="selectMode(true)">予約モード</b-dropdown-item>
        </b-dropdown>
      </div>
      <div v-if="!isScheduledMode" class="textarea">
        <b-form-textarea
          id="new-tweet"
          v-model="newTweetContent"
          placeholder="いまどうしてる？"
          rows="3"
          max-rows="6"
          :maxlength="280"
          style="width: 600px"
        />
        <BButton
          pill
          variant="primary"
          class="tweet-button"
          :disabled="newTweetContent === ''"
          @click="createTweet"
        >
          ツイート
        </BButton>
      </div>
      <div v-else class="textarea">
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
          class="datetime-input"
          type="datetime-local"
          :min="new Date().toISOString().slice(0, 16)"
          step="300"
          @input="handleDateTimeInput"
        />
        <BButton
          pill
          variant="primary"
          class="tweet-button"
          :disabled="scheduledTweetContent === '' || !scheduledDatetime || dateError"
          @click="createScheduledTweet"
        >
          予約ツイート
        </BButton>
        <p v-if="dateError" class="error-message">{{ dateError }}</p>
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
.tweet-button {
  display: inline-flex;
  height: 20px;
  font-size: 12px;
  text-align: center;
  align-items: center;
}
.datetime-input {
  display: inline-flex;
  height: 20px;
  font-size: 12px;
  text-align: center;
  align-items: center;
  margin-right: 5px;
}
.error-message {
  color: red;
}
</style>
