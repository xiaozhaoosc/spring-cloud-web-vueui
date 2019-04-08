import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: '/WhiteList/list',
    method: 'post',
    params: query
  })
}

export function saveData(temp) {
  return request({
    url: '/WhiteList/saveData',
    method: 'post',
    params: temp
  })
}
