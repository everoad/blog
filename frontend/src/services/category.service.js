import {authUtils} from '@/helpers'
import axios from "axios"

export const categoryService = {
  getCategoryList,
  getCategoryListForSidebar,
  addCategory,
  editCategory,
  removeCategory
}

const baseUrl = '/api/categories'

function getCategoryList() {
  return axios.get(baseUrl)
}

function getCategoryListForSidebar() {
  return axios.get(`${baseUrl}/sidebar`)
}

function addCategory(data) {
  return axios.post(baseUrl, data, {headers: authUtils.getHeader()})
}

function editCategory(categoryId, data) {
  return axios.put(`${baseUrl}/${categoryId}`, data, {headers: authUtils.getHeader()})
}

function removeCategory(categoryId) {
  return axios.delete(`${baseUrl}/${categoryId}`, {headers: authUtils.getHeader()})
}

