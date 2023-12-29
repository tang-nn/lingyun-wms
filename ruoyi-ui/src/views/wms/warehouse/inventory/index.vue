<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="盘点单号" prop="is_code">
        <el-input
          v-model="queryParams.is_code"
          placeholder="请输入盘点单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="盘点结果">
        <el-select
          v-model="queryParams.is_result"
          placeholder="请选择"
          clearable
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
          v-model="queryParams.is_type"
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
      <el-form-item label="仓库名称" prop="w_id">
        <el-select
          v-model="queryParams.w_id"
          placeholder="请选择"
          clearable
          style="width: 240px"
        >
          <el-option
            v-for="wn in wareNameList"
            :key="wn.w_id"
            :label="wn.w_name"
            :value="wn.w_id"
          />

        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['InventorySheet:inventory:add']"
        >新增
        </el-button>
      </el-col>
<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="danger"-->
<!--          plain-->
<!--          icon="el-icon-delete"-->
<!--          size="mini"-->
<!--          :disabled="multiple"-->
<!--          @click="handleDelete"-->
<!--          v-hasPermi="['InventorySheet:inventory:remove']"-->
<!--        >删除-->
<!--        </el-button>-->
<!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-unlock"
          size="mini"
          @click="lock"
          v-hasPermi="['InventorySheet:inventory:export']"
        >锁定仓库
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-coordinate"
          size="mini"
          :disabled="single"
          @click="review"
          v-hasPermi="['transfer:transfer:edit']"
        >审核</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-table max-height="520"  v-loading="loading" :data="inventoryList" @selection-change="handleSelectionChange">
      <el-table-column fixed="left" type="selection" width="55" align="center"/>
      <el-table-column fixed="left" label="盘点ID" align="center" prop="isId"/>
      <el-table-column fixed="left" label="盘点单号" align="center" prop="isCode" width="140x">
        <template slot-scope="{ row }">
          <span @click="goToDetails(row.isId)">{{row.isCode}}</span>
        </template>
      </el-table-column>
      <el-table-column fixed="left" label="审核状态" align="center" prop="reviewStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.inventory_review_status" :value="scope.row.reviewStatus"/>
        </template>
      </el-table-column>
      <el-table-column fixed="left" label="盘点结果" align="center" prop="isResult">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.p_result_check" :value="scope.row.isResult"/>
        </template>
      </el-table-column>
      <el-table-column label="仓库名称" align="center" prop="whName"/>
      <el-table-column label="盘点类型" align="center" prop="isType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.pd_type_check" :value="scope.row.isType"/>
        </template>
      </el-table-column>
      <el-table-column label="盘点开始时间" prop="is_start_time" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.isStartTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="盘点结束时间" prop="is_end_time" width="100">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.isEndTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="出库状态" align="center">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.outbound_status" :value="scope.row.outStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="入库状态" align="center">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.inbound_status" :value="scope.row.inStatus"/>
        </template>
      </el-table-column>
      <el-table-column :show-overflow-tooltip="true" label="盘点货品" :formatter="handlerProductName" align="center" prop="gname"/>
      <el-table-column label="盘点数量" align="center" :formatter="handlerCountQuantity"/>
      <el-table-column :formatter="handlerProfitQuantity" label="盘盈数量" align="center"/>
      <el-table-column :formatter="handlerLossQuantity" label="盘亏数量" align="center"
                       prop="profit_loss_quantity" style="color: red"/>
      <el-table-column :formatter="handlerCountAmount" label="盘点金额"  align="center" width="100"/>
      <el-table-column :formatter="handlerProfitAmount" label="盘盈金额" align="center" prop="count_amount" width="100" style="color: #1ab394"/>
      <el-table-column :formatter="handlerLossAmount" label="盘亏金额" align="center" prop="count_amount" width="100" style="color: red"/>
      <el-table-column label="经办人" align="center" prop="managerName"/>
      <el-table-column label="制单人"  align="center" prop="creatorName"/>
      <el-table-column label="所在部门"  align="center" prop="creatorDept" width="140px"/>
      <el-table-column label="制单时间" align="center" prop="createTime" width="150px"/>
      <el-table-column label="操作" width="110" fixed="right" align="center" class-name="small-padding fixed-width">
        <template slot-scope="{ row }">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="row.reviewStatus === 'turn_down' || row.reviewStatus === 'pending_review'"
            @click="handleUpdate(row.isId)"
            v-hasPermi="['InventorySheet:inventory:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            v-if="row.reviewStatus === 'turn_down' || row.reviewStatus === 'pending_review'"
            @click="handleDelete(row)"
            v-hasPermi="['InventorySheet:inventory:remove']"
          >删除</el-button>
        </template>
      </el-table-column>

    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 审核 Form :label-width="" -->
    <el-dialog :visible.sync="reviewFormVisible" title="盘点审核">
      <el-form :model="reviewForm">
        <el-form-item label="审核结果">
          <el-radio v-model="reviewForm.reviewStatus" label="done">通过</el-radio>
          <el-radio v-model="reviewForm.reviewStatus" label="turn_down">驳回</el-radio>
        </el-form-item>
        <el-form-item label="审核意见">
          <el-input
            v-model="reviewForm.comments"
            :rows="3" placeholder="请输入内容"
            resize="none"
            type="textarea"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="reviewFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="handlerReview">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import {
  listInventory, reviewInventory, delInventory,
} from "@/api/wms/warehouse/InventorySheet/inventory.js";
import {listWarehouse} from "@/api/wms/warehouse/warehouse";

