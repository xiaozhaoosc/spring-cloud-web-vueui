import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: '/XnhyPayWithhold/list',
    method: 'post',
    params: query
  })
}

export function modifyStatus(id) {
  return request({
    url: '/XnhyPayWithhold/sendMqAgain',
    method: 'post',
    params: { id }
  })
}

