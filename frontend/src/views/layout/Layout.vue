<template>
  <div class="container">
    <Header/>
    <main>
      <div>
        <slot/>
      </div>
      <component :is="sidebar" />
    </main>
    <div class="footer">
    </div>
  </div>
</template>

<script>
import Header from "./Header"

export default {
  name: 'Layout',
  components: {
    Header
  },
  computed: {
    sidebar() {
      return (this.$route.meta.sidebar || 'sidebar')
    }
  }
}
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  min-height: 100%;
}
main {
  display: flex;
  position: relative;
  width: 1080px;
  margin: 0 auto;
  flex: 1;
}
main > div:first-child {
  width: 800px;
  margin: 0 auto;
  padding: 2rem 1.5rem 10rem;
  min-height: 100%;
  height: auto;
  box-sizing: border-box;
  position: relative;
}
.footer {
  height: 5rem;
  border-top: 1px solid #eee;
}
</style>
