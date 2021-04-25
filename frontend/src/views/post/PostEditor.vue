<template>
  <article>
    <Loading v-if="loading" class="loading-panel"/>
    <div class="input-wrapper">
      <label>카테고리</label>
      <select class="input" v-model="categoryId">
        <option :value="-1" disabled>선택</option>
        <option v-for="category in categories"
                :key="category.id" :value="category.id">
          {{ category.name }}
        </option>
      </select>
    </div>
    <div class="input-wrapper">
      <label>제목</label>
      <input type="text" class="input" v-model="title"/>
    </div>
    <div class="input-wrapper">
      <label>내용</label>
      <Vue2TinymceEditor v-model="description"/>
    </div>
    <div class="input-wrapper">
      <label>설정</label>
      <div class="option-wrapper">
        <div class="option-item">
          <div>공개여부</div>
          <Checkbox v-model="display"/>
        </div>
      </div>
    </div>

    <div class="input-wrapper">
      <label>이미지</label>
      <div>
        <FileInput accept="image/png,image/jpeg"
                   :file="file"
                   :on-change="handleFileChange"/>
        <div class="thumbnail" v-if="file" :style="thumbnailStyle"></div>
      </div>
    </div>

    <div class="btn-wrapper">
      <button class="btn btn-green" v-if="postId" v-on:click="handleEditBtnClick">수정</button>
      <button class="btn btn-green" v-else v-on:click="handleSaveBtnClick">저장</button>
    </div>
  </article>
</template>

<script>
import {Vue2TinymceEditor} from "vue2-tinymce-editor"
import {FileInput, Checkbox} from "@/components/Form"
import {Loading} from "@/components/Loading"
import {mapState, mapActions} from "vuex"

import {postService} from "@/services"
import router from "@/routers"

export default {
  name: 'PostEditor',
  components: {
    Vue2TinymceEditor,
    Loading,
    FileInput,
    Checkbox
  },
  computed: {
    ...mapState('category', ['categories']),
    thumbnailStyle() {
      return {backgroundImage: `url(/api/images/${this.file.name})`}
    }
  },
  data() {
    return {
      loading: true,
      categoryId: -1,
      postId: null,
      title: null,
      description: null,
      display: true,
      file: null
    }
  },
  mounted() {
    this.init()
  },
  methods: {
    ...mapActions('category', ['getCategoryListForSidebar']),
    async init() {
      const {postId} = this.$route.query
      if (postId) {
        this.postId = postId
        await this.getPost()
      }
      this.loading = false
    },
    async getPost() {
      const {data: {body: {title, description, display, categoryId, file}}} = await postService.getPost(this.postId)
      this.title = title
      this.description = description
      this.display = display
      this.categoryId = categoryId
      this.file = file
    },
    async handleSaveBtnClick() {
      const {title, description, display, categoryId, file} = this
      if (this.validate({title, description, categoryId})) {
        return
      }
      const {data: {body}} = await postService.addPost({title, description, display, categoryId, file})
      this.redirectDetail(body)
    },
    async handleEditBtnClick() {
      const {postId, title, description, display, categoryId, file} = this
      if (this.validate({title, description, categoryId})) {
        return
      }
      await postService.editPost(postId, {title, description, display, categoryId, file})
      this.redirectDetail(postId)
    },
    handleFileChange(files) {
      if (files.length > 0) {
        this.uploadFile(files[0])
      } else {
        this.file = null
      }
    },
    async uploadFile(file) {
      const formData = new FormData()
      formData.append("file", file)
      const {data: {body}} = await postService.uploadFile(formData)
      this.file = body
    },
    redirectDetail(postId) {
      this.getCategoryListForSidebar()
      router.push(`/posts/${postId}`)
    },
    validate(data) {
      if (data.categoryId === -1) {
        alert('카테고리를 선택해 주세요.')
        return true
      }
      if (!data.title) {
        alert('제목을 입력해 주세요.')
        return true
      }
      if (!data.description) {
        alert('내용을 입력해 주세요.')
        return true
      }
      return false
    }

  }
}
</script>

<style scoped>
article {
  padding-top: 2rem;
}

label {
  display: block;
  font-weight: 600;
  margin-bottom: 0.5rem;
  color: #555;
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
  vertical-align: center;
  padding: 0.6rem 0.2rem;
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

.thumbnail {
  margin-top: 1rem;
  width: 100%;
  min-height: 400px;
  border: 1px solid #ccc;
  box-sizing: border-box;
  background-position: center;
  background-repeat: no-repeat;
  background-size: contain;
}
</style>
