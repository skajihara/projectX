<script setup>
import { defineProps, defineEmits } from 'vue'
defineProps({
  tweetContent: {
    type: String,
    required: true
  },
  userId: {
    type: String,
    required: true
  },
  datetime: {
    type: String,
    required: true
  },
  location: {
    type: String,
    required: false,
    default: ''
  },
  likes: {
    type: Number,
    required: false,
    default: 0
  },
  retweet: {
    type: Number,
    required: false,
    default: 0
  },
  reply: {
    type: Number,
    required: false,
    default: 0
  },
  views: {
    type: Number,
    required: false,
    default: 0
  },
  icon: {
    type: String,
    required: true
  },
  index: {
    type: Number,
    required: true
  }
})
const emit = defineEmits(['deleteTweet'])
const deleteTweet = (index) => {
  emit('deleteTweet', index)
}
</script>
<template>
  <div class="content">
    <div class="tweet-header">
      <img class="user-icon" :src="icon" width="50" height="50" />
      <div v-show="userId === 'q30387'">
        <BButton pill size="sm" @click="deleteTweet(index)">削除</BButton>
      </div>
    </div>
    <pre class="tweet-text">{{ tweetContent }}</pre>
    <div class="tweet-info">
      <pre>{{ datetime }} tweet by @{{ userId }}  <b>{{ views }}</b> Views</pre>
      <div class="tweet-activity">
        <div>
          <BButton variant="link">
            <img src="@/assets/icons/tweet/reply.svg" width="15" height="15" />
          </BButton>
          <span class="disabled-text">{{ reply }}</span>
        </div>
        <div>
          <BButton variant="link">
            <img src="@/assets/icons/tweet/retweet.svg" width="15" height="15" />
          </BButton>
          <span class="disabled-text">{{ retweet }}</span>
        </div>
        <div>
          <BButton variant="link">
            <img src="@/assets/icons/tweet/likes.svg" width="15" height="15" />
          </BButton>
          <span class="disabled-text">{{ likes }}</span>
        </div>
        <div>
          <BButton variant="link">
            <img src="@/assets/icons/tweet/views.svg" width="15" height="15" />
          </BButton>
          <span class="disabled-text">{{ views }}</span>
        </div>
      </div>
    </div>
  </div>
</template>
<style scoped>
.content {
  width: 600px;
  border: 1px solid gray;
  padding: 10px 10px;
}
.tweet-header {
  width: 540px;
  display: flex;
  align-items: center;
  justify-content: space-between;
}
.tweet-text {
  word-wrap: break-word;
  white-space: pre-wrap;
  width: 50ch;
  font-size: 15pt;
}
.tweet-info {
  color: gray;
  position: relative;
  top: -15px;
}
.tweet-activity {
  width: 400px;
  display: grid;
  grid-template-columns: repeat(4, 80px);
  grid-column-gap: 50px;
  align-items: center;
}
.disabled-text {
  color: gray;
  position: relative;
  left: -5px;
  top: 3px;
}
</style>
