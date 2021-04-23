<template>
  <div v-if="items.length > 0">
    <PostItem
        v-for="(item, index) in items"
        :item="item"
        :index="index"
        :key="item.id" />
  </div>
  <div v-else class="no-data">
    게시글이 없습니다.
  </div>
</template>

<script>
import PostItem from "@/views/post/PostItem"

export default {
  name: "PostList",
  components: {
    PostItem
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
    window.addEventListener('scroll', this.handleScroll)
  },
  mounted() {
    this.getData()
  },
  destroyed () {
    window.removeEventListener('scroll', this.handleScroll)
  },
  methods: {
    async getData() {
      const {page, size, select, keywords} = this
      const {items, hasNext} = await select({page, size, ...keywords})
      items.forEach(one => this.items.push(one))
      this.hasNext = hasNext
      this.page += 1
    },
    filter(keywords) {
      this.keywords = keywords || {}
      this.page = 0
      this.items.splice(0)
      this.getData()
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
.no-data {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 300px;
  font-size: 1.5rem;
}
</style>