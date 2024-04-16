<script>
// export default {
//   props: {
//     userId: {
//       type: String,
//       required: true
//     },
//     userName: {
//       type: String,
//       required: true
//     },
//     userBio: {
//       type: String,
//       required: false,
//       default: 'default text'
//     }
//   }
// }
</script>

<template>
  <div>
    <h1>プロフィール</h1>
    <p>ユーザーID: {{ userId }}</p>
    <p>ユーザー名: {{ userName }}</p>
    <p>自己紹介: {{ userBio }}</p>
    <router-link :to="{ name: 'timeline' }">タイムラインへ</router-link>
    <p>{{ $route }}</p>
  </div>
</template>
