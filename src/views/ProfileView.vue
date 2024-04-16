<script></script>

<template>
  <div>
    <h1>プロフィール</h1>
    <!-- <p>ユーザーID: {{ $route.props.userId }}</p>
    <p>ユーザー名: {{ $route.props.userName }}</p>
    <p>自己紹介: {{ $route.props.userBio }}</p> -->
    <router-link :to="{ name: 'timeline' }">タイムラインへ</router-link>
    <p>{{ $route }}</p>
  </div>
</template>
