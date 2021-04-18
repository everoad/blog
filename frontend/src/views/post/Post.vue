<template>
  <div>
    <PostItem
        v-for="(item, index) in items"
        :item="item"
        :index="index"
        :key="item.id" />
  </div>
</template>

<script>
import PostItem from "@/views/post/PostItem"
import axios from "axios"

export default {
  name: 'Post',
  components: {
    PostItem
  },
  data() {
    return {
      items: []
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
