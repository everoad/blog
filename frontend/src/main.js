import Vue from 'vue'
import App from './App.vue'
import router from '@/routers/index'
import '@/assets/main.css'
import '@/lib/FontAwesomeIcon'
import Layout from "@/views/layout/Layout"
import NoLayout from "@/views/layout/NoLayout"

Vue.component('layout', Layout)
Vue.component('no-layout', NoLayout)

Vue.config.productionTip = false

new Vue({
  render: h => h(App),
  router
}).$mount('#app')
