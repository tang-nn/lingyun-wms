import request from '@/utils/request'

// 进货单列表
export function listPurchase(query) {
  console.log("listPurchase -- query: ", query);
  return request({
    url: '/wms/order/purchase/list',
    method: 'get',
    params: query
  })
}
