<template>
  <div class="container">
    <el-form ref="inboundInf" :model="inboundInf"
             :rules="rules"
             class="demo-inboundInf"
             label-width="100px">
      <div class="group">
        <el-row>
          <el-col :span="3">
            基础信息
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <div class="basicInf">
          <el-form-item label="入库单号" label-width="120px" prop="inCode">
            <el-input v-model="inboundInf.inCode" disabled placeholder="自动获取系统编号"></el-input>
          </el-form-item>
          <el-form-item label="入库类型" label-width="120px" prop="inType">
            <el-select
              v-model="inboundInf.inType"
              :disabled="isEditor"
              placeholder="请选择入库类型"
              @change="handlerChangeInType"
            >
              <el-option
                v-for="dict in dict.type.incoming_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="仓库" label-width="120px" prop="wid">
            <el-select v-model="inboundInf.wid"
                       :disabled="currFormHandler.disableFields.has('warehouse')"
                       placeholder="请选择仓库" @change="handlerWhChange">
              <el-option
                v-for="wh in whList"
                :key="wh.wId"
                :label="wh.wName"
                :value="wh.wId"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="入库日期" label-width="120px" prop="storageDate" required>
            <el-date-picker
              v-model="inboundInf.storageDate"
              format="yyyy-MM-dd"
              placeholder="选择日期"
              type="date">
            </el-date-picker>
          </el-form-item>
          <el-form-item ref="relatedOrder" :label="`${currFormHandler.label}订单号`"
                        :prop="currFormHandler.hiddenFields.has('relatedOrder')?'selectedOrder.orderId':''"
                        :show-message="true"
                        label-width="120px">
            <div @click="handlerSelectOrder">
              <el-input v-model="inboundInf.selectedOrder.orderNo" :disabled="!inboundInf.associated || isEditor"
                        placeholder="请选择订单"
                        readonly
                        style="cursor: pointer"
                        suffix-icon="el-icon-more"
              ></el-input>
            </div>
          </el-form-item>
          <el-form-item v-if="!currFormHandler.hiddenFields.has('associated')" class="no-margin-left"
                        label="是否关联订单:" label-width="120px"
                        prop="associated"
                        style="margin-left: 0 !important;display:flex;align-items: center">
            <el-switch
              v-model="inboundInf.associated"
              :disabled="isEditor"
              active-color="#13ce66"
              active-text="关联订单"
              inactive-color="#ff4949"
              inactive-text="不关联"
              style="display: block"
              @change="handlerAssociatedChange">
            </el-switch>
          </el-form-item>
          <el-form-item v-if="!currFormHandler.hiddenFields.has('supplier')" ref="supplier"
                        :prop="currFormHandler.hiddenFields.has('relatedOrder')?'':'sid'"
                        label="供应商" label-width="120px"
                        prop="sid"
          >
            <el-select v-model="inboundInf.sid"
                       :disabled="inboundInf.associated"
                       placeholder="请选择供应商" @change="handlerSupplierChange">
              <el-option
                v-for="item in supplierList"
                :key="item.sId"
                :label="item.sName"
                :value="item.sId">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item v-if="!currFormHandler.hiddenFields.has('consumer')" ref="consumer"
                        :required="currFormHandler.disableFields.has('consumer')"
                        label="客户"
                        label-width="120px" prop="cid">
            <el-select v-model="inboundInf.cid"
                       :disabled="inboundInf.associated"
                       placeholder="请选择客户" @change="handlerConsumerChange">
              <el-option
                v-for="item in consumerList"
                :key="item.cid"
                :label="item.cName"
                :value="item.cid">
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item v-if="!currFormHandler.hiddenFields.has('contact')" label="联系人" label-width="120px">
            <el-input disabled v-bind:value="contact.contactPerson"/>
          </el-form-item>
          <el-form-item v-if="!currFormHandler.hiddenFields.has('contact')" label="联系方式" label-width="120px">
            <el-input disabled v-bind:value="contact.contactNumber"/>
          </el-form-item>
          <el-form-item label="经办人" label-width="120px" prop="manager">
            <el-select v-model="inboundInf.manager"
                       placeholder="请选经办人">
              <el-option
                v-for="u in userList"
                :key="u.userId + ''"
                :label="u.nickName"
                :value="u.userId + ''"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="备注" label-width="120px" prop="remark" style="grid-column-start: 1;grid-column-end: 3;">
            <el-input v-model="inboundInf.remark" :autosize="{ minRows: 2, maxRows: 6 }" :rows="6" class="remark-inb"
                      type="textarea"></el-input>
          </el-form-item>
        </div>
        <!-- 选择订单的模态框 -->
        <el-dialog v-if="!isEditor" :visible.sync="orderFormVisible" append-to-body title="选择进货单" width="1000px">
          <!-- 调拨订单 -->
          <div v-if="this.inboundInf.inType == this.inboundType.TRANSFER_RECEIPT">
            <el-form ref="queryOrderForm" :inline="true" :model="transferOrderQueryParams" size="small">
              <el-row>
                <el-col :span="6">
                  <el-form-item prop="transferOrder">
                    <el-input
                      v-model.trim="transferOrderQueryParams.transferOrder"
                      clearable
                      placeholder="请输入调拨订单"
                      style="width: 200px"
                      @keyup.enter.native="handleOrderQuery"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item prop="outStatus">
                    <el-select
                      v-model="transferOrderQueryParams.outStatus"
                      clearable
                      placeholder="订单出库状态"
                      style="width: 220px"
                    >
                      <el-option
                        v-for="dict in transferOutStatus"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      />
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item prop="inStatus">
                    <el-select
                      v-model="transferOrderQueryParams.inStatus"
                      clearable
                      placeholder="订单入库状态"
                      style="width: 220px"
                    >
                      <el-option
                        v-for="dict in transferInStatus"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      />
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item prop="dateRange">
                    <el-date-picker
                      v-model="transferOrderQueryParams.dateRange"
                      clearable
                      end-placeholder="结束日期"
                      range-separator="-"
                      start-placeholder="开始日期"
                      style="width: 220px"
                      type="daterange"
                      value-format="yyyy-MM-dd"
                    ></el-date-picker>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="6">
                  <el-form-item>
                    <el-button icon="el-icon-search" size="mini" type="primary" @click="handleOrderQuery">搜索
                    </el-button>
                    <el-button icon="el-icon-refresh" size="mini" @click="resetOrderQuery">重置</el-button>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
            <el-table v-loading="orderTableLoading" :data="orderListData" @row-dblclick="handlerRowDblclick"
                      @row-click="singleElectionTable">
              <el-table-column align="center" fixed width="50">
                <template slot-scope="scope">
                  <el-radio
                    v-model="selectedOrderTemp.orderId"
                    :label="scope.row.tid"
                    class="table_radio"/>
                </template>
              </el-table-column>
              <el-table-column align="center" fixed label="序号" type="index" width="40"/>
              <el-table-column fixed label="调拨单号" prop="tdCode"/>
              <el-table-column align="center" fixed label="调拨日期" prop="date" width="130">
                <template slot-scope="scope">
                  <span>{{ parseTime(scope.row.date, "{y}年{m}月{d}日") }}</span>
                </template>
              </el-table-column>
              <el-table-column align="center" fixed label="调出仓库" prop="outName" width="76"/>
              <el-table-column align="center" fixed label="调入仓库" prop="inName" width="76"/>
              <el-table-column align="center" label="入库状态" prop="outStatus" width="100">
                <template slot-scope="scope">
                  <dict-tag :options="dict.type.transfer_out_status" :value="scope.row.outStatus"/>
                </template>
              </el-table-column>
              <el-table-column align="center" label="入库状态" prop="inStatus" width="100">
                <template slot-scope="scope">
                  <dict-tag :options="dict.type.transfer_in_status" :value="scope.row.inStatus"/>
                </template>
              </el-table-column>
              <el-table-column align="center" label="调拨数量" prop="transferQuantity" width="100"/>
              <el-table-column align="center" label="调拨金额" prop="transferAmount" width="100"/>
              <el-table-column align="center" label="制单人" prop="creatorName" width="100"/>
              <el-table-column align="center" label="所在部门" prop="creatorDept" width="100"/>
              <el-table-column align="center" label="制单时间" prop="createTime" width="100"/>
            </el-table>
          </div>
          <!-- 盘点订单 -->
          <div v-if="this.inboundInf.inType == this.inboundType.INVENTORY_PLUS">
            <el-form ref="queryOrderForm" :inline="true" :model="inventoryOrderParams" label-width="68px" size="small">
              <el-form-item label="盘点单号" prop="is_code">
                <el-input
                  v-model="inventoryOrderParams.is_code"
                  clearable
                  placeholder="请输入盘点单号"
                />
              </el-form-item>
              <el-form-item label="盘点结果">
                <el-select
                  v-model="inventoryOrderParams.is_result"
                  clearable
                  placeholder="请选择"
                  style="width: 240px"
                >
                  <el-option
                    v-for="dict in dict.type.p_result_check"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                  />
                </el-select>
              </el-form-item>
              <el-form-item label="盘点类型">
                <el-select
                  v-model="inventoryOrderParams.is_type"
                  clearable
                  placeholder="请选择"
                  style="width: 240px"
                >
                  <el-option
                    v-for="dict in dict.type.pd_type_check"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                  />
                </el-select>
              </el-form-item>
              <el-form-item label="仓库名称" prop="w_id">
                <el-select
                  v-model="inventoryOrderParams.w_id"
                  clearable
                  placeholder="请选择"
                  style="width: 240px"
                >
                  <el-option
                    v-for="wh in whList"
                    :key="wh.wId"
                    :label="wh.wName"
                    :value="wh.wId"
                  />
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-button icon="el-icon-search" size="mini" type="primary" @click="handleOrderQuery">搜索
                </el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetOrderQuery">重置</el-button>
              </el-form-item>
            </el-form>
            <el-table v-loading="orderTableLoading" :data="orderListData" max-height="520"
                      @row-dblclick="handlerRowDblclick" @row-click="singleElectionTable">
              <el-table-column align="center" fixed="left" width="50">
                <template slot-scope="scope">
                  <el-radio
                    v-model="selectedOrderTemp.orderId"
                    :label="scope.row.isId"
                    class="table_radio"/>
                </template>
              </el-table-column>
              <el-table-column align="center" fixed="left" label="序号" type="index" width="40"/>
              <el-table-column align="center" fixed="left" label="盘点单号" prop="isCode"/>
              <el-table-column label="盘点开始时间" prop="isStartTime" width="100">
                <template slot-scope="scope">
                  <span>{{ parseTime(scope.row.isStartTime, '{y}-{m}-{d}') }}</span>
                </template>
              </el-table-column>
              <el-table-column label="盘点结束时间" prop="isEndTime" width="100">
                <template slot-scope="scope">
                  <span>{{ parseTime(scope.row.isEndTime, '{y}-{m}-{d}') }}</span>
                </template>
              </el-table-column>
              <el-table-column align="center" label="仓库名称" prop="whName"/>
              <el-table-column align="center" label="盘点类型" prop="isType">
                <template slot-scope="scope">
                  <dict-tag :options="dict.type.pd_type_check" :value="scope.row.isType"/>
                </template>
              </el-table-column>
              <el-table-column align="center" fixed="left" label="盘点结果" prop="isResult">
                <template slot-scope="scope">
                  <dict-tag :options="dict.type.p_result_check" :value="scope.row.isResult"/>
                </template>
              </el-table-column>
              <el-table-column align="center" label="出库状态" prop="outStatus">
                <template slot-scope="scope">
                  <dict-tag :options="dict.type.outbound_status" :value="scope.row.outStatus"/>
                </template>
              </el-table-column>
              <el-table-column align="center" label="入库状态" prop="inStatus">
                <template slot-scope="scope">
                  <dict-tag :options="dict.type.inbound_status" :value="scope.row.inStatus"/>
                </template>
              </el-table-column>
              <el-table-column :formatter="handlerCountQuantity" align="center" label="盘点数量"/>
              <el-table-column :formatter="handlerProfitQuantity" align="center" label="盘盈数量" prop="pq"/>
              <el-table-column :formatter="handlerLossQuantity" align="center" label="盘亏数量" prop="lq"
                               style="color: red"/>
              <el-table-column :formatter="handlerCountAmount" align="center" label="盘点金额" width="100"/>
              <el-table-column :formatter="handlerProfitAmount" align="center" label="盘盈金额" prop="count_amount"
                               style="color: #1ab394" width="100"/>
              <el-table-column :formatter="handlerLossAmount" align="center" label="盘亏金额" prop="count_amount"
                               style="color: red" width="100"/>
              <el-table-column align="center" label="经办人" prop="managerName"/>
              <el-table-column align="center" fixed="right" label="制单人" prop="creatorName"/>
              <el-table-column align="center" fixed="right" label="所在部门" prop="creatorDept" width="140px"/>
              <el-table-column align="center" fixed="right" label="制单时间" prop="createTime" width="110px">
                <template slot-scope="scope">
                  <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <!-- 进货入库 -->
          <div v-if="this.inboundInf.inType == this.inboundType.PURCHASING_WAREHOUSING">
            <el-form ref="queryOrderForm" :inline="true" :model="purchaseOrderParams" size="small">
              <el-row>
                <el-col :span="6">
                  <el-form-item prop="purOrderSn">
                    <el-input
                      v-model.trim="purchaseOrderParams.purOrderSn"
                      clearable
                      placeholder="请输入进货订单"
                      style="width: 200px"
                      @keyup.enter.native="handleOrderQuery"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item prop="orderStatus">
                    <el-select
                      v-model="purchaseOrderParams.orderStatus"
                      clearable
                      placeholder="订单状态"
                      style="width: 220px"
                    >
                      <el-option
                        v-for="dict in dict.type.ord_doc_status"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      />
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="进货日期" label-width="76px" prop="dateRange">
                    <el-date-picker
                      v-model="purchaseOrderParams.dateRange"
                      clearable
                      end-placeholder="结束日期"
                      range-separator="-"
                      start-placeholder="开始日期"
                      style="width: 220px"
                      type="daterange"
                      value-format="yyyy-MM-dd"
                    ></el-date-picker>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="6">
                  <el-form-item prop="purchaser">
                    <el-input
                      v-model.trim="purchaseOrderParams.purchaser"
                      clearable
                      placeholder="请输入进货人"
                      style="width: 200px"
                      @keyup.enter.native="handleOrderQuery"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item prop="purDept">
                    <el-input
                      v-model.trim="purchaseOrderParams.purDept"
                      clearable
                      placeholder="请输入进货部门"
                      style="width: 220px"
                      @keyup.enter.native="handleOrderQuery"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item prop="suppName">
                    <el-input
                      v-model.trim="purchaseOrderParams.suppName"
                      clearable
                      placeholder="请输入供应商名称"
                      style="width: 200px"
                      @keyup.enter.native="handleOrderQuery"
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item>
                    <el-button icon="el-icon-search" size="mini" type="primary" @click="handleOrderQuery">搜索
                    </el-button>
                    <el-button icon="el-icon-refresh" size="mini" @click="resetOrderQuery">重置</el-button>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-form>
            <el-table v-loading="orderTableLoading" :data="orderListData" @row-dblclick="handlerRowDblclick"
                      @row-click="singleElectionTable">
              <el-table-column align="center" fixed width="50">
                <template slot-scope="scope">
                  <el-radio
                    v-model="selectedOrderTemp.orderId"
                    :label="scope.row.poId"
                    class="table_radio"/>
                </template>
              </el-table-column>
              <el-table-column align="center" fixed type="index" width="40"/>
              <el-table-column fixed label="进货单号" prop="poCode"/>
              <el-table-column align="center" fixed label="单据状态" prop="status" width="76">
                <template slot-scope="scope">
                  <dict-tag :options="dict.type.ord_doc_status" :value="scope.row.status"/>
                </template>
              </el-table-column>
              <el-table-column align="center" fixed label="进货日期" prop="purchaseDate" width="130">
                <template slot-scope="scope">
                  <span>{{ parseTime(scope.row.purchaseDate, "{y}年{m}月{d}日") }}</span>
                </template>
              </el-table-column>
              <el-table-column :show-overflow-tooltip="true" align="center" label="供应商" prop="supplName"
                               width="100"/>
              <el-table-column align="center" label="进货部门" prop="purDeptName" width="100"/>
              <el-table-column align="center" label="进货人" prop="purNickName" width="100"/>
            </el-table>
          </div>
          <pagination
            v-show="orderTotal>0"
            :limit.sync="[currFormHandler['orderParams']].pageSize"
            :page.sync="[currFormHandler['orderParams']].pageNum"
            :total="orderTotal"
            @pagination="getOrderList"
          />
          <div slot="footer" class="dialog-footer">
            <el-button @click="orderFormVisible = false">取 消</el-button>
            <el-button type="primary" @click="confirmSelectedOrder">确 定</el-button>
          </div>
        </el-dialog>
      </div>
      <div class="group" style="margin-bottom: 100px">
        <el-row>
          <el-col :span="3">
            入库明细
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <div v-if="currFormHandler.canAr" style="margin: 0 0 20px 10px;">
          <el-button type="primary" @click="handlerAddInboundDetails">添加</el-button>
          <el-button @click="removeItems(odIds)">移除</el-button>
        </div>
        <template v-if="inboundInf.inType === inboundType.TRANSFER_RECEIPT">
          <el-table :key="inboundType.TRANSFER_OUTBOUND" :cell-style="{ padding:'0px'}"
                    :data="inboundInf.inboundDetails"
                    :row-style="{ height:'70px' }"
                    @selection-change="handleSelectionGoodsChange">
            <el-table-column align="center" fixed type="selection" width="50"/>
            <el-table-column align="center" fixed label="序号" type="index" width="60"/>
            <el-table-column align="center" fixed label="货品名称" prop="orderDetails.goods.gname" width="80"/>
            <el-table-column align="center" label="货品类型" prop="orderDetails.goods.gtName" width="100"/>
            <el-table-column align="center" fixed label="货品编号" prop="orderDetails.goods.gcode" width="110"/>
            <el-table-column align="center" label="规格型号" prop="orderDetails.goods.specCode" width="100"/>
            <el-table-column align="center" label="单位" prop="orderDetails.stock.unit" width="100">
              <template slot-scope="scope">
                <dict-tag :options="dict.type.g_unit_goods" :value="scope.row.orderDetails.stock.unit"/>
              </template>
            </el-table-column>
            <el-table-column :formatter="cumulativeStocks" align="center" label="当前库存"
                             prop="stockQuantity" width="100"/>
            <el-table-column align="center" class-name="editable-column" label="入库库位" prop="slid" width="140">
              <template slot-scope="scope">
                <el-form-item :ref="`inboundDetails.${scope.$index}.slid`"
                              :prop="`inboundDetails.${scope.$index}.slid`"
                              :rules="rules.slid"
                              label-width="86px">
                  <el-select
                    v-model="scope.row.orderDetails.stock.slid"
                    :disabled="currFormHandler.disableFields.has('slid')"
                    placeholder="请选择库位"
                    style="width: 120px"
                  >
                    <el-option
                      v-for="sl in slList"
                      :key="sl.slId"
                      :label="sl.slName"
                      :value="sl.slId"
                    />
                    <el-option
                      v-if="!slList || slList.length === 0"
                      key="0"
                      disabled
                      label="请选择仓库"
                      value="0"
                    />
                  </el-select>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column align="center" label="调拨数量" prop="orderDetails.quantity" width="120"/>
            <el-table-column align="center" label="已入库数量" prop="orderDetails.receivedQuantity" width="120">
              <template slot-scope="scope">
                <span>{{ scope.row.orderDetails.receivedQuantity || 0 }}</span>
              </template>
            </el-table-column>
            <el-table-column :formatter="handlerQuantityShipped" align="center"
                             label="未入库数量" prop="orderDetails.unreceivedQuantity" width="120"/>
            <el-table-column align="center" class-name="editable-column" label="本次入库数量" prop="quantityInbound"
                             width="140">
              <template slot-scope="scope">
                <el-form-item :prop="`inboundDetails.${scope.$index}.quantityInbound`"
                              :rules="getQuantityInboundRules(scope.row)"
                >
                  <el-input v-model="scope.row.quantityInbound" placeholder="本次入库数量"></el-input>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column align="center" label="进货单价" prop="orderDetails.goods.wrPrice"/>
            <el-table-column align="center" class-name="editable-column" label="批次号" prop="batchNumber" width="150">
              <template slot-scope="scope">
                <el-form-item :prop="`inboundDetails.${scope.$index}.batchNumber`"
                              :rules="rules.batchNumber"
                >
                  <el-input v-model="scope.row.batchNumber" placeholder="请输入本次入库批次号"></el-input>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column :formatter="row=>(row.orderDetails.goods.wrPrice || 0) * (row.quantityInbound || 0)"
                             align="center"
                             label="本次入库金额"
                             prop="amountOrder"
                             width="120"></el-table-column>
            <el-table-column align="center" label="备注" prop="remark" width="200">
              <template slot-scope="scope">
                <el-form-item
                  :prop="`inboundDetails.${scope.$index}.remark`"
                >
                  <el-input v-model="scope.row.remark" placeholder="请输入备注"></el-input>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column align="center" class-name="small-padding fixed-width" fixed="right" label="操作"
                             width="90">
              <template v-if="currFormHandler.canAr" slot-scope="scope">
                <el-button
                  icon="el-icon-delete"
                  size="mini"
                  type="text"
                  @click="removeItems([scope.row.orderDetails[fieldName.onlyFieldName]])"
                >移除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </template>
        <template v-if="inboundInf.inType === inboundType.INVENTORY_PLUS">
          <el-table :key="inboundType.INVENTORY_PLUS" :cell-style="{ padding:'0px'}"
                    :data="inboundInf.inboundDetails"
                    :row-style="{ height:'70px' }"
                    @selection-change="handleSelectionGoodsChange">
            <el-table-column align="center" fixed type="selection" width="50"/>
            <el-table-column align="center" fixed label="序号" type="index" width="60"/>
            <el-table-column align="center" fixed label="货品名称" prop="orderDetails.goods.gname" width="80"/>
            <el-table-column align="center" label="货品类型" prop="orderDetails.goods.gtName" width="100"/>
            <el-table-column align="center" fixed label="货品编号" prop="orderDetails.goods.gcode" width="110"/>
            <el-table-column align="center" label="规格型号" prop="orderDetails.goods.specCode" width="100"/>
            <el-table-column align="center" label="单位" prop="orderDetails.stock.unit" width="100">
              <template slot-scope="scope">
                <dict-tag :options="dict.type.g_unit_goods" :value="scope.row.orderDetails.stock.unit"/>
              </template>
            </el-table-column>
            <el-table-column :formatter="cumulativeStocks" align="center" label="当前库存"
                             prop="stockQuantity" width="100"/>
            <el-table-column align="center" class-name="editable-column" label="入库库位" prop="slid" width="140">
              <template slot-scope="scope">
                <el-form-item :ref="`inboundDetails.${scope.$index}.slid`"
                              :prop="`inboundDetails.${scope.$index}.slid`"
                              :rules="rules.slid"
                              label-width="86px">
                  <el-select
                    v-model="scope.row.orderDetails.stock.slid"
                    :disabled="currFormHandler.disableFields.has('slid')"
                    placeholder="请选择库位"
                    style="width: 120px"
                  >
                    <el-option
                      v-for="sl in slList"
                      :key="sl.slId"
                      :label="sl.slName"
                      :value="sl.slId"
                    />
                    <el-option
                      v-if="!slList || slList.length === 0"
                      key="0"
                      disabled
                      label="请选择仓库"
                      value="0"
                    />
                  </el-select>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column
              :formatter="(row)=>row.orderDetails.profitLossQuantity < 0 ? 0 : row.orderDetails.profitLossQuantity"
              align="center" label="盘盈数量" prop="orderDetails.profitLossQuantity" width="120"/>
            <el-table-column align="center" label="已入库数量" prop="orderDetails.receivedQuantity" width="120">
              <template slot-scope="scope">
                <span>{{ scope.row.orderDetails.quantityShipped || 0 }}</span>
              </template>
            </el-table-column>
            <el-table-column :formatter="formatUndeliveredQuantity" align="center"
                             label="未入库数量" prop="orderDetails.unreceivedQuantity" width="120"/>
            <el-table-column align="center" class-name="editable-column" label="本次入库数量" prop="inboundQuantity"
                             width="140">
              <template slot-scope="scope">
                <el-form-item :prop="`inboundDetails.${scope.$index}.quantityInbound`"
                              :rules="getQuantityInboundRules(scope.row)"
                >
                  <el-input v-model="scope.row.quantityInbound" placeholder="本次入库数量"></el-input>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column align="center" label="入库单价" prop="orderDetails.goods.wrPrice"/>
            <el-table-column :formatter="row=>(row.orderDetails.goods.wrPrice || 0) * (row.quantityInbound || 0)"
                             align="center"
                             label="本次入库金额"
                             prop="amountOrder"
                             width="120"></el-table-column>
            <el-table-column align="center" class-name="editable-column" label="批次号" prop="batchNumber" width="150">
              <template slot-scope="scope">
                <el-form-item :prop="`inboundDetails.${scope.$index}.batchNumber`"
                              :rules="rules.batchNumber"
                >
                  <el-input v-model="scope.row.batchNumber" placeholder="请输入本次入库批次号"></el-input>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column align="center" label="备注" prop="remark" width="200">
              <template slot-scope="scope">
                <el-form-item
                  :prop="`inboundDetails.${scope.$index}.remark`"
                >
                  <el-input v-model="scope.row.remark" placeholder="请输入备注"></el-input>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column align="center" class-name="small-padding fixed-width" fixed="right" label="操作"
                             width="90">
              <template v-if="currFormHandler.canAr" slot-scope="scope">
                <el-button
                  icon="el-icon-delete"
                  size="mini"
                  type="text"
                  @click="removeItems([scope.row.orderDetails[fieldName.onlyFieldName]])"
                >移除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </template>
        <template v-if="inboundInf.inType === inboundType.PURCHASING_WAREHOUSING">
          <el-table :key="inboundType.PURCHASING_WAREHOUSING" :cell-style="{ padding:'0px'}"
                    :data="inboundInf.inboundDetails"
                    :row-style="{ height:'70px' }"
                    @selection-change="handleSelectionGoodsChange">
            <el-table-column align="center" fixed type="selection" width="50"/>
            <el-table-column align="center" fixed label="序号" type="index" width="60"/>
            <el-table-column align="center" fixed label="货品名称" prop="orderDetails.goods.gname"
                             width="80"/>
            <el-table-column align="center" label="货品类型" prop="orderDetails.goods.gtName" width="100"/>
            <el-table-column align="center" fixed label="货品编号" prop="orderDetails.goods.gcode"
                             width="110"/>
            <el-table-column align="center" label="规格型号" prop="orderDetails.goods.specCode" width="100"/>
            <el-table-column align="center" label="单位" prop="orderDetails.unit" width="100">
              <template slot-scope="scope">
                <dict-tag :options="dict.type.g_unit_goods" :value="scope.row.orderDetails.unit"/>
              </template>
            </el-table-column>
            <!--            <el-table-column :formatter="cumulativeStocks" align="center" label="当前库存" prop="item_quantity" width="100"/>-->
            <el-table-column :formatter="handlerPurchaseQuantity" align="center" label="进货数量"
                             prop="purchaseQuantity"
                             width="120"/>
            <el-table-column align="center" label="已入库数量" prop="orderDetails.receivedQuantity"
                             width="120"/>
            <el-table-column align="center" label="未入库数量" prop="unreceivedQuantity" width="120"/>
            <el-table-column align="center" class-name="editable-column" label="入库库位" prop="slid"
                             width="140">
              <template slot-scope="scope">
                <el-form-item :ref="`inboundDetails.${scope.$index}.slid`"
                              :prop="`inboundDetails.${scope.$index}.slid`"
                              :rules="rules.slid" label-width="86px">
                  <el-select
                    v-model="scope.row.slid"
                    placeholder="请选择库位"
                    style="width: 120px"
                  >
                    <el-option
                      v-for="sl in slList"
                      :key="sl.slId"
                      :label="sl.slName"
                      :value="sl.slId"
                    />
                    <el-option
                      v-if="!slList || slList.length === 0"
                      key="0"
                      disabled
                      label="请选择仓库"
                      value="0"
                    />
                  </el-select>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column align="center" class-name="editable-column" label="本次入库数量"
                             prop="quantityInbound"
                             width="140">
              <template slot-scope="scope">
                <el-form-item :prop="`inboundDetails.${scope.$index}.quantityInbound`"
                              :rules="getQuantityInboundRules(scope.row)"
                >
                  <el-input v-model="scope.row.quantityInbound" placeholder="本次入库数量"></el-input>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column align="center" label="进货单价" prop="orderDetails.puPrice"/>
            <el-table-column align="center" class-name="editable-column" label="批次号" prop="batchNumber"
                             width="150">
              <template slot-scope="scope">
                <el-form-item :prop="`inboundDetails.${scope.$index}.batchNumber`"
                              :rules="rules.batchNumber"
                >
                  <el-input v-model="scope.row.batchNumber" placeholder="请输入本次入库批次号"></el-input>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column :formatter="row=>row.orderDetails.puPrice * (row.quantityInbound || 0)"
                             align="center"
                             label="入库金额"
                             width="120"/>
            <el-table-column align="center" class-name="editable-column" label="生产日期"
                             prop="productionDate"
                             width="200">
              <template slot-scope="scope">
                <el-form-item :prop="`inboundDetails.${scope.$index}.productionDate`"
                              :rules="rules.productionDate"
                              required>
                  <el-date-picker
                    v-model="scope.row.productionDate"
                    :picker-options="{ disabledDate(date) {return date && date > new Date();} }"
                    placeholder="选择日期"
                    style="width: 160px;"
                    type="date">
                  </el-date-picker>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column align="center" label="备注" prop="remark" width="200">
              <template slot-scope="scope">
                <el-form-item
                  :prop="`inboundDetails.${scope.$index}.remark`"
                >
                  <el-input v-model="scope.row.remark" placeholder="请输入备注"></el-input>
                </el-form-item>
              </template>
            </el-table-column>
            <el-table-column align="center" class-name="small-padding fixed-width" fixed="right" label="操作"
                             width="90">
              <template v-if="currFormHandler.canAr" slot-scope="scope">
                <el-button
                  icon="el-icon-delete"
                  size="mini"
                  type="text"
                  @click="removeItems([scope.row[fieldName]])"
                >移除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </template>
      </div>
      <div class="footer-btn">
        <el-button type="primary" @click="submitForm('inboundInf')">提交</el-button>
        <el-button @click="cancel()">取消</el-button>
        <el-button v-if="isEditor" @click="rest()">重置</el-button>
      </div>
    </el-form>
    <!-- 入库明细选择 -->
    <el-dialog :visible.sync="dialogDetailsVisible" title="添加入库货品" width="1000px">
      <!-- 调拨入库 -->
      <div>
        <div style="display:flex;margin-bottom:20px;align-items:center;justify-content:space-between;">
          <span>{{ currFormHandler.label }}单号：{{ inboundInf.selectedOrder.orderNo }}</span>
          <span>只展示可入库数据</span>
        </div>
        <el-form ref="inboundDetailsQForm" :inline="true" :model="inboundDetailsQParams" size="small">
          <el-form-item label-width="86px" prop="unit">
            <el-select
              v-model="inboundDetailsQParams.unit"
              clearable
              placeholder="请选择单位"
              style="width: 120px"
            >
              <el-option
                v-for="dict in dict.type.g_unit_goods"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label-width="86px" prop="codeOrName">
            <el-input
              v-model.trim="inboundDetailsQParams.codeOrName"
              clearable
              placeholder="请输入货品编号/名称"
              style="width: 200px"
              @keyup.enter.native="goSearchDetails"
            />
          </el-form-item>
          <el-form-item>
            <el-button icon="el-icon-search" size="mini" type="primary" @click="goSearchDetails()">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini"
                       @click="goResetSearchDetails">
              重置
            </el-button>
          </el-form-item>
        </el-form>
        <el-table v-if="inboundInf.inType === inboundType.TRANSFER_RECEIPT" ref="detailsTable"
                  v-loading="detailsTableLoading" :data="orderDetails"
                  row-key="g_id" @selection-change="handlerSelectionChange">
          <el-table-column :reserve-selection="true" align="center" fixed type="selection" width="50"/>
          <el-table-column align="center" fixed label="序号" type="index" width="60"/>
          <el-table-column align="center" fixed label="货品编号" prop="orderDetails.goods.gcode" width="80"/>
          <el-table-column align="center" fixed label="货品名称" prop="orderDetails.goods.gname" width="110"/>
          <el-table-column align="center" label="规格型号" prop="orderDetails.goods.specCode" width="100"/>
          <el-table-column align="center" label="单位" width="60">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.g_unit_goods" :value="scope.row.orderDetails.stock.unit"/>
            </template>
          </el-table-column>
          <el-table-column align="center" label="入库单价（元）" prop="orderDetails.goods.wrPrice" width="100"/>
          <el-table-column align="center" label="调拨数量" prop="orderDetails.quantity" width="100"/>
          <el-table-column :formatter="(row)=>(row.quantity || 0) * row.orderDetails.goods.wrPrice" align="center" label="调拨金额"
                           prop="money" width="100"/>
          <el-table-column align="center" label="已出库" prop="orderDetails.quantityShipped" width="100"/>
          <el-table-column align="center" label="已入库" prop="orderDetails.receivedQuantity" width="100"/>
        </el-table>
        <el-table v-if="inboundInf.inType === inboundType.INVENTORY_PLUS" ref="detailsTable"
                  v-loading="detailsTableLoading" :data="orderDetails"
                  row-key="g_id" @selection-change="handlerSelectionChange">
          <el-table-column :reserve-selection="true" align="center" fixed type="selection" width="50"/>
          <el-table-column align="center" fixed label="序号" type="index" width="60"/>
          <el-table-column align="center" fixed label="货品编号" prop="orderDetails.goods.gcode" width="80"/>
          <el-table-column align="center" fixed label="货品名称" prop="orderDetails.goods.gname" width="110"/>
          <el-table-column align="center" label="规格型号" prop="orderDetails.goods.specCode" width="100"/>
          <el-table-column align="center" label="单位" width="60">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.g_unit_goods" :value="scope.row.orderDetails.stock.unit"/>
            </template>
          </el-table-column>
          <el-table-column align="center" label="入库单价（元）" prop="orderDetails.goods.wrPrice" width="100"/>
          <el-table-column align="center" label="盘点数量" prop="orderDetails.countQuantity" width="100"/>
          <el-table-column
            :formatter="(row)=>(row.orderDetails.profitLossQuantity = Math.abs(row.orderDetails.profitLossQuantity) || 0,row.orderDetails.profitLossQuantity)"
            align="center" label="盘亏数量" prop="orderDetails.profitLossQuantity" width="100"/>
          <el-table-column
            :formatter="(row)=>(row.orderDetails.profitLossQuantity || 0) * row.orderDetails.goods.wrPrice"
            align="center"
            label="盘亏金额"
            prop="money" width="100"/>
          <el-table-column align="center" label="已入库" prop="orderDetails.quantityShipped" width="100"/>
          <el-table-column align="center" label="已入库" prop="orderDetails.receivedQuantity" width="100"/>
        </el-table>
        <el-table v-if="inboundInf.inType === inboundType.PURCHASING_WAREHOUSING" ref="detailsTable"
                  v-loading="detailsTableLoading" :data="orderDetails"
                  row-key="g_id" @selection-change="handlerSelectionChange">
          <el-table-column :reserve-selection="true" align="center" fixed type="selection" width="50"/>
          <el-table-column align="center" fixed label="序号" type="index" width="60"/>
          <el-table-column align="center" fixed label="货品编号" prop="orderDetails.goods.gcode" width="80"/>
          <el-table-column align="center" fixed label="货品名称" prop="orderDetails.goods.gname" width="110"/>
          <el-table-column align="center" label="规格型号" prop="orderDetails.specCode" width="100"/>
          <el-table-column align="center" label="单位" width="60">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.g_unit_goods" :value="scope.row.orderDetails.unit"/>
            </template>
          </el-table-column>
          <el-table-column align="center" label="进货单价（元）" prop="orderDetails.puPrice" width="100"/>
          <el-table-column :formatter="(row)=>(row.orderDetails.purchaseQuantity)" align="center" label="进货数量"
                           prop="purchaseQuantity" width="100"/>
          <el-table-column :formatter="(row)=>(row.orderDetails.purchaseQuantity || 0) * row.orderDetails.puPrice"
                           align="center"
                           label="进货金额"
                           prop="money" width="100"/>
          <el-table-column align="center" label="已入库" prop="orderDetails.receivedQuantity" width="100"/>
          <el-table-column
            :formatter="(row)=>(row.orderDetails.purchaseQuantity || 0) - row.orderDetails.receivedQuantity"
            align="center"
            label="未入库" prop="unreceivedQuantity" width="100"/>
        </el-table>
      </div>
      <pagination
        v-show="orderDetailsTotal>0"
        :limit.sync="inboundDetailsQParams.pageSize"
        :page.sync="inboundDetailsQParams.pageNum"
        :total="orderDetailsTotal"
        @pagination="goSearchDetails"
      />
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="goInboundDetailsDefine">确定</el-button>
        <el-button @click="closeGoodsSelect">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import Treeselect from "@riophae/vue-treeselect";
