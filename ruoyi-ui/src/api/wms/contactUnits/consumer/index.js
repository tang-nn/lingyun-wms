import request from '@/utils/request'


// 查询客户列表
export function listComsumer(query) {
  return request({
    url: '/wms/cu/consumer/list',
    method: 'get',
    params: query
  })
}
// 删除客户列表
export function conUpdate(cid) {
  return request({
    url: 'wms/cu/consumer/del/' + cid,
    method: 'put'
  })
}
