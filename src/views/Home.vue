<script setup>
import { ref } from 'vue'
import { tweets } from '../consts/tweets.js'
import { menuList } from '../consts/menuList.js'

const newTweetContent = ref('')
const searchString = ref('')
const myProfile = ref({
  icon: '/src/assets/icons/myicon.svg',
  userName: 'skajihara',
  userId: 'sk123456789',
  userBio: 'qwertyuiop@[ \n asdfghjkl;:] \n zxcvbnm,./'
})

// console.log(myProfile.value.icon)
// console.log(myProfile.value.userName)
// console.log(myProfile.value.userId)
// console.log(myProfile.value.userBio)

function addTweet() {
  if (newTweetContent.value.trim() !== '') {
    tweets.value.push({
      content: newTweetContent.value,
      userId: 'CurrentUser'
    })
    newTweetContent.value = ''
  }
}

function deleteTweet(index) {
  tweets.value.splice(index, 1)
}

function searchTweets() {}
</script>

<template>
  <h1>ホーム</h1>
  <img alt="tlogo" class="t-logo" src="@/assets/icons/t-logo.svg" width="100" height="100" />
  <div class="whole">
    <div class="left">
      <!-- side menu -->
      <div class="menu">
        <div v-for="(menuItem, index) in menuList" :key="index" class="menu-item">
          <template v-if="menuItem.link === 'lists' || menuItem.link === 'profile'">
            <router-link :to="{ name: menuItem.link, params: { userId: myProfile.userId } }">
              <img class="menu-icon" :src="menuItem.path1" width="32" height="32" />
              {{ menuItem.name }}
            </router-link>
          </template>
          <template v-else>
            <router-link :to="{ name: menuItem.link }">
              <img class="menu-icon" :src="menuItem.path1" width="32" height="32" />
              {{ menuItem.name }}
            </router-link>
          </template>
        </div>
      </div>
      <!-- My profile -->
      <div class="my-profile">
        <img alt="myicon" class="my-icon" src="@/assets/icons/myicon.svg" width="70" height="70" />
        <div class="my-info">
          <p>{{ myProfile.userName }}</p>
          <p>@{{ myProfile.userId }}</p>
        </div>
      </div>
    </div>

    <div class="center">
      <!-- tweet form -->
      <div class="tweet-form">
        <textarea v-model="newTweetContent" placeholder="Please enter something."></textarea>
        <button @click="addTweet">Tweet</button>
      </div>
      <!-- time line -->
      <div class="time-line">
        <div v-for="(tweet, index) in tweets" :key="index" class="tweet">
          <div class="content">
            {{ tweet.content }}
            <span class="tweet-by">tweet by @{{ tweet.userId }}</span>
          </div>
          <!-- tweet delete -->
          <button @click="deleteTweet(index)">Delete</button>
        </div>
      </div>
    </div>
    <div class="right">
      <!-- search form -->
      <div class="search-form">
        <textarea v-model="searchString" placeholder="Please enter search string."></textarea>
        <button @click="searchTweets">Search</button>
      </div>
      <!-- Trend -->
      <div class="trend">
        <h3>いまどうしてる？</h3>
      </div>
      <!-- Recommended users -->
      <div class="recommended-user">
        <h3>おすすめユーザー</h3>
      </div>
    </div>
  </div>
</template>

<style scoped>
.t-logo {
  position: relative;
  top: 10px;
  left: 100px;
}
.whole {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  width: auto;
  padding: 0px 100px;
}
.left {
  width: 20%;
}
.center {
  width: 40%;
  padding-top: 10px;
}
.right {
  width: 40%;
  padding-top: 10px;
}
.menu {
  margin-top: 0px;
  margin-left: 10px;
}
.menu-item {
  margin-top: 0px;
  margin-bottom: 20px;
}
.menu-icon {
  margin-top: 5px;
  position: relative;
  top: 10px;
}
.my-profile {
  display: flex;
  align-items: center;
}
.tweet-form {
}
.tweet-form button {
  margin-left: 10px;
  position: relative;
  top: -7px;
}
.time-line {
  margin-top: 20px;
}
.tweet {
  margin-top: 20px;
  margin-bottom: 20px;
}
.search-form {
}
.search-form button {
  margin-left: 10px;
  position: relative;
  top: -7px;
}
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
