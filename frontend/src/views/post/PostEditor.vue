<template>
  <article>
    <div class="input-wrapper">
      <label for="category">카테고리</label>
      <select id="category" v-model="categoryId" class="input">
        <option :value="-1" disabled>선택</option>
        <option v-for="category in categories" :key="category.id" :value="category.id">
          {{ category.name }}
        </option>
      </select>
    </div>
    <div class="input-wrapper">
      <label for="title" ref="title">제목</label>
      <input type="text" class="input" id="title" v-model="title"/>
    </div>
    <div class="input-wrapper">
      <label for="title">내용</label>
      <Vue2TinymceEditor v-model="description"/>
    </div>

    <div class="input-wrapper">
      <label>설정</label>
      <div class="option-wrapper">
        <div class="option-item">
          <div>공개여부</div>
          <div>
            <input type="checkbox" :checked="display"/>
          </div>
        </div>
      </div>
    </div>

    <div class="btn-wrapper">
      <button class="btn btn-green" v-on:click="handleSaveBtnClick">저장</button>
    </div>
  </article>
</template>

<script>
import {Vue2TinymceEditor} from "vue2-tinymce-editor"
import {mapActions} from "vuex"

import {postService, categoryService} from "@/services"
import router from "@/routers"

export default {
  name: 'PostEditor',
  components: {
    Vue2TinymceEditor
  },
  data() {
    return {
      categories: [],
      categoryId: -1,
      title: null,
      description: null,
      display: true
    }
  },
  mounted() {
    this.getCategories()
    this.$refs.title.focus()
  },
  methods: {
    ...mapActions('category', ['getCategoryListForSidebar']),
    async getCategories() {
      const {data: {body}} = await categoryService.getCategoryList()
      this.categories = body
    },
    async handleSaveBtnClick() {
      const {title, description, display, categoryId} = this
      const {data: {body}} = await postService.addPost({title, description, display, categoryId})
      this.getCategoryListForSidebar()
      router.push(`/posts/${body}`)
    }
  }
}
</script>

<style scoped>
label {
  display: block;
  font-weight: 600;
  margin-bottom: 0.5rem;
}

.input-wrapper {
  margin-bottom: 2rem;
}

.option-wrapper {
  border: 1px solid #ccc;
  display: flex;
  flex-wrap: wrap;
}

.option-item {
  width: 50%;
}

.option-item > div {
  display: inline-block;
  line-height: 37px;
  padding: 0 0.2rem;
  box-sizing: border-box;
}

.option-item > div:first-child {
  width: 6rem;
  text-align: right;
}

.option-item > div:last-child {
  width: calc(100% - 6rem);
}

.btn-wrapper {
  text-align: right;
}
</style>
