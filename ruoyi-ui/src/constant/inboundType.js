export const InboundType = {
  // 进货入库
  PURCHASING_WAREHOUSING: "purchasing_warehousing",
  // 生产入库
  // PRODUCTION_RECEIPT: "production_receipt",
  // 盘盈入库
  INVENTORY_PLUS: "inventory_plus",
  // 调拨入库
  TRANSFER_RECEIPT: "transfer_receipt",
  // 退货入库
  RECEIPT_RETURNS: "receipt_returns",
}
let a = "associated";
let o = "relatedOrder";
let ct = "contact";
let s = "supplier";

let c = new Set(["consumer"]);
let acsct = new Set([a,...c,s,ct]);
let rs = new Set([s])

let d = new Set([ct]);
let w = new Set(["warehouse"])
let edit = new Set([a,o,ct]);
// 当前入库表单不显示字段，或被禁用的字段
export const formStatus = new Map([
  [InboundType.PURCHASING_WAREHOUSING, {
    label: "进货",
    hiddenFields: c,
    disableFields:d,
  }],
  [InboundType.INVENTORY_PLUS, {
    label: "盘盈",
    hiddenFields: acsct,
    disableFields:w,
  }],
  [InboundType.TRANSFER_RECEIPT, {
    label: "调拨",
    hiddenFields: acsct,
    disableFields:w,
  }],
  [InboundType.RECEIPT_RETURNS, {
    label: "退货",
    hiddenFields: rs,
    disableFields:d,
  }],
]);
