<script setup>
import { ref, onMounted } from 'vue'
import { trendTopics } from '@/consts/trendTopics.js'

const randomTopics = ref([])

// ランダムに10件のトピックを選択する
function pickRandom10Topics() {
  const topics = trendTopics.value.slice()
  const shuffledTopics = topics.sort(() => Math.random() - 0.5)
  randomTopics.value = shuffledTopics.slice(0, 10)
}
onMounted(() => {
  pickRandom10Topics()
})
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
