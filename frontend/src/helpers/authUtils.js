
export const authUtils = {
  getHeader,
  getToken,
  setToken,
  removeToken
}

const itemKey = 'token'

function isExpired(token) {
  const today = new Date()
  return token && token.accessTokenExpiredAt <= today.getMilliseconds()
}

function getHeader() {
  let token = getToken()
  if (token) {
    return { 'Authorization': 'Bearer ' + token.accessToken }
  } else {
    return {}
  }
}

function setToken(token) {
  localStorage.setItem(itemKey, JSON.stringify(token))
}

function getToken() {
  const token = JSON.parse(localStorage.getItem(itemKey))
  if (!token) {
    return null
  }
  if (token && isExpired(token)) {
    removeToken()
    alert('토큰이 만료되었습니다.')
    return null
  }
  return token
}

function removeToken() {
  localStorage.removeItem(itemKey)
}

