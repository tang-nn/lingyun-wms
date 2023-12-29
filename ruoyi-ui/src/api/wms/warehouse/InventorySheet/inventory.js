import request from '@/utils/request'

// 查询盘点单列表
export function listInventory(query) {
  return request({
    url: '/wms/wh/inventory/list',
    method: 'get',
    params: query
  })
}

export function listInventoryByIsId(query) {
  return request({
    url: '/wms/wh/inventory/listByIsId',
    method: 'get',
    params: query
  })
}

//根据仓库查询下面的盘点货品
export function listGoodByWid(query) {
  return request({
    url: '/wms/wh/inventory/getByWid',
    method: 'get',
    params: query
  })
}

// 新增盘点单
export function addInventory(data) {
  return request({
    url: '/wms/wh/inventory/inventoryAndDetials',
    method: 'post',
    data: data
  })
}


// 查询盘点单详细
export function getInventory(isId) {
  return request({
    url: '/InventorySheet/inventory/' + isId,
    method: 'get'
  })
}



// 修改盘点单
export function updateInventory(data) {
  return request({
    url: '/InventorySheet/inventory',
    method: 'put',
    data: data
  })
}

// 删除盘点单
export function delInventory(isIds) {
  return request({
    url: '/wms/wh/inventory/' + isIds,
    method: 'delete'
  })
}


//审核盘点单
export function reviewInventory(data) {
  return request({
    url: '/wms/wh/inventory/reviewInventory' ,
    method: 'post',
    data: data
  })
}

