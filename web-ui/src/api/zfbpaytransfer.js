import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: '/alipayTransfer/list',
    method: 'post',
    params: query
  })
}
