<script setup>
import { ref, onBeforeMount } from 'vue'
import { useRouter } from 'vue-router'
import axios from 'axios'
import { useCurrentUserStore } from '@/stores/currentUser.js'

const props = defineProps({
  id: {
    type: String,
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
const fetchData = async () => {
  loading.value = true
  error.value = null
  try {
    const resTweet = await axios.get('http://localhost:8081/api/tweets/tweet/' + props.id)
    tweet.value = resTweet.data
    if (tweet.value) {
      const resAccount = await axios.get(
        'http://localhost:8081/api/accounts/' + tweet.value.accountId
      )
      account.value = resAccount.data
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
    <div>
      <router-link :to="{ name: 'home' }">🔙戻る</router-link>
    </div>
    <div class="tweet-header">
      <router-link :to="{ name: 'profile', params: { userId: account.id } }">
        <img class="user-icon" :src="account.icon" width="50" height="50" />
      </router-link>
      <span>{{ account.name }}</span>
      <div v-show="tweet.accountId === currentUser.userId" class="delete-button">
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
.delete-button {
  display: inline-block;
  text-align: right;
  margin-left: auto;
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
