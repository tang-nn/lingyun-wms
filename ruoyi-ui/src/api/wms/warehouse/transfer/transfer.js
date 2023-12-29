import request from '@/utils/request'

// 查询调拨单列表
export function listTransfer(query) {
  return request({
    url: '/wms/wh/transfer/list',
    method: 'get',
    params: query
  })
}

// 查询调拨单详细
export function getTransfer(tid) {
  return request({
    url: '/wms/wh/transfer/'+tid,
    method: 'get'
  })
}

// 新增调拨单
export function addTransfer(data) {
  return request({
    url: '/wms/wh/transfer/addtransfer',
    method: 'post',
    data: data
  })
}

// 修改调拨单
export function updateTransfer(data) {
  return request({
    url: '/wms/wh/transfer/editTransfer',
    method: 'put',
    data: data
  })
}

// 删除调拨单
export function delTransfer(tid) {
  return request({
    url: '/wms/wh/transfer/' + tid,
    method: 'delete'
  })
}

//删除调拨明细单
export function deleteTransferDetails(tdId) {
  return request({
    url: '/wms/wh/transfer/deleteDetails/' + tdId,
    method: 'delete'
  })
}

//根据调出仓库查询下面的调拨货品
export function listGoodByWid(query) {
  return request({
    url: '/wms/wh/inventory/getByWid',
    method: 'get',
    params: query
  })
}



//查询入库仓库库位

export function listSlName(w_id) {
  return request({
    url: '/wms/wh/transfer/' + w_id,
    method: 'POST'
  })
}



/**
 * 查询调入仓库某库位下的某货品货品计划数量
 * @param w_id 仓库
 * @param sl_id 库位
 * @param g_id 货品
 * @returns {AxiosPromise}
 */
export function inNumsPlan(w_id,sl_id,g_id) {
  const data = {
    w_id,
    sl_id,
    g_id
  }
  return request({
    url: '/wms/wh/transfer/inNumsPlans',
    method: 'post',
    data:data
  })
}


//审核调拨单
export function reviewInventory(data) {
  return request({
    url: '/wms/wh/transfer/reviewInventory' ,
    method: 'post',
    data: data
  })
}
