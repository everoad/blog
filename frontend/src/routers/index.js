import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from "@/views/home/Home"
import Post from "@/views/post/Post"
import PostEditor from "@/views/post/PostEditor"
import PostDetail from "@/views/post/PostDetail"
import Login from "@/views/login/Login"

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
  {
    path: '/login',
    name: 'Login',
    component: Login,
    meta: { unauthorized: false, layout: 'no-layout' }
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.unauthorized)) {
    if (localStorage.getItem('jwt') === null) {
      next({
        path: '/login',
        params: { nextUrl: to.fullPath }
      })
    } else {
      let user = JSON.parse(localStorage.getItem('user'))
      if (to.matched.some(record => record.meta.is_admin)) {
        if(user.is_admin == 1){
          next()
        }
        else{
          next({ name: 'userboard'})
        }
      }else {
        next()
      }
    }
  } else {
    next()
  }
})

export default router