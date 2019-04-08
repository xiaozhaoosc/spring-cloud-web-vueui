import request from '@/utils/request'
import $ from 'jquery'

export function fetchList(query) {
  return request({
    url: '/payment/list',
    method: 'post',
    params: query
  })
}

export function paymentExport(query) {
  window.location = process.env.BASE_API + '/payment/paymentExport?' + $.param(query)
}

export function repush(id) {
  return request({
    url: '/payment/repush',
    method: 'post',
    params: { id }
  })
}

export function modifyStatus(id, status) {
  return request({
    url: '/payment/modify_status',
    method: 'post',
    params: { id, status }
  })
}
