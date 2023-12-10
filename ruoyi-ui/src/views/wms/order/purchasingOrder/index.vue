<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :inline="true" :model="queryParams" size="small">
      <el-form-item label="进货订单" label-width="86px" prop="purOrderSn">
        <el-input
          v-model.trim="queryParams.purOrderSn"
          clearable
          placeholder="请输入进货订单"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="订单状态" label-width="86px" prop="orderStatus">
        <el-select
          v-model="queryParams.orderStatus"
          clearable
          placeholder="订单状态"
          style="width: 120px"
        >
          <el-option
            v-for="dict in dict.type.ord_doc_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="供应商" label-width="86px" prop="suppName">
        <el-input
          v-model.trim="queryParams.suppName"
          clearable
          placeholder="请输入供应商名称"
          style="width: 200px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="进货时间" label-width="86px" prop="purDate">
        <el-date-picker
          v-model="dateRange"
          end-placeholder="结束日期"
          range-separator="-"
          start-placeholder="开始日期"
          style="width: 240px"
          type="daterange"
          value-format="yyyy-MM-dd"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button icon="el-icon-search" size="mini" type="primary" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        <el-button icon="el-icon-search" plain size="mini" type="primary">高级搜索</el-button>
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
          :disabled="multiple"
          icon="el-icon-document-copy"
          plain
          size="mini"
          @click="handleCopy"
        >复制
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['system:role:review']"
          :disabled="multiple"
          icon="el-icon-s-check"
          plain
          size="mini"
          @click="handleReview"
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
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['order:purchase:role:close']"
          :disabled="multiple"
          plain
          size="mini"
          @click="handleClose"
        >关闭
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['system:role:turnOn']"
          :disabled="multiple"
          plain
          size="mini"
          @click="handleTurnOn"
        >开启
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['system:role:export']"
          icon="el-icon-download"
          plain
          size="mini"
          type="warning"
          @click="handleExport"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="purchaseList" @selection-change="handleSelectionChange">
      <el-table-column align="center" fixed type="selection" width="50"/>
      <el-table-column align="center" fixed type="index" width="40">
        <template slot="header">
          <i class="el-icon-setting"></i>
        </template>
      </el-table-column>
      <el-table-column fixed label="进货单号" prop="poCode" width="100"/>
      <el-table-column align="center" fixed label="单据状态" prop="status" width="80">
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
      <el-table-column align="center" label="联系人" prop="contactPerson" width="80"/>
      <el-table-column align="center" label="联系方式" prop="contactNumber" width="110"/>
      <el-table-column align="center" label="进货部门" prop="purDeptName" width="100"/>
      <el-table-column align="center" label="进货人" prop="purNickName" width="100"/>
      <el-table-column :formatter="handlerFormatQuantity" align="center" label="货品数量" width="100"/>
      <el-table-column :formatter="handlerFormatPrice" align="center" label="货品金额" width="100"/>
      <el-table-column :formatter="handlerProductName" :show-overflow-tooltip="true" align="center" label="进货货品" width="140"/>
      <el-table-column align="center" label="所在部门" prop="cudeptName" width="100"/>
      <el-table-column align="center" label="制单时间" prop="cudate" width="130">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.cudate, "{y}年{m}月{d}日") }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" label="审核人" prop="reviewerUname" width="100"/>
      <el-table-column align="center" label="审核时间" prop="reviewerDate" width="100"/>
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
  </div>
</template>

<script>

import {listPurchase} from "@/api/wms/order/purchase";

export default {
  name: "Role",
  dicts: ['ord_doc_status'],
  data() {
    return {
      // 遮罩层
      loading: false,
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
      // 角色表格数据
      purchaseList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示弹出层（数据权限）
      openDataScope: false,
      menuExpand: false,
      menuNodeAll: false,
      deptExpand: true,
      deptNodeAll: false,
      // 日期范围
      dateRange: [],
      // 数据范围选项
      dataScopeOptions: [],
      // 菜单列表
      menuOptions: [],
      // 部门列表
      deptOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        purOrderSn: "",
        orderStatus: undefined,
        suppName: undefined
      },
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      rules: {}
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
      if(this.dateRange?.[0]){
        params.beginTime = this.dateRange[0];
        params.endTime = this.dateRange[1];
      }
      console.log("query params: ", params);
      ({
        rows: this.purchaseList,
        total: this.total
      } = (await listPurchase(params)));
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
    //  处理表格货品展示
    handlerProductName(row) {
      let arr = row.purchaseDetails?.map((e) => (e?.goodsName || ''));
      return arr.slice(0, Boolean(arr.slice(-1)[0]) ? arr.length : -1).join("，");
    },
    handleAdd() {
        this.$tab.openPage("添加进货单据", '/order/purchase/add');
    },
    handleUpdate() {
      this.$tab.openPage("编辑进货单据", '/order/purchase/edit/' + this.ids?.[0]);
    },
    handleDelete() {
    },
    handleCopy() {
    },
    handleReview() {
    },
    handleCancel() {
    },
    handleClose() {
    },
    handleTurnOn() {
    },
    handleExport() {
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.poId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
  }
};
</script>
