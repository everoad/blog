<template>
  <li>
    <div class="category-info">
      <div class="drag"></div>
      <div class="display">
        공개<br/>
        <input type="checkbox" :checked="display" name="display" @change="handleChange"/>
      </div>
      <div class="name">
        명칭<br/>
        <input type="text" class="input" :value="name" name="name" @input="handleChange">
      </div>
    </div>
    <div class="category-manage">
      관리<br/>
      <button v-for="(button, index) in buttons"
              :key="index" @click="handleClick(button.onClick)">
        {{button.text}}
      </button>
    </div>
  </li>
</template>

<script>
export default {
  props: {
    id: Number,
    name: String,
    display: {
      type:Boolean,
      default: true
    },
    buttons: {
      type: Array,
      child: {
        onClick: Function
      }
    }
  },
  data() {
    return {
      edit: {
        name: null,
        display: true
      },
    }
  },
  methods: {
    handleChange(event) {
      this.edit[event.target.name] = event.target.value
    },
    handleClick(handler) {
      const {id, edit: {display, name}} = this
      handler({name, display, id})
    },
  }
}
</script>

<style scoped>

li {
  border: 1px solid #eee;
  line-height: 45px;
  background-color: #fff;
  display: flex;
  justify-content: space-between;
}

.category-info {
  flex: 1;
}

.category-info > div {
  margin: 0 1rem;
  text-align: center;
  display: inline-block;
}

.category-info > .drag {
  width: 2rem;
}

.category-info > .display {
  width: 4rem;
}

.category-manage {
  width: 100px;
}
</style>