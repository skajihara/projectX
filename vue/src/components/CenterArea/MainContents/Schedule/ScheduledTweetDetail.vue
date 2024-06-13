<script setup>
import { ref, onBeforeMount } from 'vue'
import { useRouter } from 'vue-router'
import { useCurrentUserStore } from '@/stores/currentUser.js'
import { formatDateTime } from '@/utils/formatDateTime.js'
import axios from 'axios'

const props = defineProps({
  scheduleId: {
    type: Number,
    required: true
  }
})
const router = useRouter()
const currentUser = useCurrentUserStore()

const tweet = ref(null)
const account = ref(null)
const loading = ref(true)
const error = ref(null)
const errDtl = ref(null)
const isEditMode = ref(false)
const editedText = ref('')
const editedScheduledDatetime = ref('')

const handleDateTimeInput = (event) => {
  // カレンダーから選択された値が 5 分単位でない場合、最も近い 5 分単位に丸める
  const selectedDatetime = new Date(event.target.value)
  const minutes = selectedDatetime.getMinutes()
  const roundedMinutes = Math.round(minutes / 5) * 5
  selectedDatetime.setMinutes(roundedMinutes)
  selectedDatetime.setHours(selectedDatetime.getHours() + 9)
  editedScheduledDatetime.value = selectedDatetime.toISOString().slice(0, 16)
}

const fetchData = async () => {
  loading.value = true
  error.value = null
  try {
    const resTweet = await axios.get('http://localhost:8081/api/schedule/' + props.scheduleId)
    tweet.value = resTweet.data
    if (tweet.value) {
      const resAccount = await axios.get(
        'http://localhost:8081/api/accounts/' + tweet.value.accountId
      )
      account.value = resAccount.data
      editedText.value = tweet.value.text
      // 取得した scheduledDatetime を 9 時間進める
      const scheduledDatetime = new Date(tweet.value.scheduledDatetime)
      scheduledDatetime.setHours(scheduledDatetime.getHours() + 9)
      editedScheduledDatetime.value = scheduledDatetime.toISOString().slice(0, 16)
    } else {
      console.log('Not retrieved.')
    }
  } catch (err) {
    error.value = 'Failed to fetch data'
    errDtl.value = err.response ? `${err.response.status}: ${err.response.statusText}` : err.message
  } finally {
    loading.value = false
  }
}

async function updateTweet() {
  try {
    tweet.value.text = editedText.value
    tweet.value.scheduledDatetime = formatDateTime(editedScheduledDatetime.value, 'update')
    tweet.value.createdDatetime = formatDateTime(Date.now(), 'update')
    await axios.put('http://localhost:8081/api/schedule/' + tweet.value.id, tweet.value)
    isEditMode.value = false
  } catch (err) {
    error.value = err.response ? `${err.response.status}: ${err.response.statusText}` : err.message
  } finally {
    router.replace({ name: 'schedule', params: { userId: currentUser.userId } })
  }
}

async function deleteTweet(id) {
  try {
    await axios.delete('http://localhost:8081/api/schedule/' + id)
  } catch (err) {
    error.value = err.response ? `${err.response.status}: ${err.response.statusText}` : err.message
  } finally {
    router.replace({ name: 'schedule', params: { userId: currentUser.userId } })
  }
}
onBeforeMount(() => {
  fetchData()
})
</script>

<template>
  <div v-if="loading">Loading...</div>
  <div v-else-if="error">
    <p>{{ error }}</p>
    <p>{{ errDtl }}</p>
  </div>
  <div v-else-if="tweet" class="content">
    <div>
      <router-link :to="{ name: 'schedule', params: { userId: currentUser.userId } }"
        >🔙戻る</router-link
      >
    </div>
    <div class="tweet-header">
      <img class="user-icon" :src="account.icon" width="50" height="50" />
      <span>{{ account.name }}</span>
      <div v-show="tweet.accountId === currentUser.userId" class="action-buttons-1">
        <BButton pill size="sm" @click="isEditMode = true">編集</BButton>&nbsp;
        <BButton pill size="sm" @click="deleteTweet(tweet.id)">削除</BButton>
      </div>
    </div>
    <div v-if="isEditMode">
      <textarea v-model="editedText" rows="5" cols="40"></textarea>
      <br />
      <div class="edit-area">
        <input
          v-model="editedScheduledDatetime"
          class="datetime-input"
          type="datetime-local"
          :min="new Date().toISOString().slice(0, 16)"
          step="300"
          @input="handleDateTimeInput"
        />
        <div class="action-buttons-2">
          <BButton pill size="sm" class="button-2" @click="updateTweet">更新</BButton>&nbsp;
          <BButton pill size="sm" class="button-2" @click="isEditMode = false">キャンセル</BButton>
        </div>
      </div>
    </div>
    <div v-else>
      <pre class="tweet-text">{{ tweet.text }}</pre>
    </div>
    <div v-if="tweet.image" class="tweet-image">
      <img :src="tweet.image" style="max-width: 500px; max-height: 200px" />
    </div>
    <div class="tweet-info">
      <span>ツイート予定：{{ formatDateTime(tweet.scheduledDatetime, 'display') }}</span>
      <br />
      <span>登録日時：{{ formatDateTime(tweet.createdDatetime, 'display') }}</span>
    </div>
  </div>
  <div v-else>
    <p>Tweet was not found or Error has occurred.</p>
  </div>
</template>
<style scoped>
.content {
  width: 600px;
  border: 1px solid rgba(200, 200, 200, 0.6);
  border-radius: 5px;
  padding: 10px 10px;
}
.action-buttons-1 {
  display: inline-block;
  text-align: right;
  margin-left: auto;
}
.edit-area {
  display: inline-flex;
  text-align: center;
  align-items: center;
  margin-bottom: 5px;
}
.datetime-input {
  margin-right: 5px;
  text-align: center;
}
.button-2 {
  display: inline-flex;
  height: 20px;
  font-size: 12px;
  text-align: center;
  align-items: center;
}
.tweet-header {
  width: 540px;
  display: flex;
  align-items: center;
}
.tweet-text {
  word-wrap: break-word;
  white-space: pre-wrap;
  width: 50ch;
  font-size: 15pt;
}
.tweet-image {
  padding-bottom: 20px;
}
.tweet-info {
  color: gray;
  position: relative;
}
.disabled-text {
  color: gray;
  position: relative;
  left: -5px;
  top: 3px;
}
</style>
