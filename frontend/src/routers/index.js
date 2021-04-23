import Vue from 'vue'
import VueRouter from 'vue-router'
import Post from "@/views/post/Post"
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
    name: 'Post',
    component: Post,
    props: true,
    meta: {authorized: false}
  },
  {
    path: '/posts/editor',
    name: 'PostEditor',
    component: () => import("@/views/post/PostEditor"),
    meta: {authorized: true}
  },
  {
    path: '/posts/:id',
    name: 'PostDetail',
    component: PostDetail,
    props: true,
    meta: {authorized: false}
  },
  {
    path: '/categories',
    name: 'Category',
    component: () => import("@/views/category/Category"),
    meta: {authorized: true, layout: 'system-layout'}
  },
  {
    path: '/login',
    name: 'Login',
    component: Login,
    meta: {authorized: false, anonymous: true, layout: 'no-layout'}
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.authorized)) {
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