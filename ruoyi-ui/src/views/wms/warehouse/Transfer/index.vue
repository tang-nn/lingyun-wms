<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="调拨单号" prop="tdCode">
        <el-input
          v-model="queryParams.tdCode"
          placeholder="请输入调拨单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="单据状态">
        <el-select
          v-model="queryParams.docStatus"
          placeholder="请选择"
          style="width: 240px"
        >
          <el-option
            v-for="dict in dict.type.transfer_doc_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="出库状态" label-width="80">
        <el-select
          v-model="queryParams.outStatus"
          placeholder="请选择"
          style="width: 240px"
        >
          <el-option
            v-for="dict in dict.type.transfer_out_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="入库状态" label-width="80">
        <el-select
          v-model="queryParams.inStatus"
          placeholder="请选择"
          style="width: 240px"
        >
          <el-option
            v-for="dict in dict.type.transfer_in_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="调拨申请日期" label-width="80">
          <el-date-picker
            v-model="queryParams.dates"
            end-placeholder="结束日期" format="yyyy-MM-dd"
            range-separator="至"
            start-placeholder="开始日期"
            type="daterange"
            value-format="yyyy-MM-dd">
          </el-date-picker>
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
          v-hasPermi="['transfer:transfer:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['transfer:transfer:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-coordinate"
          size="mini"
          :disabled="single"
          @click="review"
          v-hasPermi="['transfer:transfer:examine']"
        >审核</el-button>
      </el-col>

      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="transferList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" fixed="left" width="55" align="center" />
      <el-table-column label="调拨ID" fixed="left" align="center" prop="tid" />
      <el-table-column label="调拨单号" fixed="left" align="center" prop="tdCode" width="130px">
        <template slot-scope="{ row }">
          <span @click="goToDetails(row.tid)">{{row.tdCode}}</span>
        </template>
      </el-table-column>
      <el-table-column label="单据状态" fixed="left" align="center" prop="docStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.transfer_doc_status" :value="scope.row.docStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="调拨申请日期" fixed="left" align="center" prop="date" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.date, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="调出仓库" align="center" prop="outName" />
      <el-table-column label="调入仓库" align="center" prop="inName" />
      <el-table-column label="出库状态" align="center" prop="outStatus" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.transfer_out_status" :value="scope.row.outStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="入库状态" align="center" prop="inStatus" >
        <template slot-scope="scope">
          <dict-tag :options="dict.type.transfer_in_status" :value="scope.row.inStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="调拨数量" align="center" :formatter="handlerQuantity"  />
      <el-table-column label="调拨金额" align="center" width="130px" :formatter="handlerCountAmount"  />
      <el-table-column label="经办人" align="center" prop="managerName" />
      <el-table-column label="调拨货品" :show-overflow-tooltip="true"  align="center" :formatter="handlerProductName" />
      <el-table-column label="制单人" align="center" prop="creatorName" />
      <el-table-column label="所在部门" align="center" prop="creatorDept" />
      <el-table-column label="制单时间" align="center" prop="createTime"  width="160px"/>
      <el-table-column label="审核人" align="center" prop="revieerName" />
      <el-table-column label="审核时间" align="center" prop="reviewer_time"  width="160px"/>
      <el-table-column label="操作" width="135" fixed="right" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['transfer:transfer:edit']"
            v-if="scope.row.docStatus === 'turn_down'||scope.row.docStatus === 'pending_review'"
          >修改</el-button>

          <el-button
            size="mini"
            v-if="scope.row.docStatus === 'turn_down'||scope.row.docStatus === 'pending_review'"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['transfer:transfer:remove']"
          >删除</el-button>
<!--            <el-button-->
<!--              size="mini"-->
<!--              v-if="scope.row.docStatus === 'done'"-->
<!--              type="text"-->
<!--              icon="el-icon-refresh-right"-->
<!--              @click="handleUndo(scope.row)"-->
<!--              v-hasPermi="['transfer:transfer:undo']"-->
<!--            >撤销</el-button>-->
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
          <el-radio v-model="reviewForm.docStatus" label="done">通过</el-radio>
          <el-radio v-model="reviewForm.docStatus" label="turn_down">驳回</el-radio>
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
import { listTransfer, delTransfer,reviewInventory }from "@/api/wms/warehouse/transfer/transfer.js";

