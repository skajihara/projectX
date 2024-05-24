import { defineStore } from 'pinia'
import { ref } from 'vue'

export const useCurrentUserStore = defineStore('currentUser', () => {
  const userId = ref('q30387')
  return {
    userId
  }
})
