import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: '/XnPayWithhold/list',
    method: 'post',
    params: query
  })
}

export function saveData(temp) {
  return request({
    url: '/XnPayWithhold/saveData',
    method: 'post',
    params: temp
  })
}

export function modifyStatus(id, payStatus) {
  return request({
    url: '/XnPayWithhold/sendMqAgain',
    method: 'post',
    params: { id, payStatus }
  })
}
