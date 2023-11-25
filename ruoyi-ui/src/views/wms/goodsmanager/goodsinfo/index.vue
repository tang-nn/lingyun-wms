<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="货品" prop="g_name">
        <el-input
          v-model="queryParams.g_name"
          placeholder="请输入货品名称"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
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
      <el-form-item label="货品类型" prop="type">
          <el-select
            v-model="queryParams.type"
            placeholder="请选择"
            clearable
            style="width: 240px"
          >
  <!--          <el-option :value="searchForm.mineStatus" key="id" :label="label" style="height: auto">-->
  <!--            <el-tree-->
  <!--              :data="dataList"-->
  <!--              :props="defaultProps"-->
  <!--              ref="tree"-->
  <!--              node-key="id"-->
  <!--              :expand-on-click-node="false"-->
  <!--              :check-on-click-node="true"-->
  <!--              @node-click="handleNodeClick"-->
  <!--            ></el-tree>-->
  <!--          </el-option>-->
          </el-select>
      </el-form-item>
      <!--      :label属性来指定在页面上显示的文本    :value属性来指定选项的值。-->
      <el-form-item label="单位" prop="unit" >
          <el-select
            v-model="queryParams.unit"
            placeholder="请选择"
            clearable
            style="width: 240px"
          >
            <el-input
              placeholder="请输入"
              style="width: 220px;margin-left: 8px"
            />
            <el-option
              v-for="dict in dict.type.g_unit_goods"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"

            />
          </el-select>
      </el-form-item>
      <el-form-item>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          <el-button type="primary"  size="mini" @click="handleQuery">查询</el-button>
  <!--        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">高级搜索</el-button>-->
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
          v-hasPermi="['system:dept:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit-outline"
          size="mini"
          :disabled="single"
          @click="handleAdd"
          v-hasPermi="['system:dept:add']"
        >编辑</el-button>
      </el-col>
      <el-col :span="1.5">

        <el-button
          type="danger"
          plain
          icon="el-icon-minus"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['gd:good:delete']"
        >删除</el-button>
      </el-col>

<!--      <el-col :span="1.5">-->
<!--        <el-button-->
<!--          type="primary"-->
<!--          plain-->
<!--          icon="el-icon-document-copy"-->
<!--          size="mini"-->
<!--          @click="handleAdd"-->
<!--          v-hasPermi="['system:dept:add']"-->
<!--        >复制</el-button>-->
<!--      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['gd:good:import']"
        >导入</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['gd:good:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-loading"
          size="mini"
          @click="handleQuery"
          v-hasPermi="['system:dept:add']"
        >刷新</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="goodList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" fixed width="55" align="center" />
      <el-table-column label='' fixed prop="g_id" width="100"/>
      <el-table-column label="货品编号" fixed prop="g_code" width="120" />
      <el-table-column label="货品名称" fixed prop="g_name" :show-overflow-tooltip="true" width="150" />
      <el-table-column label="状态"  fixed align="center" width="100">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            :active-value="0"
            :inactive-value="1"
            @change="handleStatusChange(scope.row)"
          ></el-switch>

        </template>
      </el-table-column>
      <el-table-column label="货品类型" prop="gt_name" :show-overflow-tooltip="true" width="150" />
      <el-table-column label="规格型号" prop="spec_code" width="100" />
      <el-table-column label="单位" align="center"  width="180">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.g_unit_goods" :value="scope.row.unit" />
        </template>
      </el-table-column>

      <el-table-column label="供应商" prop="s_name" width="100" />
      <el-table-column label="入库参考价/元" prop="wr_price" :formatter="formatPrice" width="110" />
      <el-table-column label="出库参考价/元" prop="or_price" :formatter="formatPrice2" width="110" />
      <el-table-column label="显示顺序" prop="sort" width="100" />
      <el-table-column label="当前库存" prop="item_quantity" :formatter="formatStock" width="100" />
      <el-table-column label="库存总金额" prop="stocktotalmoney" :formatter="formatPrice3" width="100" />
      <el-table-column label="保质期管理" prop="has_shelf_life" width="100" :formatter="formathasShelfLife" />
      <el-table-column label="保质期" prop="shelf_life" width="100" />
      <el-table-column label="预警天数(天)" prop="w_days" width="100" />
      <el-table-column label="操作人" prop="oneuser" width="100" />
      <el-table-column label="操作时间" prop="create_time" width="100" >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.create_time) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope" >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:role:edit']"
          >编辑</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['gd:good:delete']"
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

    <!-- 添加或修改角色配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="角色名称" prop="roleName">
          <el-input v-model="form.roleName" placeholder="请输入角色名称" />
        </el-form-item>
        <el-form-item prop="roleKey">
          <span slot="label">
            <el-tooltip content="控制器中定义的权限字符，如：@PreAuthorize(`@ss.hasRole('admin')`)" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
            权限字符
          </span>
          <el-input v-model="form.roleKey" placeholder="请输入权限字符" />
        </el-form-item>
        <el-form-item label="角色顺序" prop="roleSort">
          <el-input-number v-model="form.roleSort" controls-position="right" :min="0" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="菜单权限">
          <el-checkbox v-model="menuExpand" @change="handleCheckedTreeExpand($event, 'menu')">展开/折叠</el-checkbox>
          <el-checkbox v-model="menuNodeAll" @change="handleCheckedTreeNodeAll($event, 'menu')">全选/全不选</el-checkbox>
          <el-checkbox v-model="form.menuCheckStrictly" @change="handleCheckedTreeConnect($event, 'menu')">父子联动</el-checkbox>
          <el-tree
            class="tree-border"
            :data="menuOptions"
            show-checkbox
            ref="menu"
            node-key="id"
            :check-strictly="!form.menuCheckStrictly"
            empty-text="加载中，请稍候"
            :props="defaultProps"
          ></el-tree>
        </el-form-item>
        <el-form-item label="备注">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>


    <!-- 商品导入对话框 -->
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
            <el-checkbox v-model="upload.updateSupport" /> 是否更新已经存在的商品数据
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

