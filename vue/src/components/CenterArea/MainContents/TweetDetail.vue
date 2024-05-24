<script setup>
import { ref, defineProps, onMounted } from 'vue'
import { tweets } from '@/consts/tweets.js'
import { accounts } from '@/consts/accounts.js'
import { useCurrentUserStore } from '@/stores/currentUser.js'

const currentUser = useCurrentUserStore()
const tweet = ref(null)
const icon = ref('')
const props = defineProps({
  id: {
    type: String,
    required: true
  }
})

function deleteTweet(id) {
  const index = tweets.value.findIndex((tweet) => tweet.id === id)
  if (index !== -1) {
    tweets.value.splice(index, 1)
  }
}

function findTweet(id) {
  return tweets.value.find((tweet) => {
    return tweet.id == id
  })
}

function searchIcon(userId) {
  const account = accounts.value.find((account) => account.userId === userId)
  return account ? account.icon : ''
}

onMounted(() => {
  tweet.value = findTweet(props.id)
  if (tweet.value) {
    icon.value = searchIcon(tweet.value.userId)
  } else {
    console.log('Not retrieved.')
  }
})
</script>

<template>
  <div v-if="tweet" class="content">
    <div class="tweet-header">
      <img class="user-icon" :src="icon" width="50" height="50" />
      <div v-show="tweet.userId === currentUser.userId">
        <BButton pill size="sm" @click="deleteTweet(props.id)">削除</BButton>
      </div>
    </div>
    <pre class="tweet-text">{{ tweet.content }}</pre>
    <div v-if="tweet.image" class="tweet-image">
      <img :src="tweet.image" style="max-width: 500px; max-height: 200px" />
    </div>
    <div class="tweet-info">
      <pre>{{ tweet.datetime }} tweet by @{{ tweet.userId }}  <b>{{ tweet.views }}</b> Views</pre>
      <div class="tweet-activity">
        <div>
          <BButton variant="link">
            <img src="@/assets/icons/tweet/reply.svg" width="15" height="15" />
          </BButton>
          <span class="disabled-text">{{ tweet.reply }}</span>
        </div>
        <div>
          <BButton variant="link">
            <img src="@/assets/icons/tweet/retweet.svg" width="15" height="15" />
          </BButton>
          <span class="disabled-text">{{ tweet.retweet }}</span>
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
