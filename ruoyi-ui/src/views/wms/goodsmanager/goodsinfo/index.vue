<template>
  <div class="app-container">
    <el-form v-show="showSearch" ref="queryForm" :inline="true" :model="queryParams" size="small">
      <el-form-item label="货品">
        <el-input
          v-model="queryParams.g_name"
          clearable
          placeholder="请输入货品名称"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态">
        <el-select
          v-model="queryParams.status"
          clearable
          placeholder="请选择"
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

      <el-form-item label="货品类型">
        <treeselect v-model="queryParams.gt_id"
                    :defaultExpandLevel="Infinity"
                    :options="goodsTypes"
                    :show-count="false"
                    placeholder="请选择"
                    style="width: 240px"/>
      </el-form-item>

      <!--      :label属性来指定在页面上显示的文本    :value属性来指定选项的值。-->
<!--      <el-form-item label="单位"  >-->
<!--          <el-select-->
<!--            v-model="queryParams.unit"-->
<!--            clearable-->
<!--            filterable-->
<!--            placeholder="请选择"-->
<!--            style="width: 240px"-->
<!--          >-->
<!--            <el-option-->
<!--              v-for="dict in dict.type.g_unit_goods"-->
<!--              :key="dict.value"-->
<!--              :label="dict.label"-->
<!--              :value="dict.value"-->

<!--            />-->
<!--          </el-select>-->
<!--      </el-form-item>-->
      <el-form-item>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          <el-button size="mini"  type="primary" @click="handleQuery">查询</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['gd:good:add']"
          icon="el-icon-plus"
          plain
          size="mini"
          type="primary"
          @click="handleAdd"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">

        <el-button
          v-hasPermi="['gd:good:delete']"
          :disabled="multiple"
          icon="el-icon-minus"
          plain
          size="mini"
          type="danger"
          @click="handleDelete"
        >删除</el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button
          v-hasPermi="['gd:good:import']"
          icon="el-icon-upload2"
          plain
          size="mini"
          type="info"
          @click="handleImport"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          v-hasPermi="['gd:good:export']"
          icon="el-icon-download"
          plain
          size="mini"
          type="warning"
          @click="handleExport"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" max-height="600" :data="goodList" @selection-change="handleSelectionChange">
      <el-table-column align="center" fixed type="selection" width="55" />

      <el-table-column fixed="left" label='序号' prop="g_id" width="100"/>
      <el-table-column fixed="left" label="货品编号" prop="g_code" width="170" />
      <el-table-column :show-overflow-tooltip="true" fixed="left" label="货品名称" prop="g_name" width="150" />
      <el-table-column align="center"  fixed="left" label="状态" width="100">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            :active-value="0"
            :inactive-value="1"
            @change="handlegGoodsChange(scope.row)"
          ></el-switch>

        </template>
      </el-table-column>
      <el-table-column :show-overflow-tooltip="true" label="货品类型" prop="gt_name" width="150" />
      <el-table-column label="规格型号" prop="spec_code" width="120" />
<!--      <el-table-column align="center" label="单位"  width="180">-->
<!--        <template slot-scope="scope">-->
<!--          <dict-tag :options="dict.type.g_unit_goods" :value="scope.row.unit" />-->
<!--        </template>-->
<!--      </el-table-column>-->

      <el-table-column label="供应商" prop="s_name" width="120" />
      <el-table-column :formatter="formatPrice" label="入库参考价/元" prop="wr_price" width="110" />
      <el-table-column :formatter="formatPrice2" label="出库参考价/元" prop="or_price" width="110" />
<!--      <el-table-column label="显示顺序" prop="sort" width="100" />-->
      <el-table-column :formatter="formatStock" label="当前库存" prop="item_quantity" width="100" />
      <el-table-column :formatter="formatPrice3" label="库存总金额" prop="stocktotalmoney" width="100" />
      <el-table-column :formatter="formathasShelfLife" label="保质期管理" prop="has_shelf_life" width="100" />
      <el-table-column label="保质期" prop="shelf_life" width="100">
        <template slot-scope="{ row }">
          {{ row.shelf_life ? row.shelf_life : '/' }}
        </template>
      </el-table-column>
      <el-table-column label="预警天数(天)" prop="w_days" width="100">
        <template slot-scope="{ row }">
          {{ row.w_days ? row.w_days : '/' }}
        </template>
      </el-table-column>
      <el-table-column label="操作人" prop="oneuser" width="100" />
      <el-table-column label="操作时间" prop="create_time" fixed="right"  width="100" >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.create_time) }}</span>
        </template>
      </el-table-column>
      <el-table-column align="center" class-name="small-padding fixed-width" fixed="right" label="操作" width="130">
        <template slot-scope="scope" >
          <el-button
            v-hasPermi="['system:role:edit']"
            icon="el-icon-edit"
            size="mini"
            type="text"
            @click="handleEdit(scope.row)"
          >编辑</el-button>
          <el-button
            v-hasPermi="['gd:good:delete']"
            icon="el-icon-delete"
            size="mini"
            type="text"
            v-if="scope.row.item_quantity ===0 || scope.row.item_quantity ==null"
            @click="handleDelete(scope.row)"
          >删除</el-button>
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


    <!-- 商品导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" append-to-body width="400px">
      <el-upload
        ref="upload"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :auto-upload="false"
        :disabled="upload.isUploading"
        :headers="upload.headers"
        :limit="1"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        accept=".xlsx, .xls"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div slot="tip" class="el-upload__tip text-center">
          <div slot="tip" class="el-upload__tip">
            <el-checkbox v-model="upload.updateSupport" /> 是否更新已经存在的商品数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link :underline="false" style="font-size:12px;vertical-align: baseline;" type="primary" @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>

