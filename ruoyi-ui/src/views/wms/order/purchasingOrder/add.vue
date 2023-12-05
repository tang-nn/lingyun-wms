<template>
  <div class="container">
    <el-form ref="ruleForm" :model="ruleForm" :rules="rules" class="demo-ruleForm" label-width="100px">
      <div class="group">
        <el-row>
          <el-col :span="3">
            基础信息
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="进货单号" prop="name">
              <el-input v-model="ruleForm.name" placeholder="自动获取系统编号" style="width: 432px;"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="进货日期" required>
              <el-date-picker
                v-model="ruleForm.date"
                placeholder="选择日期"
                type="datetime">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="供应商" prop="region">
              <el-select v-model="ruleForm.region" placeholder="请选择供应商">
                <el-option label="供应商一" value="1"></el-option>
                <el-option label="供应商二" value="0"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系人" prop="nickName">
              <el-input v-model="ruleForm.nickName" maxlength="30" placeholder="请输入用户昵称"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="联系方式" prop="nickName">
          <el-input v-model="ruleForm.nickName" maxlength="30" placeholder="请输入用户昵称"/>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="进货部门" prop="deptId">
              <treeselect v-model="ruleForm.deptId" :options="deptOptions" :show-count="true"
                          placeholder="请选择归属部门"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="进货人" prop="nickName">
              <el-input v-model="ruleForm.nickName" maxlength="30" placeholder="请输入用户昵称"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注" prop="desc">
          <el-input v-model="ruleForm.desc" :autosize="{ minRows: 2, maxRows: 6 }" :rows="6" type="textarea"></el-input>
        </el-form-item>
      </div>
      <div class="group">
        <el-row>
          <el-col :span="3">
            进货明细
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <el-form-item>
          <el-button type="primary" @click="dialogTableVisible = true">添加</el-button>
          <el-button @click="resetForm('ruleForm')">移除</el-button>
        </el-form-item>
        <el-table v-loading="loading" :data="selectGoods" @selection-change="handleSelectionChange">
          <el-table-column align="center" fixed type="selection" width="50"/>
          <el-table-column align="center" fixed label="序号" type="index" width="60"/>
          <el-table-column align="center" fixed label="货品名称" prop="goodsName" width="80"/>
          <el-table-column align="center" fixed label="货品编号" prop="goodsCode" width="110"/>
          <el-table-column align="center" label="规格型号" prop="specCode" width="100"/>
          <el-table-column align="center" label="单位" prop="unit" width="100"/>
          <el-table-column align="center" label="货品类型" prop="goodsType" width="100"/>
          <el-table-column align="center" label="当前库存" prop="currentInventory" width="100"/>
          <el-table-column align="center" label="货品数量" prop="itemQuantity" width="100"/>
          <el-table-column align="center" label="进货单价" prop="puPrice" width="100"/>
          <el-table-column align="center" label="金额" prop="总金额" width="100"/>
          <el-table-column align="center" label="备注" prop="remark" width="100"/>
          <el-table-column align="center" class-name="small-padding fixed-width" fixed="right" label="操作" width="120">
            <template v-if="scope.row.roleId !== 1" slot-scope="scope">
              <el-button
                icon="el-icon-delete"
                size="mini"
                type="text"
                @click="handleDelete(scope.row)"
              >删除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div class="group">
        <el-row>
          <el-col :span="3">
            上传附件
          </el-col>
        </el-row>
        <el-upload
          ref="upload"
          :auto-upload="false"
          :file-list="selectAnnex"
          :multiple="true"
          :on-preview="handlePreview"
          :on-remove="handleRemove"
          accept="*.doc,*.pdf,*.rar"
          action="https://jsonplaceholder.typicode.com/posts/"
          class="upload-demo">
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          <el-button size="small" style="margin-left: 10px;" type="success" @click="submitUpload">上传到服务器
          </el-button>
          <div slot="tip" class="el-upload__tip">您可以上传 doc, pdf, rar 等格式的文件，单个文件上传最大 200MB。</div>
        </el-upload>
      </div>
    </el-form>
    <el-dialog :visible.sync="dialogTableVisible" title="添加货品">
      <el-form ref="queryForm" :inline="true" :model="queryGoodsParams" size="small">
        <el-form-item label-width="86px" prop="unit">
          <el-select
            v-model="queryGoodsParams.orderStatus"
            clearable
            placeholder="请选择单位"
            style="width: 120px"
          >
            <el-option
              v-for="dict in dict.type.g_unit_goods"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label-width="86px" prop="codeOrName">
          <el-input
            v-model.trim="queryGoodsParams.suppName"
            clearable
            placeholder="请输入货品编号/名称"
            style="width: 200px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button icon="el-icon-search" size="mini" type="primary" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table :data="goodsList">
        <el-table-column align="center" fixed type="selection" width="50"/>
        <el-table-column align="center" fixed label="序号" type="index" width="60"/>
        <el-table-column align="center" fixed label="货品编号" prop="goodsCode" width="80"/>
        <el-table-column align="center" fixed label="货品名称" prop="goodsName" width="110"/>
        <el-table-column align="center" label="规格型号" prop="specCode" width="100"/>
        <el-table-column align="center" label="单位" prop="unit" width="100"/>
        <el-table-column align="center" label="入库单价" prop="iuPrice" width="100"/>
        <el-table-column align="center" label="当前库存" prop="currentInventory" width="100"/>
      </el-table>
      <pagination
        v-show="goodsTotal>0"
        :limit.sync="queryGoodsParams.pageSize"
        :page.sync="queryGoodsParams.pageNum"
        :total="goodsTotal"
        @pagination="getList"
      />
    </el-dialog>
  </div>
</template>

<script>
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import Treeselect from "@riophae/vue-treeselect";
import {deptTreeSelect} from "@/api/system/user";

export default {
  name: "addPurchaseInf",
  dicts: ['g_unit_goods'],
  components: {
    Treeselect
  },
  data() {
    return {
      ruleForm: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: ''
      },
      rules: {
        name: [
          {required: true, message: '请输入活动名称', trigger: 'blur'},
          {min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur'}
        ],
        region: [
          {required: true, message: '请选择活动区域', trigger: 'change'}
        ],
        date1: [
          {type: 'date', required: true, message: '请选择日期', trigger: 'change'}
        ],
        date2: [
          {type: 'date', required: true, message: '请选择时间', trigger: 'change'}
        ],
        type: [
          {type: 'array', required: true, message: '请至少选择一个活动性质', trigger: 'change'}
        ],
        resource: [
          {required: true, message: '请选择活动资源', trigger: 'change'}
        ],
        desc: [
          {required: true, message: '请填写活动形式', trigger: 'blur'}
        ]
      },
      // 部门树选项
      deptOptions: undefined,
      loading: false,
      selectGoods: [],
      selectAnnex: [],
      fileList: [],
      dialogTableVisible: false,
      goodsList: [],
      queryGoodsParams: {},
      goodsTotal:0
    }
  },
  created() {
    this.getDeptTree();
  },
  methods: {
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data;
      });
    },
    resetForm(formName) {
      this.$refs[formName].resetFields();
    },
    submitUpload() {
      this.$refs.upload.submit();
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    }
  },
  mounted() {
  }
}
</script>

<style scoped>
.container {
  margin: 30px 300px;
  background: #f3f6f8;
}

.container .group {
  background: #FFFFFF;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
  padding: 20px;
  margin-bottom: 20px;
}
</style>