import {deptTreeSelect, listUser} from "@/api/system/user";
import {getToken} from "@/utils/auth";
import {getPurchaseDetails, getPurchaseInf, listPurchase} from "@/api/wms/order/purchase";
import {deepClone} from "@/utils";
import SelectorUser from "@/components/wms/user/SelectorUser.vue";
import {listsWarehouse} from "@/api/wms/standingbook/Inventoryquery";
import {listStorageByWid} from "@/api/wms/warehouse/warehouse";
import qs from "qs";
import {getSupplier, listSupplier} from "@/api/wms/contactUnits/supplier";
import {listGoodsStocks} from "@/api/wms/good/goodsinfo";
import {formStatus, InboundType} from "@/constant/inboundType";
import {listTransferByInOut, transferDetailsByInOut} from "@/api/wms/warehouse/transfer/transfer";
import {getInventoryDetails, listInventory} from "@/api/wms/warehouse/InventorySheet/inventory";

export default {
  name: "InboundFrom",
  dicts: ['outbound_status', 'p_result_check', 'inbound_status', 'inbound_status', 'pd_type_check', 'transfer_out_status', 'transfer_in_status', 'incoming_type', 'inbound_status', 'inbound_status', 'g_unit_goods', 'ord_doc_status'],
  components: {
    Treeselect,
    SelectorUser
  },
  data() {
    let inboundType = InboundType;
    return {
      // 入库库存类型，返回导入的入库类型
      inboundType,
      // 前入库表单不显示字段
      // 选择入库明细的唯一字段名称, 默认: 货品ID (gid)
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
        whName: '',
      },
      // 库位列表
      slList: [],
      // 库位下拉框 key
      selectSLKey: 1,
      // 仓库列表
      whList: [],
      orderTotal: 0,
      // 选择订单，弹出订单选择
      orderFormVisible: false,
      selectedOrderTemp: {},
      // 保存选中的入库明细的唯一字段的数据 【this.fieldName.onlyFieldName】
      selectedDetailTemp: [],
      orderTableLoading: false,
      // 进货单据
      orderListData: [],
      detailsTableLoading: false,
      // 当前进货明细 - 元数据
      orderDetails: [],
      // 选择的当前订单明细 id
      odIds: [],
      orderDetailsTotal: 0,
      inboundDetailsQParams: {
        pageNum: 1,
        pageSize: 5,
        codeOrName: undefined,
        unit: undefined
      },
      purchaseOrderParams: {
        pageNum: 1,
        pageSize: 5,
        purOrderSn: "",
        orderStatus: undefined,
        suppName: undefined,
        dateRange: [],
        status: ['partial_storage', 'not_in_stock']
      },
      transferOrderQueryParams: {
        transferOrder: undefined,
        outStatus: undefined,
        inStatus: undefined,
        pageNum: 1,
        pageSize: 5,
        dateRange: [],
      },
      inventoryOrderParams: {
        pageNum: 1,
        pageSize: 5,
        is_code: null,
        is_result: null,
        w_id: null,
        is_type: null,
        is_start_time: null,
        is_end_time: null,
        out_status: null,
        in_status: null,
        is_manager: null,
      },
      selectorUserVisible: false,
      deptTree: [],
      baseApi: process.env.VUE_APP_BASE_API,
      rules: {
        storageDate: [
          {required: true, message: '请选择入库日期', trigger: 'change'},
        ],
        purchaseQuantity: [
          {required: true, message: '请选输入进货数量', trigger: 'blur'},
          {pattern: /^[1-9]\d*$/, message: '请输入正确的数量', trigger: 'blur'}
        ],
        wid: [
          {required: true, message: '请选择入库仓库', trigger: ["blur", 'change']},
        ],
        inType: [
          {required: true, message: '请选择入库类型', trigger: ["blur", 'change']},
        ],
        sid: [
          {required: true, message: '请选择入库类型', trigger: ["blur", 'change']},
        ],
        manager: [
          {required: true, message: '请选择经办人', trigger: ["blur", 'change']},
        ],
        slid: [
          {required: true, message: '请选择入库库位', trigger: ["blur", 'change']},
        ],
        batchNumber: [
          {required: true, message: '请输入批次号', trigger: 'blur'}
        ],
        "selectedOrder.orderId": [
          {required: true, message: '请选择订单', trigger: 'change'},
        ],
        productionDate: [
          {required: true, message: '请选择生产日期', trigger: 'change'},
        ]
      },
      // 部门树选项
      deptOptions: undefined,
      loading: false,
      fileList: [],
      dialogDetailsVisible: false,
      inboundInf: {
        wid: undefined,
        // 供应商 ID
        sid: undefined,
        // 客户 ID
        cid: undefined,
        manager: undefined,
        storageDate: undefined,
        relatedOrder: undefined,
        remark: undefined,
        inboundDetails: [],
        selectedOrder: {},
        // 默认为销售入库
        inType: inboundType.PURCHASING_WAREHOUSING,
        associated: true,
      },
      // orderList: [],
      userList: [],
      supplierList: [],
      consumerList: [],
      supplName: undefined,
      // 供应商或客户的联系方式和联系人
      contact: {
        contactPerson: undefined,
        contactNumber: undefined
      },
      // 返回当前入库的类型的不同逻辑
      formHandlerMap: new Map([
        [inboundType.PURCHASING_WAREHOUSING, {
          getOrderList: this.getPurchaseOrderList,
          getDetailsList: this.getPurchaseDetailsList,
          orderParams: 'purchaseOrderParams',
        }],
        [inboundType.RECEIPT_RETURNS, {}],
        [
          InboundType.INVENTORY_PLUS,
          {
            getContact: this.getSupplier,
            getOrderList: this.getInventoryList,
            getDetailsList: this.getInventoryDetails,
            orderParams: 'inventoryOrderParams'
          }
        ],
        [
          InboundType.TRANSFER_RECEIPT,
          {
            getOrderList: this.getTransferList,
            getDetailsList: this.getTransferDetails,
            orderParams: 'transferOrderQueryParams'
          }
        ]
      ]),
      transferOutStatus: [
        {
          label: '部分出库',
          value: 'partial_inbound',
        },
        {
          label: '未出库',
          value: 'not_shipped_put'
        }
      ],
      transferInStatus: [
        {
          label: '部分入库',
          value: 'partial_warehousing',
        },
        {
          label: '未入库',
          value: 'not_in_stock'
        }
      ]
    }
  },
  props: {
    inboundData: {
      type: Object,
      default: undefined
    },
    isEditor: {
      type: Boolean,
      default: false
    }
  },
  async created() {
    // 获取仓库列表
    await this.getWarehouse();
    // 获取用户列表
    await this.getUser();
    // 编辑
    this.supplierList = (await listSupplier()).rows;
    if (this.isEditor)
      this.initProps();
  },
  mounted() {
  },
  computed: {
    token() {
      return {
        Authorization: `Bearer ${getToken()}`
      }
    },
    currFormHandler() {
      let runnable = true;
      if (!this.inboundInf.inType) {
        runnable = false;
        this.$message("请选择入库类型");
        console.error("未找到当前入库类型, inType: ", this.inboundInf.inType);
        return;
      }
      this.inboundInf.associated = true;
      let currHandler = this.formHandlerMap.get(this.inboundInf.inType);
      let form = formStatus.get(this.inboundInf.inType);
      let fs = {runnable, ...form, ...currHandler};
      this.fieldName = form.fieldName;
      console.log("当前表单: ", fs)
      return fs;
    }
  },
  methods: {
    // 执行表单当前逻辑
    callFormHandler(funcName, ...params) {
      if (!this.currFormHandler.runnable) return;
      let func = this.currFormHandler[funcName];
      if (func) {
        return func(...params);
      } else {
        console.error("未找到当前入库类型对应的表单逻辑, funcName: ", funcName)
      }
    },
    // 判断两个入库明细对象是否 算是同一个
    // isObjectEqual(obj1, obj2) {
    //   return obj1.orderDetails[this.fieldName.onlyFieldName] === obj2.orderDetails[this.fieldName.onlyFieldName];
    // },
    // 返回本次入库数量的校验规则
    getQuantityInboundRules(row) {
      return [
        {
          validator: (rule, value, callback) => {
            if (!rule || !/^[1-9]\d*$/.test(value)) {
              callback(new Error('请输入正确的数量'));
            }
            if (this.inboundInf.inType === this.inboundType.PURCHASING_WAREHOUSING) {
              if (value > ((row?.orderDetails.purchaseQuantity || 0) - (row?.orderDetails.receivedQuantity || 0))) {
                callback(new Error('本次入库数量不可大于未入库数量'));
              } else {
                callback();
              }
            } else if (this.inboundInf.inType === this.inboundType.INVENTORY_PLUS) {
              if (value > ((row.orderDetails?.profitLossQuantity || 0) - (row?.orderDetails.receivedQuantity || 0))) {
                callback(new Error('本次入库数量不可大于未入库数量'));
              } else {
                callback();
              }
            } else if(this.inboundInf.inType === this.inboundType.TRANSFER_RECEIPT){
              if (value > ((row.orderDetails?.quantity || 0) - (row?.orderDetails.receivedQuantity || 0))) {
                callback(new Error('本次入库数量不可大于未入库数量'));
              } else {
                callback();
              }
            }
          }, trigger: 'blur'
        }
      ];
    },
    handlerSelectOrder() {
      if (this.isEditor || !this.inboundInf.associated) {
        return;
      }
      if (!this.inboundInf.inType) {
        this.$refs["inboundInf"].validateField('inType');
        this.$message.warning("请选择入库类型！");
        return;
      }
      this.getOrderList();
      this.orderFormVisible = true;
    },
    async handlerSelectManager() {
      let {code, data} = (await deptTreeSelect());
      if (code === 200) {
        this.deptTree = data;
        this.selectorUserVisible = true;
      } else {
        this.$message.error("部门数据请求失败")
      }
    },
    // 获取订单列表
    async getOrderList() {
      this.orderTableLoading = true;
      await this.callFormHandler("getOrderList")
      this.orderTableLoading = false;
    },
    // 获取调拨订单列表
    async getTransferList() {
      let params = JSON.parse(JSON.stringify(this.transferOrderQueryParams));
      if (params.dateRange && params.dateRange.length) {
        params.beginTime = params.dateRange[0];
        params.endTime = params.dateRange[1];
      }
      params.iStatus = 'inbound';
      console.log("params", params)
      delete params.dateRange;
      ({
        rows: this.orderListData,
        total: this.orderTotal
      } = (await listTransferByInOut(qs.stringify(params, {arrayFormat: 'repeat'}))));
    },
    // 盘点订单查询
    async getInventoryList() {
      let params = JSON.parse(JSON.stringify({...this.inventoryOrderParams, inOutbound: true}));
      let {total, rows} = (await listInventory(params));
      this.orderListData = rows;
      this.orderTotal = total;
    },
    // 获取入库明细
    async goSearchDetails() {
      this.detailsTableLoading = true;
      await this.callFormHandler("getDetailsList");
      console.log("this.orderDetails", this.orderDetails)
      this.orderDetails = this.orderDetails?.map(e => {
        return {
          slid: e?.stock?.slid,
          orderDetails: e
        };
      })
      this.detailsTableLoading = false;
      // this.orderDetailsF = this.orderDetailsO?.filter(e => {
      //   let params = this.inboundDetailsQParams;
      //   return (e.goods.gname.includes(params.codeOrName) ||
      //     e.unit === params.unit ||
      //     e.goods.gcode.includes(params.codeOrName));
      // })
    },
    // 获取调拨明细
    async getTransferDetails() {
      let {total, rows} = (await transferDetailsByInOut({
        iStatus: "inbound",
        tid: this.inboundInf.selectedOrder.orderId, ...this.inboundDetailsQParams
      }));
      this.orderDetails = rows;
      this.orderDetailsTotal = total;
    },
    // 盘点明细查询
    async getInventoryDetails() {
      let {data} = (await getInventoryDetails({
        iStatus: 'inbound',
        [this.fieldName.orderFieldName]: this.inboundInf.selectedOrder.orderId, ...this.inboundDetailsQParams
      }));
      this.orderDetailsTotal = 0;
      this.orderDetails = data;
    },
    // 进货明细查询
    async getPurchaseDetailsList() {
      let data;
      if (this.inboundInf.associated) {
        ({data} = (await getPurchaseDetails(this.inboundInf.selectedOrder.orderId, this.inboundDetailsQParams)));
      } else {
        let {total, rows} = (await listGoodsStocks(this.inboundDetailsQParams));
        this.orderDetailsTotal = total;
        data = rows;
      }
      // data?.forEach(pd => pd.odid = pd.pdId);
      this.orderDetails = data;
    },
    // 获取进货订单列表
    async getPurchaseOrderList() {
      let params = JSON.parse(JSON.stringify({...this.purchaseOrderParams, inOutbound: true}));
      if (params.dateRange && params.dateRange.length) {
        params.beginTime = params.dateRange[0];
        params.endTime = params.dateRange[1];
      }
      delete params.dateRange;
      ({
        rows: this.orderListData,
        total: this.total
      } = (await listPurchase(params)));
    },
    async getSupplier(supplierId) {
      let p = (await getSupplier(supplierId));
      if (p.code === 200) {
        this.contact.contactPerson = p.data.contactPerson;
        this.contact.contactNumber = p.data.contactNumber;
        this.inboundInf.sid = p.data.sId;
      }
    },
    // 双击订单进行确定选择的订单
    handlerRowDblclick(row, column, event) {
      this.selectedOrderTemp = {
        ...row,
        orderId: row[this.fieldName.orderFieldName],
        orderNo: row[this.fieldName.onFieldName],
        [this.fieldName.contactFieldName]: row[this.fieldName.contactFieldName]
      }
      this.confirmSelectedOrder();
    },
    singleElectionTable(row, column, cell, event) {
      this.selectedOrderTemp = {
        ...row,
        orderId: row[this.fieldName.orderFieldName],
        orderNo: row[this.fieldName.onFieldName],
        [this.fieldName.contactFieldName]: row[this.fieldName.contactFieldName]
      }
    },
    // 确认进货订单
    confirmSelectedOrder() {
      if (!this.selectedOrderTemp?.orderId) {
        this.$message.warning("还未选择订单，请先选择订单！")
        return;
      }
      this.inboundInf.selectedOrder = deepClone(this.selectedOrderTemp)
      this.inboundInf[this.fieldName.contactFieldName] = this.selectedOrderTemp[this.fieldName.contactFieldName];
      this.orderFormVisible = false;
    },
    closeSelectedOrder() {
      this.orderFormVisible = false;
    },
    /** 搜索订单按钮操作 */
    handleOrderQuery() {
      this[this.currFormHandler.orderParams].pageNum = 1;
      this.getOrderList();
    },
    /** 重置订单按钮操作 */
    resetOrderQuery() {
      this.resetQuery('queryOrderForm', () => {
        this.dateRange = [];
        this.handleOrderQuery();
      })
    },
    resetQuery(formName, destroy) {
      this.resetForm(formName);
      if (destroy && destroy instanceof Function) {
        destroy();
      }
    },
    resetFormField(formName) {
      let formItem = this.$refs[formName];
      if (formItem) {
        formItem?.resetField();
        formItem?.clearValidate();
      }
    },
    // 处理已选的进货货品已入库数据 - 调拨明细
    handlerQuantityShipped(row) {
      if (!row?.orderDetails?.unreceivedQuantity) {
        row.orderDetails.unreceivedQuantity = row.orderDetails.quantity - row.orderDetails.receivedQuantity
      }
      return row.orderDetails.unreceivedQuantity;
    },
    // 处理已选的进货货品未入库数据 - 盘点明细
    formatUndeliveredQuantity(row) {
      if (!row?.orderDetails?.unreceivedQuantity) {
        row.orderDetails.unreceivedQuantity = row.orderDetails?.profitLossQuantity - row.orderDetails.receivedQuantity
      }
      return row.orderDetails.unreceivedQuantity;
    },
    // 累加库存 StockList
    cumulativeStocks(row) {
      if (this.currFormHandler.hiddenFields.has('associated')) {
        return row?.orderDetails?.stock?.itemQuantity || 0;
      } else
        return (row.orderDetails?.goods?.stockList?.reduce((accumulator, currentValue) => (accumulator + (currentValue?.itemQuantity || 0)), 0) || 0)
    },
    handlerPurchaseQuantity(row) {
      if (!row.orderDetails.purchaseQuantity) {
        row.orderDetails.purchaseQuantity = row.orderDetails.unreceivedQuantity + row.orderDetails.receivedQuantity;
      }
      row.unreceivedQuantity = row.orderDetails.purchaseQuantity - row.orderDetails.receivedQuantity;
      return row.orderDetails.purchaseQuantity;
    },
    // 计算盘点数量 - 盘点
    handlerCountQuantity(row) {
      return row.inventoryDetailsList?.reduce((accumulator, currentValue) => {
        return accumulator + (currentValue?.countQuantity || 0);
      }, 0);
    },
    // 盘盈数量 - 盘点
    handlerProfitQuantity(row) {
      let pq = row.inventoryDetailsList?.reduce((accumulator, currentValue) => {
        let t = currentValue.profitLossQuantity || 0;
        return accumulator + (t > 0 ? t : 0);
      }, 0);
      row.pq = pq;
      return pq;
    },
    // 盘亏数量 - 盘点
    handlerLossQuantity(row) {
      let lq = row.inventoryDetailsList?.reduce((accumulator, currentValue) => {
        let t = currentValue.profitLossQuantity || 0;
        return accumulator + (t < 0 ? t : 0);
      }, 0);
      row.lq = lq;
      return lq;
    },
    // 盘点总金额 - 盘点
    handlerCountAmount(row) {
      return row.inventoryDetailsList?.reduce((accumulator, currentValue) => accumulator + (currentValue.countAmount || 0), 0).toFixed(2);
    },
    //盘盈金额 - 盘点
    handlerProfitAmount(row) {
      return row.inventoryDetailsList?.reduce((accumulator, currentValue) => (currentValue.goods.wrPrice || 0) * row.pq, 0).toFixed(2);
    },
    //盘亏金额 - 盘点
    handlerLossAmount(row) {
      return row.inventoryDetailsList?.reduce((accumulator, currentValue) =>
        accumulator + (currentValue.goods.wrPrice || 0) * row.lq, 0).toFixed(2);
    },
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data;
      });
    },
    initProps() {
      // this.inboundInf = this.inboundData;
      this.inboundInf = Object.assign({}, this.inboundInf, deepClone(this.inboundData));
      // 获取库位信息
      this.getSlList(this.inboundInf.wid);
      console.log("inboundInf: ", this.inboundInf)
    },
    async getUser() {
      this.userList = (await listUser()).rows;
    },
    // 获取仓库列表，下拉框
    async getWarehouse() {
      this.whList = (await listsWarehouse())
      this.selectSLKey = this.selectSLKey + 1;
    },
    // 获取库位
    async getSlList(wid) {
      if (wid) {
        this.slList = (await listStorageByWid(wid)).data
      }
    },
    async getUserList() {
      if (this.inboundInf.purDeptId) {
        this.userList = (await listUser({deptId: this.inboundInf?.purDeptId})).rows
        this.inboundInf.purchaser = undefined;
      }
      console.log("userList: ", this.userList)
    },
    handlerWhChange(row) {
      this.getSlList(row);
      // this.inboundInf?.inboundDetails?.forEach(e => e.slid = null);
      for (let i = 0; i < this.inboundInf?.inboundDetails?.length || 0; i++) {
        // this.inboundInf.inboundDetails[i].slid = null;
        this.$set(this.inboundInf.inboundDetails[i], "slid", null)
        this.resetFormField(`inboundDetails.${i}.slid`)
        // let fi = this.$refs[`inboundDetails.${i}.slid`];
        // fi.resetField();
        // this.$nextTick(()=>fi.clearValidate());
      }
      // this.$refs['inboundInf'].clearValidate();
    },
    // 处理关联订单的 Switch 开关变化
    handlerAssociatedChange() {
      this.resetSupplierInf()
      this.resetOrderInf()
    },
    resetSupplierInf() {
      this.resetFormField("supplier")
      this.contact.contactNumber = undefined;
      this.contact.contactPerson = undefined;
      // console.log("this.contact: ", this.contact)
    },
    resetOrderInf() {
      this.resetFormField("selectedOrder.orderId")
      this.inboundInf.selectedOrder.orderNo = undefined;
      this.inboundInf.inboundDetails = [];
      // this.$.set(this.inboundInf, "inboundDetails", [])
    },
    // 处理供应商选择改变时
    handlerSupplierChange(sid) {
      let ct = this.supplierList.filter(e => e.sId === sid)?.[0];
      if (ct)
        this.contact = ct
    },
    // 处理客户选择改变时
    handlerConsumerChange() {

    },
    handlerChangeInType() {
      this.handlerAssociatedChange();
      if (!this.isEditor) {
        [...this.currFormHandler.hiddenFields, ...this.currFormHandler.disableFields].forEach(e => this.resetFormField(e))
      }
      // if(this.currFormHandler.hiddenFields.has("contact") || this.currFormHandler.disableFields.has("contact")){
      //   this.resetFormField("contactNumber");
      //   this.resetFormField("contactPerson");
      // }
    },
    closeGoodsSelect() {
      this.dialogDetailsVisible = false;
      this.selectedDetailTemp = [];
      this.inboundInf.inboundDetails.forEach(e => this.selectedDetailTemp.push(e.orderDetails[this.fieldName.onlyFieldName]));
    },
    goInboundDetailsDefine() {
      console.log("selectedDetailTemp: ", this.selectedDetailTemp)
      if (!this.selectedDetailTemp || !this.selectedDetailTemp.length) {
        this.$message.error("请选择入库的货品!");
        return;
      }
      if (this.inboundInf?.inboundDetails && this.inboundInf.inboundDetails.length) {
        this.detailsTableLoading = true
        let temp = this.orderDetails.filter(e => this.selectedDetailTemp.includes(e.orderDetails[this.fieldName.onlyFieldName]));
        temp.forEach(e => {
          let every = this.inboundInf.inboundDetails.every((ev) => ev.orderDetails[this.fieldName.onlyFieldName] !== e.orderDetails[this.fieldName.onlyFieldName]);
          if (every) {
            this.inboundInf.inboundDetails.push(e);
          }
        })
        for (let i = this.inboundInf.inboundDetails.length - 1; i >= 0; i--) {
          let every = temp.every(ev => ev.orderDetails[this.fieldName.onlyFieldName] !== this.inboundInf.inboundDetails[i].orderDetails[this.fieldName.onlyFieldName])
          if (every) {
            this.inboundInf.inboundDetails.splice(i, 1);
          }
        }
        this.detailsTableLoading = false;
      } else {
        this.inboundInf.inboundDetails = this.orderDetails.filter(e => this.selectedDetailTemp.includes(e.orderDetails[this.fieldName.onlyFieldName]));
      }
      console.log("goInboundDetailsDefine inboundDetails: ", this.inboundInf.inboundDetails)
      this.closeGoodsSelect();
    },
    handlerSelectionChange(selectRow) {
      if (selectRow && selectRow.length > 0) {
        this.selectedDetailTemp = selectRow.map(e => e.orderDetails[this.fieldName.onlyFieldName])
      } else {
        this.selectedDetailTemp = [];
      }
      // this.selectedDetailTemp = selectRow;
      // console.log("inboundDetails: ", this.inboundInf.inboundDetails)
      // console.log("selectedDetailTemp：", this.selectedDetailTemp)
    },
    goResetSearchDetails() {
      this.resetQuery('inboundDetailsQForm', () => this.goSearchDetails())
    },
    cancel() {
      this.$confirm('检测到未保存的内容，是否关闭？', '确认信息', {
        distinguishCancelAndClose: true,
        confirmButtonText: '确认关闭',
        cancelButtonText: '取消'
      })
        .then(() => {
          this.$tab.closeOpenPage({path: '/inboundInbound/inbound'});
        })
        .catch(action => {
          // 未关闭
        });
    },
    rest() {
      this.$confirm('检测到未保存的内容，是否重置？', '确认信息', {
        distinguishCancelAndClose: true,
        confirmButtonText: '确认关闭',
        cancelButtonText: '取消'
      })
        .then(() => {
          this.initProps();
        })
        .catch(action => {
          // 未关闭
        });
    },
    async submitForm() {
      this.$refs.inboundInf.validate(async (valid) => {
        if (valid) {
          if (!(this.inboundInf.inboundDetails && this.inboundInf.inboundDetails.length)) {
            this.$message.error(`请添加入库货品！`);
            return false;
          }
          let inboundDetails = this.inboundInf.inboundDetails.map(obd => {
            console.log("inboundDetails: ", obd);
            return {
              // obdId 入库明细 id
              indId: obd.indId,
              odid: obd.orderDetails[this.fieldName.odid],
              goodsId: obd.orderDetails?.stock?.gid || obd.orderDetails.goodsId,
              unit: obd.orderDetails?.stock?.unit || obd.orderDetails.unit,
              slid: obd.orderDetails?.stock?.slid || obd.slid,
              productionDate: obd.productionDate,
              // 本次入库数量
              quantityInbound: obd.quantityInbound,
              puPrice: obd.orderDetails.puPrice || obd.orderDetails.goods.wrPrice,
              remark: obd.remark,
              batchNumber: obd.batchNumber
            }
          });
          this.$emit("submitForm", {
            inid: this.inboundInf.inid,
            inCode: this.inboundInf.inCode,
            inType: this.inboundInf.inType,
            wid: this.inboundInf.wid,
            sid: this.inboundInf.sid, // 供应商 ID
            cid: this.inboundInf.cid, // 客户 ID
            manager: this.inboundInf.manager,
            storageDate: this.inboundInf.storageDate,
            orderId: this.inboundInf.selectedOrder.orderId,
            relatedOrder: this.inboundInf.selectedOrder.orderNo,
            remark: this.inboundInf.remark,
            inboundDetails
          });
          console.log("relatedOrder: ", this.inboundInf.relatedOrder)
          console.log("orderId: ", this.inboundInf.selectedOrder.orderId)
        } else {
          this.$message.warning("请将表单填写完整");
          return false;
        }
      });
    },
    handleSelectionGoodsChange(selection) {
      this.odIds = selection.map(item => item.orderDetails[this.fieldName.onlyFieldName]);
    },
    removeItems(ids) {
      console.log("removeItems ids: ", ids)
      this.inboundInf.inboundDetails = this.inboundInf.inboundDetails.filter(e => !ids.includes(e.orderDetails[this.fieldName.onlyFieldName]))
    },
    handlerAddInboundDetails() {
      if (!this.inboundInf.selectedOrder?.orderId && this.inboundInf.associated) {
        this.$message.warning("请先选择订单号！")
        this.$refs["inboundInf"].validateField("selectedOrder.orderId");
        this.$refs["inboundInf"].validateField("inType");
        return;
      }
      this.dialogDetailsVisible = true;
      this.selectedDetailTemp = [];
      if (!this.inboundInf.associated)
        this.goSearchDetails();
      this.inboundInf.inboundDetails.forEach(e => this.selectedDetailTemp.push(e.orderDetails[this.fieldName.onlyFieldName]));
      // console.log("selectedDetailTemp: ", this.selectedDetailTemp)
      // detailsTable toggleRowSelection
      if (this.dialogDetailsVisible) {
        this.$nextTick(() => {
          let detailsTable = this.$refs.detailsTable;
          detailsTable.clearSelection();
          console.log("detailsTable.toggleRowSelection")
          for (let id of this.inboundInf.inboundDetails) {
            for (let e of this.orderDetails) {
              if (e.orderDetails[this.fieldName.onlyFieldName] === id.orderDetails[this.fieldName.onlyFieldName]) {
                detailsTable.toggleRowSelection(e, true);
                break;
              }
            }
          }
        })
      }
    }
  },
  watch: {
    'inboundInf.selectedOrder': {
      async handler(newValue, oldValue) {
        try {
          if (newValue) {
            if (!this.currFormHandler.hiddenFields.has("contact")) {
              getSupplier(this.inboundInf.selectedOrder.sId).then(({code, data}) => {
                if (code === 200) {
                  this.contact.contactPerson = data.contactPerson;
                  this.contact.contactNumber = data.contactNumber;
                  this.inboundInf.sid = this.inboundInf.selectedOrder.sId
                  console.log("sId")
                }
              });
            }
            if (this.currFormHandler.disableFields.has('warehouse') && !this.isEditor) {
              this.inboundInf.wid = newValue[this.fieldName.whName];
              this.getSlList(newValue[this.fieldName.whName]);
            }
            await this.goSearchDetails()
            if (this.isEditor || this.inboundInf.associated) {
              if (this.isEditor) {
                this.inboundInf.inboundDetails = [];
                for (let outd of this.inboundInf.inboundDetailsList) {
                  for (const orderd of this.orderDetails) {
                    if (outd.odid === orderd.orderDetails[this.fieldName.odid]) {
                      this.inboundInf.inboundDetails.push({
                        ...outd,
                        ...orderd,
                        slid: outd.slid,
                        remark: outd.remark
                      })
                      break;
                    }
                  }
                }
              } else
                this.inboundInf.inboundDetails = this.orderDetails;
            }
            if (this.inboundInf.associated && !this.inboundInf?.inboundDetails?.length) {
              this.$message.warning("当前订单没有可入库的货品！")
            }
            console.log("inboundDetails：", this.inboundInf.inboundDetails)
          }
        } catch (e) {
          console.log("error: ", e)
        }
      },
      // deep: true // 开启深度监听
    }
  }
}

