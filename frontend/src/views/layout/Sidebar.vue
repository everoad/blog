<template>
  <aside>
    <nav>
      <ul>
        <router-link to="/posts" tag="li">전체보기</router-link>
        <router-link v-for="item in categories" :key="item.id"
                     :to="{path: '/posts', query: { categoryId: item.id }}" tag="li">
          <font-awesome-icon icon="caret-right"/>
          {{item.name}}
        </router-link>
      </ul>
    </nav>
  </aside>
</template>

<script>
import {mapState} from "vuex"

export default {
  name: 'Sidebar',
  computed: {
    ...mapState('category', ['categories'])
  }
}
</script>

<style scoped>
aside {
  width: 280px;
  border-left: 1px solid #eee;
  box-sizing: border-box;
  padding: 2rem;
}

li {
  padding: 0.6rem 0;
  box-sizing: border-box;
  cursor: pointer;
  font-size: 1.1rem;
}

li:hover {
  color: #4CAF50;
  text-decoration: underline;
}

</style>
