<template>
  <article>
    <header>
      <span>목록</span>
      <button @click="add.mode = !add.mode" :disabled="add.mode">추가</button>
    </header>
    <ul class="category-list">
      <CategoryEditItem
          v-if="add.mode"
          :buttons="[
              {text: '저장', onClick: handleSaveBtnClick},
              {text: '취소', onClick: handleCancelBtnClick}
          ]"
      />
      <li v-for="item in categories" :key="item.id">
        <div class="category-info">
          <div class="drag">
            <font-awesome-icon icon="bars"/>
          </div>
          <div class="display" v-if="item.display">공개</div>
          <div class="display" v-else>비공개</div>
          <div class="name">{{ item.name }}</div>
        </div>
        <div class="category-manage">
          <button @click="handleEditBtnClick">수정</button>
          <button @click="handleRemoveBtnClick(item.id)">삭제</button>
        </div>
      </li>
    </ul>
  </article>
</template>

<script>
import CategoryEditItem from "@/views/category/CategoryEditItem"
import CategoryItem from "@/views/category/CategoryItem"
import {categoryService} from "@/services"

const addDefault = {
  mode: false,
  name: null,
  display: true
}

export default {
  name: 'Category',
  components: {
    CategoryEditItem,
    CategoryItem
  },
  data() {
    return {
      categories: [],
      add: addDefault,
      edit: {
        id: null,
        name: null,
        display: null
      }
    }
  },
  mounted() {
    this.getCategoryList()
  },
  methods: {
    async getCategoryList() {
      const {data: {body}} = await categoryService.getCategoryList()
      this.categories = body
    },
    async handleSaveBtnClick(data) {
      await categoryService.addCategory(data)
      await this.getCategoryList()
      this.add.mode = false
    },
    handleCancelBtnClick() {
      this.add.mode = false
    },
    handleEditBtnClick(category) {
      this.edit.id = category.id
      this.edit.name = category.name
      this.edit.display = category.display
    },
    async handleRemoveBtnClick(categoryId) {
      await categoryService.removeCategory(categoryId)
      await this.getCategoryList()
    }
  }
}
</script>
<style scoped>
article {
}

header {
  padding: 1rem;
  border: 1px solid #eee;
  background-color: #eee;
  font-weight: 600;
  display: flex;
  justify-content: space-between;
}

.category-list {
  width: 100%;
  box-sizing: border-box;
  border: 1px solid #eee;
  padding: 0.5rem;
  height: 500px;
  overflow-y: auto;
}

.category-list > li {
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

.category-list > li + li {
  margin-top: 0.5rem;
}

</style>