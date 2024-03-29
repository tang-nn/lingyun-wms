import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: 路由配置项
 *
 * hidden: true                     // 当设置 true 的时候该路由不会再侧边栏出现 如401，login等页面，或者如一些编辑页面/edit/1
 * alwaysShow: true                 // 当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面
 *                                  // 只有一个时，会将那个子路由当做根路由显示在侧边栏--如引导页面
 *                                  // 若你想不管路由下面的 children 声明的个数都显示你的根路由
 *                                  // 你可以设置 alwaysShow: true，这样它就会忽略之前定义的规则，一直显示根路由
 * redirect: noRedirect             // 当设置 noRedirect 的时候该路由在面包屑导航中不可被点击
 * name:'router-name'               // 设定路由的名字，一定要填写不然使用<keep-alive>时会出现各种问题
 * query: '{"id": 1, "name": "ry"}' // 访问路由的默认传递参数
 * roles: ['admin', 'common']       // 访问路由的角色权限
 * permissions: ['a:a:a', 'b:b:b']  // 访问路由的菜单权限b
 * meta : {
 noCache: true                   // 如果设置为true，则不会被 <keep-alive> 缓存(默认 false)
 title: 'title'                  // 设置该路由在侧边栏和面包屑中展示的名字
 icon: 'svg-name'                // 设置该路由的图标，对应路径src/assets/icons/svg
 breadcrumb: false               // 如果设置为false，则不会在breadcrumb面包屑中显示
 activeMenu: '/system/user'      // 当路由设置了该属性，则会高亮相对应的侧边栏。
 }
 */

// 公共路由
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login'),
    hidden: true
  },
  {
    path: '/register',
    component: () => import('@/views/register'),
    hidden: true
  },
  {
    path: '',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/gdsadd',
        component: () => import('@/views/wms/goodsmanager/goodsinfo/goodsadd'),
        name: 'goodsadd',
        meta: {title: '商品管理 / 新增货品信息', icon: 'dashboard'}
      }
    ]
  },
  {
    path: '',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/gdsedit/:gid',
        component: () => import('@/views/wms/goodsmanager/goodsinfo/goodsedit'),
        name: 'goodsedit',
        meta: {title: '商品管理 / 编辑货品信息', icon: 'dashboard'}
      }
    ]
  },
  {
    path: '',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/goods',
        component: () => import('@/views/wms/goodsmanager/goodsinfo/index.vue'),
        name: 'gdpage',
        meta: {title: '货品信息', icon: 'dashboard'}
      }
    ]
  },
  {
    path: '',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/housedetails/:w_id',
        component: () => import('@/views/wms/warehouse/warehousedetails/index'),
        name: 'warehousedetail',
        meta: {title: '仓库详情', icon: 'dashboard'}
      }
    ]
  },
  {
    path: '',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'warehousemanager',
        component: () => import('@/views/wms/warehouse/index'),
        name: 'whmanager',
        meta: {title: '仓库管理', icon: 'dashboard'}
      }
    ]
  },
  {
    path: '',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'addwarehouse',
        component: () => import('@/views/wms/warehouse/addwarehouse/index'),
        name: 'whadd',
        meta: {title: '新增仓库', icon: 'dashboard'}
      }
    ]
  },
  {
    path: '',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/edithouse/:w_id',
        component: () => import('@/views/wms/warehouse/editwarehouse/index'),
        name: 'whedit',
        meta: { title: '修改仓库', icon: 'dashboard'}
      }
    ]
  },
  {
    path: '',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/InventorySheet',
        component: () => import('@/views/wms/warehouse/inventory/index'),
        name: 'InSheet',
        meta: {title: '库存盘点', icon: 'dashboard'}
      }
    ]
  },
  {
    path: '',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/addInventorySheet',
        component: () => import('@/views/wms/warehouse/inventory/addIndex'),
        name: 'addInSheet',
        meta: {title: '库存盘点 / 新增盘点', icon: 'dashboard'}
      }
    ]
  },
  {
    path: '',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/Inventorydetails/:isId',
        component: () => import('@/views/wms/warehouse/inventory/detailsIndex'),
        name: 'Indetails',
        meta: {title: '库存盘点/盘点详情', icon: 'dashboard'}
      }
    ]
  },
  {
    path: '',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/addtransfer',
        component: () => import('@/views/wms/warehouse/Transfer/addIndex'),
        name: 'addtransferOrder',
        meta: {title: '库存调拨/新增调拨', icon: 'dashboard'}
      }
    ]
  },
  {
    path: '',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/transfer',
        component: () => import('@/views/wms/warehouse/Transfer/index'),
        name: 'transfer',
        meta: {title: '库存调拨', icon: 'dashboard'}
      }
    ]
  },
  {
    path: '',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/Transferdetails/:tid',
        component: () => import('@/views/wms/warehouse/Transfer/detailsIndex'),
        name: 'tfdetails',
        meta: {title: '库存调拨/调拨详情', icon: 'dashboard'}
      }
    ]
  },
  {
    path: '',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/updateTransfer/:tid',
        component: () => import('@/views/wms/warehouse/Transfer/editIndex.vue'),
        name: 'updetails',
        meta: {title: '库存调拨/修改调拨', icon: 'dashboard'}
      }
    ]
  },
  {
    path: '/404',
    component: () => import('@/views/error/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error/401'),
    hidden: true
  },
  {
    path: '',
    component: Layout,
    redirect: 'index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/Home'),
        name: 'Index',
        meta: {title: '首页', icon: 'dashboard', affix: true}
      }
    ]
  },
  {
    path: '/user',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [
      {
        path: 'profile',
        component: () => import('@/views/system/user/profile/index'),
        name: 'Profile',
        meta: {title: '个人中心', icon: 'user'}
      }
    ]
  }
]

