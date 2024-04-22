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
const emit = defineEmits(['childEmit'])
const childEmit = (index) => {
  emit('childEmit', index)
}
</script>
<template>
  <div class="content">
    {{ tweetContent }}
    <span class="tweet-by">tweet by @{{ userId }}</span>
  </div>
  <button @click="childEmit(index)">Delete</button>
</template>
<style scoped></style>
