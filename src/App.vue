<script setup>
import LeftArea from './components/LeftArea.vue'
import CenterArea from './components/CenterArea.vue'
import RightArea from './components/RightArea.vue'
</script>
<template>
  <div id="app">
    <div class="whole">
      <img alt="tlogo" class="t-logo" src="@/assets/icons/t-logo.svg" width="100" height="100" />
      <LeftArea class="left"></LeftArea>
      <CenterArea class="center"></CenterArea>
      <RightArea class="right"></RightArea>
    </div>
  </div>
</template>
<style scoped>
#app {
  overflow-y: scroll;
}
#app::-webkit-scrollbar {
  display: none;
}
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
</style>
