<template>
  <div class="loading-screen">
    <font-awesome-icon icon="spinner" spin />
  </div>
</template>

<script>
export default {
}
</script>

<style scoped>
.loading-screen {
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 2.5rem;
  background-color: #fff;
  position: absolute;
  width: 100%;
  height: 100%;
  z-index: 999;
}
</style>