<script setup>
import { ref, onMounted } from 'vue'
import { trendTopics } from '@/consts/trendTopics.js'

const randomTopics = ref([])

// ランダムに10件のトピックを選択する
function selectRandomTopics() {
  const topics = trendTopics.value.slice()
  const selectedTopics = []
  while (selectedTopics.length < 10 && topics.length > 0) {
    const randomIndex = Math.floor(Math.random() * (topics.length - 1))
    selectedTopics.push(topics.splice(randomIndex, 1)[0])
    if (!selectedTopics) {
      // undefinedの場合も除外する
      continue
    }
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
      <span style="font-size: small; color: gray">{{ topic.genre }}のトレンド</span>
      <br /><span style="font-weight: bold">{{ topic.word }}</span> <br />
      <span style="font-size: small; color: gray">{{ topic.num }} tweets</span>
    </div>
  </div>
</template>
<style scoped>
.trend-topics h3 {
  margin: 10px 10px;
}
.topics {
  margin-left: 10px;
  padding-bottom: 10px;
}
</style>
