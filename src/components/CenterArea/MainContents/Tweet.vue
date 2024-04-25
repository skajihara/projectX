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
  datetime: {
    type: String,
    required: true
  },
  location: {
    type: String,
    required: false,
    default: ''
  },
  likes: {
    type: Number,
    required: false,
    default: 0
  },
  retweet: {
    type: Number,
    required: false,
    default: 0
  },
  reply: {
    type: Number,
    required: false,
    default: 0
  },
  views: {
    type: Number,
    required: false,
    default: 0
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
    <span class="tweet-info">
      {{ datetime }} tweet by @{{ userId }} <span style="font-weight: bold">{{ views }}</span> Views
    </span>
  </div>
  <BButton variant="outline-secondary" @click="deleteTweet(index)">Delete</BButton>
</template>
<style scoped>
.tweet-text {
  word-wrap: break-word;
  white-space: pre-wrap;
  width: 80ch;
}
.tweet-info {
  font-size: small;
  color: gray;
  position: relative;
  top: -20px;
}
</style>
