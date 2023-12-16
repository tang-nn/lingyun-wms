<template>
  <div class="container">
    <el-form ref="inboundInf" :model="inboundInf" :rules="rules"
             class="demo-inboundInf"
             label-width="100px">
      <div class="group">
        <el-row>
          <el-col :span="3">
            基础信息
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="进货单号" prop="poCode">
              <el-input v-model="inboundInf.inCode" disabled placeholder="自动获取系统编号"
                        style="width: 432px;"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="入库类型" label-width="86px" prop="inType">
              <el-select
                  v-model="inboundInf.inType"
                  placeholder="请选择入库类型"
                  style="width: 240px"
              >
                <el-option
                    v-for="dict in dict.type.incoming_type"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="仓库" prop="wid">
              <el-select v-model="inboundInf.wid"
                         placeholder="请选择仓库" @change="handlerSupplierChange">
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="入库日期" prop="storageDate" required>
              <el-date-picker
                  v-model="inboundInf.storageDate"
                  placeholder="选择日期"
                  type="datetime">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="管理订单号" prop="orderId">
              <div @click="handlerSelectOrder">
                <el-input v-model="inboundInf.selectedOrder.orderNo" placeholder="请选择订单" readonly
                          style="cursor: pointer"
                          suffix-icon="el-icon-more"
                ></el-input>
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="供应商">
              <el-input disabled v-bind:value="inboundInf.selectedOrder.supplName"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="联系人">
              <el-input disabled v-bind:value="inboundInf.selectedOrder.contactPerson"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系方式">
              <el-input disabled v-bind:value="inboundInf.selectedOrder.contactNumber"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="经办人" prop="manager">
              <el-select v-model="inboundInf.uid"
                         placeholder="请选经办人">
                <el-option
                    v-for="u in userList"
                    :key="u.userId"
                    :label="u.nickName"
                    :value="u.userId"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="inboundInf.remark" :autosize="{ minRows: 2, maxRows: 6 }" :rows="6"
                    type="textarea"></el-input>
        </el-form-item>
        <!-- 选择订单的模态框 -->
        <el-dialog :visible.sync="orderFormVisible" append-to-body title="选择进货单" width="1000px">
          <el-form ref="queryOrderForm" :inline="true" :model="purchaseQueryParams" size="small">
            <el-row>
              <el-col :span="6">
                <el-form-item prop="purOrderSn">
                  <el-input
                      v-model.trim="purchaseQueryParams.purOrderSn"
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
                      v-model="purchaseQueryParams.orderStatus"
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
                      v-model="purchaseQueryParams.dateRange"
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
                      v-model.trim="purchaseQueryParams.purchaser"
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
                      v-model.trim="purchaseQueryParams.purDept"
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
                      v-model.trim="purchaseQueryParams.suppName"
                      clearable
                      placeholder="请输入供应商名称"
                      style="width: 200px"
                      @keyup.enter.native="handleOrderQuery"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item>
                  <el-button icon="el-icon-search" size="mini" type="primary" @click="handleOrderQuery">搜索</el-button>
                  <el-button icon="el-icon-refresh" size="mini" @click="resetOrderQuery">重置</el-button>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <el-table v-loading="orderTableLoading" :data="purchaseData" @row-dblclick="handlerRowDblclick"
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
            <el-table-column :show-overflow-tooltip="true" align="center" label="供应商" prop="supplName" width="100"/>
            <el-table-column align="center" label="进货部门" prop="purDeptName" width="100"/>
            <el-table-column align="center" label="进货人" prop="purNickName" width="100"/>
            <el-table-column :formatter="handlerFormatQuantity" align="center" label="货品数量" width="100"/>
            <el-table-column :formatter="handlerFormatPrice" align="center" label="货品金额" width="100"/>
          </el-table>
          <pagination
              v-show="orderTotal>0"
              :limit.sync="purchaseQueryParams.pageSize"
              :page.sync="purchaseQueryParams.pageNum"
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
            进货明细
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <el-form-item>
          <el-button type="primary" @click="handlerAddInboundDetails">添加</el-button>
          <el-button @click="removeItems()">移除</el-button>
        </el-form-item>
        <el-table :cell-style="{ padding:'0px' }"
                  :data="inboundInf.selectGoods"
                  :row-style="{ height:'70px' }"
                  @selection-change="handleSelectionGoodsChange">
          <el-table-column align="center" fixed type="selection" width="50"/>
          <el-table-column align="center" fixed label="序号" type="index" width="60"/>
          <el-table-column align="center" fixed label="货品名称" prop="goods.g_name" width="80"/>
          <el-table-column align="center" label="货品类型" prop="goods.gt_name" width="100"/>
          <el-table-column align="center" fixed label="货品编号" prop="goods.g_code" width="110"/>
          <el-table-column align="center" label="规格型号" prop="goods.spec_code" width="100"/>
          <el-table-column align="center" label="单位" prop="unit" width="100">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.g_unit_goods" :value="scope.row.unit"/>
            </template>
          </el-table-column>
          <el-table-column :formatter="(row)=>(row.item_quantity || '暂无货')" align="center" label="当前库存"
                           prop="item_quantity" width="100"/>
          <el-table-column align="center" label="进货数量" prop="purchaseQuantity" width="120"/>
          <el-table-column align="center" label="已入库数量" prop="quantityInStock" width="120"/>
          <el-table-column :formatter="(row)=>(row.purchaseQuantity - (row.item_quantity || 0))" align="center"
                           label="未入库数量" prop="qgne" width="120"/>
          <el-table-column :rules="rules.wr_price" align="center" label="入库库位" prop="storageLocation" width="140">
            <template slot-scope="scope">
              <el-form-item label-width="86px" prop="storageLocation">
                <el-select
                    v-model="scope.row.storageLocation"
                    clearable
                    placeholder="请选择库位"
                    style="width: 120px"
                >
                  <el-option
                      v-for="sl in slList"
                      :key="sl.value"
                      :label="sl.label"
                      :value="sl.value"
                  />
                </el-select>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column :rules="rules.wr_price" align="center" label="本次入库数量" prop="thisInboundQuantity"
                           width="140">
            <template slot-scope="scope">
              <el-form-item
                  :prop="`selectGoods.${scope.$index}.thisInboundQuantity`"
              >
                <el-input v-model="scope.row.thisInboundQuantity" placeholder="本次入库数量"></el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column align="center" label="进货单价" prop="puPrice"/>
          <el-table-column :rules="rules.batchNumber" align="center" label="批次号" prop="batchNumber" width="150">
            <template slot-scope="scope">
              <el-form-item
                  :prop="`selectGoods.${scope.$index}.batchNumber`"
              >
                <el-input v-model="scope.row.batchNumber" placeholder="请输入本次入库批次号"></el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column :formatter="row=>row.puPrice * (row.thisInboundQuantity || 0)" align="center"
                           label="入库金额"
                           width="120"/>
          <el-table-column :rules="rules.productionDate" align="center" label="生产日期" prop="productionDate"
                           width="200">
            <template slot-scope="scope">
              <el-form-item :prop="`selectGoods.${scope.$index}.productionDate`" required>
                <el-date-picker
                    v-model="scope.row.productionDate"
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
                  :prop="`selectGoods.${scope.$index}.notesStorage`"
              >
                <el-input v-model="scope.row.notesStorage" placeholder="请输入备注"></el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column align="center" class-name="small-padding fixed-width" fixed="right" label="操作" width="90">
            <template slot-scope="scope">
              <el-button
                  icon="el-icon-delete"
                  size="mini"
                  type="text"
                  @click="removeItems([scope.row.g_id])"
              >移除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <!--      <div class="group">-->
      <!--        <el-row>-->
      <!--          <el-col :span="3">-->
      <!--            上传附件-->
      <!--          </el-col>-->
      <!--        </el-row>-->
      <!--        <el-divider></el-divider>-->
      <!--        <el-upload-->
      <!--            ref="upload"-->
      <!--            :action="baseApi + '/file/upload'"-->
      <!--            :auto-upload="false"-->
      <!--            :file-list="inboundInf.selectAnnex"-->
      <!--            :headers="token"-->
      <!--            :multiple="true"-->
      <!--            :on-error="handlerUploadError"-->
      <!--            :on-remove="handleRemove"-->
      <!--            :on-success="uploadComplete"-->
      <!--            :show-file-list="true"-->
      <!--            accept="*.doc,*.pdf,*.rar,*.zip"-->
      <!--            class="upload-demo">-->
      <!--          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>-->
      <!--          <el-button size="small" style="margin-left: 10px;" type="success" @click="submitUpload">上传到服务器-->
      <!--          </el-button>-->
      <!--          <div slot="tip" class="el-upload__tip">您可以上传 doc, pdf, rar 等格式的文件，单个文件上传最大 200MB。</div>-->
      <!--        </el-upload>-->
      <!--      </div>-->
      <div class="footer-btn">
        <el-button type="primary" @click="submitForm('inboundInf')">提交</el-button>
        <el-button @click="cancel()">取消</el-button>
      </div>
    </el-form>
    <el-dialog :visible.sync="dialogTableVisible" title="添加货品" width="750px">
      <el-form ref="queryGoodsForm" :inline="true" :model="queryGoodsParams" size="small">
        <el-form-item label-width="86px" prop="unit">
          <el-select
              v-model="queryGoodsParams.unit"
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
              v-model.trim="queryGoodsParams.codeOrName"
              clearable
              placeholder="请输入货品编号/名称"
              style="width: 200px"
              @keyup.enter.native="handleGoodsQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button icon="el-icon-search" size="mini" type="primary" @click="getGoodsList()">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetForm('queryGoodsForm')">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table ref="goodsTable" v-loading="loading" :data="goodsList"
                row-key="g_id" @selection-change="handlerSelectionChange">
        <el-table-column :reserve-selection="true" align="center" fixed type="selection" width="50"/>
        <el-table-column align="center" fixed label="序号" type="index" width="60"/>
        <el-table-column align="center" fixed label="货品编号" prop="g_code" width="80"/>
        <el-table-column align="center" fixed label="货品名称" prop="g_name" width="110"/>
        <el-table-column align="center" label="规格型号" prop="spec_code" width="100"/>
        <el-table-column align="center" label="单位" width="60">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.g_unit_goods" :value="scope.row.unit"/>
          </template>
        </el-table-column>
        <el-table-column align="center" label="入库单价（元）" prop="wr_price" width="100"/>
        <el-table-column :formatter="(row)=>(row.item_quantity || '无货')" align="center" label="当前库存"
                         prop="item_quantity" width="100"/>
      </el-table>
      <pagination
          v-show="0>0"
          :limit.sync="queryGoodsParams.pageSize"
          :page.sync="queryGoodsParams.pageNum"
          :total="0"
          @pagination="getGoodsList"
      />
      <el-button type="primary" @click="handleGoodsDefine">确定</el-button>
      <el-button @click="closeGoodsSelect">取消</el-button>
    </el-dialog>
  </div>
