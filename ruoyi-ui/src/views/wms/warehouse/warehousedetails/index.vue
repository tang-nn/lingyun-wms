<template>
  <div class="app-container" style="background-color: #F3F6F8">

    <el-row :gutter="20" style="background-color: white;border-radius: 5px;box-shadow: 2px 2px 5px rgba(0,0,0,0.2);
   padding-bottom: 10px;margin-left: 200px;width: 1280px">
      <h4 style="margin-left: 20px;font-weight: bold;font-size: 18px">{{warehousedetails[0]&&warehousedetails[0].w_name}} : {{warehousedetails[0]&&warehousedetails[0].w_code}}</h4>
    <div style="position: relative;top: -50px;left: -26px">
      <el-button  style="float:right;margin-right: 5px;margin-top: 12px;padding-right: 14px;margin-left: 14px"
                  plain size="small"
                  @click="" v-hasPermi="['gd:good:add']" >编辑</el-button>
      <el-button  style="float:right;margin-right: 5px;margin-top: 12px;padding-right: 16px"
                  plain size="small"
                  @click="handleDelete"  v-hasPermi="['wh:house:delete']">删除</el-button>
<!--      <el-button  style="float:right;margin-right: 5px;margin-top: 12px;padding-right: 17px"-->
<!--                  plain size="small"-->
<!--                  @click="" v-hasPermi="['gd:good:add']" >停用</el-button>-->
<!--      <el-button  style="float:right;margin-right: 5px;margin-top: 12px;"-->
<!--                  plain size="small"-->
<!--                  @click="" v-hasPermi="['gd:good:add']" >锁定</el-button>-->
    </div>
      <div style="padding-top: 30px;padding-left: 30px">
        <el-form ref="elForm" size="medium" label-width="95px" style="margin-top: 14px">
          <el-row>
            <el-col :span="6">
              <el-form-item label="提交人">
                <span>{{warehousedetails[0]&&warehousedetails[0].oneuser}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="操作时间">
                <span>
                  {{parseTime(warehousedetails[0]&&warehousedetails[0].create_time)}}
                </span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label=更新时间>
                <span>{{parseTime(warehousedetails[0]&&warehousedetails[0].update_time)}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row style="float: right;margin-right:30px;margin-top: -58px;color: #606266">
            <el-col :span="2">
              <el-form-item label="状态"/>
            </el-col>
          </el-row>

          <el-row style="width:100px;font-size:20px;font-weight:bolder;float: right;margin-right:-22px;margin-top: -5px;color: #606266">
            <el-col :span="10">
              <template>
                {{ warehousedetails[0]&&warehousedetails[0].status === 0 ? '启用' : '停用' }}
              </template>
            </el-col>
          </el-row>



          <el-row>
            <el-col :span="6">
              <el-form-item label="仓库编号">
                <span>{{warehousedetails[0]&&warehousedetails[0].w_code}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="仓库名称">
                <span>{{warehousedetails[0]&&warehousedetails[0].w_name}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="仓库容量/m³">
                <span>{{warehousedetails[0]&&warehousedetails[0].w_capacity}}</span>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row style="float: right;margin-right:26px;margin-top:20px;color: #606266">
            <el-col :span="2">
              <el-form-item label="库位数量">
              </el-form-item>
            </el-col>
          </el-row>

          <el-row style="font-size:20px;font-weight:bolder;float: right;margin-right:-69px;margin-top: 54px;color: #606266">
            <el-col :span="6">
              <template>
                {{warehousedetails[0]&& warehousedetails[0].storageNum}}
              </template>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6">
              <el-form-item label="库管部门">
                <span>{{warehousedetails[0]&&warehousedetails[0].deptName}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="仓库主管">
                <span>{{warehousedetails[0]&&warehousedetails[0].supervisorUser}}</span>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="联系电话">
                <span>{{warehousedetails[0]&&warehousedetails[0].tel}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="仓库地址">
                <span>{{warehousedetails[0]&&warehousedetails[0].w_address}}</span>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <el-form-item label="备注">
                <span>{{warehousedetails[0]&&warehousedetails[0].remark}}</span>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <div style="border-top: 1px solid #eeeeee;">
        <el-form ref="elForm" size="medium" label-width="95px" style="margin-top: 14px">
        <el-row>
          <el-col :span="3">
            <el-form-item label="创建人">
              <span>{{warehousedetails[0]&&warehousedetails[0].oneuser}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="创建时间">
              <span>{{parseTime(warehousedetails[0]&&warehousedetails[0].create_time)}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="3">
            <el-form-item label="更新人">
              <span>{{warehousedetails[0]&&warehousedetails[0].twouser}}</span>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="更新时间">
              <span>{{parseTime(warehousedetails[0]&&warehousedetails[0].update_time)}}</span>
            </el-form-item>
          </el-col>
        </el-row>
        </el-form>
      </div>

    </el-row>

    <el-row :gutter="20" style="background-color: white;border-radius: 5px;box-shadow: 2px 2px 5px rgba(0,0,0,0.2);
    margin-top: 20px;padding-bottom: 10px;margin-left: 200px;width: 1280px;height: 600px">
      <ul class="nav" style="margin-left: 20px" >
        <li @click="locations" ref="listItem" style="color: #45B984;border-bottom: 2px solid #45B984">仓位信息</li>
        <li @click="operate" ref="listop">操作记录</li>
      </ul>

     <!-- 仓位信息小窗口-->
      <div  v-show="open" style="border-top: 1px solid #eeeeee;padding-top: 40px;padding-left: 30px;margin-top: 74px">
        <el-form :model="queryParams"  ref="queryForm" size="small" :inline="true"  label-width="68px">
          <el-form-item label="仓位" >
            <el-input
              v-model="queryParams.sl_name"
              placeholder="请输入仓位名称"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="仓库状态" >
            <el-select
              v-model="queryParams.status"
              placeholder="请选择"
              clearable
              style="width: 240px"
            >
              <el-option
                v-for="dict in dict.type.sys_normal_disable"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>

          <el-form-item label="库库主管" >
            <el-input
              v-model="queryParams.position_manager"
              placeholder="请输入"
              clearable
              @keyup.enter.native="handleQuery"
              suffix-icon="el-icon-more"
            />
          </el-form-item>
          <el-form-item style="margin-left: 150px">
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
<!--          <el-col :span="1.5">-->
<!--            <el-button-->
<!--              plain-->
<!--              icon="el-icon-download"-->
<!--              size="mini"-->
<!--              @click="handleExport"-->
<!--            >导出</el-button>-->
<!--          </el-col>-->
          <el-col :span="1.5">
            <el-button
              plain
              type="success"
              icon="el-icon-circle-check"
              size="mini"
              :button-value="buttonValuez"
              :disabled="multiple"
              @click="changeWareHouseStatusz"
              v-hasPermi="['wh:house:start']"
            >启用</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              plain
              type="danger"
              icon="el-icon-circle-close"
              size="mini"
              :button-value="buttonValueo"
              :disabled="multiple"
              @click="changeWareHouseStatuso"
              v-hasPermi="['wh:house:disadble']"
            >停用</el-button>
          </el-col>
        </el-row>
        <el-table style="margin-top: 50px" :data="storageList" v-loading="loading"  max-height="520px" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="序号"  prop="sl_id" width="120" />
          <el-table-column label="库位编号"  prop="sl_code" :show-overflow-tooltip="true" width="150" />
          <el-table-column label="库位名称"  prop="sl_name" :show-overflow-tooltip="true" width="150" />
          <el-table-column label="库位容量/m³" align="center" prop="location_capacity" />
            <el-table-column label="仓位主管" align="center" prop="manager" />
          <el-table-column label="排序" align="center" prop="sort" />
          <el-table-column label="库位状态"   align="center" width="100" >
            <template slot-scope="scope">
              {{ scope.row.status === 0 ? '启用' : '停用' }}
            </template>
          </el-table-column>
          <el-table-column label="备注" align="center" prop="remark"/>
        </el-table>
        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getStorageList"
        />
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
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
        </el-row>
        <el-table style="margin-top: 50px" :row-class-name="operateIndex" :data="operateList" v-loading="loading"  max-height="520px" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
          <el-table-column label="操作人"  prop="sl_code" :show-overflow-tooltip="true" width="150" />
          <el-table-column label="操作时间" align="center" prop="manager" />
        </el-table>
        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getStorageList"
        />
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

import {changeLocationStatus, delWarehouse, getWarehouse, listStorage} from "@/api/wms/warehouse/warehouse.js";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  components: {Treeselect},
  dicts: ['wh_status','sys_normal_disable'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 是否显示弹出层
      open:false,
      openOperate:false,
      // 选中数组
      ids: [],
      // 非多个禁用
      multiple: true,
      //详情w_id
      w_id:undefined,
      //仓库详情数据
      warehousedetails:[],
      // 仓库库位表格数据
      storageList:[],
      //操作记录表格数据
      operateList:[],
      //选中多个库位id值
      sl_ids:[],
      // 总条数
      total: 0,
      //库位状态
      buttonValuez:0,
      buttonValueo:1,
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
    this.locations();
  },
  mounted() {
    this.w_id=this.$route.params.w_id;
    this.getDetailsByWid();
    this.getStorageList();
  },
  updated(){
  },
  //
  methods: {
    //根据w_id查询仓库
    getDetailsByWid(){
      getWarehouse(this.w_id).then(response => {
        this.warehousedetails = response.data;
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
    //查询仓库下的所有库位
    getStorageList(){
      this.loading = true;
      this.queryParams.w_id=this.w_id;
      listStorage(this.queryParams).then(response => {
        this.storageList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getStorageList();
    },
    /** 重置按钮操作 */
    resetQuery() {
       this.queryParams.sl_name=null,
       this.queryParams.status=null,
      this.queryParams.position_manager=null,
      this.handleQuery();
    },
    //更改库位状态
    changeWareHouseStatusz(row) {
      const sl_ids =  this.ids;
      const sl_name = this.sl_names;
      var left =this;
      this.$modal.confirm('是否确认将"' + sl_name + '"启用？').then(function() {
        return changeLocationStatus(left.buttonValuez,sl_ids);
      }).then(() => {
        this.getStorageList();
        this.$modal.msgSuccess("操作成功");
      }).catch(() => {});
    },
    changeWareHouseStatuso(row) {
      const sl_ids =  this.ids;
      const sl_name = this.sl_names;
      var left =this;
      this.$modal.confirm('是否确认将"' + sl_name + '"停用？').then(function() {
        return changeLocationStatus(left.buttonValueo,sl_ids);
      }).then(() => {
        this.getStorageList();
        this.$modal.msgSuccess("操作成功");
      }).catch(() => {});
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
    //仓位信息
    locations(){
      this.open = true;
      this.openOperate = false;
      this.$refs.listItem.style.color = '#45B984';
      this.$refs.listItem.style.borderBottom = '2px solid #45B984';
      this.$refs.listop.style.color = '#000000';
      this.$refs.listop.style.borderBottom = 'none';
    },
    //操作记录
    operate(){
      this.openOperate = true;
      this.open = false;
      this.$refs.listop.style.color = '#45B984';
      this.$refs.listop.style.borderBottom = '2px solid #45B984';
      this.$refs.listItem.style.color = '#000000';
      this.$refs.listItem.style.borderBottom = 'none';
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
