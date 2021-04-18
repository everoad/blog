import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from "@/views/home/Home"
import Post from "@/views/post/Post"
import PostEditor from "@/views/post/PostEditor"
import PostDetail from "@/views/post/PostDetail"

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home,
    meta: { unauthorized: false },
  },
  {
    path: '/posts',
    component: Post,
    meta: { unauthorized: false }
  },
  {
    path: '/posts/editor',
    component: PostEditor,
    meta: { unauthorized: true }
  },
  {
    path: '/posts/:id',
    name: 'PostDetail',
    component: PostDetail,
    props: true,
    meta: { unauthorized: false }
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router