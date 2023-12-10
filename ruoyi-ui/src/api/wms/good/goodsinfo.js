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



// 查询角色详细
export function getRole(roleId) {
  return request({
    url: '/system/role/' + roleId,
    method: 'get'
  })
}

// 新增角色
export function addRole(data) {
  return request({
    url: '/system/role',
    method: 'post',
    data: data
  })
}

// 修改角色
export function updateRole(data) {
  return request({
    url: '/system/role',
    method: 'put',
    data: data
  })
}

// 角色数据权限
export function dataScope(data) {
  return request({
    url: '/system/role/dataScope',
    method: 'put',
    data: data
  })
}





// 查询角色已授权用户列表
export function allocatedUserList(query) {
  return request({
    url: '/system/role/authUser/allocatedList',
    method: 'get',
    params: query
  })
}

// 查询角色未授权用户列表
export function unallocatedUserList(query) {
  return request({
    url: '/system/role/authUser/unallocatedList',
    method: 'get',
    params: query
  })
}

// 取消用户授权角色
export function authUserCancel(data) {
  return request({
    url: '/system/role/authUser/cancel',
    method: 'put',
    data: data
  })
}

// 批量取消用户授权角色
export function authUserCancelAll(data) {
  return request({
    url: '/system/role/authUser/cancelAll',
    method: 'put',
    params: data
  })
}

// 授权用户选择
export function authUserSelectAll(data) {
  return request({
    url: '/system/role/authUser/selectAll',
    method: 'put',
    params: data
  })
}

// 根据角色ID查询部门树结构
export function deptTreeSelect(roleId) {
  return request({
    url: '/system/role/deptTree/' + roleId,
    method: 'get'
  })
}
