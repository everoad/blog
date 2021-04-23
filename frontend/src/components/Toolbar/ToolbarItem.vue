<template>
  <li v-if="visible">
    <router-link v-if="to" tag="a" :to="to">{{ text }}</router-link>
    <a v-if="click" @click.prevent="click">{{ text }}</a>
  </li>
</template>

<script>
export default {
  name: "ToolbarItem",
  props: {
    text: {
      type: String,
      required: true,
    },
    to: String,
    click: Function,
    visible: {
      type: Boolean,
      default: true
    }
  }
}
</script>

<style scoped>
li {
  cursor: pointer;
  background-color: #fff;
  text-align: center;
}

li > a {
  color: #aaa;
  text-decoration: none;
  padding: 0.4rem 1rem;
  line-height: 2.2rem;
}

li:hover {
  background-color: #eee;
}

ul > li + li {
  border-top: 1px solid #ccc;
}
</style>