// 动态路由，基于用户权限动态去加载
export const dynamicRoutes = [
  {
    path: '/system/user-auth',
    component: Layout,
    hidden: true,
    permissions: ['system:user:edit'],
    children: [
      {
        path: 'role/:userId(\\d+)',
        component: () => import('@/views/system/user/authRole'),
        name: 'AuthRole',
        meta: {title: '分配角色', activeMenu: '/system/user'}
      }
    ]
  },
  {
    path: '/system/role-auth',
    component: Layout,
    hidden: true,
    permissions: ['system:role:edit'],
    children: [
      {
        path: 'user/:roleId(\\d+)',
        component: () => import('@/views/system/role/authUser'),
        name: 'AuthUser',
        meta: {title: '分配用户', activeMenu: '/system/role'}
      }
    ]
  },
  {
    path: '/system/dict-data',
    component: Layout,
    hidden: true,
    permissions: ['system:dict:list'],
    children: [
      {
        path: 'index/:dictId(\\d+)',
        component: () => import('@/views/system/dict/data'),
        name: 'Data',
        meta: {title: '字典数据', activeMenu: '/system/dict'}
      }
    ]
  },
  {
    path: '/monitor/job-log',
    component: Layout,
    hidden: true,
    permissions: ['monitor:job:list'],
    children: [
      {
        path: 'index/:jobId(\\d+)',
        component: () => import('@/views/monitor/job/log'),
        name: 'JobLog',
        meta: {title: '调度日志', activeMenu: '/monitor/job'}
      }
    ]
  },
  {
    path: '/tool/gen-edit',
    component: Layout,
    hidden: true,
    permissions: ['tool:gen:edit'],
    children: [
      {
        path: 'index/:tableId(\\d+)',
        component: () => import('@/views/tool/gen/editTable'),
        name: 'GenEdit',
        meta: {title: '修改生成配置', activeMenu: '/tool/gen'}
      }
    ]
  },
  {
    path: '/order',
    component: Layout,
    hidden: true,
    permissions: ['order:purchase'],
    children: [
      {
        path: 'purchase/add',
        permissions: ['order:purchase:add'],
        component: () => import('@/views/wms/order/purchasingOrder/add.vue'),
        name: '添加进货单据',
        meta: {title: '添加进货信息', activeMenu: '/order/purchase/add'}
      },
      {
        path: 'purchase/edit/:poId(\\d+)',
        permissions: ['order:purchase:exit'],
        component: () => import('@/views/wms/order/purchasingOrder/edit.vue'),
        name: '编辑进货单据',
        meta: {title: '编辑进货信息', activeMenu: '/order/purchase/edit'}
      },
      {
        path: 'purchase/details/:poId(\\d+)',
        permissions: ['order:purchase:details'],
        component: () => import('@/views/wms/order/purchasingOrder/details.vue'),
        name: '进货单据明细',
        meta: {title: '进货单据明细', activeMenu: '/order/purchase/details'}
      }
    ]
  },
  {
    path: '/inOutbound',
    hidden: true,
    component: Layout,
    permissions: ['inbound'],
    children: [
      {
        path: 'inbound/add',
        permissions: ['inbound:add'],
        component: () => import('@/views/wms/warehouse/inOutbound/inbound/add.vue'),
        name: '添加入库单',
        meta: {title: '添加入库单', activeMenu: '/inOutbound/inbound'}
      },
      {
        path: 'inbound/edit/:inid(\\d+)',
        permissions: ['inbound:editor'],
        component: () => import('@/views/wms/warehouse/inOutbound/inbound/editor.vue'),
        name: '编辑入库单',
        meta: {title: '编辑入库单', activeMenu: '/inOutbound/inbound'}
      },
      {
        path: 'inbound/details/:inid(\\d+)',
        permissions: ['inbound:details'],
        component: () => import('@/views/wms/warehouse/inOutbound/inbound/details.vue'),
        name: '入库单明细',
        meta: {title: '入库单明细', activeMenu: '/inOutbound/inbound'}
      },
      {
        name: '添加出库单',
        path: 'outbound/add',
        permissions: ['outbound:add'],
        component: () => import('@/views/wms/warehouse/inOutbound/outbound/OutboundAdd.vue'),
        meta: {title: '添加出库单', activeMenu: '/inOutbound/outbound'}
      },
      {
        name: '编辑出库单',
        path: 'outbound/edit/:outId(\\d+)',
        permissions: ['outbound:edit'],
        component: () => import('@/views/wms/warehouse/inOutbound/outbound/OutboundEditor.vue'),
        meta: {title: '添加出库单', activeMenu: '/inOutbound/outbound'}
      }
    ],
  },
  {
    path: '/contactUnits/consumer/add',
    component: Layout,
    hidden: true,
    permissions: ['contactUnits:consumer:add'],
    children: [
      {
        path: '',
        component: () => import('@/views/wms/contactUnits/consumer/add'),
        name: '新增客户',
        meta: {title: '新增客户', activeMenu: '/contactUnits/consumer/add'}
      }
    ]
  },
  {
    path: '/contactUnits/consumer/index',
    component: Layout,
    hidden: true,
    permissions: ['contactUnits:consumer:index'],
    children: [
      {
        path: '',
        component: () => import('@/views/wms/contactUnits/consumer/index'),
        name: '客户',
        meta: {title: '客户', activeMenu: '/contactUnits/consumer/index'}
      }
    ]
  },
]

// 防止连续点击多次路由报错
let routerPush = Router.prototype.push;
let routerReplace = Router.prototype.replace;
// push
Router.prototype.push = function push(location) {
  return routerPush.call(this, location).catch(err => err)
}
// replace
Router.prototype.replace = function push(location) {
  return routerReplace.call(this, location).catch(err => err)
}

export default new Router({
  mode: 'history', // 去掉url中的#
  scrollBehavior: () => ({y: 0}),
  routes: constantRoutes
})
