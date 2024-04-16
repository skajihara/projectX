<script setup>
import { ref } from 'vue'

const newTweetContent = ref('')
const searchString = ref('')
const myProfile = ref({
  icon: '/src/assets/icons/myicon.svg',
  userName: 'skajihara',
  userId: '@sk123456789',
  userBio: 'qwertyuiop@[ \n asdfghjkl;:] \n zxcvbnm,./'
})
import { tweets } from '../consts/tweets.js'
import { menuList } from '../consts/menuList.js'

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
  <h1>タイムライン</h1>
  <!-- <div class="x-logo"> -->
  <img alt="xlogo" class="x-logo" src="@/assets/icons/x1.svg" width="70" height="70" />
  <!-- </div> -->
  <div class="whole">
    <div class="left">
      <!-- side menu -->
      <div class="menu">
        <div v-for="(menuItem, index) in menuList" :key="index" class="menu-item">
          <img class="logo" :src="menuItem.path1" width="32" height="32" />
          <a href="menuItem.link">{{ menuItem.name }}</a>
        </div>
      </div>
      <div class="my-profile">
        <router-link
          :to="{
            name: 'profile',
            params: {
              userId: myProfile.userId.value,
              userName: myProfile.userName.value,
              userBio: myProfile.userBio.value
            }
          }"
        >
          <img
            alt="myicon"
            class="my-icon"
            src="@/assets/icons/myicon.svg"
            width="70"
            height="70"
          />
        </router-link>
        <div class="my-info">
          {{ myProfile.userName }}
          <br />
          {{ myProfile.userId }}
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
            tweet by {{ tweet.userId }}
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
      <div class="trend">
        <h3>いまどうしてる？</h3>
      </div>
      <div class="recommended-user">
        <h3>おすすめユーザー</h3>
      </div>
    </div>
  </div>
</template>

<style scoped>
.x-logo {
  position: relative;
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
  margin-top: 10px;
  margin-left: 10px;
}
.menu-item {
  margin-top: 10px;
  margin-bottom: 10px;
}
.my-profile {
  display: flex;
  flex-wrap: wrap;
}
.my-info {
  margin-top: 10px;
  margin-left: 5px;
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
