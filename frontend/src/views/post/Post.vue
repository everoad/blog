<template>
  <div>
    <PostList
        ref="postList"
        :select="getPostData"/>
  </div>
</template>

<script>
import PostList from "@/views/post/PostList"
import {postService} from "@/services"

export default {
  name: 'Post',
  components: {
    PostList
  },
  watch: {
    $route(to) {
      const {categoryId, keyword} = to.query
      this.$refs.postList.filter({categoryId, keyword})
    }
  },
  methods: {
    async getPostData(params) {
      const {data: {body: {content, last}}} = await postService.getPostList(params)
      return {items: content, hasNext: !last}
    }
  }
}
</script>

<style scoped>
</style>
