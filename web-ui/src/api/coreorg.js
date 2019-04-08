import request from '@/utils/request'

export function fetchCoreorgList() {
  return request({
    url: '/usercore/core-org/list',
    method: 'post'
  })
}

export function saveData(temp) {
  return request({
    url: '/usercore/core-org/saveData',
    method: 'post',
    params: temp
  })
}

export function delData(id) {
  return request({
    url: '/usercore/core-org/delData',
    method: 'post',
    params: { id }
  })
}
