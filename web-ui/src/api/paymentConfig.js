import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: '/payment/config/list',
    method: 'post',
    params: query
  })
}

export function updateLoanChannel(data) {
  return request({
    url: '/payment/config/update_loan_channel',
    method: 'post',
    data
  })
}

export function fetchListXiaodai(query) {
  return request({
    url: '/payment/config/list_xiaodai',
    method: 'post',
    params: query
  })
}

export function updateLoanChannelXiaodai(data) {
  return request({
    url: '/payment/config/update_loan_channel_xiaodai',
    method: 'post',
    data
  })
}

export function uploadLoanChannelXiaodai(data) {
  return request({
    url: '/payment/config/upload_loan_channel_xiaodai',
    method: 'post',
    headers: { 'Content-Type': 'multipart/form-data' },
    data
  })
}

export function updateUseLoanChannelXiaodai(query) {
  return request({
    url: '/payment/config/update_use_loan_channel_xiaodai',
    method: 'post',
    params: query
  })
}
