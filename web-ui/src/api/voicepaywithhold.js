import request from '@/utils/request'

export function fetchList(query) {
  return request({
    url: '/VoicePayWithhold/list',
    method: 'post',
    params: query
  })
}

export function modifyStatus(id) {
  return request({
    url: '/VoicePayWithhold/sendMqAgain',
    method: 'post',
    params: { id }
  })
}

