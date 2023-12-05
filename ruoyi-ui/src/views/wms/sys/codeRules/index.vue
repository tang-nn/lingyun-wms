<template>
  <div class="app-container">
    <el-form
      v-show="showSearch"
      ref="queryForm"
      :inline="true"
      :model="queryParams"
      size="small"
    >
      <el-form-item
        label="目标表单"
        label-width="100px"
        prop="targetForm"
      >
        <el-select
          v-model="queryParams.targetForm"
          clearable
          placeholder="请选择"
          style="width: 120px"
        >
          <el-option
            v-for="dict in dict.type.order_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item
        label="编号前缀"
        label-width="86px"
        prop="numberPrefix"
      >
        <el-input
          v-model.trim="queryParams.numberPrefix"
          clearable
          placeholder="请输入单位名称/编号"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item
        label="时间规则"
        label-width="86px"
        prop="timeRules"
      >
        <el-input
          v-model.trim="queryParams.timeRules"
          clearable
          placeholder="请输入"
          style="width: 200px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item
        label="操作时间"
        label-width="86px"
      >
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
        <el-button
          icon="el-icon-search"
          size="mini"
          type="primary"
          @click="handleQuery"
        >搜索
        </el-button>
        <el-button
          icon="el-icon-refresh"
          size="mini"
          @click="resetQuery"
        >重置
        </el-button>
      </el-form-item>
    </el-form>

    <el-row
      :gutter="10"
      class="mb8"
    >
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
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="codeRuleList"
    >
      <el-table-column
        align="center"
        fixed
        label="序号"
        type="index"
        width="40"
      >
        <template slot="header">
          <i class="el-icon-setting"></i>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        label="目标表单"
        prop="poCode"
      >
        <template slot-scope="scope">
          <dict-tag
            :options="dict.type.order_type"
            :value="scope.row.targetForm"
          />
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        label="编号前缀"
        prop="numberPrefix"
      />
      <el-table-column
        :formatter="(row)=>this.timeRules.map(e=> row.timeRules.includes(e) ? this.codeRuleMap[e] : '').join('')"
        align="center"
        label="时间规则"
        prop="timeRules"
      />
      <el-table-column
        align="center"
        label="流水号/位"
        prop="serialNumber"
      />
      <el-table-column
        align="center"
        label="步长"
        prop="stepLength"
      />
      <el-table-column
        align="center"
        label="编号生成规则"
        prop="numberingRules"
        width="240"
      />
      <el-table-column
        align="center"
        label="操作时间"
        prop="updateTime"
      >
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, "{y}年{m}月{d}日") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        class-name="small-padding fixed-width"
        fixed="right"
        label="操作"
        width="120"
      >
        <template
          v-if="scope.row.roleId !== 1"
          slot-scope="scope"
        >
          <el-button
            v-hasPermi="['system:role:edit']"
            icon="el-icon-edit"
            size="mini"
            type="text"
            @click="handleUpdate(scope.row)"
          >修改
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination
      v-show="total > 0"
      :limit.sync="queryParams.pageSize"
      :page.sync="queryParams.pageNum"
      :total="total"
      @pagination="getList"
    />
    <!-- 添加或修改参数配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" append-to-body width="600px">
      <el-form ref="codeRuleForm" :inline="true" :model="formData" :rules="rules" label-width="80px">
        <el-form-item label="目标表单" prop="targetForm">
          <el-input disabled style="width: 180px" v-bind:value="formData.targetForm"/>
        </el-form-item>
        <el-form-item label="编号前缀" prop="numberPrefix">
          <el-input v-model="formData.numberPrefix" placeholder="请输入编号前缀" style="width: 180px"/>
        </el-form-item>
        <el-form-item label="时间规则" prop="timeRules">
          <div style="margin: 0 15px;">
            <el-checkbox v-model="checkAll" :indeterminate="isIndeterminate" @change="handleCheckAllChange">
              全选
            </el-checkbox>
            <el-checkbox-group v-model="checkedRules" @change="handleCheckedRulesChange">
              <el-checkbox v-for="(r,i) in timeRules" :key="i" :label="r">{{ codeRuleMap[r] + r }}</el-checkbox>
            </el-checkbox-group>
          </div>
        </el-form-item>
        <el-form-item label="流水号/位" label-width="92px" prop="serialNumber">
          <el-input-number v-model="formData.serialNumber" :max="5" :min="2" :step="1" placeholder="请输入单位名称/编号" style="width: 180px"></el-input-number>
        </el-form-item>
        <el-form-item label="步长" prop="stepLength">
          <el-input-number v-model="formData.stepLength" :max="10" :min="1" :step="1" placeholder="请输入步长" style="width: 180px"></el-input-number>
        </el-form-item>
        <el-form-item label="编号生成规则" label-width="104px" prop="numberingRules">
          <el-input v-model="formData.numberingRules" readonly style="width: 426px;" ></el-input>
        </el-form-item>
        <el-form-item label="预览">
          <div>{{codeRulePreview}}</div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

import {exportEncodeRules, listRules, updateEncodeRules} from "@/api/wms/system/codeRules";

