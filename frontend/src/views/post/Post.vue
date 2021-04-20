<template>
  <div>
    <PostItem
        v-for="(item, index) in items"
        :item="item"
        :index="index"
        :key="item.id"/>
  </div>
</template>

<script>
import PostItem from "@/views/post/PostItem"
import {postService} from "@/services"

export default {
  name: 'Post',
  components: {
    PostItem
  },
  data() {
    return {
      items: [],
      hasNext: false,
      page: 0,
      size: 10
    }
  },
  mounted() {
    this.getData()
  },
  created() {
    window.addEventListener('scroll', this.handleScroll)
  },
  destroyed () {
    window.removeEventListener('scroll', this.handleScroll)
  },
  methods: {
    async getData() {
      const {page, size} = this
      const {data: {body: {content, last}}} = await postService.getPostList({page, size})
      content.forEach(one => this.items.push(one))
      this.hasNext = !last
    },
    handleScroll() {
      if(this.hasNext
          && document.documentElement.scrollTop + window.innerHeight >= document.body.scrollHeight) {
        this.getData()
      }
    }
  }
}
</script>

<style scoped>
</style>
