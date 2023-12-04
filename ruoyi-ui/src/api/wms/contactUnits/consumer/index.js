import request from '@/utils/request'


// 查询客户列表
export function listComsumer(query) {
  return request({
    url: '/wms/cu/consumer/list',
    method: 'get',
    params: query
  })
}
