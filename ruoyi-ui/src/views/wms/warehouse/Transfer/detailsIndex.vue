<template>
  <div class="app-container" style="background-color: #F3F6F8">

    <el-row :gutter="20" style="background-color: white;border-radius: 5px;box-shadow: 2px 2px 5px rgba(0,0,0,0.2);
   padding-bottom: 10px;margin-left: 200px;width: 1280px">
      <h4 style="margin-left: 25px;padding-top:10px;font-weight: bold;font-size: 20px">
        调拨单号:{{ transferDetails[0] && transferDetails[0].tdCode }}</h4>
      <div style="position: relative;top: -50px;left: -26px">
      </div>
      <div style="padding-top: 30px;padding-left: 30px">
        <el-form ref="elForm" size="medium" label-width="95px" style="margin-top: 14px">
          <el-row>
            <el-col :span="6">
              <el-form-item label="制单人">
                <span>{{ transferDetails[0] && transferDetails[0].creatorName }}</span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="所在部门">
                <span>{{ transferDetails[0] && transferDetails[0].creatorDept }}</span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label=制单时间>
                <span>{{ transferDetails[0] && transferDetails[0].createTime }}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row style="float: right;margin-right:30px;margin-top: -58px;color: #606266">
            <el-col :span="2">
              <el-form-item label="单据状态"/>
            </el-col>
          </el-row>

          <el-row
            style="width:175px;font-size:23px;font-weight:bolder;float: right;margin-right:-67px;margin-top: -5px;color: #606266">
            <el-col :span="10">
              <template>
                {{
                  transferDetails[0] && transferDetails[0].docStatus === 'pending_review' ? '待审核' : transferDetails[0] && transferDetails[0].docStatus == 'done' ? '已完成' : '驳回'
                }}
              </template>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6">
              <el-form-item label="调拨类型">
                <span>
                  {{ transferDetails[0] && transferDetails[0].type == 'alert' ? "库存预警" : '满仓调离' }}
                </span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="调拨申请日期" label-width="100">
                <span>{{ transferDetails[0] && transferDetails[0].date }}</span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="调出仓库" label-width="100">
                <span>{{ transferDetails[0] && transferDetails[0].outName }}</span>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6">
              <el-form-item label="调入仓库">
                <span>{{ transferDetails[0] && transferDetails[0].inName }}</span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="经办人">
                <span>{{ transferDetails[0] && transferDetails[0].managerName }}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="12">
              <el-form-item label="备注">
                <span>{{ transferDetails[0] && transferDetails[0].remark }}</span>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </el-row>

    <el-row :gutter="20" style="background-color: white;border-radius: 5px;box-shadow: 2px 2px 5px rgba(0,0,0,0.2);
    margin-top: 20px;padding-bottom: 10px;margin-left: 200px;width: 1280px;height: 600px">
      <ul class="nav" style="margin-left: 20px">
        <li @click="details" ref="listItem" style="color: #45B984;border-bottom: 2px solid #45B984">调拨明细</li>
        <li @click="instock" ref="lisin">入库记录</li>
        <li @click="outstock" ref="lisout">出库记录</li>
        <li @click="operate" ref="listop">操作记录</li>
      </ul>

      <!-- 调拨明细小窗口-->
      <div v-show="open" style="border-top: 1px solid #eeeeee;padding-top: 40px;padding-left: 30px;margin-top: 74px">
        <el-table style="margin-top: 20px" :data="transferDetails[0]&&transferDetails[0].transferDetailsList"
                  max-height="520px"
                  :row-class-name="operateIndex"
                  @selection-change="handleSelectionChange">
          <el-table-column type="selection" fixed="left" width="55" align="center"/>
          <el-table-column label="序号" fixed="left" prop="index" width="120"/>
          <el-table-column label="货品名称" fixed="left" prop="goods.gname" width="100"/>
          <el-table-column label="货品编号" fixed="left" align="center" prop="goods.gcode"/>
          <el-table-column label="规格型号" align="center" prop="goods.specCode"/>
          <el-table-column label="单位" align="center" prop="stock.unit">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.g_unit_goods" :value="scope.row.stock.unit"/>
            </template>
          </el-table-column>
          <el-table-column label="调出仓库当前库存" width="150px" align="center" prop="stock.itemQuantity"/>
          <el-table-column label="调入仓库当前库存" width="150px" align="center" prop="totalItemQuantity"/>

          <el-table-column label="调拨数量" align="center" width="100px">
            <template slot-scope="scope">
              <span>{{ scope.row.quantity }}</span>
            </template>
          </el-table-column>
          <el-table-column label="调拨单价" align="center" prop="goods.wrPrice"/>
          <el-table-column label="调拨金额" align="center">
            <template slot-scope="scope">
              {{ transferAccount(scope.row.quantity, scope.row.goods.wrPrice) }}
            </template>
          </el-table-column>
          <el-table-column label="备注" align="center" prop="remark" width="140px"/>
        </el-table>

        <div id="count" style="border:1px solid  #eeeeee;height: 38px">
          <el-row>
            <el-col>
              <div class="down" style="float:right;margin-right:127px;margin-top:9px;font-size: 14px">

                <span style="margin-right: 47px">调拨数量:<em>{{ totalTransferCount }}</em></span>
                <span>调拨金额:<em>{{ totalTransferAccount }}</em></span>
              </div>
            </el-col>


          </el-row>


        </div>

      </div>


      <!--入库记录-->
      <div v-show="openIn" style="border-top: 1px solid #eeeeee;padding-top: 40px;padding-left: 30px;margin-top: 74px">
        <el-table style="margin-top: 20px" v-loading="loading" max-height="520px"
                  @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center"/>
          <el-table-column label="序号" prop="sl_id" width="120"/>
          <el-table-column label="入库单号" prop="sl_code" :show-overflow-tooltip="true" width="150"/>
          <el-table-column label="入库日期" prop="sl_code" :show-overflow-tooltip="true" width="150"/>
          <el-table-column label="仓库名称" prop="sl_name" :show-overflow-tooltip="true" width="150"/>
          <el-table-column label="入库货品" align="center" prop="location_capacity"/>
          <el-table-column label="入库数量" align="center" prop="manager"/>
          <el-table-column label="入库金额" align="center" prop="sort"/>
          <el-table-column label="经办人" align="center" prop="sort"/>
          <el-table-column label="制单人" align="center" prop="sort"/>
          <el-table-column label="所在部门" align="center" prop="sort"/>
          <el-table-column label="制单时间" align="center" prop="sort"/>
        </el-table>
      </div>


      <!--      出库记录-->
      <div v-show="openOut" style="border-top: 1px solid #eeeeee;padding-top: 40px;padding-left: 30px;margin-top: 74px">
        <el-table style="margin-top: 20px" v-loading="loading" max-height="520px"
                  @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" align="center"/>
          <el-table-column label="序号" prop="sl_id" width="120"/>
          <el-table-column label="出库单号" prop="sl_code" :show-overflow-tooltip="true" width="150"/>
          <el-table-column label="出库日期" prop="sl_code" :show-overflow-tooltip="true" width="150"/>
          <el-table-column label="仓库名称" prop="sl_name" :show-overflow-tooltip="true" width="150"/>
          <el-table-column label="出库货品" align="center" prop="location_capacity"/>
          <el-table-column label="出库数量" align="center" prop="manager"/>
          <el-table-column label="出库金额" align="center" prop="sort"/>
          <el-table-column label="经办人" align="center" prop="sort"/>
          <el-table-column label="制单人" align="center" prop="sort"/>
          <el-table-column label="所在部门" align="center" prop="sort"/>
          <el-table-column label="制单时间" align="center" prop="sort"/>
        </el-table>
      </div>


      <!-- 操作记录小窗口-->
      <div v-show="openOperate"
           style="border-top: 1px solid #eeeeee;padding-top: 40px;padding-left: 30px;margin-top: 74px">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="68px">
          <el-form-item label="操作人">
            <el-input
              v-model="queryParams.sl_name"
              placeholder="请输入仓位名称"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>

          <el-form-item label="操作时间" prop="createTimes">
            <el-date-picker
              v-model="queryParams.createTimes"
              end-placeholder="结束日期" format="yyyy-MM-dd"
              range-separator="至"
              start-placeholder="开始日期"
              type="daterange"
              value-format="yyyy-MM-dd">
            </el-date-picker>
          </el-form-item>
          <el-form-item style="margin-right: 30px;float: right;">
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="">重置</el-button>
          </el-form-item>
        </el-form>
        <el-table style="margin-top: 50px" :row-class-name="operateIndex" :data="operateList" v-loading="loading"
                  max-height="520px" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center"/>
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="操作人" prop="sl_code" :show-overflow-tooltip="true" width="150"/>
          <el-table-column label="操作时间" align="center" prop="manager"/>
        </el-table>
        <!--        <pagination-->
        <!--          v-show="total>0"-->
        <!--          :total="total"-->
        <!--          :page.sync="queryParams.pageNum"-->
        <!--          :limit.sync="queryParams.pageSize"-->
        <!--          @pagination="getStorageList"-->
        <!--        />-->
      </div>
    </el-row>

    <div class="footer-container" style=" position: fixed;
          box-shadow: 2px 2px 5px rgba(0,0,0,0.9);  background-color:white;border-radius: 2px;
          width: 89.5%; height: 60px; bottom: 0; right: 0;  right: 0; z-index: 9999;">
      <el-footer>
        <!-- 底部导航栏内容 -->
        <el-button style="float:right;margin-right: 5px;margin-top: 12px"
                   plain size="medium" @click="cancle">关闭
        </el-button>
      </el-footer>
    </div>


  </div>