</script>

<style scoped>
.container {
  margin: 30px 160px;
  background: #f3f6f8;
}

.container .group {
  background: #FFFFFF;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
  padding: 20px;
  margin-bottom: 20px;
}

.el-table .el-form-item,
.el-table .el-form-item__content {
  margin-bottom: 0 !important;
}

/*
* 在 el-form 的 inline=false 的情况下需要打开
*/
.el-table .el-form-item {
  margin-left: -100px !important;
}


/*
* 用于 table
*/
::v-deep .editable-column .cell {
  position: absolute;
  top: 0;
  bottom: 0;
  left: 0;
  right: 0;
  display: flex;
  align-items: center;
  justify-content: center;
}

.app-main {
  //background: #f3f6f8;
}

.footer-btn {
  position: fixed;
  z-index: 20;
  bottom: 0;
  right: 0;
  background: #fff;
  width: 100vw;
  height: 67px;
  padding: 15px 15px 15px 0;
  -webkit-box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
  display: flex;
  justify-content: flex-end;
}

::v-deep .table_radio .el-radio__label {
  display: none !important;
}

::v-deep .el-date-editor.el-input, .el-date-editor.el-input__inner {
  width: 182px;
}

.no-margin-left.el-form-item > .el-form-item__content {
  margin-left: 0 !important;
}

.remark-inb textarea {
  resize: none;
}

.basicInf {
  display: grid;
  grid: auto-flow dense / 1fr 1fr;
}
</style>
