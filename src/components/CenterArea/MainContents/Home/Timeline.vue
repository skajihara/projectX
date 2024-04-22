<script setup>
// import { ref } from 'vue'
import Tweet from '../Tweet.vue'
import { tweets } from '@/consts/tweets.js'
function deleteTweet(index) {
  tweets.value.splice(index, 1)
}
</script>
<template>
  <div>
    <h1>タイムライン</h1>
    <div v-for="(tweet, index) in tweets" :key="index" class="tweet">
      <Tweet
        :tweet-content="tweet.content"
        :index="index"
        :user-id="tweet.userId"
        @child-emit="deleteTweet"
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
