<template>
  <div>
    <input type="text" :class="this.class" @input="handleInput" />
    <ul>
      <li v-for="(item, index) in items"
          :key="index"
          @click="() => handleClick(item)">
        {{item.name}}
      </li>
    </ul>
  </div>
</template>

<script>
export default {
  name: "SearchInput",
  props: {
    class: {
      type: String
    },
    data: {
      type: Function,
      required: true
    }
  },
  data() {
    return {
      items: []
    }
  },
  methods: {
    async handleInput(event) {
      const {data} = this
      const {data: {body: {categories}}} = await data({value: event.target.value})
      this.items.splice(0)
      categories.forEach(category => this.items.push(category))
    },

    handleClick(categories) {

    }
  }

}
</script>

<style scoped>


</style>