export default {
  name: "Role",
  dicts: ['order_type'],
  data() {
    return {
      // 遮罩层
      loading: false,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 角色表格数据
      codeRuleList: [],
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
      // 查询参数
      queryParams: {
        targetForm: "",
        numberPrefix: undefined,
        timeRules: undefined,
        params: {}
      },
      codeRulePreview:"YYYYMMDDHHMMSS001",
      // 表单参数
      formData: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      rules: {
        stepLength: [
          {pattern: /^[1-9]\d*$/, required: true, message: '请输入数字', trigger: 'change'},
        ],
        serialNumber: [
          {pattern: /^[1-9]\d*$/, required: true, message: '请输入数字', trigger: 'change'},
        ],
      },
      // 修改时的时间规则 - 复选框
      codeRuleMap:{
        'yyyy': '年',
        'MM': '月',
        'dd': '日',
        'HH': '时',
        'mm': '分',
        'ss': '秒',
      },
      checkedRules:[],
      checkAll: false,
      isIndeterminate: true
    };
  },
  watch: {
    formData: {
      handler(){
        this.computedCodeRules()
      },
      deep:true
    },
    checkedRules:{
      handler(){
        this.computedCodeRules()
      },
      deep:true
    }
  },
computed:{
  timeRules(){return Object.keys(this.codeRuleMap)}
},
  created() {
    this.getList();
  },
  methods: {
    format(date, formatString) {
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, '0');
      const day = String(date.getDate()).padStart(2, '0');
      const hours = String(date.getHours()).padStart(2, '0');
      const minutes = String(date.getMinutes()).padStart(2, '0');
      const seconds = String(date.getSeconds()).padStart(2, '0');

      formatString = formatString.replace(/yyyy/g, year);
      formatString = formatString.replace(/MM/g, month);
      formatString = formatString.replace(/dd/g, day);
      formatString = formatString.replace(/hh/g, hours);
      formatString = formatString.replace(/mm/g, minutes);
      formatString = formatString.replace(/ss/g, seconds);

      return formatString;
    },
    computedCodeRules(){
      try {
        let cr = this.timeRules.filter(e => this.checkedRules.includes(e));
        let dateFormat = cr.join('');
        this.formData.numberingRules = this.formData.numberPrefix + dateFormat + '0'.repeat(this.formData.serialNumber - 1) + '1';
        // let date = this.parseTime(Date.now(), "{y}{m}{d}");
        this.codeRulePreview = this.formData.numberingRules.replace(dateFormat, this.format(new Date(),  dateFormat));
      } catch (e) {
        this.formData.numberingRules = '解析异常';
        this.codeRulePreview = '解析异常';
      }
    },
    /** 查询角色列表 */
    async getList() {
      this.loading = true;
      if (this.dateRange != null && this.dateRange.length > 0) {
        this.queryParams.params.beforeTime = this.dateRange[0];
        this.queryParams.params.endTime = this.dateRange[1];
      } else {
        this.queryParams.params = {};
      }
      this.codeRuleList = (await listRules(this.queryParams)).data;
      // console.log("codeRuleList: ", this.codeRuleList)
      this.loading = false;
    },
    /** 搜索按钮操作 */
    handleQuery() {
      console.log("搜索按钮操作")
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置搜索按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleUpdate(row) {
      // console.log("row.numberingRules: ", row.numberingRules);
      this.open = true;
      this.title = "编辑编码规则";
      this.formData = Object.assign({}, row)
      // this.formData.targetForm = row.targetForm;
      // this.formData.numberPrefix = row.numberPrefix;
      // this.formData.serialNumber = row.serialNumber;
      // this.formData.stepLength = row.stepLength;
      // this.formData.numberingRules = row.numberingRules;
      // console.log("formData.checkedRules: ", [...codeRuleMap.keys()].filter(e=>row.numberingRules.includes(e)));
      this.checkedRules = this.timeRules.filter(e => row.numberingRules.includes(e));
      this.formData.targetForm = this.dict.type.order_type.filter(e => e.value === this.formData.targetForm)?.[0]?.label
      // this.formData.numberingRules = this.numberingRules;
    },
    handleExport() {
      exportEncodeRules();
    },
    handleCheckAllChange(val) {
      // console.log("val: ", val)
      this.checkedRules = val ? this.timeRules : [];
      this.isIndeterminate = false;
    },
    handleCheckedRulesChange(value) {
      // console.log("checkedRules: ", this.formData.checkedRules)
      // debugger
      // console.log("values: ", value)
      let checkedCount = value.length;
      this.checkAll = checkedCount === this.timeRules.length;
      this.isIndeterminate = checkedCount > 0 && checkedCount < this.timeRules.length;
    },
    async submitForm() {
      let timeRules = this.timeRules.filter(e => this.checkedRules.includes(e)).join('');
      let {code,msg,...data} = (await updateEncodeRules({
        numberPrefix:this.formData.numberPrefix,
        timeRules,
        serialNumber: this.formData.serialNumber,
        stepLength: this.formData.stepLength,
        numberingRules: this.formData.numberingRules,
        erId: this.formData.erId
      }));
      if(code === 200){
        this.open = false;
        this.$message({
          message: msg,
          type: 'success'
        });
        await this.getList();
      }else{
        this.$message.error(msg);
      }
    },
    /** 重置编辑编码规则按钮操作 */
    resetCodeRule() {
      this.checkedRules = [];
      this.resetForm("codeRuleForm");
    },
    cancel() {
      this.$confirm('检测到未保存的内容，是否关闭？', '确认信息', {
        distinguishCancelAndClose: true,
        confirmButtonText: '确认关闭',
        cancelButtonText: '取消'
      })
        .then(() => {
          this.resetCodeRule();
          this.open = false;
        })
        .catch(action => {
          // 未关闭
        });
    }
  }
}
</script>
