import Vue from "vue"
import Vuex from "vuex"
import { auth, category } from "./modules"

Vue.use(Vuex)

const store = new Vuex.Store({
  modules: {
    auth,
    category
  }
})

export default store