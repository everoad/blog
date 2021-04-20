import Vue from 'vue'
import VueRouter from 'vue-router'
import Post from "@/views/post/Post"
import PostEditor from "@/views/post/PostEditor"
import PostDetail from "@/views/post/PostDetail"
import Login from "@/views/login/Login"
import {authUtils} from "@/helpers"

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    redirect: '/posts'
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
  {
    path: '/login',
    name: 'Login',
    component: Login,
    meta: { unauthorized: false, anonymous: true, layout: 'no-layout' }
  },
  {
    path: '/logout'
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.unauthorized)) {
    const token = authUtils.getToken()
    if (!token) {
      return next('/login')
    }
  }
  if (to.matched.some(record => record.meta.anonymous)) {
    const token = authUtils.getToken()
    if (token) {
      return next('/')
    }
  }
  next()
})

export default router