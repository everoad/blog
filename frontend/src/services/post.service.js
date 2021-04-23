import {authUtils} from '@/helpers'
import axios from "axios"

export const postService = {
  addPost,
  getPost,
  getPostList
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