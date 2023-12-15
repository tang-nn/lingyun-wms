<template>
  <div class="app-container" style="background-color: #F3F6F8">

    <el-row :gutter="20" style="background-color: white;border-radius: 5px;box-shadow: 2px 2px 5px rgba(0,0,0,0.2);
   padding-bottom: 10px;margin-left: 200px;width: 1280px">
      <h4 style="margin-left: 25px;padding-top:10px;font-weight: bold;font-size: 20px">盘点单号:{{InventoryDetails[0] && InventoryDetails[0].isCode}}</h4>
    <div style="position: relative;top: -50px;left: -26px">
      <el-button  style="float:right;margin-right: 5px;margin-top: 12px;padding-right: 14px;margin-left: 14px"
                  plain size="small"
                  @click="" v-hasPermi="['gd:good:add']" >入库</el-button>
      <el-button  style="float:right;margin-right: 5px;margin-top: 12px;padding-right: 16px"
                  plain size="small"
                  @click="handleDelete"  v-hasPermi="['wh:house:delete']">出库</el-button>
    </div>
      <div style="padding-top: 30px;padding-left: 30px">
        <el-form ref="elForm" size="medium" label-width="95px" style="margin-top: 14px">
          <el-row>
            <el-col :span="6">
              <el-form-item label="制单人">
                <span>{{InventoryDetails[0] && InventoryDetails[0].creatorName}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="所在部门">
                <span>{{InventoryDetails[0] && InventoryDetails[0].creatorDept}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label=制单时间>
                <span>{{InventoryDetails[0] && InventoryDetails[0].createTime}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row style="float: right;margin-right:30px;margin-top: -58px;color: #606266">
            <el-col :span="2">
              <el-form-item label="盘点结果"/>
            </el-col>
          </el-row>

          <el-row style="width:100px;font-size:18px;font-weight:bolder;float: right;margin-right:-3px;margin-top: -5px;color: #606266">
            <el-col :span="10">
              <template>
                {{ InventoryDetails[0].isResult === 'yes' ? '有盈亏' : '无盈亏' }}
              </template>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6">
              <el-form-item label="盘点类型">
                <span>
                  {{InventoryDetails[0] && InventoryDetails[0].isType==0?"期初盘点":InventoryDetails[0] &&InventoryDetails[0].isType==1?"存货盘点":"期末盘点"}}
                </span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="盘点开始时间"  label-width="100">
                <span>{{InventoryDetails[0] && InventoryDetails[0].isStartTime}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="盘点结束时间"  label-width="100">
                <span>{{InventoryDetails[0] && InventoryDetails[0].isEndTime}}</span>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6">
              <el-form-item label="仓库名称">
                <span>{{InventoryDetails[0] && InventoryDetails[0].whName}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="经办人">
                <span>{{InventoryDetails[0] && InventoryDetails[0].managerName}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <el-form-item label="备注">
                <span>{{InventoryDetails[0] && InventoryDetails[0].remark}}</span>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </el-row>

    <el-row :gutter="20" style="background-color: white;border-radius: 5px;box-shadow: 2px 2px 5px rgba(0,0,0,0.2);
    margin-top: 20px;padding-bottom: 10px;margin-left: 200px;width: 1280px;height: 600px">
      <ul class="nav" style="margin-left: 20px" >
        <li @click="details" ref="listItem" style="color: #45B984;border-bottom: 2px solid #45B984">盘点明细</li>
        <li @click="instock" ref="lisin">入库记录</li>
        <li @click="outstock" ref="lisout">出库记录</li>
        <li @click="operate" ref="listop">操作记录</li>
      </ul>

     <!-- 盘点明细小窗口-->
      <div  v-show="open" style="border-top: 1px solid #eeeeee;padding-top: 40px;padding-left: 30px;margin-top: 74px">
        <el-table style="margin-top: 20px" :data="InventoryDetails[0]&&InventoryDetails[0].inventoryDetailsList"  max-height="520px" @selection-change="handleSelectionChange">
          <el-table-column type="selection" fixed="left" width="55" align="center" />
          <el-table-column label="序号"  fixed="left" prop="isId" width="120" />
          <el-table-column label="货品名称" fixed="left" prop="goods.gname"  width="100" />
          <el-table-column label="货品编号"  fixed="left" align="center" prop="goods.gcode" />
          <el-table-column label="规格型号" align="center" prop="goods.specCode" />
          <el-table-column label="单位" align="center" prop="stock.unit">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.g_unit_goods" :value="scope.row.stock.unit"/>
            </template>
          </el-table-column>
          <el-table-column label="账面库存"  align="center" prop="stock.itemQuantity"/>
          <el-table-column label="账面金额" align="center" width="100px">
            <template slot-scope="scope">
              {{ handlerCountAmount(scope.row) }}
            </template>
          </el-table-column>
          <el-table-column label="盘点仓位" align="center" prop="" width="100px">
<!--            <template slot-scope="scope">-->
<!--              <el-input v-model="scope.row.sl_name" readonly ></el-input>-->
<!--            </template>-->
          </el-table-column>
          <el-table-column label="盘点数量" align="center" width="100px">
            <template slot-scope="scope">
              <el-input  v-model="scope.row.countQuantity" ></el-input>
            </template>
          </el-table-column>
          <el-table-column label="盘点金额" align="center" prop="countAmount"/>
          <el-table-column label="盈亏数量" align="center" prop="">
            <template slot-scope="scope">
              {{ profitLossQuantity(scope.row.countQuantity, scope.row.stock.itemQuantity) }}
            </template>
          </el-table-column>

          <el-table-column label="盘点状态"  align="center" prop="isStatus" width="100px">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.pd_status_check" :value="scope.row.isStatus"/>
            </template>
          </el-table-column>
          <el-table-column label="入库单价" align="center"  prop="goods.wrPrice" width="100px"/>
          <el-table-column label="盈亏金额" align="center" prop="">
            <template slot-scope="scope">
              {{ profitLossAmount(scope.row.countQuantity, scope.row.goods.wrPrice) }}
            </template>
          </el-table-column>
          <el-table-column label="备注" align="center" prop="remark" width="140px"/>
        </el-table>

      </div>




<!--入库记录-->
      <div  v-show="openIn" style="border-top: 1px solid #eeeeee;padding-top: 40px;padding-left: 30px;margin-top: 74px">
        <el-table style="margin-top: 20px"  v-loading="loading"  max-height="520px" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="序号"  prop="sl_id" width="120" />
          <el-table-column label="入库单号"  prop="sl_code" :show-overflow-tooltip="true" width="150" />
          <el-table-column label="入库日期"  prop="sl_code" :show-overflow-tooltip="true" width="150" />
          <el-table-column label="仓库名称"  prop="sl_name" :show-overflow-tooltip="true" width="150" />
          <el-table-column label="入库货品" align="center" prop="location_capacity" />
          <el-table-column label="入库数量" align="center" prop="manager" />
          <el-table-column label="入库金额" align="center" prop="sort" />
          <el-table-column label="经办人" align="center" prop="sort" />
          <el-table-column label="制单人" align="center" prop="sort" />
          <el-table-column label="所在部门" align="center" prop="sort" />
          <el-table-column label="制单时间" align="center" prop="sort" />
        </el-table>
      </div>



      <!--      出库记录-->
      <div  v-show="openOut" style="border-top: 1px solid #eeeeee;padding-top: 40px;padding-left: 30px;margin-top: 74px">
        <el-table style="margin-top: 20px"  v-loading="loading"  max-height="520px" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="序号"  prop="sl_id" width="120" />
          <el-table-column label="出库单号"  prop="sl_code" :show-overflow-tooltip="true" width="150" />
          <el-table-column label="出库日期"  prop="sl_code" :show-overflow-tooltip="true" width="150" />
          <el-table-column label="仓库名称"  prop="sl_name" :show-overflow-tooltip="true" width="150" />
          <el-table-column label="出库货品" align="center" prop="location_capacity" />
          <el-table-column label="出库数量" align="center" prop="manager" />
          <el-table-column label="出库金额" align="center" prop="sort" />
          <el-table-column label="经办人" align="center" prop="sort" />
          <el-table-column label="制单人" align="center" prop="sort" />
          <el-table-column label="所在部门" align="center" prop="sort" />
          <el-table-column label="制单时间" align="center" prop="sort" />
        </el-table>
      </div>


      <!-- 操作记录小窗口-->
      <div  v-show="openOperate"  style="border-top: 1px solid #eeeeee;padding-top: 40px;padding-left: 30px;margin-top: 74px">
        <el-form :model="queryParams"  ref="queryForm" size="small" :inline="true"  label-width="68px">
          <el-form-item label="操作人" >
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
        <el-table style="margin-top: 50px" :row-class-name="operateIndex" :data="operateList" v-loading="loading"  max-height="520px" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="操作人"  prop="sl_code" :show-overflow-tooltip="true" width="150" />
          <el-table-column label="操作时间" align="center" prop="manager" />
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
                   plain size="medium"  @click="cancle" >关闭</el-button>
      </el-footer>
    </div>



  </div>
</template>
<script>

import { delWarehouse, listStorage} from "@/api/wms/warehouse/warehouse.js";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {listInventory} from "@/api/wms/warehouse/InventorySheet/inventory";

export default {
  components: {Treeselect},
  dicts: ['wh_status','sys_normal_disable','pd_status_check','g_unit_goods'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 是否显示弹出层
      open:false,
      openOut:false,
      openIn:false,
      openOperate:false,
      // 选中数组
      ids: [],
      // 非多个禁用
      multiple: true,
      //明细：isId
      isId:undefined,
      //盘点单详情数据
      InventoryDetails:[],
      // // 盘点明细表格数据
      // detailsList:[],
      //操作记录表格数据
      operateList:[],
      // 总条数
      total: 0,
      sl_names:[],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        sl_name: null,
        status: null,
        position_manager: null,
        w_id:null,
        createTimes:[],
      },

    }
  },
  computed: {
  },
  watch: {},
  created() {
    // this.locations();

  },
  mounted() {
    this.isId=this.$route.params.isId;
    this.getDetailsByIsId();
    // this.getStorageList();
    this.open=true;
  },
  updated(){
  },
  //
  methods: {
    //根据isId查询盘点单
    getDetailsByIsId(){
      listInventory({"isId":this.isId}).then(response => {
        this.InventoryDetails = response.rows;
        console.log("InventoryDetails",this.InventoryDetails)
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
      this.sl_names=selection.map(item => item.sl_name)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    //关闭窗口
    cancle(){
      this.$router.push(`/warehousemanager`);
    },
    // //查询仓库下的所有库位
    // getStorageList(){
    //   this.loading = true;
    //   this.queryParams.w_id=this.w_id;
    //   listStorage(this.queryParams).then(response => {
    //     this.DetailsList = response.rows;
    //     this.total = response.total;
    //     this.loading = false;
    //   });
    // },
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

    //账面金额
    handlerCountAmount(row){
      let wr = row.goods.wrPrice || 0;
      let iq = row.stock.itemQuantity || 0;
      let ca = wr * iq;
      row.ca = ca;
      return ca.toFixed(2);
    },

    //盈亏数量
    profitLossQuantity(countQuantity, itemQuantity) {
      return countQuantity - itemQuantity || 0;
    },
    //盈亏金额
    profitLossAmount(profitlossQuantity,wrPrice){
      return profitlossQuantity||0 * wrPrice||0;
    },



    /** 删除按钮操作 */
    handleDelete() {
      const w_ids = this.w_id;
      this.$modal.confirm('是否确认删除仓库编号为"' + w_ids + '"的数据项？').then(function() {
        return delWarehouse(w_ids);
      }).then(() => {
        this.$modal.msgSuccess("删除成功");
        this.$router.push(`/warehousemanager`);
      }).catch(() => {});
    },
    //盘点明细
    details(){
      this.open = true;
      this.openOperate = false;
      this.openOut=false;
      this.openIn=false;
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
    instock(){
      this.openIn=true;
      this.open = false;
      this.openOperate = false;
      this.openOut=false;
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
    outstock(){
      this.openOut=true;
      this.openIn=false;
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
    operate(){
      this.openOperate = true;
      this.open = false;
      this.openOut=false;
      this.openIn=false;
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
    operateIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
  }
}

</script>
<style scoped>
::v-deep{
  .el-form-item__content>span{
    color: #606266;
    margin-left: 40px;
  }
  .nav>li{
    float: left;
    list-style: none;
    font-size: 16px;
    padding-left: 33px;
    width: 137px;
    height: 58px;
  }
  .nav>li:hover{
    cursor: pointer;
  }
}

</style>