</div>
</template>

<script>
import {listGood, TypesList, delGoodsInfo, changeGoodsStatus} from "@/api/wms/good/goodsinfo";
import {getToken} from "@/utils/auth";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "goodsinfo",
  components: { Treeselect },
  dicts: ['g_unit_goods','sys_normal_disable'],
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
      // 总条数
      total: 0,
      //货品类型树选项
      goodsTypes: undefined,
      // 商品表格数据
      goodList: [],
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

      // 商品导入参数
      upload: {
        // 是否显示弹出层（商品导入）
        open: false,
        // 弹出层标题（商品导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的商品数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/wms/goods/importData"
      },

      // 菜单列表
      menuOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        g_name: undefined,
        status:undefined,
        unit: undefined,
        type:undefined,
        gt_id: undefined,
      },
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
    };
  },
  created() {
    this.getList();
    this.goodsTypesList();
  },
  methods: {

    //保留两位小数
    formatPrice(row,column) {
      return row.wr_price.toFixed(2);
    },
    formatPrice2(row, column) {
      return row.or_price.toFixed(2);
    },
    formatPrice3(row,column) {
      if (row.stocktotalmoney==null||row.stocktotalmoney==0){
        return 0;
      }else {
        return row.stocktotalmoney.toFixed(2);
      }
    },
    formatStock(row) {
     if(row.item_quantity==null){
       return 0;
     }
     return row.item_quantity;
    },
    //查询---保质期
    formathasShelfLife(row) {
      if (row.has_shelf_life === 0) {
        return '关闭';
      } else {
        return '开启';
      }
    },
    /** 查询商品列表 */
    getList() {
      this.loading = true;
      listGood(this.addDateRange(this.queryParams)).then(response => {

          this.goodList = response.rows;
         console.log(response.goodList);
          this.total = response.total;
          console.log(response.total);
          this.loading = false;
        }
      );
    },


    /* 查看所有商品分类 */
    goodsTypesList(){
      TypesList().then(response => {
        this.goodsTypes = response.data;
        console.info(this.goodsTypes);
      });

    },



    // 商品信息状态修改
    handlegGoodsChange(row) {
       let text = row.status == 0 ? "启用" : "停用";
       var that =this;
      this.$modal.confirm('确认要"' + text + '""' + row.g_name + '"商品吗？').then(function() {
        return changeGoodsStatus(row.g_id, row.status);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        //用户取消
        that.getList();
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      if (this.$refs.menu != undefined) {
        this.$refs.menu.setCheckedKeys([]);
      }
      this.menuExpand = false,
      this.menuNodeAll = false,
      this.deptExpand = true,
      this.deptNodeAll = false,
      this.form = {
        status: "0",
        menuCheckStrictly: true,
        deptCheckStrictly: true,
        remark: undefined
      };
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
      this.queryParams.gt_id=undefined;
      this.queryParams.unit=undefined;
      this.queryParams.g_name=undefined;
      this.queryParams.status=undefined;
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.g_id)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const g_ids = row.g_id || this.ids;
      console.info(g_ids);
      this.$modal.confirm('是否确认删除货品编号为"' + g_ids + '"的数据项？').then(function() {
        return delGoodsInfo(g_ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('wms/goods/export', {
        ...this.queryParams
      }, `goods_${new Date().getTime()}.xlsx`)
    },

    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "货品导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('wms/goods/importTemplate', {
      }, `goods_template_${new Date().getTime()}.xlsx`)
    },
   // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },


    /** 新增按钮操作 */
    handleAdd() {
      this.$router.push('/gdsadd');
    },

    /** 修改按钮操作 */
    handleEdit(row){
      this.$router.push({ path: `/gdsedit/${row.g_id}` }); // 将 row.g_id 参数传递给路径占位符
    },


  },

};
</script>
