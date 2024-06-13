<script setup>
import { useCurrentUserStore } from '@/stores/currentUser.js'
import { formatDateTime } from '@/utils/formatDateTime.js'

const currentUser = useCurrentUserStore()
const props = defineProps({
  scheduleId: {
    type: Number,
    required: true
  },
  accountId: {
    type: String,
    required: true
  },
  text: {
    type: String,
    required: true
  },
  image: {
    type: String,
    required: false,
    default: ''
  },
  location: {
    type: String,
    required: false,
    default: ''
  },
  scheduledDatetime: {
    type: String,
    required: true
  },
  createdDatetime: {
    type: String,
    required: true
  }
})
const emit = defineEmits(['deleteTweet'])
const deleteTweet = (id) => {
  emit('deleteTweet', id)
}
</script>
<template>
  <div class="content">
    <div class="tweet-header">
      <span>@{{ props.accountId }}</span>
      <div v-show="props.accountId === currentUser.userId" class="delete-button">
        <BButton pill size="sm" @click="deleteTweet(props.scheduleId)">削除</BButton>
      </div>
    </div>
    <router-link
      :to="{ name: 'scheduled-tweet-detail', params: { scheduleId: props.scheduleId } }"
      class="no-hover"
    >
      <pre class="tweet-text">{{ props.text }}</pre>
      <div v-if="props.image" class="tweet-image">
        <img :src="props.image" style="max-width: 500px; max-height: 200px" />
      </div>
      <div class="tweet-info">
        <span>ツイート予定：{{ formatDateTime(props.scheduledDatetime, 'display') }}</span>
        <br />
        <span>登録日時：{{ formatDateTime(props.createdDatetime, 'display') }}</span>
      </div>
    </router-link>
  </div>
</template>
<style scoped>
.content {
  width: 600px;
  border: 1px solid rgba(200, 200, 200, 0.6);
  border-radius: 5px;
  padding: 10px 10px;
}
.delete-button {
  display: inline-block;
  text-align: right;
  margin-left: auto;
}
.tweet-header {
  width: 540px;
  display: flex;
  align-items: center;
}
.tweet-text {
  word-wrap: break-word;
  white-space: pre-wrap;
  width: 50ch;
  font-size: 15pt;
}
.tweet-image {
  padding-bottom: 20px;
}
.tweet-info {
  color: gray;
  position: relative;
  top: -15px;
}
.disabled-text {
  color: gray;
  position: relative;
  left: -5px;
  top: 3px;
}
.no-hover {
  color: inherit;
  text-decoration: none;
}
.no-hover:hover {
  color: inherit;
  text-decoration: none;
}
a {
  text-decoration: none;
  color: hsla(100%, 100%, 100%, 1);
  padding: 0px;
}
</style>
