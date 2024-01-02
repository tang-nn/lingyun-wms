import request from '@/utils/request'


// 查询供应商列表
export function listSupplier(query) {
  return request({
    url: '/wms/cu/supplier/list',
    method: 'get',
    params: query
  })
}

// 查询供应商详情
export function getSupplier(sid) {
  return request({
    url: '/wms/cu/supplier/' + sid,
    method: 'get'
  })
}
