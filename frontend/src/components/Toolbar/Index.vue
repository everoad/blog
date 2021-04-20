<template>
  <div>
    <button class="icon-btn" @click.stop="handleClick"><font-awesome-icon icon="bars"/></button>
    <ul v-if="visible">
      <slot />
    </ul>
  </div>
</template>
<script>

export default {
  props: {
    items: {
      type: Array,
      required: false
    }
  },
  data() {
    return {
      visible: false
    }
  },
  methods: {
    handleClick() {
      window.addEventListener('click', this.handleScreenClick)
      this.visible = !this.visible
    },
    handleScreenClick() {
      window.removeEventListener('click', this.handleScreenClick)
      this.visible = false
    }
  },
  destroyed() {
    window.removeEventListener('click', this.handleScreenClick)
  }
}
</script>

<style scoped>
ul {
  position: absolute;
  border: 1px solid #ccc;
  box-sizing: border-box;
  z-index: 100;
}
</style>
