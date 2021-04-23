<template>
  <div class="loading-screen">
    <font-awesome-icon icon="spinner" spin />
  </div>
</template>

<script>
export default {
  props: {
  }
}
</script>

<style scoped>
.loading-screen {
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 2.5rem;
  background-color: #fff;
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
}
</style>