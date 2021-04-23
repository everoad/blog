<template>
  <LoadingPanel v-if="loading"/>
  <div class="detail-wrapper" v-else>
    <header>
      <div class="image" v-if="item.image" :style="{ 'background-image': 'url(' + item.image + ')' }"></div>
      <div class="title">{{ item.title }}</div>
      <div class="info">
        <div>작성일 {{ item.createdTime | moment('YYYY-MM-DD HH:mm') }}</div>
        <div>조회수 {{ item.viewCount }}</div>
        <div v-if="status.loggedIn">
          <button class="btn btn-default btn-sm" @click="removePost">삭제</button>
        </div>
      </div>
    </header>
    <article v-html="item.description"></article>
  </div>
</template>

<script>
import {mapState} from "vuex"
import {LoadingPanel} from "@/components/LoadingPanel"
import {postService} from "@/services"
import router from "@/routers"

export default {
  name: 'PostDetail',
  components: {
    LoadingPanel
  },
  props: ['id'],
  computed: {
    ...mapState('auth', ['status'])
  },
  data() {
    return {
      loading: true,
      item: {
        title: null,
        description: null,
        createdTime: null,
        viewCount: null,
        image: null
      }
    }
  },
  mounted() {
    this.getData()
  },
  methods: {
    async getData() {
      const {data: {body}} = await postService.getPost(this.id)
      this.item = body
      this.loading = false
    },
    async removePost() {
      await postService.removePost(this.id)
      router.push({ path: '/posts' })
    }

  }
}
</script>

<style scoped>
.detail-wrapper {
  padding: 1rem;
}

.image {

}

header {
  padding: 0.5rem;
  border-bottom: 1px solid #eee;
}

.title {
  font-size: 1.5rem;
  font-weight: 600;
  margin-bottom: 1rem;
}

.info {
  color: rgba(0, 0, 0, 0.5);
  margin-bottom: 0.5rem;
}

.info > div {
  display: inline-block;
  vertical-align: top;
}

.info > div + div {
  margin-left: 1rem;
}

article {
  margin-top: 3rem;
  font-size: inherit;
  padding: 0.5rem;
}
</style>
