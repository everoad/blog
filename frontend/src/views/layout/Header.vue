<template>
  <header>
    <div>ER BLOG</div>
    <div>
      <input type="text"/>
      <button class="icon-btn">
        <font-awesome-icon icon="search"/>
      </button>
    </div>
    <div>
      <router-link to="/posts/editor">글쓰기</router-link>
      <router-link to="/login">로그인</router-link>
    </div>
  </header>
</template>
<script>

export default {
  name: 'Sidebar'
}
</script>

<style scoped>
header {
  width: 100%;
  height: 145px;
  padding: 2rem 6rem;
  border-bottom: 1px solid #eee;
  box-sizing: border-box;
  display: flex;
  justify-content: space-between;
  justify-items: center;
}

input {
  padding: 0.35rem 1rem;
  box-sizing: border-box;
  display: inline-block;
  border-right: 0;
  border-left: 1px solid #ccc;
  border-top: 1px solid #ccc;
  border-bottom: 1px solid #ccc;
  border-top-left-radius: 15px;
  border-bottom-left-radius: 15px;
  font-size: 1.2rem;
}

input:focus {
  outline: none;
}

button {
  border-top-right-radius: 15px;
  border-bottom-right-radius: 15px;
}
</style>
