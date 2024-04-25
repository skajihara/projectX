<script setup>
import TweetForm from './Home/TweetForm.vue'
import Timeline from './Home/Timeline.vue'
</script>
<template>
  <div class="home">
    <TweetForm class="tweet-form"></TweetForm>
    <Timeline class="timeline"></Timeline>
  </div>
</template>
<style scoped>
.home {
}
.tweet-form {
}
.timeline {
  margin-top: 20px;
}
</style>
