import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: '/usercore/core-role/list',
    method: 'post',
    params: query
  })
}

export function allList() {
  return request({
    url: '/usercore/core-role/allList',
    method: 'post'
  })
}

export function saveRoleFunction(temp) {
  return request({
    url: '/usercore/core-role/saveRoleFunction',
    method: 'post',
    params: temp
  })
}

export function userAllList(temp) {
  return request({
    url: '/usercore/core-role/userAllList',
    method: 'post',
    params: temp
  })
}

export function saveData(temp) {
  return request({
    url: '/usercore/core-role/saveData',
    method: 'post',
    params: temp
  })
}

export function getLastChildNodeIds(id) {
  return request({
    url: '/usercore/core-role/getLastChildNodeIds',
    method: 'post',
    params: { id }
  })
}
