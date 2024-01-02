<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :inline="true" :model="queryParams" label-width="68px" size="small">
      <el-form-item label="盘点单号" prop="is_code">
        <el-input
          v-model="queryParams.is_code"
          clearable
          placeholder="请输入盘点单号"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="盘点结果">
        <el-select
          v-model="queryParams.is_result"
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
          v-model="queryParams.is_type"
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
          v-model="queryParams.w_id"
          clearable
          placeholder="请选择"
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
        <el-button icon="el-icon-search" size="mini" type="primary" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['InventorySheet:inventory:add']"
          icon="el-icon-plus"
          plain
          size="mini"
          type="primary"
          @click="handleAdd"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['InventorySheet:inventory:remove']"
          :disabled="multiple"
          icon="el-icon-delete"
          plain
          size="mini"
          type="danger"
          @click="handleDelete"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['InventorySheet:inventory:export']"
          icon="el-icon-unlock"
          plain
          size="mini"
          @click="lock"
        >锁定仓库
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['transfer:transfer:edit']"
          :disabled="single"
          icon="el-icon-edit"
          plain
          size="mini"
          type="success"
        >审核</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-table v-loading="loading"  :data="inventoryList" max-height="520" @selection-change="handleSelectionChange">
      <el-table-column align="center" fixed="left" type="selection" width="55"/>
      <el-table-column align="center" fixed="left" label="盘点ID" prop="isId"/>
      <el-table-column align="center" fixed="left" label="盘点单号" prop="isCode" width="120px">
        <template slot-scope="{ row }">
          <span @click="goToDetails(row.isId)">{{row.isCode}}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" fixed="left" label="盘点结果" prop="isResult">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.p_result_check" :value="scope.row.isResult"/>
        </template>
      </el-table-column>
      <el-table-column align="center" label="仓库名称" prop="whName"/>
      <el-table-column align="center" label="盘点类型" prop="isType">
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
      <el-table-column align="center" label="出库状态">
        <template slot-scope="scope">
          {{ scope.row.outStatus === 0 ? '已出库' : '未出库' }}
        </template>
      </el-table-column>
      <el-table-column align="center" label="入库状态">
        <template slot-scope="scope">
          {{ scope.row.inStatus === 0 ? '已入库' : '未入库' }}
        </template>
      </el-table-column>
      <el-table-column :formatter="handlerProductName" :show-overflow-tooltip="true" align="center" label="盘点货品" prop="gname"/>
      <el-table-column :formatter="handlerCountQuantity" align="center" label="盘点数量"/>
      <el-table-column :formatter="handlerProfitQuantity" align="center" label="盘盈数量"/>
      <el-table-column :formatter="handlerLossQuantity" align="center" label="盘亏数量"
                       prop="profit_loss_quantity" style="color: red"/>
      <el-table-column :formatter="handlerCountAmount" align="center"  label="盘点金额" width="100"/>
      <el-table-column :formatter="handlerProfitAmount" align="center" label="盘盈金额" prop="count_amount" style="color: #1ab394" width="100"/>
      <el-table-column :formatter="handlerLossAmount" align="center" label="盘亏金额" prop="count_amount" style="color: red" width="100"/>
      <el-table-column align="center" label="经办人" prop="managerName"/>
      <el-table-column align="center" fixed="right" label="制单人" prop="creatorName"/>
      <el-table-column align="center" fixed="right" label="所在部门" prop="creatorDept" width="140px"/>
      <el-table-column align="center" fixed="right" label="制单时间" prop="createTime" width="110px">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
<!--      <el-table-column label="操作" fixed="right" align="center" class-name="small-padding fixed-width">-->
<!--        <template slot-scope="{ row }">-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-edit"-->
<!--            @click="handleUpdate(row.isId)"-->
<!--            v-hasPermi="['InventorySheet:inventory:edit']"-->
<!--          >修改-->
<!--          </el-button>-->
<!--          <el-button-->
<!--            size="mini"-->
<!--            type="text"-->
<!--            icon="el-icon-delete"-->
<!--            @click="handleDelete(scope.row)"-->
<!--            v-hasPermi="['InventorySheet:inventory:remove']"-->
<!--          >删除-->
<!--          </el-button>-->
<!--        </template>-->
<!--      </el-table-column>-->
    </el-table>

    <pagination
      v-show="total>0"
      :limit.sync="queryParams.pageSize"
      :page.sync="queryParams.pageNum"
      :total="total"
      @pagination="getList"
    />

  </div>
</template>

<script>
import {
  listInventory, getInventory, delInventory,
  addInventory, updateInventory
} from "@/api/wms/warehouse/InventorySheet/inventory.js";
import {listWarehouse} from "@/api/wms/warehouse/warehouse";

export default {
  name: "Inventory",
  dicts: ['p_result_check', 'pd_type_check', 'pd_status_check'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
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
        pageSize: 10,
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
      // 表单校验
      rules: {
        isCode: [
          {required: true, message: "盘点单号不能为空", trigger: "blur"}
        ],
        isResult: [
          {required: true, message: "盘点结果不能为空", trigger: "blur"}
        ],
        wId: [
          {required: true, message: "仓库 ID不能为空", trigger: "blur"}
        ],
        isType: [
          {required: true, message: "来自数据字典,盘点类型不能为空", trigger: "change"}
        ],
        isStartTime: [
          {required: true, message: "盘点开始时间不能为空", trigger: "blur"}
        ],
        isEndTime: [
          {required: true, message: "盘点结束时间不能为空", trigger: "blur"}
        ],
        outStatus: [
          {required: true, message: "出库状态不能为空", trigger: "change"}
        ],
        inStatus: [
          {required: true, message: "入库状态不能为空", trigger: "change"}
        ],
        isManager: [
          {required: true, message: "关联用户表,盘点经办人不能为空", trigger: "blur"}
        ],
        createBy: [
          {required: true, message: "关联至用户表,创建人不能为空", trigger: "blur"}
        ],
        createTime: [
          {required: true, message: "操作时间不能为空", trigger: "blur"}
        ],
        updateBy: [
          {required: true, message: "关联至用户表,修改人不能为空", trigger: "blur"}
        ],
        updateTime: [
          {required: true, message: "修改时间不能为空", trigger: "blur"}
        ],
        isDelete: [
          {required: true, message: "0：存在；1：已删除，不存在不能为空", trigger: "blur"}
        ]
      }
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
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.isId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$router.push('/addInventorySheet');
    },
    // /** 修改按钮操作 */
    // handleUpdate(isId) {
    //   this.$router.push({ path: `/editInventorySheet/${isId}` }); // 将 row.isCode 参数传递给路径占位符
    //
    // },
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
    /** 盘点明细序号 */
    rowInventoryDetailsIndex({row, rowIndex}) {
      row.index = rowIndex + 1;
    },
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
