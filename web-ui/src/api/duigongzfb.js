import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: '/duigonghuankuan/selectList',
    method: 'post',
    params: query
  })
}
export function uploadZfb(data) {
  return request({
    url: '/duigonghuankuan/importZfb',
    method: 'post',
    headers: { 'Content-Type': 'multipart/form-data' },
    data
  })
}
