import request from '@/utils/request'


// 查询商品列表
export function ptStockNum() {
  return request({
    url: '/wms/wh/warehouse/stockNumber',
    method: 'get',
  })
}

export function ptStockMoney() {
  return request({
    url: '/wms/wh/warehouse/stockPrice',
    method: 'get',
  })
}

export function inbound() {
  return request({
    url: '/wms/wh/warehouse/ibStatistics',
    method: 'get',
  })
}

export function outbound() {
  return request({
    url: '/wms/wh/warehouse/obStatistics',
    method: 'get',
  })
}

export function inRank() {
  return request({
    url: '/wms/wh/warehouse/ibRank',
    method: 'get',
  })
}

export function outRank() {
  return request({
    url: '/wms/wh/warehouse/obRank',
    method: 'get',
  })
}

export function itwarehouse() {
  return request({
    url: '/wms/wh/warehouse/itdetails',
    method: 'get',
  })
}
export function outTwarehouse() {
  return request({
    url: '/wms/wh/warehouse/otdetails',
    method: 'get',
  })
}



export function iywarehouse() {
  return request({
    url: '/wms/wh/warehouse/iydetails',
    method: 'get',
  })
}
export function outYwarehouse() {
  return request({
    url: '/wms/wh/warehouse/oydetails',
    method: 'get',
  })
}


