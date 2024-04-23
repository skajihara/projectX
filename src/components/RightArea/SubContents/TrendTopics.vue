<script setup>
import { ref, onMounted } from 'vue'
import { trendTopics } from '@/consts/trendTopics.js'

const randomTopics = ref([])

// ランダムに10件のトピックを選択する
function selectRandomTopics() {
  const topics = trendTopics.value.slice() // コピーを作成することで元の配列を変更しない
  const selectedTopics = []
  while (selectedTopics.length < 10 && topics.length > 0) {
    const randomIndex = Math.floor(Math.random() * topics.length)
    selectedTopics.push(topics.splice(randomIndex, 1)[0])
    // console.log('a')
  }
  randomTopics.value = selectedTopics
}
// 画面の更新時に再度トピックを選択する
onMounted(() => {
  selectRandomTopics()
  console.log(randomTopics.value)
})
// onUpdatedに設定すると再帰呼び出しループが発生してエラーとなる
// onUpdated(() => {
//   selectRandomTopics()
// })
</script>
<template>
  <div>
    <h3 style="padding-top: 10px">トレンドトピックス</h3>
    <div v-for="(topic, index) in randomTopics" :key="index" class="topics">
      <span>{{ topic.genre }}のトレンド</span>
      <p>
        <strong>{{ topic.word }}</strong>
        <br />
        {{ topic.num }} tweets
      </p>
    </div>
  </div>
</template>
<style scoped>
.trend-topics h3 {
  margin: 10px 10px;
}
.search-form {
}
.search-form button {
  margin-left: 10px;
  position: relative;
  top: -7px;
}
.topics {
  margin-left: 10px;
  padding-bottom: 1px;
}
</style>
