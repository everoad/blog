<template>
  <aside>
    <nav>
      <ul>
        <router-link to="/posts" tag="li">전체보기 ({{ totalCount }})</router-link>
        <router-link v-for="item in categories" :key="item.id"
                     :to="{path: '/posts', query: { categoryId: item.id }}" tag="li">
          {{ item.name }} ({{ item.count }})
        </router-link>
      </ul>
    </nav>
  </aside>
</template>

<script>
import {mapActions, mapState} from "vuex"

export default {
  name: 'Sidebar',
  computed: {
    ...mapState('category', {
      categories: state => state.categories
    }),
    totalCount() {
      if (this.categories.length > 0) {
        return this.categories.map(one => one.count).reduce((a, b) => a + b)
      } else {
        return 0
      }
    }
  },
  methods: {
    ...mapActions('category', ['getCategoryListForSidebar']),
  },
  created() {
    this.getCategoryListForSidebar()
  }
}
</script>

<style scoped>
aside {
  width: 280px;
  border-left: 1px solid #eee;
  box-sizing: border-box;
  padding: 3rem 1.5rem 1.5rem;
}

li {
  padding: 0.6rem 0;
  box-sizing: border-box;
  cursor: pointer;
  font-size: 1.1rem;
  font-weight: 400;
  color: #999;
}

li:hover {
  color: #4CAF50;
  text-decoration: underline;
}
</style>
