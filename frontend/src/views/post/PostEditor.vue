<template>
  <article>
    <LoadingScreen v-if="loading"/>
    <div class="input-wrapper">
      <label>카테고리</label>
      <select class="input" v-model="categoryId">
        <option :value="-1" disabled>선택</option>
        <option v-for="category in categories" :key="category.id" :value="category.id">
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
<!--            <input type="checkbox" v-model="display"/>-->
        </div>
      </div>
    </div>

    <div class="input-wrapper">
      <label>이미지</label>
      <div>
        <FileInput accept="image/png,image/jpeg"
                   :filename="file.originalName"
                   :on-change="handleFileChange"/>
        <div class="thumbnail" v-if="file.name" :style="thumbnailStyle"></div>
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
import {FileInput,Checkbox} from "@/components/Form"
import {LoadingScreen} from "@/components/LoadingPanel"
import {mapState, mapActions} from "vuex"

import {postService} from "@/services"
import router from "@/routers"

export default {
  name: 'PostEditor',
  components: {
    Vue2TinymceEditor,
    LoadingScreen,
    FileInput,
    Checkbox
  },
  computed: {
    ...mapState('category', ['categories']),
    thumbnailStyle() {
      return { backgroundImage: `url(/api/images/${this.file.name})` }
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
      file: {
        id: null,
        originalName: null,
        name: null,
        size: null,
        type: null
      }
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
      this.setFile(file)
    },
    async handleSaveBtnClick() {
      const {title, description, display, categoryId, file} = this
      const {data: {body}} = await postService.addPost({title, description, display, categoryId, file})
      this.redirectDetail(body)
    },
    async handleEditBtnClick() {
      const {postId, title, description, display, categoryId, file} = this
      await postService.editPost(postId, {title, description, display, categoryId, file})
      this.redirectDetail(postId)
    },
    handleFileChange(files) {
      if (files.length > 0) {
        this.uploadFile(files[0])
      } else {
        this.setFile({})
      }
    },
    setFile(file) {
      const {id, originalName, name, size, type} = file
      this.file.id = id
      this.file.originalName = originalName
      this.file.name = name
      this.file.size = size
      this.file.type = type
    },
    async uploadFile(file) {
      const formData = new FormData()
      formData.append("file", file)
      const {data: {body}} = await postService.uploadFile(formData)
      this.setFile(body)
    },
    redirectDetail(postId) {
      this.getCategoryListForSidebar()
      router.push(`/posts/${postId}`)
    }
    ,test(event) {
      console.log(event.target.checked)
    }
  }
}
</script>

<style scoped>
article {
  position: relative;
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
