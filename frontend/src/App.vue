<template>
  <div id="app">
    <component :is="layout">
      <router-view/>
    </component>
  </div>
</template>

<script>
import { mapActions } from "vuex"

export default {
  name: 'App',
  computed: {
    layout() {
      return (this.$route.meta.layout || 'layout')
    }
  },
  methods: {
    ...mapActions('category', ['getCategoryListForSidebar']),
  },
  created() {
    this.getCategoryListForSidebar()
  }
}
</script>
