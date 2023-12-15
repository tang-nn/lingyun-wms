<template>
  <div class="app-container" style="background-color: #F3F6F8">

    <el-row :gutter="20" style="background-color: white;border-radius: 5px;box-shadow: 2px 2px 5px rgba(0,0,0,0.2);
   padding-bottom: 10px;margin-left: 200px;width: 1280px">
      <h3 style="margin-left: 20px">基础信息</h3>
      <div style="border-top: 1px solid #eeeeee;padding-top: 30px;padding-left: 30px">
        <el-form ref="elForm" :model="formData" label-width="95px" size="medium">
          <el-row>
            <el-col :span="9">
              <el-form-item label="盘点单号" prop="isCode">
                <el-input v-model="formData.isCode" :style="{width: '100%'}" clearable placeholder="自动获取系统编码"readonly>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="9" style="margin-left: 180px">
              <el-form-item label="盘点类型">
                <el-select :style="{width: '100%'}"
                  v-model="queryParams.isType"
                  placeholder="请选择"
                  clearable
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
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="9">
              <el-form-item label="盘点开始时间" prop="isStartTime" label-width="100">
                <el-date-picker clearable
                                v-model="formData.isStartTime"
                                type="date"
                                value-format="yyyy-MM-dd mm:HH:ss"
                                placeholder="请选择盘点开始时间">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="10" style="margin-left: 180px">
              <el-form-item label="盘点结束时间" prop="isStartTime" label-width="100">
                <el-date-picker clearable
                                v-model="formData.isEndTime"
                                type="date"
                                value-format="yyyy-MM-dd mm:HH:ss"
                                placeholder="请选择盘点结束时间">
                </el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="9">
              <el-form-item label="仓库名称" prop="wId">
                <el-select :style="{width: '100%'}"
                  v-model="queryGoodsParams.wId"
                  placeholder="请选择"
                  clearable
                  style="width: 240px"
                  @change="handleSelectChange"
                >
                  <el-option
                    v-for="wn in wareNameList"
                    :key="wn.w_id"
                    :label="wn.w_name"
                    :value="wn.w_id"
                  />
                </el-select>
              </el-form-item>
              <el-link type="primary" @click="gotoaddwarehouse" style="position: relative;left: 491px;top: -48px  ">设置</el-link>
            </el-col>
            <el-col :span="9" style="margin-left: 180px">
              <el-form-item label="经办人" prop="usera" >
                <el-input
                  v-model="formData.usera"
                  placeholder="请输入"
                  clearable
                  @focus="handleUsera"
                  suffix-icon="el-icon-more"
                />
              </el-form-item>
            </el-col>

          </el-row>
          <el-row>
            <el-col :span="19">
              <el-form-item label="备注" prop="remark">
                <el-input v-model="formData.remark" :autosize="{minRows: 4, maxRows: 4}" :style="{width: '100%'}"
                          placeholder="请输入" type="textarea"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </el-row>

    <el-row :gutter="20" style="background-color: white;border-radius: 5px;box-shadow: 2px 2px 5px rgba(0,0,0,0.2);
    margin-top: 20px;padding-bottom: 10px;margin-left: 200px;width: 1280px;height: 700px ">
      <h3 style="margin-left: 20px">盘点明细</h3>
      <div style="border-top: 1px solid #eeeeee;padding-top: 20px;padding-left: 30px">

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              v-hasPermi="['gd:good:add']"
              icon="el-icon-plus"
              plain
              size="mini"
              type="primary"
              @click="addGoodsPage"
            >添加</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              v-hasPermi="['gd:good:delete']"
              :disabled="multiple"
              icon="el-icon-minus"
              plain
              size="mini"
              type="danger"
              @click="removeItems"
            >移除</el-button>
          </el-col>
        </el-row>

        <el-table :loading="loading" style="margin-top: 50px"
                  :data="inventorysheetInf.selectGoods"
                  max-height="520px"
                  @selection-change="handleSelectionChange"
                  :row-class-name="rowStorageLocationIndex">
          <el-table-column type="selection" fixed="left" width="55" align="center" />
          <el-table-column label="序号" fixed="left" align="center" prop="index" width="50"/>
          <el-table-column label="货品名称" fixed="left" prop="g_name"  width="100" />
          <el-table-column label="货品编号" fixed="left" align="center" prop="g_code" />
          <el-table-column label="规格型号" align="center" prop="spec_code" />
          <el-table-column label="单位" align="center">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.g_unit_goods" :value="scope.row.unit"/>
            </template>
          </el-table-column>
          <el-table-column label="货品类型"   align="center" width="100" prop="gt_name" />
          <el-table-column label="账面库存"  :formatter="itemQuantity"  align="center" prop="item_quantity"/>
          <el-table-column label="账面金额" align="center" width="100px">
            <template slot-scope="scope">
              {{ handlerCountAmount(scope.row) }}
            </template>
          </el-table-column>
          <el-table-column label="盘点仓位" align="center" prop="sl_name" width="100px">
            <template slot-scope="scope">
              <el-input v-model="scope.row.sl_name" readonly ></el-input>
            </template>
          </el-table-column>
          <el-table-column label="盘点数量" align="center" prop="countQuantity" width="100px">
            <template slot-scope="scope">
              <el-input  v-model="scope.row.countQuantity" @blur="countQuantity(scope.row)"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="盘点金额" align="center" prop="countAmount"/>
          <el-table-column label="盈亏数量" align="center" prop="profitLossQuantity"/>
          <el-table-column label="盘点状态"  align="center" prop="isStatus" width="100px">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.pd_status_check" :value="scope.row.isStatus"/>
            </template>
          </el-table-column>
          <el-table-column label="入库单价" align="center" :formatter="wrPrice"  prop="wr_price" width="100px">
            <template slot-scope="scope">
              <el-input v-model="scope.row.wr_price" ></el-input>
            </template>
          </el-table-column>
          <el-table-column label="盈亏金额" prop="profitLossAmount" align="center"/>
          <el-table-column label="备注" align="center" prop="remark" width="140px">
            <template slot-scope="scope">
              <el-input v-model="scope.row.remark" ></el-input>
            </template>
          </el-table-column>
          <el-table-column align="center"  class-name="small-padding fixed-width" fixed="right" label="操作">
            <template slot-scope="scope" >
              <el-button
                v-hasPermi="['system:role:edit']"
                icon="el-icon-edit"
                size="mini"
                type="text"
                @click="removeItems(scope.row)"
              >移除</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div id="count" style="border:1px solid  #eeeeee;height: 38px">
           <el-row>
             <span>合计</span>
             <el-col >
               <div class="down" style="float:right;margin-right: 5px;margin-top:-14px;font-size: 14px">
                 <span>账面库存：<em :formatter="totalItemQuantity"></em></span>
                 <span>账面金额：<em ></em></span>
                 <span>盘点数量：<em :formatter="totalcountQuantity"></em></span>
                 <span>盘盈数量：<em></em></span>
                 <span>盘亏数量:<em></em></span>
                 <span>盘盈金额：:<em></em></span>
                 <span>盘亏金额：:<em></em></span>
               </div>
             </el-col>


           </el-row>


        </div>

