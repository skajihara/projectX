<script setup>
import { ref } from 'vue'
import { accounts } from '@/consts/accounts.js'

const newTweetContent = ref('')
const addedTweets = ref([])

function searchIcon(userId) {
  return accounts.value.find((account) => account.userId === userId).icon
}
function addTweet() {
  if (newTweetContent.value.trim() !== '') {
    const currentDate = new Date()
    const year = currentDate.getFullYear()
    const month = String(currentDate.getMonth() + 1).padStart(2, '0') // 0から始まるため、1を足す
    const day = String(currentDate.getDate()).padStart(2, '0')
    const hours = String(currentDate.getHours()).padStart(2, '0')
    const minutes = String(currentDate.getMinutes()).padStart(2, '0')
    const seconds = String(currentDate.getSeconds()).padStart(2, '0')
    const formattedCurrentDate =
      year + '-' + month + '-' + day + ' ' + hours + ':' + minutes + ':' + seconds
    addedTweets.value.unshift({
      content: newTweetContent.value,
      userId: 'q30387',
      datetime: formattedCurrentDate,
      location: 'somewhere',
      likes: 0,
      retweet: 0,
      reply: 0,
      views: 0
    })
    newTweetContent.value = ''
  }
}
function deleteTweet(index) {
  addedTweets.value.splice(index, 1)
}
</script>
<template>
  <div>
    <textarea v-model="newTweetContent" placeholder="いまどうしてる？"></textarea>
    <BButton variant="outline-primary" @click="addTweet">ツイート</BButton>
    <div v-for="(tweet, index) in addedTweets" :key="index" class="tweet">
      <Tweet
        :tweet-content="tweet.content"
        :index="index"
        :user-id="tweet.userId"
        :datetime="tweet.datetime"
        :location="tweet.location"
        :likes="tweet.likes"
        :retweet="tweet.retweet"
        :reply="tweet.reply"
        :views="tweet.views"
        :icon="searchIcon(tweet.userId)"
        @delete-tweet="deleteTweet"
      ></Tweet>
    </div>
  </div>
</template>
<style scoped>
.tweet-form button {
  margin-left: 10px;
  position: relative;
  top: -15px;
}
</style>
