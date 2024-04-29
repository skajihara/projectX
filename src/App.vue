<script setup>
import LeftArea from './components/LeftArea.vue'
import CenterArea from './components/CenterArea.vue'
import RightArea from './components/RightArea.vue'
</script>
<template>
  <div id="app">
    <b-container fluid>
      <b-row>
        <div class="whole d-flex justify-content-center">
          <div class="left">
            <b-col>
              <a href="/home">
                <img class="t-logo" src="@/assets/icons/logo/t-logo.svg" width="100" height="100" />
              </a>
              <LeftArea class="left-area"></LeftArea>
            </b-col>
          </div>
          <div class="center">
            <b-col>
              <CenterArea class="center-area"></CenterArea>
            </b-col>
          </div>
          <div class="right">
            <b-col>
              <RightArea class="right-area"></RightArea>
            </b-col>
          </div>
        </div>
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
.whole {
  display: flex;
  align-content: center;
}
.left {
  min-width: 300px;
}
.center {
  min-width: 700px;
}
.right {
  min-width: 300px;
}
</style>
