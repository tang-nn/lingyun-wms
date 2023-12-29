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

// 查询商品信息
export function GoodsInfo(g_id) {
  return request({
    url: '/wms/goods/findByGid/'+g_id,
    method: 'get',
  })
}



//修改货品信息
export function editGoods(goods) {
  console.log("nibi",goods);
  return request({
    url: '/wms/goods/edit',
    method: 'post',
    data:goods,
  })
}