export default {
  name: "Inventory",
  dicts: ['outbound_status','inbound_status','p_result_check', 'pd_type_check', 'pd_status_check','inventory_review_status'],
  data() {
    return {
      // 审核模态框
      reviewFormVisible: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      reviewStatus:[],
      // 子表选中数据
      checkedInventoryDetails: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 盘点单表格数据
      inventoryList: [],
      // 盘点明细表格数据
      inventoryDetailsList: [],
      //仓库名称数据
      wareNameList:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
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
        is_delete: null
      },
      // 表单参数
      form: {},
      reviewForm:{},

    };
  },
  created() {
    this.getList();
    this.wareName();
  },
  methods: {
    /** 查询盘点单列表 */
    getList() {
      this.loading = true;
      console.log("this.queryParams",this.queryParams)
      listInventory(this.queryParams).then(response => {
        this.inventoryList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    //锁定仓库
    lock(){
      this.$router.push(`/warehousemanager`);
    },
    //审核
    review(){
      console.log("this.reviewStatus?.[0]",this.reviewStatus?.[0])
      if (this.reviewStatus?.[0] =="done"){
        this.$message.warning("该盘点单已通过审核！");
      }else {
        this.reviewFormVisible=true;
      }
    },
    async handlerReview() {
      console.log("handlerReview: ", this.reviewForm);
      this.loading = true;
      this.reviewForm.isId = this.ids?.[0];
      let {code, msg} = (await reviewInventory(this.reviewForm));
      if (code === 200) {
        this.$message.success("审核成功！");
        await this.getList();
        this.reviewFormVisible = false;
      } else {
        console.log("审核失败信息：", msg)
        this.$message.error("审核失败！");
        this.loading = false;
      }


    },
    //查询仓库名称集合
    wareName(){
      listWarehouse().then(response => {
        this.wareNameList = response.rows;
      });
    },
    //  处理表格货品展示
    handlerProductName(row) {
      let arr = row.inventoryDetailsList?.map((e) => (e?.goods?.gname || ''));
      return arr.slice(0, Boolean(arr.slice(-1)[0]) ? arr.length : -1).join("，");
    },
    // 计算盘点数量
    handlerCountQuantity(row) {
      return row.inventoryDetailsList?.reduce((accumulator, currentValue) => {
        return accumulator + (currentValue.countQuantity || 0);
      }, 0);
    },
    // 盘盈数量
    handlerProfitQuantity(row) {
      let pq = row.inventoryDetailsList?.reduce((accumulator, currentValue) => {
        let t = currentValue.profitLossQuantity || 0;
        return accumulator + (t > 0 ? t : 0);
      }, 0);
      row.pq = pq;
       return pq;
    },

    // 盘亏数量
    handlerLossQuantity(row) {
      let lq = row.inventoryDetailsList?.reduce((accumulator, currentValue) => {
        let t = currentValue.profitLossQuantity || 0;
        return accumulator + (t < 0 ? t : 0);
      }, 0);
      row.lq = lq;
      return lq;
    },
    // 盘点总金额
    handlerCountAmount(row){
      return row.inventoryDetailsList?.reduce((accumulator, currentValue) => accumulator + (currentValue.countAmount || 0), 0).toFixed(2);
    },
    //盘盈金额
    handlerProfitAmount(row) {
      return row.inventoryDetailsList?.reduce((accumulator, currentValue)=> (currentValue.goods.wrPrice || 0) * row.pq, 0).toFixed(2);
    },

    //盘亏金额
    handlerLossAmount(row){
      return row.inventoryDetailsList?.reduce((accumulator, currentValue) =>
       accumulator + (currentValue.goods.wrPrice || 0) * row.lq, 0).toFixed(2);
    },
    // 表单重置
    reset() {
      this.form = {
        isId: null,
        isCode: null,
        isResult: null,
        wId: null,
        isType: null,
        isStartTime: null,
        isEndTime: null,
        outStatus: null,
        inStatus: null,
        remark: null,
        isManager: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        isDelete: null
      };
      this.inventoryDetailsList = [];
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.is_result=undefined;
      this.queryParams.is_type=undefined;
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.isId)
      this.reviewStatus = selection.map(item => item.reviewStatus)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$router.push('/addInventorySheet');
    },
    /** 修改按钮操作 */
    handleUpdate(isId) {
      this.$router.push({ path: `/editInventorySheet/${isId}` }); // 将 row.isCode 参数传递给路径占位符

    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const isIds = row.isId || this.ids;
      this.$modal.confirm('是否确认删除盘点单编号为"' + isIds + '"的数据项？').then(function () {
        return delInventory(isIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /**
     * 去盘点详情页面
     */
    goToDetails(isId){
      this.$router.push({ path: `/Inventorydetails/${isId}` }); // 将 row.isCode 参数传递给路径占位符
    },
    // /** 盘点明细序号 */
    // rowInventoryDetailsIndex({row, rowIndex}) {
    //   row.index = rowIndex + 1;
    // },
  }
};
</script>

<style scoped>
::v-deep{
  .el-table td.el-table__cell div:hover{
    cursor: pointer;
  }
}
</style>
