<template>
  <div>
    <button v-on:click="handleClick">펼치기</button>
    <ul v-if="visible">
      <li v-for="(item, index) in items" :key="index">
        {{ item.text }}
      </li>
    </ul>
  </div>
</template>
<script>

export default {
  props: {
    items: Array
  },
  data() {
    return {
      visible: false
    }
  },
  methods: {
    handleClick() {
      this.visible = !this.visible
    }
  }
}
</script>

<style scoped>
ul {
  width: 150px;
  position: absolute;
  border: 1px solid #eee;
  box-sizing: border-box;
}

li {
  padding: 1rem;
}

ul > li + li {
  border-top: 1px solid #eee;
}
</style>
