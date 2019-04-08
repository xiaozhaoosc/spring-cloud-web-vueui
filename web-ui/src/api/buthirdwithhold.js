import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: '/thirdPay/list',
    method: 'post',
    params: query
  })
}

export function modifyStatus(id) {
  return request({
    url: '/thirdPay/sendMqAgain',
    method: 'post',
    params: { id }
  })
}

