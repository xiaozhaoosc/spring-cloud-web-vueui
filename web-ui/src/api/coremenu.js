import request from '@/utils/request'

export function fetchList() {
  return request({
    url: '/usercore/core-menu/list',
    method: 'post'
  })
}

export function saveData(temp) {
  return request({
    url: '/usercore/core-menu/saveData',
    method: 'post',
    params: temp
  })
}

export function delData(id) {
  return request({
    url: '/usercore/core-menu/delData',
    method: 'post',
    params: { id }
  })
}
