import request from '@/utils/request'
import {download} from '@/utils/request'

// 查询编码规则列表
export function listRules(query) {
  return request({
    url: '/wms/whcomm/encoding/list',
    method: 'get',
    params: query
  })
}

// 查询编码规则详细
export function getEncoding_rules(erId) {
  return request({
    url: '/wms/whcomm/encoding/' + erId,
    method: 'get'
  })
}

// 新增编码规则
export function addEncoding_rules(data) {
  return request({
    url: '/wms/whcomm/encoding',
    method: 'post',
    data: data
  })
}

// 修改编码规则
export function updateEncodeRules(data) {
  return request({
    url: '/wms/whcomm/encoding',
    method: 'put',
    data: data
  })
}

// 导出编码规则
export function exportEncodeRules() {
  return download('/wms/whcomm/encoding/export',{}, "编号规则.xlsx")
}
