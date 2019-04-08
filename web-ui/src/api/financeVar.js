import request from '@/utils/request'

export function fetchSystemVars(type) {
  return request({
    url: '/system/var/' + type,
    method: 'get'
  })
}
