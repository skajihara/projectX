<script setup>
defineProps({
  catchError: {
    type: Array,
    required: true
  }
})
</script>

<template>
  <div>
    <div class="error">
      <img
        alt="tlogo"
        class="t-logo"
        src="@/assets/icons/logo/t-logo.svg"
        width="300"
        height="300"
      />
      <h1>404 Not Found</h1>
    </div>
    <div>
      <p>{{ $route.params.catchError }}</p>
      <p>{{ catchError }}</p>
    </div>
  </div>
</template>

<style>
.error {
  min-height: 10vh;
  display: flex;
  align-items: center;
  font-size: 50pt;
}
.error h1 {
  color: red;
}
</style>
