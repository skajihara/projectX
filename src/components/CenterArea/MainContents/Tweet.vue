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
const emit = defineEmits(['clickDeleteButton'])
const clickDeleteButton = (index) => {
  emit('clickDeleteButton', index)
}
</script>
<template>
  <div class="content">
    {{ tweetContent }}
    <span class="tweet-by">tweet by @{{ userId }}</span>
  </div>
  <BButton @click="clickDeleteButton(index)">Delete</BButton>
</template>
<style scoped></style>
