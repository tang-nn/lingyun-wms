<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="仓库" prop="w_name">
        <el-input
          v-model="queryParams.w_name"
          placeholder="请输入仓库名称"
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
            v-for="dict in dict.type.wh_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="仓库部门" prop="deptName">
        <el-input
          v-model="queryParams.deptName"
          placeholder="请输入"
          clearable
          @focus="handledept"
          suffix-icon="el-icon-more"
         />

      </el-form-item>

      <el-form-item label="仓库主管" prop="w_supervisor">
        <el-input
          v-model="queryParams.w_supervisor"
          placeholder="请输入"
          clearable
          @keyup.enter.native="handleQuery"
          suffix-icon="el-icon-more"
        />
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
          v-hasPermi="['system:warehouse:add']"
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
          v-hasPermi="['wh:house:delete']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
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
          type="warning"
          plain
          icon="el-icon-circle-close"
          size="mini"
          :button-value="buttonValueo"
          :disabled="multiple"
          @click="changeWareHouseStatuso"
          v-hasPermi="['wh:house:disadble']"
        >停用</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-remove-outline"
          size="mini"
          :button-value="buttonValuet"
          :disabled="multiple"
          @click="changeWareHouseStatust"
          v-hasPermi="['wh:house:lock']"
        >锁定</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" max-height="520px" :data="warehouseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="w_id" />
      <el-table-column label="仓库下库存num" align="center" v-if="false" prop="WareStockNum" ref="WareStockNum"/>
      <el-table-column label="仓库编号" align="center" prop="w_code" width="150px">
        <template slot-scope="{ row }">
          <span @click="goToDetails(row)">{{ row.w_code }}</span>
        </template>
      </el-table-column>
      <el-table-column label="仓库名称" align="center" prop="w_name" />
      <el-table-column label="仓库容量/m³" align="center" prop="w_capacity" />
      <el-table-column label="仓库地址" align="center" prop="w_address" width="300px"/>
      <el-table-column label="库管部门" align="center" prop="deptName" width="150px"/>
      <el-table-column label="仓库主管" align="center" prop="threeuser" />
      <el-table-column label="库位数量" align="center" prop="storageNum" />
      <el-table-column label="仓库状态" align="center"  width="180">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.wh_status" :value="scope.row.status" />
        </template>
      </el-table-column>

      <el-table-column label="操作人" align="center" prop="oneuser" />
      <el-table-column label="操作时间" prop="create_time" width="200" >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.create_time) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作"  align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:warehouse:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:warehouse:remove']"
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

    <!--    仓库部门对话框-->
    <el-dialog :title="title" :visible.sync="opendept" width="420px" append-to-body>
    <div class="head-container">
      <el-input
        v-model="queryParams.deptName"
        placeholder="请搜索"
        clearable
        size="small"
        suffix-icon="el-icon-search"
        style="margin-bottom: 20px"
      />
    </div>
    <div class="head-container">
      <el-tree
        :data="deptOptions"
        :props="defaultProps"
        :expand-on-click-node="false"
        ref="tree"
        node-key="id"
        default-expand-all
        highlight-current
        @node-click="handleNodeClick"
        :filter-node-method="filterNode"
      />
    </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="canceldept">确 定</el-button>
        <el-button @click="canceldept">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import { listWarehouse, delWarehouse, updateWarehouse,changeStatus } from "@/api/wms/warehouse/warehouse.js";
import Treeselect from "@riophae/vue-treeselect";
import {deptTreeSelect} from "@/api/system/user";

export default {
  name: "Warehouse",
  components: {Treeselect},
  dicts: ['wh_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 部门树选项
      deptOptions: undefined,
      // 总条数
      total: 0,
      //仓库状态
      buttonValuez:0,
      buttonValueo:1,
      buttonValuet:2,
      w_names:[],
      // 仓库表格数据
      warehouseList: [],
      // 库位信息表格数据
      storageLocationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      opendept:false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 5,
        w_name: null,
        status: null,
        w_supervisor: null,
        deptName:null,
      },
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
    };
  },
  mounted() {
    const WareStockNum = this.$refs.WareStockNum;
  },
  created() {
    this.getList();
    this.getDeptTree();
  },
  methods: {
    /** 查询仓库列表 */
    getList() {
      this.loading = true;
      console.info(this.queryParams);
      listWarehouse(this.queryParams).then(response => {
        this.warehouseList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data;
      });
    },
    // // 节点单击事件
    handleNodeClick(data) {
     this.queryParams.deptName=data.label;
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    handledept(){
      this.opendept = true;
      this.title = "选择部门";
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    canceldept(){
      this.opendept=false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        w_id: null,
        w_code: null,
        w_name: null,
        status: null,
        w_address: null,
        is_default: null,
        is_lock: null,
        w_supervisor: null,
        remark: null,
        sort: null,
        create_by: null,
        create_time: null,
        update_by: null,
        update_time: null,
        is_delete: null,
        deptName:null,
      };
      this.storageLocationList = [];
      this.resetForm("form");
    },
    /**
     * 去仓库详情页面
     */
    goToDetails(row){
      this.$router.push({ path: `/housedetails/${row.w_id}` }); // 将 row.w_id 参数传递给路径占位符
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.status=null;
      this.deptName=null;
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.w_id)
      this.w_names=selection.map(item => item.w_name)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },

    /** 新增按钮操作 */
    handleAdd() {
      this.$router.push('/addwarehouse');
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.$router.push({ path: `/edithouse/${row.w_id}` }); // 将 row.w_id 参数传递给路径占位符
    },
    //更改仓库状态(start)
    changeWareHouseStatusz(row) {
      const w_ids =  this.ids;
      const w_name = this.w_names;
      var left =this;
      this.$modal.confirm('是否确认将"' + w_name + '"启用？').then(function() {
        return changeStatus(left.buttonValuez,w_ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("启用成功");
      }).catch(() => {});
    },
    changeWareHouseStatuso(row) {
      const w_ids =  this.ids;
      const w_name = this.w_names;
      var left =this;
      this.$modal.confirm('是否确认将"' + w_name + '"停用？').then(function() {
        return changeStatus(left.buttonValueo,w_ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("停用成功");
      }).catch(() => {});
    },
    changeWareHouseStatust(row) {
      const w_ids =  this.ids;
      const w_name =  this.w_names;
      var left =this;
      this.$modal.confirm('是否确认将"' + w_name + '"锁定？').then(function() {
        return changeStatus(left.buttonValuet,w_ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("锁定成功");
      }).catch(() => {
      });
    },


    /** 删除按钮操作 */
    handleDelete(row) {
      const w_ids = row.w_id || this.ids;
      const WareStockNum=row.WareStockNum;
      if (WareStockNum>0){
        this.$modal.msgError("不允许操作改仓库！");
      }else {
        this.$modal.confirm('是否确认删除仓库编号为"' + w_ids + '"的数据项？').then(function() {
          return delWarehouse(w_ids);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      }

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
