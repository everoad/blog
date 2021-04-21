<template>
  <div>
    <Header/>
    <main>
      <div>
        <slot />
      </div>
    </main>
  </div>
</template>

<script>
import Header from "./Header"

export default {
  name: 'SystemLayout',
  components: {
    Header
  }
}
</script>

<style scoped>
main {
  position: relative;
  width: 1080px;
  margin: 0 auto 100px;
}
main>div {
  width: 100%;
  margin: 0 auto;
  padding: 1.5rem;
}
</style>
