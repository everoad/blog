<template>
  <article>
    <div class="input-wrapper">
      <label for="display">공개여부</label>
      <input type="checkbox" id="display" :checked="display"/>
    </div>
    <div class="input-wrapper">

    </div>
    <div class="input-wrapper">
      <label for="title" ref="title">제목</label>
      <input type="text" class="input" id="title" v-model="title"/>
    </div>
    <div class="input-wrapper">
      <label for="title">내용</label>
      <Vue2TinymceEditor v-model="description"/>
    </div>
    <div class="btn-wrapper">
      <button class="btn btn-green" v-on:click="handleSaveBtnClick">저장</button>
    </div>
  </article>
</template>

<script>
import { Vue2TinymceEditor } from "vue2-tinymce-editor"

import {postService} from "@/services"
import router from "@/routers"

export default {
  name: 'PostEditor',
  components: {
    Vue2TinymceEditor
  },
  data() {
    return {
      title: null,
      description: null,
      display: true
    }
  },
  mounted() {
    this.$refs.title.focus()
  },
  methods: {
    async handleSaveBtnClick() {
      const {title, description, display} = this
      const {data:{body}} = await postService.addPost({title, description, display})
      router.push(`/posts/${body}`)
    }
  }
}
</script>

<style scoped>
label {
  display: block;
  font-size: 1.1rem;
  font-weight: 600;
  padding-bottom: 0.5rem;
}

.input-wrapper {
  margin-bottom: 1rem;
}

.btn-wrapper {
  text-align: right;
}
</style>
