import request from '@/utils/request'

//查询商品分类
export function TypesList(){
  return request({
    url: '/wms/goods/type/select',
    method: 'get'
  })
}


// 查询仓库列表
export function listsWarehouse(query) {
  return request({
    url: '/wms/wh/warehouse/lists',
    method: 'get',
    params: query
  })
}
