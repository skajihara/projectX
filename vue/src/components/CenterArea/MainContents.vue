<script setup></script>

<template>
  <div>
    <transition name="fade" mode="out-in">
      <!-- サイドメニューで選択したコンテンツの表示 -->
      <RouterView />
    </transition>
  </div>
</template>
<style scoped>
.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s;
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}
</style>
