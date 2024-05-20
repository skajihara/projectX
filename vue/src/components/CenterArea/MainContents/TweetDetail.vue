<script setup>
import { ref, defineProps, onMounted } from 'vue'
import { tweets } from '@/consts/tweets.js'
import { accounts } from '@/consts/accounts.js'

const tweet = ref()
const icon = ref()

defineProps({
  id: {
    type: String,
    required: true
  }
})

function deleteTweet(id) {
  tweets.value.splice(id, 1)
}
function findTweet(id) {
  // console.log(id)
  return tweets.value.find((tweet) => tweet.id === id)
}
function searchIcon(userId) {
  return accounts.value.find((account) => account.userId === userId).icon
}
onMounted(() => {
  tweet.value = findTweet()
  if (tweet.value) {
    icon.value = searchIcon(tweet.value.userId)
  }
  console.log(tweet.value)
  console.log(icon.value)
})
</script>
<template>
  <div class="content">
    <div class="tweet-header">
      <img class="user-icon" :src="icon" width="50" height="50" />
      <div v-show="userId === 'q30387'">
        <BButton pill size="sm" @click="deleteTweet(id)">削除</BButton>
      </div>
    </div>
    <pre class="tweet-text">{{ tweetContent }}</pre>
    <div v-if="image" class="tweet-image">
      <img :src="image" style="max-width: 500px; max-height: 200px" />
    </div>
    <div class="tweet-info">
      <pre>{{ datetime }} tweet by @{{ userId }}  <b>{{ views }}</b> Views</pre>
      <div class="tweet-activity">
        <div>
          <BButton variant="link">
            <img src="@/assets/icons/tweet/reply.svg" width="15" height="15" />
          </BButton>
          <span class="disabled-text">{{ reply }}</span>
        </div>
        <div>
          <BButton variant="link">
            <img src="@/assets/icons/tweet/retweet.svg" width="15" height="15" />
          </BButton>
          <span class="disabled-text">{{ retweet }}</span>
        </div>
        <div>
          <BButton variant="link">
            <img src="@/assets/icons/tweet/likes.svg" width="15" height="15" />
          </BButton>
          <span class="disabled-text">{{ likes }}</span>
        </div>
        <div>
          <BButton variant="link">
            <img src="@/assets/icons/tweet/views.svg" width="15" height="15" />
          </BButton>
          <span class="disabled-text">{{ views }}</span>
        </div>
      </div>
    </div>
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
