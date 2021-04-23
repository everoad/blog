<template>
  <div class="container">
    <Header/>
    <main>
      <div>
        <slot/>
      </div>
      <Sidebar/>
    </main>
  </div>
</template>

<script>
import Header from "./Header"
import Sidebar from "./Sidebar"

export default {
  name: 'Layout',
  components: {
    Header,
    Sidebar
  }
}
</script>

<style scoped>
main {
  display: flex;
  position: relative;
  width: 1080px;
  margin: 0 auto 200px;
}

main > div:first-child {
  width: 800px;
  margin: 0 auto;
  padding: 3rem 1.5rem 1.5rem;
}
</style>
