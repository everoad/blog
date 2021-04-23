import {userService} from "@/services"
import {authUtils} from '@/helpers'
import router from "@/routers"

const token = authUtils.getToken()

const state = {status: {loggedIn: !!token}, user: null}

const actions = {
  login({dispatch, commit}, {username, password}) {
    commit('loginRequest', {username})
    userService.login(username, password)
        .then(token => {
          userService.userMe(token).then(user => {
            commit('loginSuccess', user)
            router.push('/posts')
          })
        })
        .catch(error => {
          commit('loginFailure', error)
          dispatch('alert/error', error, {root: true})
        })
  },
  logout({commit}) {
    userService.logout().then(() => {
      commit('logout')
      router.push('/posts')
    })
  }
}

const mutations = {
  loginRequest(state, user) {
    state.status = {loggingIn: true}
    state.user = user
  },
  loginSuccess(state, user) {
    state.status = {loggedIn: true}
    state.user = user
  },
  loginFailure(state) {
    state.status = {loggedIn: false}
    state.user = null
  },
  logout(state) {
    state.status = {loggedIn: false}
    state.user = null
  },
}

export default {
  namespaced: true,
  state,
  actions,
  mutations
}