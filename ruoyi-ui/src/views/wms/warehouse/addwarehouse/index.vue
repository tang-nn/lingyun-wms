<template>
  <div class="app-container" style="background-color: #F3F6F8">

    <el-row :gutter="20" style="background-color: white;border-radius: 5px;box-shadow: 2px 2px 5px rgba(0,0,0,0.2);
   padding-bottom: 10px;margin-left: 200px;width: 1280px">
      <h3 style="margin-left: 20px">基础信息</h3>
      <div style="border-top: 1px solid #eeeeee;padding-top: 30px;padding-left: 30px">
      <el-form ref="elForm" :model="formData" label-width="95px" size="medium">
        <el-row>
            <el-col :span="9">
              <el-form-item label="仓库编号" prop="wCode">
                <el-input v-model="formData.wCode" :style="{width: '100%'}" clearable placeholder="自动获取系统编码"readonly>
                </el-input>
              </el-form-item>
            </el-col>
          <el-col :span="1" style="margin-left: -35px">
            <el-form-item prop="isDefault">
              <el-checkbox v-model="formData.isDefault" label="默认仓库">
              </el-checkbox>
            </el-form-item>
          </el-col>
            <el-col :span="9" style="margin-left: 180px">
              <el-form-item label="仓库名称" prop="wName">
                <el-input v-model="formData.wName" :style="{width: '100%'}" clearable placeholder="请输入">
                </el-input>
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="9">
              <el-form-item label="仓库容量/m³" prop="wCapacity">
                <el-input v-model="formData.wCapacity" :style="{width: '100%'}" clearable placeholder="请输入">
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="9" style="margin-left: 200px">
              <el-form-item label="库管部门" prop="deptName">
                <el-input
                  v-model="queryParams.deptName"
                  placeholder="请输入"
                  clearable
                  @focus="handledept"
                  suffix-icon="el-icon-more"
                />
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
          <el-col :span="9">
            <el-form-item label="仓库主管" prop="wSupervisor">
              <el-input
                v-model="formData.wSupervisor"
                placeholder="请输入"
                clearable
                @focus="handleUser"
                suffix-icon="el-icon-more"
              />
            </el-form-item>
          </el-col>
          <el-col :span="9" style="margin-left: 200px">
            <el-form-item label="联系电话" prop="tel">
              <el-input v-model="formData.tel" :style="{width: '100%'}" readonly clearable placeholder="请输入">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
            <el-col :span="22">
              <el-form-item label="仓库地址" prop="wAddress">
                <el-input v-model="formData.wAddress" :style="{width: '100%'}" clearable placeholder="请输入">
                </el-input>
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="排序" prop="sort">
              <el-input v-model="formData.sort" :style="{width: '100%'}" clearable placeholder="请输入">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="9" style="margin-left: 225px">

            <el-form-item label="仓库状态" prop="status" style="margin-left: 90px">
              <el-radio-group v-model="formData.status">
                <el-radio
                  v-for="dict in dict.type.sys_normal_disable"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
<!--        <el-row>-->
<!--          <el-col :span="11">-->
<!--            <el-form-item label="仓库锁定" prop="isLock">-->
<!--              <el-switch v-model="formData.isLock"></el-switch>-->
<!--            </el-form-item>-->
<!--          </el-col>-->
<!--        </el-row>-->
        <el-row>
          <el-col :span="19">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="formData.remark" :autosize="{minRows: 4, maxRows: 4}" :style="{width: '100%'}"
                        placeholder="请输入" type="textarea"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      </div>
     </el-row>

    <el-row :gutter="20" style="background-color: white;border-radius: 5px;box-shadow: 2px 2px 5px rgba(0,0,0,0.2);
    margin-top: 20px;padding-bottom: 10px;margin-left: 200px;width: 1280px;height: 700px ">
      <h3 style="margin-left: 20px">库位信息</h3>
      <div style="border-top: 1px solid #eeeeee;padding-top: 20px;padding-left: 30px">

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              v-hasPermi="['gd:good:add']"
              icon="el-icon-plus"
              plain
              size="mini"
              type="primary"
              @click="AddStoragePage"
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
        </el-row>

        <el-table :loading="loading" style="margin-top: 50px" :data="storageListAdd"
                  max-height="520px" @selection-change="handleSelectionChange" :row-class-name="rowStorageLocationIndex">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="序号" align="center" prop="index" width="50"/>
