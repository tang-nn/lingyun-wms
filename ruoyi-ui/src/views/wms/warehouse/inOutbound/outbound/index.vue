<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :inline="true" :model="queryParams" size="small">
      <el-form-item label="出库订单" label-width="86px" prop="outCode">
        <el-input
          v-model.trim="queryParams.outCode"
          clearable
          placeholder="请输出库订单"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出库类型" label-width="86px" prop="outType">
        <el-select
          v-model="queryParams.outType"
          clearable
          placeholder="出库类型"
          style="width: 120px"
        >
          <el-option
            v-for="dict in dict.type.outbound_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="单据状态" label-width="86px" prop="status">
        <el-select
          v-model="queryParams.status"
          clearable
          placeholder="单据状态"
          style="width: 120px"
        >
          <el-option
            v-for="dict in dict.type.doc_review_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="仓库" label-width="86px" prop="wid">
        <el-select
          v-model="queryParams.wid"
          clearable
          placeholder="请选择仓库"
          style="width: 120px"
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
        <el-button icon="el-icon-search" size="mini" type="primary" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        <!--        <el-button icon="el-icon-search" plain size="mini" type="primary">高级搜索</el-button>-->
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['system:role:add']"
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
          v-hasPermi="['system:role:edit']"
          :disabled="single"
          icon="el-icon-edit"
          plain
          size="mini"
          type="success"
          @click="handleUpdate(undefined)"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['system:role:remove']"
          :disabled="multiple"
          icon="el-icon-delete"
          plain
          size="mini"
          type="danger"
          @click="handleDelete(undefined)"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['system:role:review']"
          :disabled="single"
          icon="el-icon-s-check"
          plain
          size="mini"
          @click="handlerReviewClick"
        >审核
        </el-button>
      </el-col>
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          v-hasPermi="['system:role:cancel']"-->
      <!--          :disabled="multiple"-->
      <!--          plain-->
      <!--          size="mini"-->
      <!--          @click="handleCancel"-->
      <!--        >撤销-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="outboundList" @selection-change="handleSelectionChange">
      <el-table-column align="center" fixed type="selection" width="50"/>
      <el-table-column align="center" fixed type="index" width="40">
        <template slot="header">
          <i class="el-icon-setting"></i>
        </template>
      </el-table-column>
      <el-table-column fixed label="出库单号" prop="inCode" width="100">
        <template slot-scope="scope">
          <router-link :to="'/inOutbound/outbound/details/' + scope.row.outId" class="link-type">
            <span>{{ scope.row.outCode }}</span>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column align="center" fixed label="出库类型" prop="outType" width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.outbound_type" :value="scope.row.outType"/>
        </template>
      </el-table-column>
      <el-table-column align="center" label="关联单号" prop="relatedOrder" width="120"/>
      <el-table-column align="center" fixed label="单据状态" prop="status" width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.doc_review_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column align="center" fixed label="出库日期" prop="outboundDate" width="130">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.outboundDate, "{y}年{m}月{d}日") }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="仓库名称" prop="whName" width="100"/>
      <el-table-column :formatter="row=>row.supplierName || '-'" align="center" label="供应商" prop="supplierName"
                       width="100"/>
      <el-table-column :formatter="row=>row.consumerName || '-'" align="center" label="客户" prop="consumerName"
                       width="100"/>
      <el-table-column :formatter="row=>row.contactPerson || '-'" align="center" label="联系人" prop="contactPerson"
                       width="100"/>
      <el-table-column :formatter="row=>row.contactNumber || '-'" align="center" label="联系方式" prop="contactNumber"
                       width="100"/>
      <el-table-column :show-overflow-tooltip="true" align="center" label="出库货品" prop="goodsNames" width="110"/>
      <el-table-column align="center" label="出库数量" prop="outboundQuantity" width="100"/>
      <el-table-column align="center" label="出库金额" prop="outboundAmount" width="100"/>
      <el-table-column align="center" label="经办人" prop="managerName" width="100"/>
      <el-table-column :show-overflow-tooltip="true" align="center" label="制单人" prop="creatorName"
                       width="140"/>
      <el-table-column align="center" label="所在部门" prop="creatorDept" width="100"/>
      <el-table-column align="center" label="制单时间" prop="createTime" width="130">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, "{y}年{m}月{d}日") }}</span>
        </template>
      </el-table-column>
      <el-table-column :formatter="row=>row.reviewerName || '-'" align="center" label="审核人" prop="reviewerName"
                       width="100"/>
      <el-table-column :formatter="row=>row.reviewerTime || '-'" align="center" label="审核时间" prop="reviewerTime"
                       width="100"/>
      <el-table-column align="center" class-name="small-padding fixed-width" fixed="right" label="操作" width="120">
        <template v-if="scope.row.status !== 'done'" slot-scope="scope">
          <el-button
            v-hasPermi="['system:role:edit']"
            icon="el-icon-edit"
            size="mini"
            type="text"
            @click="handleUpdate(scope.row)"
          >修改
          </el-button>
          <el-button
            v-hasPermi="['system:role:remove']"
            icon="el-icon-delete"
            size="mini"
            type="text"
            @click="handleDelete(scope.row.outId)"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination
      v-show="total>0"
      :limit.sync="queryParams.pageSize"
      :page.sync="queryParams.pageNum"
      :total="total"
      @pagination="getList"
    />
    <!-- 审核 Form :label-width="" -->
    <el-dialog :visible.sync="reviewFormVisible" title="订单审核">
      <el-form :model="reviewForm">
        <el-form-item label="审核结果">
          <!-- 使用 doc_review_status 数据字典 -->
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

