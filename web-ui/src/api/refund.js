import request from '@/utils/request'

export function list(param) {
  return request({
    url: '/refund/list',
    method: 'post',
    params: param
  })
}

export function apply(data) {
  return request({
    url: '/refund/apply',
    method: 'post',
    data: data
  })
}

export function fetch(data) {
  return request({
    url: '/refund/application/query',
    method: 'post',
    params: data
  })
}

export function approve(data) {
  return request({
    url: '/refund/approve',
    method: 'post',
    data: data
  })
}

