<script setup>
import { ref } from 'vue'

const newTweetContent = ref('')
const searchString = ref('')
const myProfile = ref({
  icon: '/src/assets/icons/myicon.svg',
  userName: 'skajihara',
  userId: '@sk123456789'
})

const tweets = ref([
  {
    content: '富山のホタルイカ、最高🍻',
    userId: '@user_A'
  },
  {
    content: '夜間はライトアップも実施「令和6年度 八女黒木大藤まつり」開催！',
    userId: '@user_B'
  },
  {
    content: 'プレゼントキャンペーン🎁',
    userId: '@user_C'
  },
  {
    content: 'ガチャ爆死したなう',
    userId: '@user_D'
  },
  {
    content: 'コカ・コーラ 500ml×24本がクーポンと定期お得便で1691円に #広告',
    userId: '@user_E'
  },
  {
    content: '急速に溶けていくギルバトのモチベ',
    userId: '@user_F'
  }
])
const menu = [
  {
    name: 'ホーム',
    link: '#',
    path1: '/src/assets/icons/icon011.svg',
    path2: '/src/assets/icons/icon012.svg'
  },
  {
    name: '話題を検索',
    link: '#',
    path1: '/src/assets/icons/icon021.svg',
    path2: '/src/assets/icons/icon022.svg'
  },
  {
    name: '通知',
    link: '#',
    path1: '/src/assets/icons/icon031.svg',
    path2: '/src/assets/icons/icon032.svg'
  },
  {
    name: 'メッセージ',
    link: '#',
    path1: '/src/assets/icons/icon041.svg',
    path2: '/src/assets/icons/icon042.svg'
  },
  {
    name: 'リスト',
    link: '#',
    path1: '/src/assets/icons/icon051.svg',
    path2: '/src/assets/icons/icon052.svg'
  },
  {
    name: 'ブックマーク',
    link: '#',
    path1: '/src/assets/icons/icon061.svg',
    path2: '/src/assets/icons/icon062.svg'
  },
  {
    name: 'コミュニティ',
    link: '#',
    path1: '/src/assets/icons/icon071.svg',
    path2: '/src/assets/icons/icon072.svg'
  },
  {
    name: 'プレミアム',
    link: '#',
    path1: '/src/assets/icons/icon081.svg',
    path2: '/src/assets/icons/icon082.svg'
  },
  {
    name: 'プロフィール',
    link: '#',
    path1: '/src/assets/icons/icon091.svg',
    path2: '/src/assets/icons/icon092.svg'
  },
  {
    name: 'もっと見る',
    link: '#',
    path1: '/src/assets/icons/icon101.svg',
    path2: '/src/assets/icons/icon102.svg'
  }
]

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
  <!-- <div class="x-logo"> -->
  <img alt="xlogo" class="x-logo" src="@/assets/icons/x1.svg" width="70" height="70" />
  <!-- </div> -->
  <div class="whole">
    <div class="left">
      <!-- side menu -->
      <div class="menu">
        <div v-for="(menuItem, index) in menu" :key="index" class="menu-item">
          <img class="logo" :src="menuItem.path1" width="32" height="32" />
          <a href="menuItem.link">{{ menuItem.name }}</a>
        </div>
      </div>
      <div class="my-profile">
        <img alt="myicon" class="my-icon" src="@/assets/icons/myicon.svg" width="70" height="70" />
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
