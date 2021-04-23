import {categoryService} from "@/services"


const state = {
  categories: []
}

const actions = {
  getCategoryListForSidebar({dispatch, commit}) {
    categoryService.getCategoryListForSidebar()
        .then(res => {
          commit('getCategoryListRequest', res.data.body)
        })
        .catch(error => {
          commit('loginFailure', error)
          dispatch('alert/error', error, {root: true})
        })
  }
}


const mutations = {
  getCategoryListRequest(state, categories) {
    state.categories = categories
  }
}

export default {
  namespaced: true,
  state,
  actions,
  mutations
}