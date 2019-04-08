import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: '/BuWithhold/list',
    method: 'post',
    params: query
  })
}

export function modifyStatus(id) {
  return request({
    url: '/BuWithhold/sendMqAgain',
    method: 'post',
    params: { id }
  })
}

