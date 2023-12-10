import request from '@/utils/request'


// 查询客户列表
export function listSupplier(query) {
  return request({
    url: '/wms/cu/supplier/list',
    method: 'get',
    params: query
  })
}