<!--        <pagination-->
<!--          v-show="storageListAdd.length>0"-->
<!--          :total="storageListAdd.length"-->
<!--          :page.sync="queryParams.pageNum"-->
<!--          :limit.sync="queryParams.pageSize"-->
<!--          @pagination="storageListAdd"-->
<!--        />-->
      </div>
    </el-row>


    <div class="footer-container" style=" position: fixed;
          box-shadow: 2px 2px 5px rgba(0,0,0,0.9);  background-color:white;border-radius: 2px;
          width: 89.5%; height: 60px; bottom: 0; right: 0;  right: 0;z-index: 1000">
      <el-footer>
        <!-- 底部导航栏内容 -->
        <el-button  v-hasPermi="['gd:good:add']"
                    plain
                    size="medium" style="float:right;margin-top: 12px"
                    type="success" @click="submitForm" >保存</el-button>
        <el-button
          plain size="medium" style="float:right;margin-right: 20px;margin-top: 12px" @click="cancle" >关闭</el-button>
      </el-footer>
    </div>




    <!--    经办热对话框-->
    <el-dialog :title="title" :visible.sync="openUsera" width="420px" append-to-body>
      <div class="head-container">
        <el-input
          v-model="userdga"
          placeholder="请搜索"
          clearable
          size="small"
          suffix-icon="el-icon-search"
          style="margin-bottom: 20px"
        />
      </div>
      <div class="head-container" style="width:379px">
        <el-table :data="userLista" @row-click="handleUseraClick">
          <el-table-column class="bg" align="center" prop="userName" width="379px"/>
        </el-table>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="okUsera">确 定</el-button>
        <el-button @click="cancleUsera">取 消</el-button>
      </div>
    </el-dialog>


