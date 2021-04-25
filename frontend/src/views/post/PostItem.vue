<template>
  <router-link :to="{ name: 'PostDetail', params: { id: item.id }}" tag="article">
    <div class="content">
      <header>{{ item.title }}</header>
      <div v-html="shortDescription"></div>
      <footer>
        <div>작성일 {{ createdTime }}</div>
        <div>조회수 {{ item.viewCount }}</div>
      </footer>
    </div>
    <div class="image">
      <div v-if="item.image" :style="{
        background: `url(/api/images/${item.image}) no-repeat center`,
        backgroundSize: 'cover'
      }">
      </div>
    </div>
  </router-link>
</template>

<script>
import moment from "moment"

export default {
  name: 'PostItem',
  props: {
    item: {
      id: Number,
      title: String,
      image: String,
      description: String,
      createdTime: String,
      viewCount: Number
    }
  },
  computed: {
    shortDescription() {
      if (this.item.description.length > 120) {
        return this.item.description.substring(0, 120) + '...'
      }
      return this.item.description
    },
    createdTime() {
      return moment(this.item.createdTime).format('YYYY-MM-DD HH:mm')
    }
  }
}
</script>

<style scoped>
article {
  width: 100%;
  padding: 1rem;
  display: flex;
  justify-content: space-between;
  cursor: pointer;
  box-sizing: border-box;
}

article:hover header {
  text-decoration: underline;
}

header {
  color: #222;
  font-size: 1.4rem;
}

.content {
  color: #999;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 0.5rem 0.5rem 0.5rem 0;
  flex: 1;
}

.image {
  width: 128px;
  border: 1px solid #eee;
  height: 142px;
  box-sizing: border-box;
  overflow: hidden;
}

.image > div {
  width: 128px;
  height: 100%;
}

footer {
  color: #999;
  font-size: 0.9rem;
}

footer > div {
  display: inline-block;
  vertical-align: top;
}

footer > div + div {
  margin-left: 1rem;
}
</style>
