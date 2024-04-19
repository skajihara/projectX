<script setup>
import SideMenu from './LeftArea/SideMenu.vue'
import MyProfile from './LeftArea/MyProfile.vue'
</script>
<template>
  <div>
    <SideMenu class="side-menu"></SideMenu>
    <MyProfile class="my-profile"></MyProfile>
  </div>
</template>
<style scoped>
.side-menu {
  margin-top: 0px;
  margin-left: 10px;
}
.my-profile {
  display: flex;
  align-items: center;
}
</style>
