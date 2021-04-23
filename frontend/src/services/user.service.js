import {authUtils} from '@/helpers'
import axios from "axios"

export const userService = {
  login,
  logout,
  userMe
}

const baseUrl = '/api/auth'

function login(username, password) {
  return axios.post(`${baseUrl}/login`, {username, password})
      .then(res => {
        const {accessToken, accessTokenExpiredAt} = res.data.body
        if (accessToken) {
          authUtils.setToken({accessToken, accessTokenExpiredAt})
        }
        return accessToken
      })
}

function userMe(token) {
  return axios.get('/api/users/me', {params: {token}}).then(res => {
    const {user} = res.data.body
    return user
  })
}

function logout() {
  return axios.post(`${baseUrl}/logout`, null, {headers: authUtils.getHeader()}).then(() => {
    authUtils.removeToken()
  })
}

// function handleResponse(response) {
//   return response.json().then(data => {
//     if (!response.ok) {
//       if (response.status === 401) {
//         logout()
//         location.reload()
//       }
//       const error = (data && data.message) || response.statusText
//       return Promise.reject(error)
//     }
//     return data
//   })
// }