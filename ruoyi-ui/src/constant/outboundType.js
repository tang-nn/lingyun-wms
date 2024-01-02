export const OutboundType = {
  // 其他出库
  OTHER_OUTBOUND: "other_outbound",
  // 退货出库
  OUTBOUND_RETURN: "outbound_return",
  // 盘亏出库,
  INVENTORY_LOSS_OUTBOUND: "inventory_loss_outbound",
  // 调拨出库
  TRANSFER_OUTBOUND: "transfer_outbound",
  // 销售出库
  SALES_OUTBOUND: "sales_outbound",
}
// 一下变量均为 form-item 的 prop 属性值
let a = "associated";
let o = "relatedOrder";
let ct = "contact";
let s = "supplier";
let sl = "slid";

let c = new Set(["consumer"]);
let acsct = new Set([a, ...c, s, ct]);
let rs = new Set([s])

let ss = new Set([s]);
let d = new Set([ct]);
let wsl = new Set(["warehouse", sl])
let edit = new Set([a, o, ct]);
// 当前入库表单不显示字段，或被禁用的字段
// canAr [can add or remove (可以添加或移除明细)]
export const formStatus = new Map([
  [OutboundType.OTHER_OUTBOUND,
    {
      label: "其它",
      hiddenFields: acsct,
      disableFields: wsl,
      fieldName: {
        onlyFieldName: 'stockId',
        // 订单 id 字段名称
        orderFieldName: 'tid',
        // 订单明细 id
        odid: 'obdId',
        // 订单号字段名称
        onFieldName: 'tdCode',
        //  客户 id，或是 供应商 id
        contactFieldName: 'cid',
      }
    }
  ],
  [
    OutboundType.OUTBOUND_RETURN,
    {
      label: "退货",
      hiddenFields: acsct,
      disableFields: wsl,
      fieldName: {
        onlyFieldName: 'stockId',
        // 订单 id 字段名称
        orderFieldName: 'tid',
        // 订单明细 id
        odid: 'obdId',
        // 订单号字段名称
        onFieldName: 'tdCode',
        //  客户 id，或是 供应商 id
        contactFieldName: 'cid',
      }
    }
  ],
  [
    OutboundType.INVENTORY_LOSS_OUTBOUND,
    {
      label: "盘亏",
      hiddenFields: acsct,
      disableFields: wsl,
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
    }
  ],
  [
    OutboundType.TRANSFER_OUTBOUND,
    {
      label: "调拨",
      hiddenFields: acsct,
      disableFields: wsl,
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
    }
  ],
  [
    OutboundType.SALES_OUTBOUND,
    {
      label: "销售",
      hiddenFields: ss,
      disableFields: wsl,
      fieldName: {
        onlyFieldName: 'stockId',
        // 订单 id 字段名称
        orderFieldName: 'tid',
        // 订单明细 id
        odid: 'obdId',
        // 订单号字段名称
        onFieldName: 'tdCode',
        //  客户 id，或是 供应商 id
        contactFieldName: 'cid',
      }
    }
  ]
]);
