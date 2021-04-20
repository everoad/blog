<template>
  <main>
    <article>
      <header>BLOG</header>
      <input type="text" class="input input-lg" placeholder="ID.." ref="id" v-model="id" />
      <input type="password" class="input input-lg" placeholder="Password.." v-model="password" />
      <button class="btn btn-green btn-block btn-lg" @click="login">LOGIN</button>
      <div>
        <router-link to="/posts">돌아가기</router-link>
      </div>
    </article>
  </main>
</template>

<script>
export default {
  name: 'Login',
  data() {
    return {
      id: null,
      password: null
    }
  },
  mounted() {
    this.$refs.id.focus()
  },
  methods: {
    login() {
      const {id, password} = this
      console.log('login', id, password)
    }
  }
}
</script>

<style scoped>
main {
  position: absolute;
  display: flex;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  justify-content: center;
  align-items: center;
}
article {
  width: 400px;
  /*border: 1px solid #ddd;*/
  padding: 2rem 1.5rem;
  box-sizing: border-box;
  margin-bottom: 8rem;
}
header {
  font-size: 2rem;
  font-weight: 600;
  text-align: center;
  padding-bottom: 2rem;
  color: #4CAF50;
}
input {
  margin-bottom: 0.5rem;
}
button {
  margin-top: 1rem;
}
div {
  padding-top: 1rem;
  text-align: center;
}
a {
  color: #4CAF50;
}
</style>