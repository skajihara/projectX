<script setup>
import LeftArea from './components/LeftArea.vue'
import CenterArea from './components/CenterArea.vue'
import RightArea from './components/RightArea.vue'
</script>
<template>
  <div id="app">
    <img alt="tlogo" class="t-logo" src="@/assets/icons/logo/t-logo.svg" width="100" height="100" />
    <b-container fluid>
      <b-row>
        <b-col>
          <LeftArea class="left-area"></LeftArea>
        </b-col>
        <b-col>
          <CenterArea class="center-area"></CenterArea>
        </b-col>
        <b-col>
          <RightArea class="right-area"></RightArea>
        </b-col>
      </b-row>
    </b-container>
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
