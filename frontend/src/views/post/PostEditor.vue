<template>
  <article>
    <div class="input-wrapper">
      <label for="title">제목</label>
      <input type="text" class="input" id="title" v-model="edit.title" />
    </div>
    <div>
      <label for="title">내용</label>
      <textarea class="textarea" v-model="edit.description" />
    </div>
<!--    <TipTap/>-->
    <div class="btn-wrapper">
      <button class="btn btn-green" v-on:click="handleSaveBtnClick">저장</button>
    </div>
  </article>
</template>

<script>
import axios from "axios"
// import TipTap from "@/components/Tiptap/Index"


export default {
  name: 'PostEditor',
  components: {
    // TipTap: TipTap
  },
  data() {
    return {
      edit: {
        title: null,
        description: null
      }
    }
  },
  methods: {
    async handleSaveBtnClick() {
      const { edit } = this
      const res = await axios.post('/api/posts', edit)
      console.log(res)
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
