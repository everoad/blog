<template>
  <li>
    <div>
      <div class="drag">
        <font-awesome-icon icon="bars"/>
      </div>
      <div class="display" v-if="item.display">공개</div>
      <div class="display" v-else>비공개</div>
      <div class="name">{{ item.name }}</div>
      <div class="manage">
        <slot/>
      </div>
    </div>
  </li>
</template>
<script>
export default {
  name: 'CategoryItem',
  props: {
    item: {
      name: {
        type: String,
        required: true
      },
      display: {
        type: Boolean,
        required: true
      }
    }
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
  line-height: 40px;
}

li > div > div {
  text-align: center;
  margin: 0 1rem;
}

.drag {
  width: 2rem;
  cursor: pointer;
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