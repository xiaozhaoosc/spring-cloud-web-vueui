import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: '/ScParam/list',
    method: 'post',
    params: query
  })
}

export function saveData(temp) {
  return request({
    url: '/ScParam/saveData',
    method: 'post',
    params: temp
  })
}
