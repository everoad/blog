<template>
  <li>
    <div class="category-info">
      <div class="drag"><font-awesome-icon icon="bars"/></div>
      <div class="display" v-if="item.display">공개</div>
      <div class="display" v-else>비공개</div>
      <div class="name">{{ item.name }}</div>
    </div>
    <div class="category-manage">
      <button>수정</button>
      <button>삭제</button>
    </div>
  </li>
</template>
<script>
export default {
  name: 'CategoryItem',
  props: {
    item: {
      name: {
        type: String
      },
      display: {
        type: Boolean,
        default: true
      }
    },
    save: {
      type: Function,
      required: true
    },
    cancel: {
      type: Function,
      required: true
    }
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