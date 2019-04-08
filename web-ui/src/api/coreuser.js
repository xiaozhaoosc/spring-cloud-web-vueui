import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: '/usercore/core-user/list',
    method: 'post',
    params: query
  })
}

export function saveData(temp) {
  return request({
    url: '/usercore/core-user/saveData',
    method: 'post',
    params: temp
  })
}
