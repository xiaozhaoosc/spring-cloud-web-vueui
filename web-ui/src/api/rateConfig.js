import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: '/rate/rateConfigList',
    method: 'post',
    data: query
  })
}

export function saveData(temp) {
  return request({
    url: '/rate/saveRateConfig',
    method: 'post',
    data: temp
  })
}

export function deleteData(temp) {
  return request({
    url: '/rate/deleteRateConfig',
    method: 'post',
    data: temp
  })
}
