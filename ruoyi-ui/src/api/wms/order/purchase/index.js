import request from '@/utils/request'
import qs from "qs";

// 进货单列表
export function listPurchase(params) {
  let query = qs.stringify(params, {arrayFormat: 'repeat'});
  // console.log("query: ", query);
  return request({
    url: '/wms/order/purchase/list?' + query,
    method: 'get'
  })
}

// 进货单详细信息
export function getPurchaseInf(poId) {
  return request({
    url: '/wms/order/purchase/' + poId,
    method: 'get'
  })
}

// 根据进货 ID 进货明细
export function getPurchaseDetails(poId, params) {
  console.log("getPurchaseDetails params: ", params)
  return request({
    url: '/wms/order/purchase/details/' + poId,
    method: 'get',
    params
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

// 审核进货订单
export function reviewPurchase(data) {
  return request({
    url: '/wms/order/purchase/review',
    method: 'PUT',
    data
  })
}