</template>
<script>


import {listTransfer} from "@/api/wms/warehouse/transfer/transfer.js";

export default {
  dicts: ['wh_status', 'sys_normal_disable', 'pd_status_check', 'g_unit_goods'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 是否显示弹出层
      open: false,
      openOut: false,
      openIn: false,
      openOperate: false,
      // 选中数组
      ids: [],
      // 非多个禁用
      multiple: true,
      //明细：tid
      tid: undefined,
      //调拨单单详情数据
      transferDetails: [],
      //操作记录表格数据
      operateList: [],
      // 总条数
      total: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        sl_name: null,
        status: null,
        position_manager: null,
        w_id: null,
        createTimes: [],
      },

    }
  },
  computed: {
    //调拨总数
    totalTransferCount() {
      return this.transferDetails?.[0]?.transferDetailsList?.reduce((accumulator, currentValue) => {
        return accumulator + (Number(currentValue.quantity));
      }, 0);
    },

    //调拨总金额
    totalTransferAccount(){
      const totalAmount =  this.transferDetails?.[0]?.transferDetailsList?.reduce((accumulator, currentValue) => {
        const wrPrice = currentValue?.goods?.wrPrice || 0;
        const quantity = currentValue.quantity || 0;
        const total = wrPrice * quantity;
        return accumulator + total;
      }, 0);
      return totalAmount ?.toFixed(2);
    }
  },
  watch: {},
  created() {
  },

  mounted() {
    this.tid = this.$route.params.tid;
    this.getDetailsByTid();
    this.open = true;
  },
  updated() {
  },
  //
  methods: {
    //根据tid查询调拨单
    getDetailsByTid() {
      listTransfer({"tid": this.tid}).then(response => {
        this.transferDetails = response.rows;
        console.log("transferDetails", this.transferDetails)
      });
    },
    // //查询操作记录
    // getOperate(){
    //   getOperateList().then(response => {
    //     this.operateList = response.data;
    //   });
    // },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.sl_id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    //关闭窗口
    cancle() {
      this.$router.push(`/transfer`);
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      // this.getStorageList();
    },
    /** 重置按钮操作 */
    // resetQuery() {
    //    this.queryParams.sl_name=null,
    //    this.queryParams.status=null,
    //   this.queryParams.position_manager=null,
    //   this.handleQuery();
    // },


    //调拨单个货品金额
    transferAccount(quantity, wrPrice) {
      return (quantity * wrPrice).toFixed(2);
    },


    //盘点明细
    details() {
      this.open = true;
      this.openOperate = false;
      this.openOut = false;
      this.openIn = false;
      this.$refs.listItem.style.color = '#45B984';
      this.$refs.listItem.style.borderBottom = '2px solid #45B984';
      this.$refs.listop.style.color = '#000000';
      this.$refs.listop.style.borderBottom = 'none';
      this.$refs.lisin.style.color = '#000000';
      this.$refs.lisin.style.borderBottom = 'none';
      this.$refs.lisout.style.color = '#000000';
      this.$refs.lisout.style.borderBottom = 'none';
    },
    //入库记录
    instock() {
      this.openIn = true;
      this.open = false;
      this.openOperate = false;
      this.openOut = false;
      this.$refs.lisin.style.color = '#45B984';
      this.$refs.lisin.style.borderBottom = '2px solid #45B984';
      this.$refs.listop.style.color = '#000000';
      this.$refs.listop.style.borderBottom = 'none';
      this.$refs.listItem.style.color = '#000000';
      this.$refs.listItem.style.borderBottom = 'none';
      this.$refs.lisout.style.color = '#000000';
      this.$refs.lisout.style.borderBottom = 'none';
    },
    //出库记录
    outstock() {
      this.openOut = true;
      this.openIn = false;
      this.open = false;
      this.openOperate = false;
      this.$refs.lisout.style.color = '#45B984';
      this.$refs.lisout.style.borderBottom = '2px solid #45B984';
      this.$refs.listop.style.color = '#000000';
      this.$refs.listop.style.borderBottom = 'none';
      this.$refs.listItem.style.color = '#000000';
      this.$refs.listItem.style.borderBottom = 'none';
      this.$refs.lisin.style.color = '#000000';
      this.$refs.lisin.style.borderBottom = 'none';
    },
    //操作记录
    operate() {
      this.openOperate = true;
      this.open = false;
      this.openOut = false;
      this.openIn = false;
      this.$refs.listop.style.color = '#45B984';
      this.$refs.listop.style.borderBottom = '2px solid #45B984';
      this.$refs.listItem.style.color = '#000000';
      this.$refs.listItem.style.borderBottom = 'none';
      this.$refs.lisin.style.color = '#000000';
      this.$refs.lisin.style.borderBottom = 'none';
      this.$refs.lisout.style.color = '#000000';
      this.$refs.lisout.style.borderBottom = 'none';
    },
    /** 操作记录序号 */
    operateIndex({row, rowIndex}) {
      row.index = rowIndex + 1;
    },
  }
}

</script>
<style scoped>
::v-deep {
  .el-form-item__content > span {
    color: #606266;
    margin-left: 40px;
  }

  .nav > li {
    float: left;
    list-style: none;
    font-size: 16px;
    padding-left: 33px;
    width: 137px;
    height: 58px;
  }

  .nav > li:hover {
    cursor: pointer;
  }

  .down em{
    color: #ffae00;
  }
}

</style>
