<script setup>
import TrendTopics from './SubContents/TrendTopics.vue'
import RecommendedUsers from './SubContents/RecommendedUsers.vue'
</script>
<template>
  <div>
    <TrendTopics class="trend-topics"></TrendTopics>
    <RecommendedUsers class="recommended-users"></RecommendedUsers>
  </div>
</template>
<style scoped>
.trend-topics {
  background-color: rgb(230, 230, 230);
  border-radius: 5px;
}
.recommended-users {
  background-color: rgb(230, 230, 230);
  border-radius: 5px;
}
</style>
