<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :inline="true" :model="queryParams" size="small">
      <el-form-item label="入库订单" label-width="86px" prop="inCode">
        <el-input
          v-model.trim="queryParams.inCode"
          clearable
          placeholder="请输入库订单"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="入库类型" label-width="86px" prop="inType">
        <el-select
          v-model="queryParams.inType"
          clearable
          placeholder="入库类型"
          style="width: 120px"
        >
          <el-option
            v-for="dict in dict.type.incoming_type"
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
            v-for="dict in dict.type.inbound_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="仓库" label-width="86px" prop="whNo">
        <el-select
          v-model="queryParams.wid"
          clearable
          placeholder="请选择仓库"
          style="width: 120px"
        >
          <el-option
            v-for="wh in whList"
            :key="wh.value"
            :label="wh.label"
            :value="wh.value"
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
          @click="handleUpdate"
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
          @click="handleDelete"
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
          @click="reviewFormVisible = true"
        >审核
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['system:role:cancel']"
          :disabled="multiple"
          plain
          size="mini"
          @click="handleCancel"
        >撤销
        </el-button>
      </el-col>
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          v-hasPermi="['order:purchase:role:close']"-->
      <!--          :disabled="multiple"-->
      <!--          plain-->
      <!--          size="mini"-->
      <!--          @click="handleClose"-->
      <!--        >关闭-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          v-hasPermi="['system:role:turnOn']"-->
      <!--          :disabled="multiple"-->
      <!--          plain-->
      <!--          size="mini"-->
      <!--          @click="handleTurnOn"-->
      <!--        >开启-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <!--      <el-col :span="1.5">-->
      <!--        <el-button-->
      <!--          v-hasPermi="['system:role:export']"-->
      <!--          icon="el-icon-download"-->
      <!--          plain-->
      <!--          size="mini"-->
      <!--          type="warning"-->
      <!--          @click="handleExport"-->
      <!--        >导出-->
      <!--        </el-button>-->
      <!--      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="inventoryList" @selection-change="handleSelectionChange">
      <el-table-column align="center" fixed type="selection" width="50"/>
      <el-table-column align="center" fixed type="index" width="40">
        <template slot="header">
          <i class="el-icon-setting"></i>
        </template>
      </el-table-column>
      <el-table-column fixed label="入库单号" prop="inCode" width="100">
        <template slot-scope="scope">
          <router-link :to="'/order/inventory/details/' + scope.row.inCode" class="link-type">
            <span>{{ scope.row.inCode }}</span>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column align="center" fixed label="入库类型" prop="inType" width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.incoming_type" :value="scope.row.inType"/>
        </template>
      </el-table-column>
      <el-table-column align="center" fixed label="单据状态" prop="status" width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.inbound_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column align="center" fixed label="入库日期" prop="storageDate" width="130">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.storageDate, "{y}年{m}月{d}日") }}</span>
        </template>
      </el-table-column>
      <el-table-column :show-overflow-tooltip="true" align="center" label="仓库名称" prop="whName" width="100"/>
      <el-table-column align="center" label="关联单号" prop="relatedOrder" width="80"/>
      <el-table-column :formatter="handlerProductName" align="center" label="入库货品" prop="contactNumber" width="110"/>
      <el-table-column align="center" label="经办人" prop="managerName" width="100"/>
      <el-table-column :formatter="handlerFormatPrice" align="center" label="入库金额" width="100"/>
      <el-table-column :show-overflow-tooltip="true" align="center" label="制单人" prop="creatorName"
                       width="140"/>
      <el-table-column align="center" label="所在部门" prop="creatorDept" width="100"/>
      <el-table-column align="center" label="制单时间" prop="createTime" width="130">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, "{y}年{m}月{d}日") }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="审核人" prop="reviewerName" width="100"/>
      <el-table-column align="center" label="审核时间" prop="reviewerTime" width="100"/>
      <el-table-column align="center" class-name="small-padding fixed-width" fixed="right" label="操作" width="120">
        <template v-if="scope.row.roleId !== 1" slot-scope="scope">
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
            @click="handleDelete(scope.row)"
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
          <el-radio v-model="reviewForm.status" label="pending_review">通过</el-radio>
          <el-radio v-model="reviewForm.status" label="turn_down">驳回</el-radio>
        </el-form-item>
        <el-form-item label="审核意见">
          <el-input
            v-model="reviewForm.reviewComments"
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

import { listInventory } from "@/api/wms/inboundOutbound/inboundMgt";

export default {
  name: "purchaseList",
  dicts: ['incoming_type','inbound_status', 'inbound_status'],
  data() {
    return {
      // 遮罩层
      loading: false,
      // 审核模态框
      reviewFormVisible: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 入库数据
      inventoryList: [],
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
      reviewForm: {}
    };
  },
  created() {
    this.getList();
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
      console.log("query params: ", params);
      ({
        rows: this.inventoryList,
        total: this.total
      } = (await listInventory(params)));
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
          return accumulator + (currentValue.purchaseQuantity || 0);
        }, 0);
      } catch (e) {
        return '计算出错';
      }
    },
    //  处理表格金额展示
    handlerFormatPrice(row) {
      try {
        return row.inventoryDetailsList?.reduce((accumulator, currentValue) => {
          return accumulator + (currentValue.goods.wrPrice || 0);
        }, 0);
      } catch (e) {
        return '计算出错';
      }
    },
    //  处理表格货品展示
    handlerProductName(row) {
      try {
        let arr = row.inventoryDetailsList?.map((e) => (e?.goods?.gname || ''));
        return arr.slice(0, Boolean(arr?.slice(-1)[0]) ? arr.length : -1).join("，");
      } catch (e) {
        return '计算出错';
      }
    },
    handleAdd() {
      this.$tab.openPage("添加进货单据", '/order/purchase/add');
    },
    handleUpdate() {
      this.$tab.openPage("编辑进货单据", '/order/purchase/edit/' + this.ids?.[0]);
    },
    handleDelete(row) {
      const poIds = row.poId || this.ids;
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
    handleCancel() {
    },
    handleClose() {
    },
    handleExport() {
    },
    async handlerReview() {
      console.log("handlerReview: ", this.reviewForm);
      this.loading = true;
      this.reviewForm.poId = this.ids?.[0];
      let {code, msg} = (await reviewPurchase(this.reviewForm));
      if (code === 200) {
        this.$message.success("审核成功！");
        await this.getList();
      } else {
        console.log("审核失败信息：", msg)
        this.$message.error("审核失败！");
        this.loading = false;
      }
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.poId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
  }
};
</script>
<style>
</style>
