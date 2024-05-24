<script setup>
import { ref, onMounted } from 'vue'
import Tweet from '../Tweet.vue'
import { tweets } from '@/consts/tweets.js'
import { accounts } from '@/consts/accounts.js'

const randomTweets = ref([])
function searchIcon(userId) {
  return accounts.value.find((account) => account.userId === userId).icon
}
function deleteTweet(index) {
  randomTweets.value.splice(index, 1)
}
function pickRandom20Tweets() {
  const copyTweets = tweets.value.slice()
  const shuffledTweets = copyTweets.sort(() => Math.random() - 0.5)
  randomTweets.value = shuffledTweets.slice(0, 20)
}
onMounted(() => {
  pickRandom20Tweets()
})
</script>

<template>
  <div>
    <div v-for="(tweet, index) in randomTweets" :key="index" class="tweet">
      <Tweet
        :id="tweet.id"
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
        :image="tweet.image"
        @delete-tweet="deleteTweet"
      ></Tweet>
    </div>
  </div>
</template>
<style scoped></style>
