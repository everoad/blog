<template>
  <div>
    <div v-if="items.length > 0">
      <PostItem
          v-for="(item, index) in items"
          :item="item"
          :index="index"
          :key="item.id"/>
    </div>
    <div v-else class="no-data">
      {{ noDataMsg }}
    </div>
    <ul class="pagination">
      <li v-on:click="page = 0">＜</li>
      <li v-for="index in range"
          :key="index"
          :class="{ active: (page === index) }"
          v-on:click="page = index">
        {{ index + 1 }}
      </li>
      <li v-on:click="page = pageCount - 1">＞</li>
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
    },
    noDataMsg: {
      type: String,
      default: "게시글이 없습니다."
    },
    keywords: {
      type: Object,
      required: false
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
      range: [],
      totalElements: 0
    }
  },
  mounted() {
    this.getData()
  },
  watch: {
    page() {
      this.getData()
    },
    keywords() {
      this.page = 0
      this.getData()
    }
  },
  computed: {
    pageCount() {
      const {page, totalElements, size} = this
      return (page === 0 && totalElements === 0) ? 1 : Math.ceil(totalElements / size)
    },
  },
  methods: {
    async getData() {
      const {page, url, size, keywords} = this
      const params = {page, size, ...keywords}
      const {data: {body: {content, totalElements}}} = await axios.get(url, {params})
      this.items = content
      this.totalElements = totalElements
      this.calculateRange()
    },

    calculateRange() {
      const {page, indent, pageCount} = this
      const start = (page - indent) > -1 ? (page - indent) : 0
      const end = (page + indent) < (pageCount - 1) ? (page + indent) : (pageCount - 1)
      this.range = new Array(end - start + 1).fill(start).map((n, i) => n + i)
    },

  }
}
</script>

<style scoped>
.item-wrapper {
  min-height: 300px;
}

.pagination {
  margin-top: 1rem;
  float: right;
}

.pagination > li {
  padding: 0.2rem 1rem;
  display: inline-block;
  cursor: pointer;
  color: #aaa;
}

.pagination > li.active {
  color: green;
}

.pagination > li + li {
  border-left: 1px solid #ddd;
}

.pagination > li:hover {
  background-color: #eee;
}

.pagination > li:first-child,
.pagination > li:last-child {
  color: green;
  font-weight: 600;
}

.no-data {
  min-height: 20rem;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 1.5rem;
  color: #aaa;
}
</style>
