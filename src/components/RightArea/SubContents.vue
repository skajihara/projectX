<script setup>
import { ref } from 'vue'
import TrendTopics from './SubContents/TrendTopics.vue'
import RecommendedUsers from './SubContents/RecommendedUsers.vue'

const searchQuery = ref('')
const filteredItems = ref([])
const items = [
  { id: 1, name: 'abcDEFhijKLMn' },
  { id: 2, name: '-0oll;ns' },
  { id: 3, name: 'さんぷるアイテムAa_912' }
]
const search = () => {
  filteredItems.value = items.filter((item) =>
    item.name.toLowerCase().includes(searchQuery.value.toLowerCase())
  )
}
</script>
<template>
  <div>
    <div class="search-form">
      <input v-model="searchQuery" type="text" placeholder="search" />
      <button @click="search">検索</button>
      <ul>
        <li v-for="item in filteredItems" :key="item.id">{{ item.name }}</li>
      </ul>
    </div>
    <TrendTopics class="trend-topics"></TrendTopics>
    <RecommendedUsers class="recommended-users"></RecommendedUsers>
  </div>
</template>
<style scoped>
.search-form {
}
.search-form button {
  margin-left: 10px;
  position: relative;
  top: -7px;
}
.trend-topics {
  background-color: rgb(230, 230, 230);
  border-radius: 5px;
}
.recommended-users {
  background-color: rgb(230, 230, 230);
  border-radius: 5px;
}
</style>
