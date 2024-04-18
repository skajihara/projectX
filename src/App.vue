<script setup></script>
<template>
  <div id="app">
    <router-view />
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
