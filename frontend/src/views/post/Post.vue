<template>
  <div>
    <div class="breadcrumb">
      {{ category }}
    </div>
    <PostList
        ref="postList"
        :select="getPostData"/>
  </div>
</template>

<script>
import {mapState} from "vuex"
import PostList from "@/views/post/PostList"
import {postService} from "@/services"

export default {
  name: 'Post',
  components: {
    PostList
  },
  data() {
    return {
      category: null
    }
  },
  computed: {
    ...mapState('category', ['categories'])
  },
  watch: {
    $route(to) {
      const {categoryId, keyword} = to.query
      this.$refs.postList.filter({categoryId, keyword})
      this.getCategory(categoryId)
    }
  },
  mounted() {
    const {categoryId} = this.$route.query
    this.getCategory(categoryId)
  },
  methods: {
    async getPostData(params) {
      const {data: {body: {content, last}}} = await postService.getPostList(params)
      return {items: content, hasNext: !last}
    },
    getCategory(categoryId) {
      const filtered = this.categories.filter(item => item.id === Number(categoryId))
      this.category = filtered.length > 0 ? filtered[0].name : '전체보기'
    }
  }
}
</script>

<style scoped>
.breadcrumb {
  font-size: 1.2rem;
  padding: 1rem 1rem 1.5rem;
  border-bottom: 1px solid #eee;
  color: #555;
  font-weight: 600;
}
</style>
