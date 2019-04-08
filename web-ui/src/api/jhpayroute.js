import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: '/JhPayRoute/list',
    method: 'post',
    params: query
  })
}

export function saveData(temp) {
  return request({
    url: '/JhPayRoute/saveData',
    method: 'post',
    params: temp
  })
}
