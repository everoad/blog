<template>
  <Loading v-if="loading" class="loading-panel"/>
  <div v-else class="detail-wrapper">
    <header>
      <div class="image" v-if="item.file" :style="thumbnailStyle"></div>
      <div class="title">{{item.categoryName}} <font-awesome-icon icon="angle-right" /> {{ item.title }}</div>
      <div class="info">
        <div>작성일 {{ createdTime }}</div>
        <div>조회수 {{ item.viewCount }}</div>
        <div v-if="status.loggedIn" class="info-btn">
          <button class="btn btn-default btn-sm" @click="handleEditBtnClick">수정</button>
          <button class="btn btn-default btn-sm" @click="handleRemoveBtnClick">삭제</button>
        </div>
      </div>
    </header>
    <article v-html="item.description"></article>
  </div>
</template>

<script>
import {mapState} from "vuex"
import {Loading} from "@/components/Loading"
import {postService} from "@/services"
import router from "@/routers"
import moment from "moment"

export default {
  name: 'PostDetail',
  components: {
    Loading
  },
  props: ['id'],
  computed: {
    ...mapState('auth', ['status']),
    thumbnailStyle() {
      return {
        backgroundImage: `linear-gradient(to bottom, rgba(0, 0, 0, 0.5) 50%, rgba(0, 0, 0, 0.5) 95%), url(/api/images/${this.item.file.name})`,
      }
    },
    createdTime() {
      return moment(this.item.createdTime).format('YYYY-MM-DD HH:mm')
    }
  },
  data() {
    return {
      loading: true,
      item: {
        title: null,
        description: null,
        createdTime: null,
        viewCount: null,
        file: null,
        categoryName: null
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
    async handleRemoveBtnClick() {
      if (confirm("삭제하시겠습니까?")) {
        await postService.removePost(this.id)
        router.push({path: '/posts'})
      }
    },
    handleEditBtnClick() {
      const {id} = this
      router.push({path: '/posts/editor', query: {postId: id}})
    }

  }
}
</script>

<style scoped>
.detail-wrapper {
  padding: 1rem;
}

.image {
  overflow: hidden;
  height: 15rem;
  margin-bottom: 2rem;
  background-position: center;
  background-repeat: no-repeat;
  background-size: cover;
}

.image>img {
  width: 100%;
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
.info > .info-btn {
  float: right;
}
.info > .info-btn>button+button {
  margin-left: 0.2rem;
}

article {
  margin-top: 3rem;
  font-size: inherit;
  padding: 0.5rem;
}
</style>