<!--          <el-table-column label="库位编号"  prop="sl_code" :show-overflow-tooltip="true" width="150" />-->
          <el-table-column label="库位名称"  prop="slName" :show-overflow-tooltip="true" width="150" />
          <el-table-column label="库位容量/m³" align="center" prop="locationCapacity" />
          <el-table-column label="仓位主管" align="center" prop="usera" />
          <el-table-column label="排序" align="center" prop="sort" />
          <el-table-column label="库位状态"   align="center" width="100" >
            <template slot-scope="scope">
              {{ scope.row.status == 0 ? '启用' : '停用' }}
            </template>
          </el-table-column>
          <el-table-column label="备注" align="center" prop="remark"/>
          <el-table-column align="center"  class-name="small-padding fixed-width" fixed="right" label="操作">
            <template slot-scope="scope" >
              <el-button
                v-hasPermi="['system:role:edit']"
                icon="el-icon-edit"
                size="mini"
                type="text"
                @click="handleUpdate(scope.row)"
              >编辑</el-button>
              <el-button
                v-hasPermi="['gd:good:delete']"
                icon="el-icon-delete"
                size="mini"
                type="text"
                @click="handleDelete(scope.row)"
              >删除</el-button>
            </template>
          </el-table-column>
        </el-table>


        <pagination
          v-show="storageListAdd.length>0"
          :total="storageListAdd.length"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="storageListAdd"
        />
      </div>
    </el-row>


      <div class="footer-container" style=" position: fixed;
          box-shadow: 2px 2px 5px rgba(0,0,0,0.9);  background-color:white;border-radius: 2px;
          width: 89.5%; height: 60px; bottom: 0; right: 0;  right: 0;z-index: 1000">
        <el-footer>
          <!-- 底部导航栏内容 -->
          <el-button  v-hasPermi="['gd:good:add']"
            plain
            size="medium" style="float:right;margin-top: 12px"
            type="success" @click="submitForm" >保存</el-button>
          <el-button
            plain size="medium" style="float:right;margin-right: 20px;margin-top: 12px" @click="cancle" >关闭</el-button>
        </el-footer>
      </div>




    <!-- 添加或修改库位信息对话框 -->
    <el-drawer
      style="z-index:9999;"
      ref="drawer"
      :title="title"
      :visible.sync="dialog"
      custom-class="demo-drawer"
      direction="rtl"
    >
      <div class="demo-drawer__content" style="border-top: 1px solid #eeeeee;padding-top: 20px">
        <div style="margin: 20px;"></div>
        <el-form ref="form"  :model="form" :inline="true" :label-position="labelPosition"
                 label-width="80px" >

          <el-row>
            <el-form-item label="库位编号"  prop="slCode">
              <el-input v-model="form.slCode" placeholder="自动获取系统编号" readonly></el-input>
            </el-form-item>

            <el-form-item label="库位名称" prop="slName" style="margin-left: 90px">
              <el-input v-model="form.slName" placeholder="请输入"></el-input>
            </el-form-item>
          </el-row>

          <el-row>
              <el-form-item label="库位容量/立方" prop="locationCapacity">
                <el-input v-model="form.locationCapacity"placeholder="请输入"></el-input>
              </el-form-item>
              <el-form-item label="库位主管" prop="usera" style="margin-left: 90px">
                <el-input
                  v-model="form.usera"
                  placeholder="请输入"
                  clearable
                  @focus="handleUsera"
                  suffix-icon="el-icon-more"
                />
              </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="联系电话" prop="tela">
              <el-input v-model="form.tela"placeholder="请输入" readonly></el-input>
            </el-form-item>

            <el-form-item label="库位状态" prop="status" style="margin-left: 90px">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.sys_normal_disable"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-row>
          <el-row>
            <el-form-item label="排序" prop="sort">
              <el-input v-model="form.sort"placeholder="请输入"></el-input>
            </el-form-item>
          </el-row>
          <el-row>
              <el-form-item label="备注" prop="remark">
                <el-input v-model="form.remark" :autosize="{minRows: 4, maxRows: 4}" :style="{width: '100%'}"
                          placeholder="请输入" type="textarea"></el-input>
              </el-form-item>
          </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer" style="margin-top:200px;border-top: 1px solid #eeeeee;padding-top: 20px">
          <div style="margin-left: 344px;">
            <el-button @click="saveAddCancle">取 消</el-button>
            <el-button type="primary" @click="saveAdd">保存</el-button>
          </div>
        </div>
      </div>
    </el-drawer>



    <!--    仓库主管对话框-->
    <el-dialog :title="title" :visible.sync="openUser" width="420px" append-to-body>
      <div class="head-container">
        <el-input
          v-model="userdg"
          placeholder="请搜索"
          clearable
          size="small"
          suffix-icon="el-icon-search"
          style="margin-bottom: 20px"
        />
      </div>
      <div class="head-container" style="width:379px">
        <el-table :data="userList" @row-click="handleUserClick">
          <el-table-column class="bg" align="center" prop="userName" width="379px"/>
        </el-table>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="okUser">确 定</el-button>
        <el-button @click="cancleUser">取 消</el-button>
      </div>
    </el-dialog>

    <!--    库位主管对话框-->
    <el-dialog :title="title" :visible.sync="openUsera" width="420px" append-to-body>
      <div class="head-container">
        <el-input
          v-model="userdga"
          placeholder="请搜索"
          clearable
          size="small"
          suffix-icon="el-icon-search"
          style="margin-bottom: 20px"
        />
      </div>
      <div class="head-container" style="width:379px">
        <el-table :data="userLista" @row-click="handleUseraClick">
          <el-table-column class="bg" align="center" prop="userName" width="379px"/>
        </el-table>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="okUsera">确 定</el-button>
        <el-button @click="cancleUsera">取 消</el-button>
      </div>
    </el-dialog>


    <!--    库管部门对话框-->
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


