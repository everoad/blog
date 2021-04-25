<template>
  <li>
    <div>
      <div class="drag"></div>
      <div class="display">공개</div>
      <div class="name">명칭</div>
      <div class="manage">관리</div>
    </div>
    <div>
      <div class="drag"></div>
      <div class="display">
<!--        <Checkbox v-model="item.display" />-->
        <input type="checkbox" :checked="item.display" name="display" @change="handleChange"/>
      </div>
      <div class="name">
        <input type="text" class="input" :value="item.name" @input="handleInput" ref="name"/>
      </div>
      <div class="manage">
        <slot/>
      </div>
    </div>
  </li>
</template>

<script>
import {Checkbox} from "@/components/Form"
export default {
  components: {
    Checkbox
  },
  props: {
    item: {
      id: Number,
      name: String,
      display: {
        type: Boolean,
        default: true
      }
    },
    handleInput: {
      type: Function,
      required: true
    },
    handleChange: {
      type: Function,
      required: true
    }
  },
  mounted() {
    this.$refs.name.focus()
  }
}
</script>

<style scoped>

li {
  border: 1px solid #eee;
  background-color: #fff;
}

li > div {
  width: 100%;
  display: flex;
  line-height: 45px;
}

li > div:first-child {
  border-bottom: 1px solid #eee;
  line-height: 35px;
}

li > div > div {
  text-align: center;
  margin: 0 1rem;
}

.drag {
  width: 2rem;
}

.display {
  width: 4rem;
}

.name {
  flex: 1;
  text-align: left;
}

.manage {
  width: 100px;
}

.manage > button + button {
  margin-left: 0.2rem;
}
</style>