<template>
  <div v-if="open">
    <div>
      Modal!!
    </div>
  </div>
</template>

<script>
export default {
  name: "Modal",
  data() {
    return {
      title: null,
      message: null,
      open: false
    }
  },
  methods: {
    show(title, description) {
      this.title = title
      this.description = description
      this.open = true
    },
    close() {
      this.title = null
      this.description = null
      this.open = false
    }
  }
}
</script>

<style scoped>
div {
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0,0,0,0.5);
  z-index: 999;
  display: flex;
  justify-content: center;
  align-items: center;
}
div>div {
  background-color: #fff;
  width: 500px;
  height: 500px;
}
</style>