</template>

<script>
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import Treeselect from "@riophae/vue-treeselect";
import {deptTreeSelect, listUser} from "@/api/system/user";
import {listGood} from "@/api/wms/good/goodsinfo";
import {getToken} from "@/utils/auth";
import {getPurchaseInf, listPurchase} from "@/api/wms/order/purchase";
import {deepClone} from "@/utils";
import SelectorUser from "@/components/wms/SelectorUser.vue";

export default {
  name: "PurchaseFrom",
  dicts: ['incoming_type', 'inbound_status', 'inbound_status', 'g_unit_goods', 'ord_doc_status'],
  components: {
    Treeselect,
    SelectorUser
  },
  data() {
    // let inboundQuantityValidator = (rule, value, callback) => {
    //   if(value > ){
    //
    //   }
    // };
    return {
      // 库位列表
      slList: [],
      orderTotal: 0,
      // 选择订单，弹出订单选择
      orderFormVisible: false,
      selectedOrderTemp: {},
      orderTableLoading: false,
      // 进货单据
      purchaseData: [],
      purchaseQueryParams: {
        pageNum: 1,
        pageSize: 5,
        purOrderSn: "",
        orderStatus: undefined,
        suppName: undefined,
        dateRange: []
      },
      queryGoodsParams: {
        pageNum: 1,
        pageSize: 5,
        codeOrName: undefined
      },
      selectorUserVisible: false,
      deptTree: [],
      baseApi: process.env.VUE_APP_BASE_API,
      rules: {
        supplier: [
          {required: true, message: '请选择供应商', trigger: 'change'}
        ],
        purchaseQuantity: [
          {required: true, message: '请选输入进货数量', trigger: 'blur'},
          {pattern: /^[1-9]\d*$/, message: '请输入正确的数量', trigger: 'blur'}
        ],
        wr_price: [
          {required: true, message: '请选输入进货单价', trigger: 'blur'},
          {pattern: /^\d+(\.\d{1,2})?$/, message: '请输入正确的价格', trigger: 'blur'}
        ],
        storageLocation: [
          {required: true, message: '请选择入库库位', trigger: 'blur'},
        ],
        thisInboundQuantity: [
          {required: true, message: '请选输入本次入库数量', trigger: 'blur'},
          {pattern: /^[1-9]\d*$/, message: '请输入正确的数量', trigger: 'blur'},
        ]
      },
      // 部门树选项
      deptOptions: undefined,
      loading: false,
      fileList: [],
      dialogTableVisible: false,
      goodsList: [],
      inboundInf: {
        selectGoods: [],
        selectAnnex: [],
        selectedOrder: {}
      },
      orderList: [],
      userList: [],
    }
  },
  props: {
    inboundData: {
      type: Object,
      default: undefined
    }
  },
  created() {
    this.getUser();
    // 进货订单明细
    this.purchaseDetailsList();
    // this.inboundInf = this.inboundData;
    // console.log("inboundInf", this.inboundInf)
  },
  mounted() {
  },
  computed: {
    token() {
      return {
        Authorization: `Bearer ${getToken()}`
      }
    }
  },
  methods: {
    handlerSelectOrder() {
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
      let params = JSON.parse(JSON.stringify(this.purchaseQueryParams));
      if (this.purchaseQueryParams.dateRange && this.purchaseQueryParams.dateRange.length) {
        params.beginTime = this.purchaseQueryParams.dateRange[0];
        params.endTime = this.purchaseQueryParams.dateRange[1];
      }
      delete params.dateRange;
      console.log("purchaseQueryParams params: ", params);
      ({
        rows: this.purchaseData,
        total: this.total
      } = (await listPurchase(params)));
      this.orderTableLoading = false;
    },
    purchaseDetailsList() {
      getPurchaseInf(20).then(({code, data}) => {
        if (code === 200) {
          // console.log("data: ", data)
          this.inboundInf.selectGoods = data.purchaseDetailsList;
        }
      });
    },
    handlerRowDblclick(row, column, event) {
      this.selectedOrderTemp = {
        orderId: row.poId,
        orderNo: row.poCode,
        supplName: row.supplName,
        contactPerson: row.contactPerson,
        contactNumber: row.contactNumber
      }
      this.confirmSelectedOrder();
    },
    singleElectionTable(row, column, cell, event) {
      console.log(
          "row: ",
          row
      )
      this.selectedOrderTemp = {
        orderId: row.poId,
        orderNo: row.poCode,
        supplName: row.supplName,
        contactPerson: row.contactPerson,
        contactNumber: row.contactNumber
      }
    },
    confirmSelectedOrder() {
      if (!this.selectedOrderTemp?.orderId) {
        this.$message.warning("还未选择订单，请先选择订单！")
        return;
      }
      this.inboundInf.selectedOrder = deepClone(this.selectedOrderTemp)
      this.orderFormVisible = false;
      console.log("selectedOrder: ", this.inboundInf.selectedOrder)
    },
    closeSelecteOrder() {
      this.orderFormVisible = false;
    },
    /** 搜索订单按钮操作 */
    handleOrderQuery() {
      this.purchaseQueryParams.pageNum = 1;
      this.getOrderList();
    },
    /** 重置订单按钮操作 */
    resetOrderQuery() {
      this.dateRange = [];
      this.resetForm("queryOrderForm");
      this.handleOrderQuery();
    },
    // 处理表格数量展示
    handlerFormatQuantity(row) {
      let count = 0;
      return row.purchaseDetails?.reduce((accumulator, currentValue) => {
        return accumulator + (currentValue.purchaseQuantity || 0);
      }, count);
    },
    //  处理表格金额展示
    handlerFormatPrice(row) {
      let count = 0;
      return row.purchaseDetails?.reduce((accumulator, currentValue) => {
        return accumulator + (currentValue.puPrice || 0);
      }, count);
    },
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data;
      });
    },
    initProps() {
    },
    async getUser() {
      this.userList = (await listUser()).rows;
    },
    async getUserList() {
      if (this.inboundInf.purDeptId) {
        this.userList = (await listUser({deptId: this.inboundInf?.purDeptId})).rows
        this.inboundInf.purchaser = undefined;
      }
      console.log("userList: ", this.userList)
    },
    handlerSupplierChange(row) {
    },
    closeGoodsSelect() {
      this.dialogTableVisible = false;
      this.tempSelectGoodsList = this.inboundInf.selectGoods;
    },
    handleGoodsDefine() {
      console.log("handleGoodsDefine")
      this.inboundInf.selectGoods = this.tempSelectGoodsList;
      // this.tempSelectGoodsList = [];
      this.closeGoodsSelect();
    },
    handlerSelectionChange(row) {
      // console.log("row: ", row)
      this.tempSelectGoodsList = row;
      // console.log("selectGoods: ", this.inboundInf.selectGoods)
      // console.log("tempSelectGoodsList：", this.tempSelectGoodsList)
    },
    getGoodsList() {
      this.loading = true;
      listGood({
        g_name: this.queryGoodsParams.codeOrName,
        unit: this.queryGoodsParams.unit,
        pageNum: this.queryGoodsParams.pageNum,
        pageSize: this.queryGoodsParams.pageSize,
      }).then(response => {
            this.$nextTick(() => {
              console.log("nextTick selectGoods: ", this.inboundInf.selectGoods)
              this.inboundInf.selectGoods.map(vl => {
                this.goodsList.map(item => {
                  if (item.g_id === vl.g_id) {
                    this.$refs.goodsTable.toggleRowSelection(item, true)
                  }
                })
              })
            });
            this.loading = false;
          }
      );
    },
    cancel() {
      this.$confirm('检测到未保存的内容，是否关闭？', '确认信息', {
        distinguishCancelAndClose: true,
        confirmButtonText: '确认关闭',
        cancelButtonText: '取消'
      })
          .then(() => {
            this.$tab.closeOpenPage({path: '/order/purchase'});
          })
          .catch(action => {
            // 未关闭
          });
    },
    submitUpload() {
      this.$refs.upload.submit();
    },
    uploadComplete(res, file, fileList) {
      console.log("fileList: ", fileList)
      let {code, data} = res;
      if (code === 200) {
        // this.inboundInf.selectAnnex = fileList;
        this.inboundInf.selectAnnex.push({name: file.name, url: data.url})
        this.$message.success(`${file.name}附件上传完成！`);
      }
    },
    handleRemove(file) {
      this.inboundInf.selectAnnex = this.inboundInf.selectAnnex.filter(e => e.name !== file.name)
    },
    handlerUploadError(err, file) {
      this.$message.error(`[${file.name}]附件上传失败！`);
    },
    async submitForm() {
      this.$refs.inboundInf.validate(async (valid) => {
        if (valid) {
          this.$emit("submitForm");
        } else {
          this.$message.warning("请将表单填写完整");
          return false;
        }
      });
    },
    handleSelectionGoodsChange(selection) {
      this.gids = selection.map(item => item.g_id);
    },
    removeItems() {
      // this.inboundInf.selectGoods = this.inboundInf.selectGoods.filter(e => !gids.includes(e.g_id))
    },
    handlerAddInboundDetails() {

    }
  }
}
</script>

<style>
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

.el-table .el-form-item {
  margin-left: -100px !important;
}

.app-main {
  background: #f3f6f8;
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
</style>
