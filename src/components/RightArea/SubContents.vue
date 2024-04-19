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
.trend {
  background-color: rgb(230, 230, 230);
  border-radius: 5px;
}
.trend h3 {
  margin: 10px 10px;
}
.recommended-user {
  background-color: rgb(230, 230, 230);
  border-radius: 5px;
}
.recommended-user h3 {
  margin: 10px 10px;
}
</style>
