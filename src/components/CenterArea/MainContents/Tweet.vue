<script setup>
import { defineProps, defineEmits } from 'vue'
defineProps({
  tweetContent: {
    type: String,
    required: true
  },
  userId: {
    type: String,
    required: true
  },
  index: {
    type: Number,
    required: true
  }
})
const emit = defineEmits(['deleteTweet'])
const deleteTweet = (index) => {
  emit('deleteTweet', index)
}
</script>
<template>
  <div class="content">
    <pre class="tweet-text">{{ tweetContent }}</pre>
    <span class="tweet-by">tweet by @{{ userId }}</span>
  </div>
  <BButton variant="outline-secondary" @click="deleteTweet(index)">Delete</BButton>
</template>
<style scoped>
.tweet-text {
  word-wrap: break-word;
  white-space: pre-wrap;
  width: 80ch;
}
</style>
