<script setup>
import { useCurrentUserStore } from '@/stores/currentUser.js'

const currentUser = useCurrentUserStore()
const props = defineProps({
  id: {
    type: Number,
    required: true
  },
  tweetContent: {
    type: String,
    required: true
  },
  accountId: {
    type: String,
    required: true
  },
  accountName: {
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
      <img class="user-icon" :src="props.icon" width="50" height="50" />
      <span>{{ props.accountName }}</span>
      <div v-show="props.accountId === currentUser.userId" class="delete-button">
        <BButton pill size="sm" @click="deleteTweet(props.id)">削除</BButton>
      </div>
    </div>
    <router-link :to="{ name: 'tweet-detail', params: { id: props.id } }" class="no-hover">
      <pre class="tweet-text">{{ props.tweetContent }}</pre>
      <div v-if="props.image" class="tweet-image">
        <img :src="props.image" style="max-width: 500px; max-height: 200px" />
      </div>
      <div class="tweet-info">
        <pre>{{ props.datetime }} tweet by @{{ props.accountId }}  <b>{{ props.views }}</b> Views</pre>
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
