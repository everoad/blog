<template>
  <header>
    <div class="logo">
      <router-link to="/posts">BLOG</router-link>
    </div>
    <div>
      <input type="text"/>
      <button class="icon-btn">
        <font-awesome-icon icon="search"/>
      </button>
    </div>
    <div>
      <Toolbar>
        <ToolbarItem to="/posts/editor" text="글쓰기" :visible="status.loggedIn" />
        <ToolbarItem to="/login" text="로그인" :visible="!status.loggedIn" />
        <ToolbarItem :click="handleLogout" text="로그아웃" :visible="status.loggedIn" />
      </Toolbar>
    </div>
  </header>
</template>
<script>
import { mapState, mapActions } from "vuex"
import Toolbar from "@/components/Toolbar/Index"
import ToolbarItem from "@/components/Toolbar/ToolbarItem"

export default {
  name: 'Sidebar',
  components: {
    Toolbar,
    ToolbarItem
  },
  computed: {
    ...mapState('auth', ['status'])
  },
  methods: {
    ...mapActions('auth', ['logout']),
    handleLogout() {
      this.logout()
    }
  }
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

.logo>a {
  font-size: 2rem;
  font-weight: 600;
  color: #4CAF50;
  text-decoration: none;
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
