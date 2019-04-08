import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: '/LoanPremium/list',
    method: 'post',
    params: query
  })
}

export function saveData(temp) {
  return request({
    url: '/LoanPremium/saveData',
    method: 'post',
    params: temp
  })
}

