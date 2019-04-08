import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: '/xnPayWithholdFq/list',
    method: 'post',
    params: query
  })
}

