import request from '@/utils/request'

export function fetchList(data) {
  return request({
    url: '/relief/list',
    method: 'post',
    data: data
  })
}

export function fetchInfo(data) {
  return request({
    url: '/relief/info',
    method: 'post',
    params: data
  })
}

export function apply(data) {
  return request({
    url: '/relief/apply',
    method: 'post',
    data: data
  })
}

export function pass(data) {
  return request({
    url: '/relief/approve',
    method: 'post',
    data: data
  })
}

