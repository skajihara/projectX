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
  randomTweets.value.splice(index, 1)
}
</script>
<template>
  <div>
    <div v-for="(tweet, index) in randomTweets" :key="index" class="tweet">
      <Tweet
        :tweet-content="tweet.content"
        :index="index"
        :user-id="tweet.userId"
        :datetime="tweet.datetime"
        :location="tweet.location"
        :likes="tweet.likes"
        :retweet="tweet.retweet"
        :reply="tweet.reply"
        :views="tweet.display"
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
