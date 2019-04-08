import request from '@/utils/request'

export function fetchListLoanInfo(query) {
  return request({
    url: '/loan_info/list',
    method: 'post',
    data: query
  })
}

export function fetchListloanList(query) {
  return request({
    url: '/loan_info/loanList',
    method: 'post',
    data: query
  })
}

export function fetchStageInfo(query) {
  return request({
    url: '/loan_info/stageInfoList',
    method: 'post',
    params: query
  })
}

export function fetchTradeInfo(query) {
  return request({
    url: '/loan_info/tradeInfolist',
    method: 'post',
    params: query
  })
}

export function getLoanInfoDetail(applicationId, loanId) {
  return request({
    url: '/loan_info/getLoanInfoDetail',
    method: 'post',
    params: { applicationId, loanId }
  })
}

export function getRemainAmount(applicationId) {
  return request({
    url: '/loan_info/getRemainAmount',
    method: 'post',
    params: { applicationId }
  })
}

export function handRepayment(temp) {
  return request({
    url: '/loan_info/handRepayment',
    method: 'post',
    params: temp
  })
}

export function loanInfoExport(query) {
  window.location = process.env.BASE_API + '/loan_info/loanInfoExport?' + $.param(query)
}
