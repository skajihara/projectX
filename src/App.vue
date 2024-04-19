<script setup>
import LeftArea from './components/LeftArea.vue'
import CenterArea from './components/CenterArea.vue'
import RightArea from './components/RightArea.vue'
</script>
<template>
  <div id="app">
    <LeftArea />
    <CenterArea />
    <RightArea />
  </div>
</template>
<style scoped>
#app {
  overflow-y: scroll;
}
#app::-webkit-scrollbar {
  display: none;
}
</style>
