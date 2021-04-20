import Vue from "vue"
import App from "@/App.vue"
import vueMoment from "vue-moment"

import router from "@/routers/index"
import store from "@/store/index"

import "@/assets/main.css"
import "@/lib/FontAwesomeIcon"


import Layout from "@/views/layout/Layout"
import NoLayout from "@/views/layout/NoLayout"

Vue.use(vueMoment)
Vue.component('layout', Layout)
Vue.component('no-layout', NoLayout)

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App),
}).$mount('#app')
