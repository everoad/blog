<template>
  <div>
    Detail
  </div>
</template>

<script>

export default {
  name: 'PostDetail',
  props: {
    id: Number
  },
  data() {
    return {
      items: []
    }
  },
  mounted() {
    this.getData()
  },
  methods: {
    async getData() {
    }
  }
}
</script>

<style scoped>
</style>
