import request from '@/utils/request'
import qs from 'qs'

// 查询入库列表
export function listInbound(query) {
  return request({
    url: '/wms/wh/inbound/list',
    method: 'get',
    params: query
  })
}

// 查询某一条入库记录
export function getInbound(inId) {
  return request({
    url: '/wms/wh/inbound/' + inId,
    method: 'get'
  })
}

// 插入入库记录
export function addInbound(data) {
  return request({
    url: '/wms/wh/inbound',
    method: 'POST',
    data
  })
}

// 修改入库记录
export function putInbound(data) {
  return request({
    url: '/wms/wh/inbound',
    method: 'PUT',
    data
  })
}
