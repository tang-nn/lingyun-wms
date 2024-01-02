import request from '@/utils/request'
import qs from 'qs'

// 查询出库列表
export function listOutbound(query) {
  return request({
    url: '/wms/wh/outbound/list',
    method: 'get',
    params: query
  })
}

// 更具id查询出库订单信息
export function getOutbound(outId) {
  return request({
    url: '/wms/wh/outbound/' + outId,
    method: 'get'
  })
}

// 新增出库单
export function addOutbound(data) {
  return request({
    url: '/wms/wh/outbound',
    method: 'POST',
    data
  })
}

// 更新出库单
export function updateOutbound(data) {
  return request({
    url: '/wms/wh/outbound',
    method: 'PUT',
    data
  })
}

/**
 * 出库审核记录
 *
 * @param data {Object}
 * @returns {AxiosPromise}
 */
export function outboundReview(data) {
  return request({
    url: '/wms/wh/outbound/review',
    method: 'PUT',
    data
  })
}
