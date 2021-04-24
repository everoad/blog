<template>
  <div class="wrapper">
    <input type="checkbox" :id="id" v-model="localValue"/>
    <label :for="id"></label>
  </div>
</template>

<script>
export default {
  props: {
    value: {
      type: Boolean,
      required: true
    }
  },
  computed: {
    id() {
      return `checkbox-${Math.round(Math.random() * 1000)}`
    },
    localValue: {
      get() {
        return this.value
      },
      set(value) {
        this.$emit('change', value)
      }
    }
  }
}
</script>

<style scoped>
.wrapper {
  position: relative;
}

input[type="checkbox"] {
  display: none;
}

input[type="checkbox"] + label {
  display: inline-block;
  vertical-align: top;
  width: 1rem;
  height: 1rem;
  cursor: pointer;
  border: 2px solid #ccc;
  border-radius: 2px;
}

input[type="checkbox"]:checked + label {
  border: 2px solid #4CAF50;
  background: #4CAF50;
  text-align: center;
}

input[type="checkbox"]:checked + label::after {
  font-weight: 900;
  content: "✔";
  color: #fff;
  line-height: 1rem;
  position: absolute;
  left: 0.4rem;
}
</style>