import {authUtils} from '@/helpers'
import axios from "axios"


export const postService = {
  addPost,
  getPost,
  getPostList
}

function getPostList(params) {
  return axios.get('/api/posts', {params})
}

function addPost(data) {
  return axios.post('/api/posts', data, {headers: authUtils.getHeader()})
}

function getPost(id) {
  return axios.get(`/api/posts/${id}`)
}