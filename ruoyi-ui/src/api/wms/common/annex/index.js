import request from '@/utils/request'

// 进货单列表
// ?type=at_purchase_annex&formId=15
export function listAnnex(query) {
  return request({
    url: '/wms/whcomm/annex/list',
    method: 'get',
    params: query
  })
}
