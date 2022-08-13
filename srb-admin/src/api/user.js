import request from '@/utils/request'

export function login(data) {
  return request({
    baseURL: '/dev-api',
    url: '/admin/core/user/login',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    baseURL: '/dev-api',
    url: '/admin/core/user/info',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    baseURL: '/dev-api',
    url: '/admin/core/user/logout',
    method: 'post'
  })
}
