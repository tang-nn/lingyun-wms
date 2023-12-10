<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="货品类型">
        <el-input
          v-model="queryParams.gtName"
          placeholder="请输入货品类型名称"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" >
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



      <el-form-item label="操作人">
        <el-input
          v-model="queryParams.creater"
          placeholder="请输入"
          clearable
          style="width: 240px"
        />
      </el-form-item>



      <el-form-item label="操作时间" prop="createTimes">
        <el-date-picker
          v-model="queryParams.createTimes"
          format="yyyy-MM-dd" value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="至"
          start-placeholder="开始日期"
          end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>


      <el-form-item>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          <el-button type="primary"  size="mini" @click="handleQuery">查询</el-button>
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
          v-hasPermi="['gd:goodtype:add']"
        >新增</el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-minus"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['gd:goodtype:delete']"
        >删除</el-button>
      </el-col>

      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['gd:goodtype:import']"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['gd:goodtype:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table   max-height="520" v-loading="loading" :data="goodList" @selection-change="handleSelectionChange">

      <el-table-column type="selection" fixed width="55" align="center" />
      <el-table-column label="货品类型编号" fixed="left" prop="gtCode" :show-overflow-tooltip="true" width="150" />
      <el-table-column label='序号' fixed="left" prop="gtId" width="100"/>


      <el-table-column label="货品类型名称" fixed="left"
                       :show-overflow-tooltip="true" width="150" >
        <template slot-scope="scope">
         <span :style="getStyle(scope.row)"> {{scope.row.gtName}}</span>
        </template>
      </el-table-column>

      <el-table-column label="状态"  fixed="left" align="center" width="100">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            :active-value="0"
            :inactive-value="1"
            @change="handleStatusChange(scope.row)"
          ></el-switch>

        </template>
      </el-table-column>
      <el-table-column label="排序" prop="sort" width="100" />
      <el-table-column label="备注" prop="remark" :show-overflow-tooltip="true" width="250" />

      <el-table-column label="操作人" prop="creater" width="100" />
      <el-table-column label="操作时间" prop="createTime" width="150" >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作"  fixed="right" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope" >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['gd:goodtype:edit']"
          >编辑</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['gd:goodtype:delete']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>



<!--    <pagination-->
<!--      v-show="total>0"-->
<!--      :total="total"-->
<!--      :page.sync="queryParams.pageNum"-->
<!--      :limit.sync="queryParams.pageSize"-->
<!--      @pagination="getList"-->
<!--    />-->


    <!-- 商品类型导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" /> 是否更新已经存在的商品类型数据
          </div>
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>



    <!-- 添加或修改商品类别对话框 -->

    <el-drawer
      :title="title"

      :visible.sync="dialog"
      direction="rtl"
      custom-class="demo-drawer"
      ref="drawer"
    >
      <div class="demo-drawer__content" style="border-top: 1px solid #eeeeee;padding-top: 20px">
        <div style="margin: 20px;"></div>
        <el-form :inline="true"  :model="form" :rules="rules" ref="form"
                 :label-position="labelPosition" label-width="80px" >
          <el-form-item label="上级分类" prop="parentId">
            <treeselect v-model="selectedParentId"
                        :options="goodsTypes"
                        :show-count="false"
                        placeholder="请选择"
                        :defaultExpandLevel="Infinity"
                        style="width: 240px"/>
          </el-form-item>

          <el-form-item label="货品类型编号"  prop="gtCode">
            <el-input placeholder="自动获取系统编号" v-model="form.gtCode" readonly></el-input>
          </el-form-item>

          <el-form-item label="货品类型名称" prop="gtName">
            <el-input placeholder="请输入" v-model="form.gtName"></el-input>
          </el-form-item>

          <el-form-item label="排序" prop="sort">
            <el-input placeholder="请输入"v-model="form.sort"></el-input>
          </el-form-item>

          <br/>


          <el-form-item label="状态">
            <el-radio-group v-model="form.status">
              <el-radio
                v-for="dict in dict.type.sys_normal_disable"
                :key="dict.value"
                :label="dict.value"
              >{{dict.label}}</el-radio>
            </el-radio-group>
          </el-form-item>


          <br/>
          <el-form-item label="备注" prop="remark">
            <el-input type="textarea"placeholder="请输入" v-model="form.remark" ></el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer" style="margin-top:320px;border-top: 1px solid #eeeeee;padding-top: 20px">
          <div style="margin-left: 360px;">
            <el-button @click="cancelForm">取 消</el-button>
            <el-button  type="primary" @click="handleClose()" :loading="loading">{{ loading ? '提交中...' : '确 定' }}</el-button>
          </div>
        </div>
      </div>
    </el-drawer>


