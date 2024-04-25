<script setup>
import { ref, computed } from 'vue'
import TrendTopics from './SubContents/TrendTopics.vue'
import RecommendedUsers from './SubContents/RecommendedUsers.vue'
import { accounts } from '@/consts/accounts.js'

const searchQuery = ref('')
const searchFilteredItems = ref([])
const searchFilteredCurrentPage = ref(1)
const itemsPerPage = 5

function search() {
  searchFilteredItems.value = accounts.value.filter((account) =>
    account.userName.toLowerCase().includes(searchQuery.value.toLowerCase())
  )
}
const paginatedItems = computed(() => {
  const start = (searchFilteredCurrentPage.value - 1) * itemsPerPage
  const end = start + itemsPerPage
  return searchFilteredItems.value.slice(start, end)
})
</script>
<template>
  <div>
    <div class="search-form">
      <b-input-group>
        <b-form-input v-model="searchQuery" placeholder="search" class="no-outline"></b-form-input>
        <b-input-group-append>
          <b-button variant="primary" @click="search">検索</b-button>
        </b-input-group-append>
      </b-input-group>
      <div class="search-result">
        <b-list-group class="mt-3">
          <b-list-group-item v-for="(item, index) in paginatedItems" :key="index">
            {{ item.userName }}
          </b-list-group-item>
        </b-list-group>
        <b-pagination
          v-model="searchFilteredCurrentPage"
          :total-rows="searchFilteredItems.length"
          :per-page="itemsPerPage"
          class="my-3"
        />
      </div>
    </div>
    <TrendTopics class="trend-topics"></TrendTopics>
    <RecommendedUsers class="recommended-users"></RecommendedUsers>
  </div>
</template>
<style scoped>
.no-outline:focus {
  box-shadow: none;
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
