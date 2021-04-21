import {authUtils} from '@/helpers'
import axios from "axios"


export const categoryService = {
  getCategoryList,
  getCategory,
  addCategory
}

function getCategoryList() {
  return axios.get('/api/categories')
}

function getCategory(id) {
  return axios.get(`/api/categories/${id}`)
}

function addCategory(data) {
  return axios.post('/api/categories', data, {headers: authUtils.getHeader()})
}
