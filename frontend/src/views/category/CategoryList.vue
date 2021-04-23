<template>
  <div class="category-wrapper">
    <header>
      <span>카테고리 목록</span>
      <button @click="addMode = !addMode" :disabled="addMode" class="btn btn-default btn-sm">추가</button>
    </header>
    <ul>
      <CategoryEditItem
          v-if="addMode"
          :item="editing" :handle-change="handleChange" :handle-input="handleInput">
        <button class="btn btn-default btn-sm" @click="handleSaveBtnClick">저장</button>
        <button class="btn btn-default btn-sm" @click="handleCancelBtnClick">취소</button>
      </CategoryEditItem>
      <template v-for="item in items">
        <CategoryEditItem
            v-if="editing.id === item.id"
            :key="item.id" :item="editing" :handle-change="handleChange" :handle-input="handleInput">
          <button class="btn btn-default btn-sm" @click="handleSaveBtnClick">저장</button>
          <button class="btn btn-default btn-sm" @click="handleCancelBtnClick">취소</button>
        </CategoryEditItem>
        <CategoryItem v-else :key="item.id" :item="item">
          <button class="btn btn-default btn-sm" @click="handleEditBtnClick(item)">수정</button>
          <button class="btn btn-default btn-sm" @click="handleRemoveBtnClick(item.id)">삭제</button>
        </CategoryItem>
      </template>
    </ul>
  </div>
</template>

<script>
import CategoryEditItem from "@/views/category/CategoryEditItem"
import CategoryItem from "@/views/category/CategoryItem"

export default {
  name: "CategoryList",
  components: {
    CategoryItem,
    CategoryEditItem
  },
  props: {
    select: {
      type: Function,
      required: true
    },
    insert: {
      type: Function,
      required: true
    },
    update: {
      type: Function,
      required: true
    },
    remove: {
      type: Function,
      required: true
    }
  },
  data() {
    return {
      items: [],
      addMode: false,
      editing: {
        id: null,
        name: null,
        display: true
      }
    }
  },
  mounted() {
    this.getData()
  },
  methods: {
    async getData() {
      const {data: {body}} = await this.select()
      this.items = body
    },
    async handleSaveBtnClick() {
      const {addMode, editing: {id, name, display}} = this
      if (addMode) {
        await this.insert({name, display})
      } else {
        await this.update(id, {name, display})
      }
      this.clearEditing()
      this.getData()
    },
    handleEditBtnClick(item) {
      this.editing.id = item.id
      this.editing.name = item.name
      this.editing.display = item.display
    },
    handleCancelBtnClick() {
      this.clearEditing()
    },
    handleRemoveBtnClick(id) {
      this.remove(id).then(this.getData)
    },
    handleInput(event) {
      this.editing.name = event.target.value
    },
    handleChange(event) {
      this.editing.display = event.target.checked
    },
    clearEditing() {
      this.addMode = false
      this.editing.id = null
      this.editing.name = null
      this.editing.display = true
    }
  }
}
</script>

<style scoped>
.category-wrapper {
  width: 600px;
  margin: 0 auto;
}
header {
  padding: 1rem;
  border: 1px solid #eee;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

ul {
  width: 100%;
  box-sizing: border-box;
  border: 1px solid #eee;
  padding: 0.5rem;
  height: 500px;
  overflow-y: auto;
}

li + li {
  margin-top: 0.5rem;
}

li:hover {
  background-color: rgba(136, 106, 181, 0.04);
}

/*button {*/
/*  border: none;*/
/*  background-color: rgba(0, 0, 0, 0);*/
/*  outline: none;*/
/*  cursor: pointer;*/
/*  color: #4CAF50;*/
/*}*/

/*button:hover {*/
/*  text-decoration: underline;*/
/*}*/
</style>