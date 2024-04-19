<script setup>
import { menuList } from '@/consts/menuList.js'
const currentUserId = 'q30387'
</script>
<template>
  <div>
    <div v-for="(menuItem, index) in menuList" :key="index" class="menu-item">
      <template v-if="menuItem.link === 'lists' || menuItem.link === 'profile'">
        <router-link :to="{ name: menuItem.link, params: { userId: currentUserId } }">
          <img class="menu-icon" :src="menuItem.path1" width="32" height="32" />
          {{ menuItem.name }}
        </router-link>
      </template>
      <template v-else>
        <router-link :to="{ name: menuItem.link }">
          <img class="menu-icon" :src="menuItem.path1" width="32" height="32" />
          {{ menuItem.name }}
        </router-link>
      </template>
    </div>
  </div>
</template>
<style scoped>
.menu-item {
  margin-top: 0px;
  margin-bottom: 20px;
}
.menu-icon {
  margin-top: 5px;
  position: relative;
  top: 10px;
}
</style>
