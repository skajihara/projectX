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
    <Tweet class="tweet"></Tweet>

    <div v-for="(tweet, index) in tweets" :key="index" class="tweet">
      <div class="content">
        {{ tweet.content }}
        <span class="tweet-by">tweet by @{{ tweet.userId }}</span>
      </div>
      <button @click="deleteTweet(index)">Delete</button>
    </div>
  </div>
</template>
<style scoped>
.tweet {
  margin-top: 20px;
  margin-bottom: 20px;
}
</style>
