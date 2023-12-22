import request from '@/utils/request'

// 查询商品列表
export function listGood(query) {
  return request({
    url: '/wms/goods/gdinfo',
    method: 'get',
    params: query
  })
}

//查询商品分类
export function TypesList(){
  return request({
    url: '/wms/goods/type/select',
    method: 'get'
  })
}


// 删除商品
export function delGoodsInfo(g_ids) {
  return request({
    url: '/wms/goods/' + g_ids,
    method: 'delete'
  })
}



// 货品信息状态修改
export function changeGoodsStatus(gId, status) {
  const data = {
    "status":status,
    "gId":gId
  }
  console.info(data);
  return request({
    url: '/wms/goods/editGoods',
    method: 'post',
    data: data
  })
}


//查询供应商list
export function SupplierList() {
  return request({
    url: '/wms/cu/supplier/supplierList',
    method: 'get',
  })
}




//新增货品

export function addGoods(data) {
  return request({
    url: '/wms/goods/agds',
    method: 'post',
    data:data,
  })
}




