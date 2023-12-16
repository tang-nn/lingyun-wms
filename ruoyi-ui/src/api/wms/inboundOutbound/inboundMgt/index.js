import request from '@/utils/request'

// 查询入库列表
export function listInventory(query) {
  return request({
    url: '/wms/wh/inventory/list',
    method: 'get',
    params: query
  })
}

// 查询某一条入库记录
export function getInventory(inId) {
  return request({
    url: '/wms/wh/inventory/' + inId,
    method: 'get'
  })
}
