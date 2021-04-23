<template>
  <div class="loading-panel">
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
.loading-panel {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 200px;
  font-size: 2.5rem;
  background-color: #fff;
}
</style>