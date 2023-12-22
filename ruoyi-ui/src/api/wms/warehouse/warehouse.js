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
  console.info(wId);
  return request({
    url: '/wms/wh/warehouse/' + wId,
    method: 'get'
  })
}

//查询仓位操作记录
// export function listWarehouse(query) {
//   return request({
//     url: '/wms/wh/warehouse/list',
//     method: 'get',
//     params: query
//   })
// }




// 新增仓库
export function addWarehouse(data) {
  return request({
    url: '/wms/wh/warehouse',
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

//修改库位状态
export function changeLocationStatus(status,sl_id) {
  const data = {
    "status":status,
    "sl_id":sl_id
  }
  return request({
    url: '/wms/wh/location/changeLocationStatus',
    method: 'put',
    data: data
  })
}

//查询仓库下的所有库位
export function listStorage(query) {
  return request({
    url: '/wms/wh/warehouse/storagelist',
    method: 'get',
    params: query
  })
}


//查询仓库下的所有库位 - 用于下拉框
export function listStorageByWid(wid) {
  return request({
    url: '/wms/wh/location/list/wid/' + wid,
    method: 'get'
  })
}

