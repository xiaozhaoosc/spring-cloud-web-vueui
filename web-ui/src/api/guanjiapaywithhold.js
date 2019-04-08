import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: '/GuanjiaPayWithhold/list',
    method: 'post',
    params: query
  })
}

export function modifyStatus(id) {
  return request({
    url: '/GuanjiaPayWithhold/sendMqAgain',
    method: 'post',
    params: { id }
  })
}

