<template>
  <aside>
    <nav>
      <ul class="category">
        <li><div>전체보기</div></li>
        <li>
          <div>WEB</div>
          <ul>
            <li>Spring</li>
            <li>HTML</li>
          </ul>
        </li>
        <li><div>DB</div></li>
      </ul>
    </nav>
  </aside>
</template>

<script>

export default {
  name: 'Sidebar'
}
</script>

<style scoped>
aside {
  width: 280px;
  border-left: 1px solid #eee;
  box-sizing: border-box;
  padding: 2rem;
}
.category li {
  padding: 0.5rem;
  box-sizing: border-box;
}
.category>li>ul {
  padding: 0.5rem;
  color: #999;
}
</style>
