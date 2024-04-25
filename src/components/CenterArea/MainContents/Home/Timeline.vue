<script setup>
import { ref, onMounted } from 'vue'
import Tweet from '../Tweet.vue'
import { tweets } from '@/consts/tweets.js'

const randomTweets = ref([])

function pickRandom20Tweets() {
  const copyTweets = tweets.value.slice()
  const shuffledTweets = copyTweets.sort(() => Math.random() - 0.5)
  randomTweets.value = shuffledTweets.slice(0, 20)
}
onMounted(() => {
  pickRandom20Tweets()
})

function deleteTweet(index) {
  tweets.value.splice(index, 1)
}
</script>
<template>
  <div>
    <h1>タイムライン</h1>
    <div v-for="(tweet, index) in randomTweets" :key="index" class="tweet">
      <Tweet
        :tweet-content="tweet.content"
        :index="index"
        :user-id="tweet.userId"
        @delete-tweet="deleteTweet"
      ></Tweet>
    </div>
  </div>
</template>
<style scoped>
.tweet {
  margin-top: 20px;
  margin-bottom: 20px;
}
</style>
