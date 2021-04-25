<template>
  <aside>
    <nav>
      <ul>
        <router-link to="/manage/categories" tag="li">카테고리</router-link>
        <router-link to="/manage/revisions" tag="li">변경이력</router-link>
      </ul>
    </nav>
  </aside>
</template>

<script>
export default {
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
