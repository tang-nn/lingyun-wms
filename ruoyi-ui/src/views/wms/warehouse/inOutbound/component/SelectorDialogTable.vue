<script>
export default {
  name: "SelectorDialogTable",
  props: {
    columns:{

    },
    listData:{
      type: Array,
      required: true
    }
  },
  data() {
    return{
      selectorVisible: false,
      selectedData: []
    }
  },
  methods: {
    close(){
      this.selectorVisible = false
    },
    confirmSelected(){
      this.$emit('confirmSelected', this.selectedData)
    }
  }
}
</script>
<template>
  <el-dialog v-if="!isEditor" :visible.sync="orderFormVisible" append-to-body title="选择进货单" width="1000px">
    <el-form ref="queryOrderForm" :inline="true" :model="purchaseQueryParams" size="small">
      <el-row>
        <el-col :span="6">
          <el-form-item prop="purOrderSn">
            <el-input
              v-model.trim="purchaseQueryParams.purOrderSn"
              clearable
              placeholder="请输入进货订单"
              style="width: 200px"
              @keyup.enter.native="handleOrderQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="orderStatus">
            <el-select
              v-model="purchaseQueryParams.orderStatus"
              clearable
              placeholder="订单状态"
              style="width: 220px"
            >
              <el-option
                v-for="dict in dict.type.ord_doc_status"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="进货日期" label-width="76px" prop="dateRange">
            <el-date-picker
              v-model="purchaseQueryParams.dateRange"
              clearable
              end-placeholder="结束日期"
              range-separator="-"
              start-placeholder="开始日期"
              style="width: 220px"
              type="daterange"
              value-format="yyyy-MM-dd"
            ></el-date-picker>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="6">
          <el-form-item prop="purchaser">
            <el-input
              v-model.trim="purchaseQueryParams.purchaser"
              clearable
              placeholder="请输入进货人"
              style="width: 200px"
              @keyup.enter.native="handleOrderQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="purDept">
            <el-input
              v-model.trim="purchaseQueryParams.purDept"
              clearable
              placeholder="请输入进货部门"
              style="width: 220px"
              @keyup.enter.native="handleOrderQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item prop="suppName">
            <el-input
              v-model.trim="purchaseQueryParams.suppName"
              clearable
              placeholder="请输入供应商名称"
              style="width: 200px"
              @keyup.enter.native="handleOrderQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="6">
          <el-form-item>
            <el-button icon="el-icon-search" size="mini" type="primary" @click="handleOrderQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetOrderQuery">重置</el-button>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <el-table v-loading="orderTableLoading" :data="listData" @row-dblclick="handlerRowDblclick"
              @row-click="singleElectionTable">
      <el-table-column align="center" fixed width="50">
        <template slot-scope="scope">
          <el-radio
            v-model="selectedOrderTemp.orderId"
            :label="scope.row.poId"
            class="table_radio"/>
        </template>
      </el-table-column>
      <el-table-column align="center" fixed type="index" width="40"/>
      <el-table-column fixed label="进货单号" prop="poCode"/>
      <el-table-column align="center" fixed label="单据状态" prop="status" width="76">
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
      <el-table-column align="center" label="进货部门" prop="purDeptName" width="100"/>
      <el-table-column align="center" label="进货人" prop="purNickName" width="100"/>
      <el-table-column :formatter="handlerFormatQuantity" align="center" label="货品数量" width="100"/>
      <el-table-column :formatter="handlerFormatPrice" align="center" label="货品金额" width="100"/>
    </el-table>
    <pagination
      v-show="total>0"
      :limit.sync="queryParams.pageSize"
      :page.sync="queryParams.pageNum"
      :total="total"
      @pagination="getDataList"
    />
    <div slot="footer" class="dialog-footer">
      <el-button @click="close">取 消</el-button>
      <el-button type="primary" @click="confirmSelected">确 定</el-button>
    </div>
  </el-dialog>
</template>
<style>

</style>
