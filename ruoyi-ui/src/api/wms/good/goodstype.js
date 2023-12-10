import request from '@/utils/request'

// 查询商品分类列表
export function listGoodType(query) {
  console.info("query-===="+query);
  return request({
    url: '/wms/goods/type/list',
    method: 'get',
    params: query
  })
}


// 删除商品分类
export function delGoodsType(gtIds) {
  return request({
    url: '/wms/goods/type/' + gtIds,
    method: 'delete'
  })
}

//新增商品分类
export function addTypeGoods(data) {
  console.info(data);
  return request({
    url: '/wms/goods/type/addGoodsType',
    method: 'post',
    data: data
  })
}

//根据gt_id查询货品分类
export function getGoodsType(gtId) {
  console.info(gtId);
  return request({
    url: '/wms/goods/type/' +gtId,
    method: 'post',
  })
}

//修改商品分类
export function editTypeGoods(data) {
  console.info(data);
  return request({
    url: '/wms/goods/type/editGoodsType',
    method: 'put',
    data: data
  })
}

// 货品type状态修改
export function changeGoodTypeStatus(gtId, status) {
  const data = {
    "status":status,
    "gtId":gtId
  }
  console.info(data);
  return request({
    url: '/wms/goods/type/editGoodsType',
    method: 'put',
    data: data
  })
}


