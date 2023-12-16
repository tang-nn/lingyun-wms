import request from '@/utils/request'


// 查询客户列表
export function listSupplier(query) {
  return request({
    url: '/wms/cu/supplier/list',
    method: 'get',
    params: query
  })
}

// 查询客户详情
export function getSupplier(sid) {
  return request({
    url: '/wms/cu/supplier/' + sid,
    method: 'get'
  })
}
