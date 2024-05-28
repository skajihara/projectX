<script setup>
import { ref, onBeforeMount } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'
import { accounts } from '@/consts/accounts.js'
import { useCurrentUserStore } from '@/stores/currentUser.js'

const props = defineProps({
  id: {
    type: String,
    required: true
  }
})
const router = useRouter()
const currentUser = useCurrentUserStore()

const tweets = ref(null)
const tweet = ref(null)
const icon = ref('')
const loading = ref(true)
const error = ref(null)
const errDtl = ref(null)
const fetchData = async () => {
  loading.value = true
  error.value = null
  try {
    const response = await axios.get('http://localhost:8081/api/tweets/recent')
    tweets.value = response.data
    tweet.value = findTweet(props.id)
    if (tweet.value) {
      icon.value = searchIcon(tweet.value.accountId)
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

function findTweet(id) {
  if (!tweets.value) {
    return null
  }
  return tweets.value.find((tweet) => {
    return tweet.id == id
  })
}

function searchIcon(accountId) {
  const account = accounts.value.find((account) => account.userId === accountId)
  return account ? account.icon : ''
}

async function deleteTweet(id) {
  try {
    await axios.delete('http://localhost:8081/api/tweets/' + id)
  } catch (err) {
    error.value = err.response ? `${err.response.status}: ${err.response.statusText}` : err.message
  } finally {
    router.replace({ name: 'home' })
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
    <div class="tweet-header">
      <img class="user-icon" :src="icon" width="50" height="50" />
      <div v-show="tweet.accountId === currentUser.userId">
        <BButton pill size="sm" @click="deleteTweet(tweet.id)">削除</BButton>
      </div>
    </div>
    <pre class="tweet-text">{{ tweet.text }}</pre>
    <div v-if="tweet.image" class="tweet-image">
      <img :src="tweet.image" style="max-width: 500px; max-height: 200px" />
    </div>
    <div class="tweet-info">
      <pre>{{ tweet.datetime }} tweet by @{{ tweet.accountId }}  <b>{{ tweet.views }}</b> Views</pre>
      <div class="tweet-activity">
        <div>
          <BButton variant="link">
            <img src="@/assets/icons/tweet/reply.svg" width="15" height="15" />
          </BButton>
          <span class="disabled-text">{{ tweet.replies }}</span>
        </div>
        <div>
          <BButton variant="link">
            <img src="@/assets/icons/tweet/retweet.svg" width="15" height="15" />
          </BButton>
          <span class="disabled-text">{{ tweet.retweets }}</span>
        </div>
        <div>
          <BButton variant="link">
            <img src="@/assets/icons/tweet/likes.svg" width="15" height="15" />
          </BButton>
          <span class="disabled-text">{{ tweet.likes }}</span>
        </div>
        <div>
          <BButton variant="link">
            <img src="@/assets/icons/tweet/views.svg" width="15" height="15" />
          </BButton>
          <span class="disabled-text">{{ tweet.views }}</span>
        </div>
      </div>
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
.tweet-header {
  width: 540px;
  display: flex;
  align-items: center;
  justify-content: space-between;
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
  top: -15px;
}
.tweet-activity {
  width: 400px;
  display: grid;
  grid-template-columns: repeat(4, 80px);
  grid-column-gap: 50px;
  align-items: center;
}
.disabled-text {
  color: gray;
  position: relative;
  left: -5px;
  top: 3px;
}
</style>
