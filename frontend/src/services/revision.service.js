import {authUtils} from '@/helpers'
import axios from "axios"

export const revisionService = {
  getRevisionList,
  getRevisionColumnList
}

const baseUrl = '/api/revisions'

function getRevisionList(classType, params) {
  return axios.get(`${baseUrl}/${classType}`, {params, headers: authUtils.getHeader()})
}

function getRevisionColumnList(classType) {
  return axios.get(`${baseUrl}/${classType}/columns`)
}