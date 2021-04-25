import Vue from "vue"
import App from "@/App.vue"

import router from "@/routers/index"
import store from "@/store/index"

import "@/assets/main.css"
import "@/libs/FontAwesomeIcon"

import Layout from "@/views/layout/Layout"
import NoLayout from "@/views/layout/NoLayout"
import ManageSidebar from "@/views/layout/ManageSidebar"
import Sidebar from "@/views/layout/Sidebar"

Vue.component('layout', Layout)
Vue.component('no-layout', NoLayout)
Vue.component('sidebar', Sidebar)
Vue.component('manage-sidebar', ManageSidebar)

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
