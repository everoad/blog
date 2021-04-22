import {authUtils} from '@/helpers'
import axios from "axios"


export const categoryService = {
  getCategoryList,
  getCategory,
  addCategory,
  editCategory,
  removeCategory
}

const baseUrl = '/api/categories'

function getCategoryList() {
  return axios.get(baseUrl)
}

function addCategory(data) {
  return axios.post(baseUrl, data, {headers: authUtils.getHeader()})
}

function getCategory(categoryId) {
  return axios.get(`${baseUrl}/${categoryId}`)
}

function editCategory(categoryId, data) {
  return axios.put(`${baseUrl}/${categoryId}`, data, {headers: authUtils.getHeader()})
}

function removeCategory(categoryId) {
  return axios.delete(`${baseUrl}/${categoryId}`, {headers: authUtils.getHeader()})
}

