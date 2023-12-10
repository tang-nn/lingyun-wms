import request from '@/utils/request'

// 查询仓库列表
export function listWarehouse(query) {
  return request({
    url: '/wms/wh/warehouse/list',
    method: 'get',
    params: query
  })
}

// 查询仓库详细
export function getWarehouse(wId) {
  return request({
    url: '/system/warehouse/' + wId,
    method: 'get'
  })
}

// 新增仓库
export function addWarehouse(data) {
  return request({
    url: '/system/warehouse',
    method: 'post',
    data: data
  })
}

// 修改仓库
export function updateWarehouse(data) {
  return request({
    url: '/system/warehouse',
    method: 'put',
    data: data
  })
}

// 删除仓库
export function delWarehouse(wIds) {
  return request({
    url: '/wms/wh/warehouse/' + wIds,
    method: 'delete'
  })
}

//修改仓库状态
export function changeStatus(status,w_id) {
  console.info("11111"+status+w_id);
  const data = {
    "status":status,
    "w_id":w_id
  }
  return request({
    url: '/wms/wh/warehouse/changeStatus',
    method: 'put',
    data: data
  })
}