</div>
</template>

<script>
import {
  delGoodsType,
  listGoodType,
  addTypeGoods,
  getGoodsType,
  editTypeGoods,
  changeGoodTypeStatus
} from "@/api/wms/good/goodstype";
import {getToken} from "@/utils/auth";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {TypesList} from "@/api/wms/good/goodsinfo";

export default {
  name: "goodsType",
  components: { Treeselect },
  dicts: ['sys_normal_disable'],
  data() {
    return {
      // 遮罩层
      loading: true,
      labelPosition: 'top',
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
      // 商品类型表格数据
      goodList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      table: false,
      dialog: false,
      // 是否显示弹出层（数据权限）
      openDataScope: false,
      menuExpand: false,
      menuNodeAll: false,
      deptExpand: true,
      deptNodeAll: false,
      gtId: '0',
      gtCode:undefined,
      radio: '0',

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
        url: process.env.VUE_APP_BASE_API + "/wms/goods/type/importData"
      },

      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 5,
        gtName:undefined,
        creater:undefined,
        createTimes:[],
        status:undefined,
        gt_id:undefined

      },
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      rules: {
        gtName: [
          { required: true, message: "货品类型名称不能为空", trigger: "blur" }
        ],
        status: [
          { required: true, message: "状态不能为空", trigger: "blur" }
        ],
        sort: [
          { required: true, message: "排序不能为空", trigger: "blur" }
        ],
        parentId:[
          { required: true, message: "上级分类不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.goodsTypesList();
  },
  computed:{
    selectedParentId: {
      get() {
        return this.form.parentId === 0 ? this.form.gtId : this.form.parentId;
      },
      set(newValue) {
        if (this.form.parentId === 0) {
          this.form.gtId = newValue;
        } else {
          this.form.parentId = newValue;
        }
      }
    }
  },
  methods: {
    getStyle(row) {
      if (row.parentId != 0) {
        return {
          // 样式对象
          paddingLeft: '20px'
        };
      }
      return {paddingLeft: '0px'};
    },
    /** 查询商品类型列表 */
    getList() {
      this.loading = true;
      listGoodType(this.addDateRange(this.queryParams)).then(response => {
          this.goodList = response.rows;
          this.total = response.total;
          console.log(this.total);
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


    // 商品类型状态修改
    handleStatusChange(row) {
      var that =this;
      let text = row.status == 0 ? "启用" : "停用";
      this.$modal.confirm('确认要"' + text + '""' + row.gtName + '"商品类型吗？').then(function() {
        return changeGoodTypeStatus(row.gtId, row.status);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        //用户取消
        that.getList();
      });
    },
    // 取消按钮
    cancelForm() {
      this.loading = false;
      this.dialog = false;
      clearTimeout(this.timer);
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
      this.queryParams.gtName=undefined,
      this.queryParams.creater=undefined,
      this.queryParams.createTimes=[],
      this.queryParams.status=undefined,
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.gtId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const gtIds = row.gtId || this.ids;
      console.info(gtIds);
      this.$modal.confirm('是否确认删除货品类型编号为"' + gtIds + '"的数据项？').then(function() {
        return delGoodsType(gtIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('wms/goods/type/export', {
        ...this.queryParams
      }, `goodsType_${new Date().getTime()}.xlsx`)
    },

    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "货品类型导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('wms/goods/type/importTemplate', {
      }, `goodsType_template_${new Date().getTime()}.xlsx`)
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
        this.reset();
        this.dialog=true;
        this.title = "新增";
    },

    //提交按钮
    handleClose() {
      if (this.loading) {
        return;
      }
      this.$confirm('确定要提交表单吗？')
        .then(_ => {
            this.$refs["form"].validate(valid => {
              if (valid) {
                this.timer = setTimeout(() => {
                  // 动画关闭需要一定的时间
                  setTimeout(() => {
                    this.loading = false;
                    }, 10);
                }, 100);
                this.loading = true;
                if (this.form.gtId != undefined) {
                  editTypeGoods(this.form).then(response => {
                    this.$modal.msgSuccess("修改成功");
                    this.dialog = false;
                    this.getList();
                  });
                }else {
                  addTypeGoods(this.form).then(response => {
                    this.$modal.msgSuccess("新增成功");
                    this.dialog = false;
                    this.getList();
                  });
                }

              }
            });
        })
    },



    /** 修改按钮操作 */
    handleUpdate(row){
      // this.reset();
      this.dialog=true;
      this.title = "修改";
      const gt_Id = row.gtId;
      getGoodsType(gt_Id).then(response => {
        this.form = response.data;
        this.form.status = ''+this.form.status
        console.info(this.form);
      });

    },


    // handleUpdate(row) {
    //   this.reset();
    //
    //   const roleId = row.roleId || this.ids
    //   const roleMenu = this.getRoleMenuTreeselect(roleId);
    //   getRole(roleId).then(response => {
    //     this.form = response.data;
    //     this.open = true;
    //     this.$nextTick(() => {
    //       roleMenu.then(res => {
    //         let checkedKeys = res.checkedKeys
    //         checkedKeys.forEach((v) => {
    //             this.$nextTick(()=>{
    //                 this.$refs.menu.setChecked(v, true ,false);
    //             })
    //         })
    //       });
    //     });
    //     this.title = "修改角色";
    //   });
    // },
    //
    // /** 提交按钮 */
    // submitForm: function() {
    //   this.$refs["form"].validate(valid => {
    //     if (valid) {
    //       if (this.form.roleId != undefined) {
    //         this.form.menuIds = this.getMenuAllCheckedKeys();
    //         updateRole(this.form).then(response => {
    //           this.$modal.msgSuccess("修改成功");
    //           this.open = false;
    //           this.getList();
    //         });
    //       } else {
    //         this.form.menuIds = this.getMenuAllCheckedKeys();
    //         addRole(this.form).then(response => {
    //           this.$modal.msgSuccess("新增成功");
    //           this.open = false;
    //           this.getList();
    //         });
    //       }
    //     }
    //   });
    // },
    // /** 提交按钮（数据权限） */
    // submitDataScope: function() {
    //   if (this.form.roleId != undefined) {
    //     this.form.deptIds = this.getDeptAllCheckedKeys();
    //     dataScope(this.form).then(response => {
    //       this.$modal.msgSuccess("修改成功");
    //       this.openDataScope = false;
    //       this.getList();
    //     });
    //   }
    // },






  },

};
</script>

<style scoped>
::v-deep .demo-drawer__content .el-input--medium .el-input__inner{
  height:30px;
  width: 230px;
}
::v-deep .demo-drawer__content .el-textarea__inner{
  width: 510px;
  height: 100px;
}
::v-deep .demo-drawer__content .el-form--inline .el-form-item{
  margin-right:18px;
  margin-left:30px;
}
::v-deep .el-form--label-top .el-form-item__label{
  padding: 0px;
}
</style>

