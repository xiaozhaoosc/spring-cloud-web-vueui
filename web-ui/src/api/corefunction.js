import request from '@/utils/request'

export function fetchFunctionList() {
  return request({
    url: '/usercore/core-function/list',
    method: 'post'
  })
}

export function saveData(temp) {
  return request({
    url: '/usercore/core-function/saveData',
    method: 'post',
    params: temp
  })
}

export function delData(id) {
  return request({
    url: '/usercore/core-function/delData',
    method: 'post',
    params: { id }
  })
}