import {inboundReview, listInbound} from "@/api/wms/inboundOutbound/inboundMgt";
import DynamicTable from "@/components/wms/DynamicTable/dynamic-table.vue";
import {listsWarehouse} from "@/api/wms/standingbook/Inventoryquery";
import {listOutbound, outboundReview} from "@/api/wms/inboundOutbound/outbound";

export default {
  name: "purchaseList",
  components: {DynamicTable},
  dicts: ['outbound_type', 'outbound_status', 'doc_review_status'],
  data() {
    return {
      // 遮罩层
      loading: false,
      // 审核模态框
      reviewFormVisible: false,
      // 选中数组
      ids: [],
      selectedOrders: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 出库数据
      outboundList: [],
      tableHeader: [
        {
          id: 1,
          label: "",
          prop: String,
          attrs: {},
          children: []
        }
      ],
      // 仓库数据
      whList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 日期范围
      dateRange: [],
      // 部门列表
      deptOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        inCode: undefined,
        inType: undefined,
        status: undefined,
        wid: undefined
      },
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      rules: {},
      // 审核表单
      reviewForm: {
        comments: '',
        reviewStatus: '',
      }
    };
  },
  async created() {
    await this.getList();
    this.whList = (await listsWarehouse())
  },
  methods: {
    /** 查询角色列表 */
    async getList() {
      this.loading = true;
      let params = this.queryParams;
      if (this.dateRange?.[0]) {
        params.beginTime = this.dateRange[0];
        params.endTime = this.dateRange[1];
      }
      ({
        rows: this.outboundList,
        total: this.total
      } = (await listOutbound(params)));
      this.loading = false;
    },
    /** 搜索按钮操作 */
    handleQuery() {
      console.log("搜索按钮操作")
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 处理表格数量展示
    handlerFormatQuantity(row) {
      try {
        return row.purchaseDetails?.reduce((accumulator, currentValue) => {
          return accumulator + (currentValue.puQuantity || 0);
        }, 0);
      } catch (e) {
        return '计算出错';
      }
    },
    handleAdd() {
      this.$tab.openPage("添加出库单据", '/inOutbound/outbound/add');
    },
    handleUpdate(row) {
      let currOrder = row ? [row.outId] : this.selectedOrders;
      if (currOrder.some(item => item.status === 'done')) {
        this.$message.error('已完成的订单不能编辑');
        return;
      }
      let id = row?.outId || this.ids?.[0];
      this.$tab.openPage("编辑出库单据", '/inOutbound/outbound/edit/' + id);
    },
    handleDelete(row) {
      let currOrder = row ? [row] : this.selectedOrders;
      console.log("currOrder", currOrder)
      if (currOrder.some(item => item.status === 'done')) {
        this.$message.error('已完成的订单不能删除');
        return;
      }
      const poIds = row?.outId || this.ids;
      this.$confirm('是否删除 ' + poIds + ' 的进货订单？', '确认信息', {
        distinguishCancelAndClose: true,
        confirmButtonText: '确认删除',
        cancelButtonText: '取消'
      })
        .then(() => {
          if (poIds.length > 5) {
            this.$message.error("一次最多删除 5 条数据")
          }
        })
        .catch(action => {
          // 未关闭
        });
    },
    handlerReviewClick() {
      let currOrder = this.selectedOrders;
      console.log("currOrder", currOrder)
      if (currOrder.some(item => item.status === 'done')) {
        this.$message.error('已完成的订单不能再审核');
        return;
      }
      this.reviewFormVisible = true;
    },
    handleCancel() {
    },
    handleClose() {
    },
    handleExport() {
    },
    async handlerReview() {
      console.log("handlerReview: ", this.reviewForm);
      this.loading = true;
      this.reviewForm.orderId = this.ids?.[0];
      console.log("this.ids: ", this.ids)
      if (!this.reviewForm.orderId) {
        this.$message.error("请重新选择订单！");
        return;
      }
      try {
        let {code, msg} = (await outboundReview(this.reviewForm));
        if (code === 200) {
          this.reviewFormVisible = false;
          this.$message.success("审核成功！");
          await this.getList();
        } else {
          console.log("审核失败信息：", msg)
          this.$message.error("审核失败，" + msg);
        }
      } finally {
        this.loading = false;
      }

    },
    handleSelectionChange(selection) {
      this.selectedOrders = selection;
      this.ids = selection.map(item => item.outId);
      console.log("this.ids: ", this.ids)
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
  }
}
;
</script>
<style>
</style>
