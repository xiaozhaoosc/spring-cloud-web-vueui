import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: '/bankduigong/selectList',
    method: 'post',
    params: query
  })
}

export function uploadBank(data) {
  return request({
    url: '/bankduigong/importBank',
    method: 'post',
    headers: { 'Content-Type': 'multipart/form-data' },
    data
  })
}

export function fetchLoanInfoList(query) {
  return request({
    url: '/bankduigong/matchLoanInfolist',
    method: 'post',
    data: query
  })
}

export function match(query) {
  return request({
    url: '/bankduigong/sendMatchMq',
    method: 'post',
    data: query
  })
}
