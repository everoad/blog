<template>
  <div>
    <font-awesome-icon icon="spinner" spin />
  </div>
</template>

<script>
export default {
}
</script>

<style scoped>
.loading-panel {
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
.loading-screen {
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 2.5rem;
  background-color: rgba(0,0,0,0.5);
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  z-index: 999;
  color: #fff;
}
</style>