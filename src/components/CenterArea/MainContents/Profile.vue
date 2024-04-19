<script setup>
//外部ファイルにユーザの情報を配列で用意して読み込む
//definePropsで引数としてユーザIDをpropsから受け取り
//配列から該当のユーザ情報を検索取得する（該当無しの場合はデフォルト値）
//取得した情報を画面に表示する
</script>

<template>
  <h1>プロフィール</h1>
  <div>
    <router-link :to="{ name: 'home' }">🔙戻る</router-link>
    <p>ユーザーID: {{ $route.params.userId }}</p>
    <!-- <p>ユーザー名: {{ userName }}</p>
    <p>自己紹介: {{ userBio }}</p> -->
    <!-- <p>{{ $route }}</p> -->
  </div>
</template>
