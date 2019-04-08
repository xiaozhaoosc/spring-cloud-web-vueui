import request from '@/utils/request'

export function loginByUsername(username, password) {
  const query = {
    username,
    password
  }
  return request({
    url: '/usercore/core-user/login',
    method: 'post',
    params: query
  })
}

export function logout(token) {
  return request({
    url: '/usercore/core-user/logout',
    method: 'post',
    params: token
  })
}

export function getUserInfo() {
  return request({
    url: '/usercore/core-user/info',
    method: 'get'
  })
}

