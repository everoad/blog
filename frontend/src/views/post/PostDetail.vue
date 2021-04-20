<template>
  <div class="detail-wrapper">

    <header>
      <div class="image" v-if="item.image" :style="{ 'background-image': 'url(' + item.image + ')' }"></div>
      <div class="title">{{item.title}}</div>
      <div class="info">{{item.createdTime | moment('YYYY-MM-DD HH:mm')}}</div>
    </header>
    <hr/>
    <article v-html="item.description"></article>
  </div>
</template>

<script>
import {postService} from "@/services"

export default {
  name: 'PostDetail',
  props: {
    id: String
  },
  data() {
    return {
      item: {
        title: null,
        description: null,
        createdTime: null,
        image: null
      }
    }
  },
  mounted() {
    this.getData()
  },
  methods: {
    async getData() {
      const {data: {body}} = await postService.getPost(this.id)
      this.item = body
    }
  }
}
</script>

<style scoped>
.detail-wrapper {
  padding: 1rem;
}
.image {

}
.title {
  font-size: 1.5rem;
  font-weight: 600;
  margin-bottom: 0.5rem;
}
.info {
  color: rgba(0,0,0,0.6);
  margin-bottom: 0.5rem;
}
article {
  margin-top: 3rem;
  font-size: inherit;
}
</style>
