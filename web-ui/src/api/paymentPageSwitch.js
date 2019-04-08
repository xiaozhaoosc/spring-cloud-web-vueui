import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: '/switch/paymentPageSwitchList',
    method: 'post',
    data: query
  })
}

export function saveData(temp) {
  return request({
    url: '/switch/savePaymentPageSwitch',
    method: 'post',
    data: temp
  })
}
