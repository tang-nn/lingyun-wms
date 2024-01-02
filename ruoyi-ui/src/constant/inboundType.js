export const InboundType = {
  // 进货入库
  PURCHASING_WAREHOUSING: "purchasing_warehousing",
  // 生产入库
  PRODUCTION_RECEIPT: "production_receipt",
  // 盘盈入库
  INVENTORY_PLUS: "inventory_plus",
  // 调拨入库
  TRANSFER_RECEIPT: "transfer_receipt",
  // 退货入库
  RECEIPT_RETURNS: "receipt_returns",
}
// 一下变量均为 form-item 的 prop 属性值
let a = "associated";
let o = "relatedOrder";
let ct = "contact";
let s = "supplier";

let c = new Set(["consumer"]);
let acsct = new Set([a, ...c, s, ct]);
let rs = new Set([s])

let d = new Set([ct]);
let w = new Set(["warehouse"])
let edit = new Set([a, o, ct]);
// 当前入库表单不显示字段，或被禁用的字段
// canAr [can add or remove (可以添加或移除明细)]
export const formStatus = new Map([
  [InboundType.PURCHASING_WAREHOUSING, {
    label: "进货",
    hiddenFields: c,
    disableFields: d,
    canAr: true,
    fieldName: {
      onlyFieldName: 'goodsId',
      // 订单 id 字段名称
      orderFieldName: 'poId',
      // 订单明细 id
      odid: 'pdId',
      // 订单号字段名称
      onFieldName: 'poCode',
      //  客户 id，或是 供应商 id
      contactFieldName: 'sid',
      whName: 'wId'
    }
  }],
  [InboundType.INVENTORY_PLUS, {
    label: "盘盈",
    hiddenFields: acsct,
    disableFields: w,
    canAr: true,
    fieldName: {
      onlyFieldName: 'sId',
      // 订单 id 字段名称
      orderFieldName: 'isId',
      // 订单明细 id
      odid: 'isdId',
      // 订单号字段名称
      onFieldName: 'isCode',
      //  客户 id，或是 供应商 id
      contactFieldName: '',
      whName: 'wId'
    }
  }],
  [InboundType.TRANSFER_RECEIPT, {
    label: "调拨",
    hiddenFields: acsct,
    disableFields: w,
    canAr: true,
    fieldName: {
      onlyFieldName: 'stockId',
      // 出库订单 id 字段名称
      orderFieldName: 'tid',
      // 订单明细 id
      odid: 'tdId',
      // 订单号字段名称
      onFieldName: 'tdCode',
      //  客户 id，或是 供应商 id
      contactFieldName: 'cid',
      // 仓库 id 字段名称
      whName: 'outWId'
    }
  }],
  [InboundType.RECEIPT_RETURNS, {
    label: "退货",
    hiddenFields: rs,
    disableFields: d,
  }],
]);
