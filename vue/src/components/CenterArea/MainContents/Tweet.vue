<script setup>
import { defineProps, defineEmits } from 'vue'

const props = defineProps({
  id: {
    type: Number,
    required: true
  },
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
  image: {
    type: String,
    required: false,
    default: ''
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
    <router-link :to="{ name: 'tweet-detail', params: { id: props.id } }">
      <div class="tweet-header">
        <img class="user-icon" :src="props.icon" width="50" height="50" />
        <div v-show="props.userId === 'q30387'">
          <BButton pill size="sm" @click="deleteTweet(props.index)">削除</BButton>
        </div>
      </div>
      <pre class="tweet-text">{{ props.tweetContent }}</pre>
      <div v-if="props.image" class="tweet-image">
        <img :src="props.image" style="max-width: 500px; max-height: 200px" />
      </div>
      <div class="tweet-info">
        <pre>{{ props.datetime }} tweet by @{{ props.userId }}  <b>{{ props.views }}</b> Views</pre>
        <div class="tweet-activity">
          <div>
            <BButton variant="link">
              <img src="@/assets/icons/tweet/reply.svg" width="15" height="15" />
            </BButton>
            <span class="disabled-text">{{ props.reply }}</span>
          </div>
          <div>
            <BButton variant="link">
              <img src="@/assets/icons/tweet/retweet.svg" width="15" height="15" />
            </BButton>
            <span class="disabled-text">{{ props.retweet }}</span>
          </div>
          <div>
            <BButton variant="link">
              <img src="@/assets/icons/tweet/likes.svg" width="15" height="15" />
            </BButton>
            <span class="disabled-text">{{ props.likes }}</span>
          </div>
          <div>
            <BButton variant="link">
              <img src="@/assets/icons/tweet/views.svg" width="15" height="15" />
            </BButton>
            <span class="disabled-text">{{ props.views }}</span>
          </div>
        </div>
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
.tweet-image {
  padding-bottom: 20px;
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
