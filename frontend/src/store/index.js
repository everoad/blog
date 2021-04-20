import Vue from "vue"
import Vuex from "vuex"

Vue.use(Vuex)

const store = new Vuex.Store({
  state: {
    isLogin: false,
    user: null
  },
  mutations: {
    login(state, payload) {
      state.isLogin = true
      state.user = payload
    },
    logout(state) {
      state.isLogin = false
      state.user = null
    }
  }

})

export default store
