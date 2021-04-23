<template>
  <div>
    <PostItem
        v-for="(item, index) in items"
        :item="item"
        :index="index"
        :key="item.id"/>
    <LoadingPanel v-if="loading" />
    <div v-else-if="items.length === 0" class="no-data">
      게시글이 없습니다.
    </div>
  </div>
</template>

<script>
import PostItem from "@/views/post/PostItem"
import {LoadingPanel} from "@/components/LoadingPanel"
import debcounce from "debounce"

export default {
  name: "PostList",
  components: {
    PostItem,
    LoadingPanel
  },
  props: {
    select: {
      type: Function,
      required: true
    }
  },
  data() {
    return {
      loading: true,
      items: [],
      hasNext: false,
      page: 0,
      size: 10,
      keywords: {}
    }
  },
  created() {
    window.addEventListener('scroll', debcounce(this.handleScroll, 200))
  },
  mounted() {
    const {categoryId, keyword} = this.$route.query
    this.filter({categoryId, keyword})
  },
  destroyed() {
    window.removeEventListener('scroll', debcounce(this.handleScroll, 200))
  },
  methods: {
    async getData() {
      this.loading = true
      const {page, size, select, keywords} = this
      const {items, hasNext} = await select({page, size, ...keywords})
      items.forEach(one => this.items.push(one))
      this.hasNext = hasNext
      this.page = this.page + 1
      this.loading = false
    },
    filter(keywords) {
      this.keywords = keywords || {}
      this.page = 0
      this.items.splice(0)
      this.getData()
    },
    handleScroll() {
      if (!this.loading && this.hasNext
          && document.documentElement.scrollTop + window.innerHeight >= document.body.scrollHeight) {
        this.getData()
      }
    }
  }
}
</script>

<style scoped>
.no-data {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 300px;
  font-size: 1.5rem;
}
</style>