package com.ruoyi.common.core.constant;

/**
 * @Author : Tang
 * @Description : 订单类型，数据字典‘order_type’，用于生成订单类型，值是在数据库中的 ID
 * @CreateDate : 2023/12/1 14:03
 */
public class OrderType {

    /**
     * 供应商
     */
    public static final int SUPPLIER = 1;
    /**
     * 货品信息
     */
    public static final int PRODUCT_INFO = 2;

    /**
     * 货品类型
     */
    public static final int GOODS_TYPE = 14;
    /**
     * 入库
     */
    public static final int INBOUND_ORDER = 3;
    /**
     * 出库
     */
    public static final int OUTBOUND_ORDER = 4;
    /**
     * 盘点
     */
    public static final int INVENTORY_SHEET = 5;
    /**
     * 调拨
     */
    public static final int TRANSFER_ORDER = 6;
    /**
     * 采购
     */
    public static final int PURCHASE_ORDER = 7;
    /**
     * 购买退货单
     */
    public static final int PURCHASE_RETURN_NOTE = 8;
    /**
     * 销售单
     */
    public static final int SALES_SLIP = 9;
    /**
     * 销售退货单
     */
    public static final int SALES_RETURN = 10;
    /**
     * 领用订单
     */
    public static final int COLLECTING_ORDERS = 11;
    /**
     * 客户
     */
    public static final int CONSUMER = 12;
}
