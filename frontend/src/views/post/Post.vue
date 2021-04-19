<template>
  <div>
    <PostList
      url="/api/posts"
      :size="10"
    />
  </div>
</template>

<script>
import PostList from "@/views/post/PostList"
import axios from "axios"

export default {
  name: 'Post',
  components: {
    PostList
  },
  data() {
    return {
      items: [],

      page: 0,
      totalElements: 0,
    }
  },
  mounted() {
    this.getData()
  },
  methods: {
    async getData() {
      const { data: { body } } = await axios.get('/api/posts')
      this.items = body
    }
  }
}
</script>

<style scoped>
</style>
