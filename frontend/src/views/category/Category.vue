<template>
  <article>
    <div class="left-panel">
      <header><span>목록</span></header>
      <ul class="category-list">
        <li v-for="item in categories"
            :key="item.id" @click="handleCategoryClick(item)">
          <div>
            <font-awesome-icon icon="bars"/>
          </div>
          <div>{{ item.name }} ({{ item.count }})</div>
        </li>
      </ul>
    </div>

    <div class="right-panel">
      <header>
        <span>설정</span>
        <span>
          <a >삭제</a>
          <a >저장</a>
        </span>
      </header>
      <div>
        <div class="input-wrapper">
          <div>카테고리명</div>
          <div>
            <input type="text" class="input" v-model="selected.name"/>
          </div>
        </div>
        <div class="input-wrapper">
          <div>공개여부</div>
          <div>
            <input type="checkbox" :checked="selected.display"/>
          </div>
        </div>
        <div>

        </div>
      </div>
    </div>
  </article>
</template>

<script>
import {categoryService} from "@/services"

export default {
  name: 'Category',
  data() {
    return {
      categories: [],
      selected: {
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
    async getCategory(id) {
      const {data: {body}} = await categoryService.getCategory(id)
      this.selected = body
    },
    handleCategoryClick(category) {
      this.getCategory(category.id)
    }
  }
}
</script>
<style scoped>
article {
  display: flex;
}

.left-panel,
.right-panel {
  flex: 1;
}

header {
  padding: 1rem;
  border: 1px solid #eee;
  background-color: #eee;
  font-weight: 600;
  display: flex;
  justify-content: space-between;
}
header a {
  color: #4CAF50;
  padding: 0 0.5rem;
  cursor: pointer;
}

.right-panel {
  margin-left: 2rem;
  border: 1px solid #eee;
}

.right-panel > div {
  padding: 0.5rem;
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
  padding: 1rem;
  background-color: #fff;
}

.category-list > li > div {
  display: inline-block;
}

.category-list > li > div:first-child {
  width: 2rem;
  text-align: center;
  margin-right: 2rem;
}

.category-list > li + li {
  margin-top: 0.5rem;
}

.input-wrapper {
  display: flex;
  align-items: center;
  margin-bottom: 0.5rem;
  line-height: 35px;
}

.input-wrapper > div:first-child {
  width: 6rem;
  text-align: right;
  margin-right: 1rem;
}
.input-wrapper > div:last-child {
  flex: 1;
}
</style>