import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {deptTreeSelect, listUser} from "@/api/system/user";
import {addWarehouse} from "@/api/wms/warehouse/warehouse.js";

export default {
  components: {Treeselect},
  dicts: ['sys_normal_disable'],
  props: [],
  data() {
    return {
      // 遮罩层
      loading: true,
      labelPosition: 'top',
      // 弹出层标题
      title: "",
      opendept:false,
      openUser:false,
      openUsera:false,
      dialog: false,
      //user保存前缓存值
      userdg:undefined,
      userid:undefined,
      userIda:undefined,
      userdga:undefined,
      tel:undefined,
      tela:undefined,
      ids:[],
      // 部门ID
      deptId:undefined,
      // 部门树选项
      deptOptions: undefined,
      // 仓库主管数据
      userList:[],
      //库位主管数据
      userLista:[],
      // 新增仓库下的所有库位表格数据
      storageListAdd:[],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deptName:null,
      },
      // 表单参数
      form: {
        status:"0",
        usera:null,
        tela:null,
      },
      // 非多个禁用
      multiple: true,
      defaultProps: {
        children: "children",
        label: "label"
      },
      formData: {
        //仓库字段
        wCode: undefined,
        //默认仓库
        isDefault: false,
        wName: undefined,
        wCapacity: undefined,
        deptName: undefined,
        wSupervisor: undefined,
        tel: undefined,
        wAddress: undefined,
        sort: undefined,
        status: "0",
        storageLocationList:[],
        // isLock: false,
        // remark: undefined,
        // //库位字段
        // slName:undefined,
        // locationCapacity:undefined,

      },
      // 表单校验
      // rules: {
      //   slName: [
      //     { required: true, message: "库位名称不能为空", trigger: "blur" }
      //   ],
      //   locationCapacity: [
      //     { required: true, message: "库位容量不能为空", trigger: "blur" }
      //   ],
      //   // usera: [
      //   //   { required: true, message: "仓位主管不能为空", trigger: "blur" }
      //   // ],
      //   tela: [
      //     { required: true, message: "联系电话不能为空", trigger: "blur" }
      //   ],
      //   status:[
      //     { required: true, message: "库位状态不能为空", trigger: "blur" }
      //   ],
      //   sort:[
      //     { required: true, message: "排序不能为空", trigger: "blur" }
      //   ],
      // },
    }
  },
  computed: {
  },
  watch: {},
  created() {
    this.getDeptTree();
  },
  mounted() {},
  methods: {
    /** 库位信息序号 */
    rowStorageLocationIndex({ row, rowIndex }) {
      row.index = rowIndex + 1;
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.index)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data;
      });
    },
    /*查询仓库主管*/
    getUser(){
      listUser({deptId: this.deptId.toString()}).then(response => {
        this.userList = response.rows;
        console.info("userList",this.userList);
      })
    },
    /*查询库位主管*/
    getUsera(){
      listUser().then(response => {
        this.userLista = response.rows;
      })
    },
    // // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.deptName=data.label;
      this.deptId=data.id;
      this.getUser();
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    //点击选中人员存入userdg缓存值
    handleUserClick(row) {
      this.userdg= row.userName;
      this.userid=row.userId;
      this.tel=row.phonenumber;
    },
    handleUseraClick(row){
      this.userdga= row.userName;
      this.userIda=row.userId;
      this.tela=row.phonenumber;
    },
    //触发部门框
    handledept(){
      this.opendept = true;
      this.title = "选择部门";
    },
    //触发人员显示框
    handleUser(){
      this.openUser=true;
      this.title = "选择人员";
    },
    handleUsera(){
      this.openUsera=true;
      this.title = "选择人员";
    },
    //确定选中人员
    okUser(){
      this.formData.wSupervisor=this.userdg;
      this.formData.tel=this.tel;
      this.openUser=false;
    },
    okUsera(){
      this.form.usera=this.userdga;
      this.form.tela=this.tela;
      this.openUsera=false;
    },
    //取消人员显示框
    cancleUser(){
      this.openUser=false;
    },
    cancleUsera(){
      this.openUsera=false;
    },
    //取消部门显示框
    canceldept(){
      this.opendept=false;
    },
    //关闭窗口
    cancle(){
      this.$router.push(`/warehousemanager`);
    },

    /** 打开新增库存页面操作 */
    AddStoragePage() {
      this.dialog=true;
      this.title = "新增";
      this.getUsera();
      this.reset();
    },
    // 表单重置
    reset() {
        this.form = {
          status: "0",
          slName: undefined,
          locationCapacity: undefined,
          remark: undefined,
          sort:undefined,
          usera: undefined
        };
      this.resetForm("form");
    },

    /*新增库位页面保存数据*/
    saveAdd(){
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.positionManager=this.userIda;
          this.storageListAdd.push(this.form);
          console.info("storageListAdd", this.storageListAdd);
          this.dialog = false;
        }
      });

    },
    /** 关闭新增库存页面操作 */
    saveAddCancle(){
      this.dialog = false;
      this.reset();
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      let indexs = row.index || this.ids;
      if(!Array.isArray(indexs)){
        indexs = [indexs];
      }
      console.info("indexs: ", indexs);
      this.$modal.confirm('是否确认删除库位编号为"' + indexs + '"的数据项？').then(()=> {
        this.storageListAdd = this.storageListAdd?.filter(e=>!indexs.includes(e.index));
      }).then(() => {
        this.$message.success("删除成功");
        console.log("this.storageListAdd: ", this.storageListAdd);
      }).catch(e => {
      });
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
    /*最终添加*/
    submitForm() {
      this.$refs['elForm'].validate(valid => {
        if (valid) {
          // TODO 提交表单
          this.formData.isDefault=this.formData.isDefault==true?1:0;
          // this.formData.isLock=this.formData.isLock==true?1:0;
          this.formData.wSupervisor=this.userid;
          this.formData.storageLocationList=this.storageListAdd;
          addWarehouse(this.formData).then(response =>{
            this.$modal.msgSuccess("新增成功");
            this.$router.push(`/warehousemanager`);

          })

        }

      })
    },
    resetForm() {
      this.$refs['Form'].resetFields();
    },


    },






}

</script>
<style scoped>
::v-deep{
 .el-drawer__body{
   margin-left: 27px;
 }
  .demo-drawer__content .el-textarea__inner{
    width: 510px;
    height: 100px
  }


}
</style>
