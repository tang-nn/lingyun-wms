<template xmlns="http://www.w3.org/1999/html">
  <div class="form-container">
    <div class="c01">
      <div class="c01-01">
        <p>基础信息</p>
      </div>
      <el-row>
        <el-col :span="12">
          <label>客户编码：</label>
          <el-input type="text"  v-model="customerCode" placeholder="自动获取系统编号"/>
        </el-col>
        <el-col :span="12">
          <label>客户名称：</label>
          <el-input type="text" v-model="customerName" placeholder="请输入"/>
        </el-col>
        <el-col :span="12">
          <label>所属行业：</label>
          <el-select v-model="industry" placeholder="请输入">
            <el-option
              v-for="dict in dict.type.cu_industry"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-col>
        <el-col :span="12">
          <label>客户来源：</label>
          <el-select v-model="customerSource" placeholder="请输入">
            <el-option
              v-for="dict in dict.type.customer_source"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-col>
        <el-col :span="12">
          <label>联系人：</label>
          <el-input type="text" v-model="contactPerson" placeholder="请输入"/>
        </el-col>
        <el-col :span="12">
          <label>联系电话：</label>
          <el-input type="text" v-model="contactPhone" placeholder="请输入"/>
        </el-col>
        <el-col :span="12">
          <label>电子邮箱：</label>
          <el-input type="text" v-model="emailAddress" placeholder="请输入"/>
        </el-col>
        <el-col :span="12">
          <label>销售部门：</label>
          <el-input type="text" v-model="salesDepartment" @focus="dialogVisible01 = true" @blur="dialogVisible01 = false" placeholder="请输入"/>
        </el-col>
        <el-col :span="12">
          <label>销售负责人：</label>
          <el-input type="text" v-model="salesManager" @focus="dialogVisible02 = true" @blur="dialogVisible02 = false" placeholder="请输入"/>
        </el-col>
        <el-col :span="12">
          <label>状态：</label>
          <el-radio v-model="radio" label="1">启用</el-radio>
          <el-radio v-model="radio" label="2">停用</el-radio>
        </el-col>
        <el-col :span="24">
          <label>客户地址：</label>
          <el-input type="text" v-model="customerAddress" placeholder="请输入"/>
        </el-col>
        <el-col :span="24">
          <label>备注：</label>
          <el-input type="text" v-model="remark" placeholder="请输入"/>
        </el-col>
      </el-row>
    </div>

    <div class="c02">
      <div class="c02-01">
        <p>财务信息</p>
      </div>
      <el-row>
        <el-col :span="12">
          <label>账户名称：</label>
          <el-input type="text" v-model="accountName" placeholder="请输入"/>
        </el-col>
        <el-col :span="12">
          <label>纳税人识别号：</label>
          <el-input type="text" v-model="taxpayerIdentificationNumber" placeholder="请输入"/>
        </el-col>
        <el-col :span="12">
          <label>银行账户：</label>
          <el-input type="text" v-model="bankAccount" placeholder="请输入"/>
        </el-col>
        <el-col :span="12">
          <label>开户银行：</label>
          <el-input type="text" v-model="openingBank" placeholder="请输入"/>
        </el-col>
        <el-col :span="12">
          <label>开户行地址：</label>
          <el-input type="text" v-model="openingBankAddress" placeholder="请输入"/>
        </el-col>
      </el-row>
    </div>

    <div class="c04">
      <el-button class="c04-01" @click="resetForm">关闭</el-button>
      <el-button class="c04-02" @click="submitForm">保存</el-button>
    </div>

    <el-dialog
      title="提示"
      :visible.sync="dialogVisible01"
      width="30%"
      :before-close="handleClose">
      <span>这是一段信息</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible01 = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible01 = false">确 定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible02"
      width="30%"
      :before-close="handleClose">
      <span>这是一段信息</span>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible02 = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible02 = false">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>
export default {
  dicts: ['cu_industry','customer_source'],
  data() {
    return {
      customerCode: '', // 客户编码
      customerName: '', // 客户名称
      industry: [], // 所属行业
      customerSource: '', // 客户来源
      contactPerson: '', // 联系人
      contactPhone: '', // 联系电话
      emailAddress: '', // 电子邮箱
      salesDepartment: '', // 销售部门
      salesManager: '', // 销售负责人
      status: '', // 状态
      customerAddress: '', // 客户地址
      remark: '', // 备注
      accountName: '', // 账户名称
      taxpayerIdentificationNumber: '', // 纳税人识别号
      bankAccount: '', // 银行账户
      openingBank: '', // 开户银行
      openingBankAddress: '', // 开户行地址
      fileList: [],
      radio: '1',
      options01:[{}],
      options02:[{}],
      dialogVisible01: false,
      dialogVisible02: false,
    };
  },
  methods: {
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${ file.name }？`);
    },
    //返回客户信息
    resetForm: function (row) {
      const roleId = row.roleId;
      this.$tab.openPage("客户", "/contactUnits/consumer/index");
    },
    //添加客户
    submitForm (){
      try{

      }catch (err){

      }
    },
    handleClose(done) {
      this.$confirm('确认关闭？')
        .then(_ => {
          done();
        })
        .catch(_ => {});
    },
  },
};
</script>

<style scoped>

.el-row > .el-col >>> .el-button{
  width: 360px;
  height: 36px;
  padding: 6px 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 12px;
  box-sizing: border-box;
}

.c04 {
  display: flex;
  justify-content: flex-end;
  position: fixed;
  left: 0;
  bottom: 0;
  width: 100%;
  background-color: #FFFFFF;
  padding: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
}

.c03-02-02{
  background-color: #FFFFFF;
  color: #191919;
  border: 1px solid #312e2e;
}

.c03-02-01{
  padding-left: 30px;
  font-size: 12px;
  color: #606266;
}

.c03-02{
  padding: 20px 40px;
}

.c01,.c02,.c03{
  background-color: #fff;
  padding-bottom: 10px;
  margin-bottom: 30px;
  box-shadow: 0 0 4px rgba(0, 0, 0, 0.3);
  border-radius: 5px;
}

.c03{
  margin-bottom: 150px;
}

.c01 > .c01-01,.c02 > .c02-01,.c03 > .c03-01{
  padding: 20px;
  border: 1px solid rgba(238, 238, 238, 1);
}

.c01 > div > p,.c02 > div > p,.c03 > div > p{
  margin: 0px 0px;
  font-weight: 550;
}

.el-row > .el-col {
  padding: 10px 10px 10px 10px;
}

.el-row > .el-col > label{
  font-size: 12px;
  text-align: center;
  font-weight: 550;
}

.el-row > .el-col > .el-input{
  width: 360px;
  height: 36px;
}

.el-row > .el-col > .el-input >>> .el-input__inner{
  width: 360px;
  height: 36px;
  padding: 6px 8px;
  border: 1px solid #ccc;
  border-radius: 4px;
  font-size: 12px;
  box-sizing: border-box;
}

.el-row .el-col .el-select >>> .el-input--medium {
  width: 360px;
  height: 36px;
  line-height: 36px;
  font-size: 12px;
}

.my-select{
  height: 30px;
  line-height: 30px;
}

.el-input >>> .el-input__inner:hover{
  border: 1px solid #b8f5b2;
}

.el-input >>> .el-input__inner:active{
  border: 1px solid #b8f5b2;
}

.form-container {
  margin: 20px 20px;
  background-color: #f7f7f7;
  border-radius: 5px;
}

.form-container label {
  display: inline-block;
  width: 120px;
  margin-right: 10px;
}

.form-container .el-row {
  margin-bottom: 10px;
}
</style>
