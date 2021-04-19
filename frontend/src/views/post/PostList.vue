<template>
  <div>
    <div>
      <PostItem
          v-for="(item, index) in items"
          :item="item"
          :index="index"
          :key="item.id" />
    </div>
    <ul class="pagination">
      <li v-for="idx in displayCnt"
          :key="idx"
          :class="{ active: (page+1 === idx) }"
      >{{idx}}</li>
    </ul>
  </div>
</template>

<script>
import PostItem from "@/views/post/PostItem"
import axios from "axios"

export default {
  name: 'PostList',
  props: {
    url: {
      type: String,
      required: true
    },
    size: {
      type: Number,
      default: 10
    }
  },
  components: {
    PostItem
  },
  data() {
    return {
      items: [],
      page: 0,
      indent: 4,
      totalElements: 0,
    }
  },
  mounted() {
    this.getData()
  },
  methods: {
    async getData() {
      const { page, url, size } = this
      const { data: { body } } = await axios.get(url, { params: { page, size } })
      this.items = body
    },

    calculate() {
      const { page, indent, totalElements, size } = this
      const pageCount = (page === 0 && totalElements === 0) ? 1 : Math.ceil(totalElements/ size)
      const start = ((page - indent) > -1) ? (page - indent) : 0
      const end = ((page + indent) < (pageCount -1)) ? (page + indent) : (pageCount - 1)
      
    }
  }
}
</script>

<style scoped>
.pagination {
  margin-top: 1rem;
  float: right;
}
.pagination>li {
  padding: 0.2rem 1rem;
  display: inline-block;
  cursor: pointer;
  color: #aaa;
}
.pagination>li.active {
  color: green;
}
.pagination>li+li {
  border-left: 1px solid #ddd;
}
.pagination>li:hover {
  background-color: #eee;
}
</style>
