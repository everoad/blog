<template>
  <header>
    <div class="logo">
      <router-link to="/posts">BLOG</router-link>
    </div>
    <div class="center">
      <form @submit.prevent="handleSubmit">
        <input type="text" v-model="keyword" placeholder="Search.."/>
        <button class="icon-btn" type="submit">
          <font-awesome-icon icon="search"/>
        </button>
      </form>
      <nav>
        <ul>
          <router-link to="/posts" tag="li" active-class="active">Post</router-link>
          <router-link to="/about" tag="li" active-class="active">About</router-link>
        </ul>
      </nav>
    </div>
    <div>
      <Toolbar>
        <ToolbarItem to="/posts/editor" text="글쓰기" :visible="status.loggedIn"/>
        <ToolbarItem to="/manage/categories" text="관리자" :visible="status.loggedIn"/>
        <ToolbarItem to="/login" text="로그인" :visible="!status.loggedIn"/>
        <ToolbarItem :click="handleLogout" text="로그아웃" :visible="status.loggedIn"/>
      </Toolbar>
    </div>
  </header>
</template>
<script>
import {mapState, mapActions} from "vuex"
import {Toolbar, ToolbarItem} from "@/components/Toolbar"
import router from "@/routers"

export default {
  name: 'Sidebar',
  components: {
    Toolbar,
    ToolbarItem
  },
  data() {
    return {
      keyword: null
    }
  },
  computed: {
    ...mapState('auth', ['status'])
  },
  mounted() {
    const {keyword} = this.$route.query
    this.keyword = keyword
  },
  methods: {
    ...mapActions('auth', ['logout']),
    handleLogout() {
      this.logout()
    },
    async handleSubmit() {
      const {keyword} = this
      await router.push({path: '/posts', query: keyword ? {keyword} : {}})
    }
  }
}
</script>

<style scoped>
header {
  width: 100%;
  height: 145px;
  padding: 2rem 12rem 0;
  border-bottom: 1px solid #eee;
  box-sizing: border-box;
  display: flex;
  justify-content: space-between;
  justify-items: center;
}

.logo > a {
  font-size: 2.5rem;
  font-weight: 600;
  color: #4CAF50;
  text-decoration: none;
}

input {
  padding: 0.6rem 1rem;
  box-sizing: border-box;
  display: inline-block;
  border-right: 0;
  border-left: 1px solid #ccc;
  border-top: 1px solid #ccc;
  border-bottom: 1px solid #ccc;
  border-top-left-radius: 4px;
  border-bottom-left-radius: 4px;
  font-size: 1.2rem;
  width: 25rem;
}

input::placeholder {
  color: #bbb;
}

input:focus {
  outline: none;
}

button {
  padding: 0.75rem 1.2rem;
  border-top-right-radius: 4px;
  border-bottom-right-radius: 4px;
}

.center {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.center ul li {
  display: inline-block;
  padding: 0.6rem 0;
  width: 6rem;
  text-align: center;
  font-weight: 600;
  cursor: pointer;
  color: #666;
}
.center ul li.active {
  border-bottom: 2px solid #4CAF50;
  color: #4CAF50;
  box-sizing: border-box;
}
</style>
