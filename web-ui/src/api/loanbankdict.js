import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: '/LoanBankDict/list',
    method: 'post',
    params: query
  })
}

export function saveData(temp) {
  return request({
    url: '/LoanBankDict/saveData',
    method: 'post',
    params: temp
  })
}