export default {
  name: "Transfer",
  dicts: ['transfer_doc_status','transfer_out_status','transfer_in_status'],
  data() {
    return {
      // 审核模态框
      reviewFormVisible: false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      docStatus:[],
      // 子表选中数据
      checkedTransferDetails: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 调拨单表格数据
      transferList: [],
      // 调拨明细表格数据
      transferDetailsList: [],
      // 弹出层标题
      title: "",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tdCode: null,
        docStatus: null,
        dates: [],
        outStatus: null,
        inStatus: null,
      },
      // 表单参数
      form: {},
      reviewForm:{},
      // 表单校验
      rules: {
        tdCode: [
          { required: true, message: "调拨单号不能为空", trigger: "blur" }
        ],
        docStatus: [
          { required: true, message: "单据状态不能为空", trigger: "change" }
        ],
        date: [
          { required: true, message: "调拨申请日期不能为空", trigger: "blur" }
        ],
        outWId: [
          { required: true, message: "调出库，仓库 ID不能为空", trigger: "blur" }
        ],
        inWId: [
          { required: true, message: "调入库，仓库 ID不能为空", trigger: "blur" }
        ],
        outStatus: [
          { required: true, message: "出库状态不能为空", trigger: "change" }
        ],
        type: [
          { required: true, message: "数据字典,调拨类型不能为空", trigger: "change" }
        ],
        remark: [
          { required: true, message: "备注不能为空", trigger: "blur" }
        ],
        inStatus: [
          { required: true, message: "入库状态不能为空", trigger: "change" }
        ],
        manager: [
          { required: true, message: "外键，关联用户表,调拨经办人不能为空", trigger: "blur" }
        ],
        createBy: [
          { required: true, message: "关联至用户表,创建人不能为空", trigger: "blur" }
        ],
        createTime: [
          { required: true, message: "操作时间不能为空", trigger: "blur" }
        ],
        updateBy: [
          { required: true, message: "关联至用户表,修改人不能为空", trigger: "blur" }
        ],
        updateTime: [
          { required: true, message: "修改时间不能为空", trigger: "blur" }
        ],
        deleted: [
          { required: true, message: "0：存在；1：已删除，不存在不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询调拨单列表 */
    getList() {
      this.loading = true;
      listTransfer(this.queryParams).then(response => {
        this.transferList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
      console.log(this.transferList)
    },
    //  处理表格货品展示
    handlerProductName(row) {
      let arr = row.transferDetailsList?.map((e) => (e?.goods?.gname || ''));
      return arr.slice(0, Boolean(arr.slice(-1)[0]) ? arr.length : -1).join("，");
    },
    // 计算调拨数量
    handlerQuantity(row){
      return row.transferDetailsList?.reduce((accumulator, currentValue) => {
        return accumulator + (currentValue.quantity || 0);
      }, 0);
    },
    // 调拨总金额
    handlerCountAmount(row) {
      const totalAmount = row.transferDetailsList?.reduce(function(accumulator, currentValue) {
        const wrPrice = currentValue?.goods?.wrPrice || 0;
        const quantity = currentValue.quantity || 0;
        const total = wrPrice * quantity;
        return accumulator + total;
      }, 0);
      return totalAmount.toFixed(2);
    },

    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.dates=undefined;
      this.queryParams.docStatus=undefined;
      this.queryParams.inStatus=undefined;
      this.queryParams.tdCode=undefined;
      this.queryParams.outStatus=undefined;
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.tid)
      this.docStatus = selection.map(item => item.docStatus)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.$router.push('/addtransfer');
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const tids = row.tid || this.ids;
      this.$modal.confirm('是否确认删除调拨单编号为"' + tids + '"的数据项？').then(function() {
        return delTransfer(tids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },

    //审核
    review(){
      console.log("this.docStatus?.[0]",this.docStatus?.[0])
      if (this.docStatus?.[0] =="done"){
        this.$message.warning("该调拨单已通过审核！");

      }else {
        this.reviewFormVisible=true;
      }
    },
    async handlerReview() {
      console.log("handlerReview: ", this.reviewForm);
      this.loading = true;
      this.reviewForm.tid = this.ids?.[0];
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

    //撤销
    handleUndo(row){

    },

    /**
     * 去调拨详情页面
     */
    goToDetails(tid){
      this.$router.push({ path:`/Transferdetails/${tid}` }); // 将 row.tid 参数传递给路径占位符
    },

    /*修改*/
    handleUpdate(row){
      this.$router.push({ path:`/updateTransfer/${row.tid}` });
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
