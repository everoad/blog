import {authUtils} from '@/helpers'
import axios from "axios"

export const postService = {
  getPost,
  addPost,
  editPost,
  removePost,
  getPostList,
  uploadFile
}

const baseUrl = '/api/posts'

function getPostList(params) {
  return axios.get(baseUrl, {params})
}

function addPost(data) {
  return axios.post(baseUrl, data, {headers: authUtils.getHeader()})
}

function getPost(id) {
  return axios.get(`${baseUrl}/${id}`)
}

function editPost(id, data) {
  return axios.put(`${baseUrl}/${id}`, data, {headers: authUtils.getHeader()})
}

function removePost(id) {
  return axios.delete(`${baseUrl}/${id}`, {headers: authUtils.getHeader()})
}

function uploadFile(formData) {
  return axios.post(`${baseUrl}/upload`, formData, {
    headers: {...authUtils.getHeader(), 'Content-Type': 'multipart/form-data'}
  })
}