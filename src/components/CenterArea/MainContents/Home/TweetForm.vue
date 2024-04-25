<script setup>
import { ref } from 'vue'
import { tweets } from '@/consts/tweets.js'
const newTweetContent = ref('')

function addTweet() {
  if (newTweetContent.value.trim() !== '') {
    tweets.value.push({
      content: newTweetContent.value,
      userId: 'CurrentUser'
    })
    newTweetContent.value = ''
  }
}
</script>
<template>
  <div>
    <textarea v-model="newTweetContent" placeholder="Please enter something."></textarea>
    <BButton variant="outline-primary" @click="addTweet">Tweet</BButton>
  </div>
</template>
<style scoped>
.tweet-form button {
  margin-left: 10px;
  position: relative;
  top: -15px;
}
</style>