<!--添加货品窗口-->
    <el-dialog :visible.sync="dialogTableVisible" title="添加货品" width="750px">
      <el-form ref="queryGoodsForm" :inline="true" :model="queryGoodsParams" size="small">
        <el-form-item label-width="86px" prop="codeOrName">
          <el-input
            v-model.trim="queryGoodsParams.codeOrName"
            clearable
            placeholder="请输入货品编号/名称"
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item>
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
        v-show="goodsTotal>0"
        :limit.sync="queryGoodsParams.pageSize"
        :page.sync="queryGoodsParams.pageNum"
        :total="goodsTotal"
        @pagination="getGoodsList"
      />
      <el-button type="primary" @click="handleGoodsDefine">确定</el-button>
      <el-button @click="closeGoodsSelect">取消</el-button>
    </el-dialog>
  </div>
</template>
<script>

import { MessageBox } from "element-ui";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { listUser} from "@/api/system/user";
import {listWarehouse} from "@/api/wms/warehouse/warehouse.js";
import { listGoodByWid,addInventory} from "@/api/wms/warehouse/InventorySheet/inventory.js";

export default {
  components: {Treeselect},
  dicts: ['sys_normal_disable','pd_type_check','g_unit_goods','pd_status_check'],
  props: [],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 弹出层标题
      title: "",
      ids:[],
      //经办人弹出框
      openUsera:false,
     //经办人name
      userdga:undefined,
      //经办人id
      userIda:undefined,
      //经办人数据
      userLista:[],
      //仓库名称数据
      wareNameList:[],
      dialogTableVisible: false,
      //货品表格数据
      goodsList: [],
      goodsTotal: 0,
      //选中货品数据
      tempSelectGoodsList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deptName:undefined,

      },
      queryGoodsParams: {
        pageNum: 1,
        pageSize: 5,
        codeOrName: undefined,
        wn:undefined,
      },
      inventorysheetInf: {
        selectGoods: [],
      },
      // 表单参数
      form: {
      },
      // 非多个禁用
      multiple: true,
      defaultProps: {
        children: "children",
        label: "label"
      },
      formData: {

      },
    }
  },
  computed: {
  },
  watch: {},
  created() {
    this.getUsera();
    this.wareName();

    this.inventorysheetInf = {
      selectGoods
    };
  },
  mounted() {},
  methods: {
    /** 库位信息序号 */
    rowStorageLocationIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.index)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    //查询仓库名称集合
    wareName(){
      listWarehouse().then(response => {
        this.wareNameList = response.rows;
      });
    },
    /*查询经办人*/
    getUsera(){
      listUser().then(response => {
        this.userLista = response.rows;
      })
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // // 节点单击事件
    handleUseraClick(row){
      this.userdga= row.userName;
      this.userIda=row.userId;
    },

    handleUsera(){
      this.openUsera=true;
      this.title = "选择人员";
    },
    //确定选中人员
    okUsera(){
      this.formData.usera=this.userdga;
      this.openUsera=false;
    },
    //取消人员显示框
    cancleUsera(){
      this.openUsera=false;
    },
    //关闭窗口
    cancle(){
      this.$router.push(`/InventorySheet`);
    },

    //仓库名称下拉事件
    handleSelectChange(e){
      let wh = this.wareNameList.filter(function (w){
        return w.w_id === e;
      })[0];
      if (wh.status !== 2){
        this.queryGoodsParams.wId=null;
        MessageBox.confirm("该仓库暂未锁定，请锁定后再进行盘点。", "提示", {
          confirmButtonText: "去锁库",
          cancelButtonText: "取消",
          type: "warning",
        })
          .then(() => {
            this.$router.push(`/warehousemanager`);
          })
          .catch(() => {
            MessageBox.close(); // 关闭对话框
          });
      }
      // this.$set(this.inventorysheetInf, 'selectGoods', null);
    },

    //盘点数量失去焦点获取信息
    countQuantity(row){
      row.countQuantity = parseFloat(row.countQuantity);//盘点数量
      //获取账面库存
      let iq=this.itemQuantity(row);
      //获取入库单价
      let wp=this.wrPrice(row);
      //计算盈亏数量（盘点数量-账面库存）
      row.profitLossQuantity=row.countQuantity-iq;
      //计算盈亏金额（入库单价*盈亏数量）
      row.profitLossAmount=wp*row.profitLossQuantity;
      //盘点金额
      row.countAmount=row.countQuantity*wp;
      //盘点状态
      row.isStatus = row.profitLossQuantity > 0 ? 0 : row.profitLossQuantity == 0 ? 2 : 1;
    },
    //账面库存数量
    itemQuantity(row){
      return row.item_quantity ||'无货';
    },

    //入库单价
    wrPrice(row){
      return row.wr_price.toFixed(2);
    },

    //打开货品窗口
    addGoodsPage(){
      if(this.queryGoodsParams.wId!=undefined){
        this.dialogTableVisible = true;
        this.getGoodsList();
      }else {
        this.$modal.msgWarning("请选择仓库!");
      }
    },
    //货品确定按钮
    handleGoodsDefine() {
      this.inventorysheetInf.selectGoods = this.tempSelectGoodsList;
      console.log(" this.inventorysheetInf.selectGoods", this.inventorysheetInf.selectGoods)
      this.closeGoodsSelect();
    },
    //货品取消按钮
    closeGoodsSelect() {
      this.dialogTableVisible = false;
      this.tempSelectGoodsList = this.inventorysheetInf.selectGoods;
    },
    //货品选中按钮
    handlerSelectionChange(row) {
      this.tempSelectGoodsList = row;
    },
    gotoaddwarehouse(){
      this.$router.push('/addwarehouse');
    },

    //移除盘点明细
    removeItems(row) {
      let indexs = row.index || this.ids;
      if(!Array.isArray(indexs)){
        indexs = [indexs];
      }
      console.info("indexs: ", indexs);
      this.$modal.confirm('是否确认删除盘点明细编号为"' + indexs + '"的数据项？').then(()=> {
        this.inventorysheetInf.selectGoods = this.inventorysheetInf.selectGoods?.filter(e=>!indexs.includes(e.index));
      }).then(() => {
        this.$message.success("删除成功");
      }).catch(e => {
      });
    },

    //账面金额
    handlerCountAmount(row) {
      let wr = row.wr_price || 0;
      let iq = row.item_quantity || 0;
      let ca = wr * iq;
      row.ca = ca;
      return ca.toFixed(2);
    },

    //总计


    // 表单重置
    reset() {
      this.form = {
        status: "0",
        slName: undefined,
        locationCapacity: undefined,
        remark: undefined,
        sort:undefined,
        usera: undefined
      };
      this.resetForm("form");
    },

    getGoodsList() {
      this.loading = true;
      listGoodByWid({
        g_name: this.queryGoodsParams.codeOrName,
        unit: this.queryGoodsParams.unit,
        w_id:this.queryGoodsParams.wId,
        pageNum: this.queryGoodsParams.pageNum,
        pageSize: this.queryGoodsParams.pageSize,
      }).then(response => {
          this.goodsList = response.rows;
        console.log("this.goodsList",this.goodsList);
          this.goodsTotal = response.total;
          this.$nextTick(() => {
            console.log("nextTick selectGoods: ", this.inventorysheetInf.selectGoods)
            this.inventorysheetInf.selectGoods.map(vl => {
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
    /** 删除按钮操作 */
    handleDelete(row) {
      let indexs = row.index || this.ids;
      if(!Array.isArray(indexs)){
        indexs = [indexs];
      }
      console.info("indexs: ", indexs);
      this.$modal.confirm('是否确认删除库位编号为"' + indexs + '"的数据项？').then(()=> {
        this.storageListAdd = this.storageListAdd?.filter(e=>!indexs.includes(e.index));
      }).then(() => {
        this.$message.success("删除成功");
        console.log("this.storageListAdd: ", this.storageListAdd);
      }).catch(e => {
      });
    },
    /*最终添加*/
    submitForm() {
      this.$refs['elForm'].validate(valid => {
        if (valid) {
          this.formData.isManager=this.userIda;
          this.formData.isType=this.queryParams.isType;
          this.formData.wId=this.queryGoodsParams.wId;
          this.formData.inventoryDetailsList=this.inventorysheetInf.selectGoods;
          // this.formData.inventoryDetailsList.add("s_id",)
          console.log("this.formData，",this.formData)
          addInventory(this.formData).then(response =>{
            this.$modal.msgSuccess("新增成功");
            this.$router.push(`/warehousemanager`);

          })

        }

      })

    },
    resetForm() {
    },


  },






}

</script>
<style scoped>
::v-deep{
  .el-drawer__body{
    margin-left: 27px;
  }
  .demo-drawer__content .el-textarea__inner{
    width: 510px;
    height: 100px
  }
.down>span{
  margin-right: 35px;

}
  .down em{
    color: #ffae00;
  }
}
</style>
