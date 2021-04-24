<template>
  <div id="app">
    <component :is="layout">
      <router-view/>
    </component>
  </div>
</template>

<script>
export default {
  name: 'App',
  computed: {
    layout() {
      return (this.$route.meta.layout || 'layout')
    }
  }
}
</script>
<style>
#app {
  height: 100%;
}
</style>