</div>
</template>

<script>
import { listGood,TypesList, getRole, delRole, addRole, updateRole, dataScope, changeRoleStatus, deptTreeSelect } from "@/api/wms/good/goodsinfo";
import { treeselect as menuTreeselect, roleMenuTreeselect } from "@/api/system/menu";
import {getToken} from "@/utils/auth";

export default {
  name: "Role",
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
      // 数据范围选项
      dataScopeOptions: [
        {
          value: "1",
          label: "全部数据权限"
        },
        {
          value: "2",
          label: "自定数据权限"
        },
        {
          value: "3",
          label: "本部门数据权限"
        },
        {
          value: "4",
          label: "本部门及以下数据权限"
        },
        {
          value: "5",
          label: "仅本人数据权限"
        }
      ],
      // 菜单列表
      menuOptions: [],
      // 部门列表
      deptOptions: [],
      //商品类型
      goodsTypes:{},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 5,
        g_name: undefined,
        status:undefined,
        unit: undefined,
        type:undefined
      },
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      rules: {
        roleName: [
          { required: true, message: "角色名称不能为空", trigger: "blur" }
        ],
        roleKey: [
          { required: true, message: "权限字符不能为空", trigger: "blur" }
        ],
        roleSort: [
          { required: true, message: "角色顺序不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.goodsTypesList();
  },
  methods: {

    //保留两位小数
    formatPrice(row,column) {
      // console.info(row);
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
    /** 查询菜单树结构 */
    getMenuTreeselect() {
      menuTreeselect().then(response => {
        this.menuOptions = response.data;
      });
    },

    /* 查看所有商品分类 */
    goodsTypesList(){
      TypesList().then(response => {
        this.goodsTypes = response.data;
        console.info(response.data);
      });



    },
    // 所有菜单节点数据
    getMenuAllCheckedKeys() {
      // 目前被选中的菜单节点
      let checkedKeys = this.$refs.menu.getCheckedKeys();
      // 半选中的菜单节点
      let halfCheckedKeys = this.$refs.menu.getHalfCheckedKeys();
      checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys);
      return checkedKeys;
    },
    // 所有部门节点数据
    getDeptAllCheckedKeys() {
      // 目前被选中的部门节点
      let checkedKeys = this.$refs.dept.getCheckedKeys();
      // 半选中的部门节点
      let halfCheckedKeys = this.$refs.dept.getHalfCheckedKeys();
      checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys);
      return checkedKeys;
    },
    /** 根据角色ID查询菜单树结构 */
    getRoleMenuTreeselect(roleId) {
      return roleMenuTreeselect(roleId).then(response => {
        this.menuOptions = response.menus;
        return response;
      });
    },
    // /** 根据角色ID查询部门树结构 */
    // getDeptTree(roleId) {
    //   return deptTreeSelect(roleId).then(response => {
    //     this.deptOptions = response.depts;
    //     return response;
    //   });
    // },
    // 角色状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$modal.confirm('确认要"' + text + '""' + row.roleName + '"角色吗？').then(function() {
        return changeRoleStatus(row.roleId, row.status);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 取消按钮（数据权限）
    cancelDataScope() {
      this.openDataScope = false;
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
        roleId: undefined,
        roleName: undefined,
        roleKey: undefined,
        roleSort: 0,
        status: "0",
        menuIds: [],
        deptIds: [],
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
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.g_id)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    // 更多操作触发
    handleCommand(command, row) {
      switch (command) {
        case "handleDataScope":
          this.handleDataScope(row);
          break;
        case "handleAuthUser":
          this.handleAuthUser(row);
          break;
        default:
          break;
      }
    },
    // 树权限（展开/折叠）
    handleCheckedTreeExpand(value, type) {
      if (type == 'menu') {
        let treeList = this.menuOptions;
        for (let i = 0; i < treeList.length; i++) {
          this.$refs.menu.store.nodesMap[treeList[i].id].expanded = value;
        }
      } else if (type == 'dept') {
        let treeList = this.deptOptions;
        for (let i = 0; i < treeList.length; i++) {
          this.$refs.dept.store.nodesMap[treeList[i].id].expanded = value;
        }
      }
    },
    // 树权限（全选/全不选）
    handleCheckedTreeNodeAll(value, type) {
      if (type == 'menu') {
        this.$refs.menu.setCheckedNodes(value ? this.menuOptions: []);
      } else if (type == 'dept') {
        this.$refs.dept.setCheckedNodes(value ? this.deptOptions: []);
      }
    },
    // 树权限（父子联动）
    handleCheckedTreeConnect(value, type) {
      if (type == 'menu') {
        this.form.menuCheckStrictly = value ? true: false;
      } else if (type == 'dept') {
        this.form.deptCheckStrictly = value ? true: false;
      }
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.getMenuTreeselect();
      this.open = true;
      this.title = "添加角色";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();

      const roleId = row.roleId || this.ids
      const roleMenu = this.getRoleMenuTreeselect(roleId);
      getRole(roleId).then(response => {
        this.form = response.data;
        this.open = true;
        this.$nextTick(() => {
          roleMenu.then(res => {
            let checkedKeys = res.checkedKeys
            checkedKeys.forEach((v) => {
                this.$nextTick(()=>{
                    this.$refs.menu.setChecked(v, true ,false);
                })
            })
          });
        });
        this.title = "修改角色";
      });
    },
    /** 选择角色权限范围触发 */
    dataScopeSelectChange(value) {
      if(value !== '2') {
        this.$refs.dept.setCheckedKeys([]);
      }
    },
    /** 分配数据权限操作 */
    handleDataScope(row) {
      this.reset();
      const deptTreeSelect = this.getDeptTree(row.roleId);
      getRole(row.roleId).then(response => {
        this.form = response.data;
        this.openDataScope = true;
        this.$nextTick(() => {
          deptTreeSelect.then(res => {
            this.$refs.dept.setCheckedKeys(res.checkedKeys);
          });
        });
        this.title = "分配数据权限";
      });
    },
    /** 分配用户操作 */
    handleAuthUser: function(row) {
      const roleId = row.roleId;
      this.$router.push("/system/role-auth/user/" + roleId);
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.roleId != undefined) {
            this.form.menuIds = this.getMenuAllCheckedKeys();
            updateRole(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.menuIds = this.getMenuAllCheckedKeys();
            addRole(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 提交按钮（数据权限） */
    submitDataScope: function() {
      if (this.form.roleId != undefined) {
        this.form.deptIds = this.getDeptAllCheckedKeys();
        dataScope(this.form).then(response => {
          this.$modal.msgSuccess("修改成功");
          this.openDataScope = false;
          this.getList();
        });
      }
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const g_ids = row.g_id || this.ids;
      this.$modal.confirm('是否确认删除货品编号为"' + g_ids + '"的数据项？').then(function() {
        return delRole(g_ids);
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
    }
  },

};
</script>
