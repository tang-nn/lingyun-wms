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
