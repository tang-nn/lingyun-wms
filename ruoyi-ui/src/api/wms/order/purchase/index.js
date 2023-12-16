import request from '@/utils/request'

// 进货单列表
export function listPurchase(query) {
  return request({
    url: '/wms/order/purchase/list',
    method: 'get',
    params: query
  })
}

// 进货单详细信息
export function getPurchaseInf(poId) {
  return request({
    url: '/wms/order/purchase/' + poId,
    method: 'get'
  })
}

// 添加进货订单信息
export function addPurchase(data) {
  return request({
    url: '/wms/order/purchase/add',
    method: 'POST',
    data
  })
}

// 添加进货订单信息
export function editPurchase(data) {
  return request({
    url: '/wms/order/purchase/edit',
    method: 'PUT',
    data
  })
}

// 批量删除进货订单
export function delPurchase(params) {
  let ids = (Array.isArray(params) ? params : [params]).toString()
  return request({
    url: '/wms/order/purchase/del',
    method: 'DELETE',
    params: {
      ids
    }
  })
}

// 批量删除进货订单
export function reviewPurchase(data) {
  return request({
    url: '/wms/order/purchase/review',
    method: 'PUT',
    data
  })
}
