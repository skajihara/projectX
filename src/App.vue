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
              <transition name="fade">
                <RouterLink to="/home" class="link-with-image">
                  <img
                    class="t-logo"
                    src="@/assets/icons/logo/t-logo.svg"
                    width="100"
                    height="100"
                  />
                </RouterLink>
              </transition>
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
::-webkit-scrollbar {
  display: none;
}
.link-with-image {
  display: inline-block;
}
.whole {
  height: 800px;
  display: flex;
  flex-direction: row;
  align-content: center;
}
.left {
  min-width: 300px;
}
.center {
  min-width: 700px;
  max-width: 700px;
  overflow-y: scroll;
}
.right {
  min-width: 300px;
  overflow-y: scroll;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}
</style>
