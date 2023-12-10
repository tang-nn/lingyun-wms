<template>
  <div class="app-container" style="background-color: #F3F6F8">

    <el-row :gutter="20" style="background-color: white;border-radius: 5px;box-shadow: 2px 2px 5px rgba(0,0,0,0.2);
   padding-bottom: 10px;margin-left: 40px;width: 1280px">
      <h3 style="margin-left: 20px">基础信息</h3>
      <div style="border-top: 1px solid #eeeeee;padding-top: 30px;padding-left: 30px">
      <el-form ref="elForm" :model="formData" :rules="rules" label-width="95px" size="medium">
        <el-col :span="19">
          <el-form-item label="货品名称" prop="field101">
            <el-input v-model="formData.field101" :style="{width: '100%'}" clearable placeholder="请输入">
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="货品编号" prop="field102">
            <el-input v-model="formData.field102" :style="{width: '100%'}" clearable placeholder="请输入">
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="货品类型" prop="field101">
            <el-select v-model="formData.field101" :style="{width: '100%'}" clearable placeholder="请选择">
              <el-option v-for="(item, index) in field101Options" :key="index" :disabled="item.disabled"
                         :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="规格型号" prop="field107">
            <el-input v-model="formData.field107" :style="{width: '100%'}" clearable placeholder="请输入">
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="供应商" prop="field108">
            <el-select v-model="formData.field108" :style="{width: '100%'}" clearable placeholder="请选择供应商">
              <el-option v-for="(item, index) in field108Options" :key="index" :disabled="item.disabled"
                         :label="item.label" :value="item.value"></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="客户名称" prop="field109">
            <el-select v-model="formData.field109" :style="{width: '100%'}" clearable placeholder="请选择">
              <el-option v-for="(item, index) in field109Options" :key="index" :disabled="item.disabled"
                         :label="item.label" :value="item.value"></el-option>

            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="入库参考价" prop="field110">
            <el-input v-model="formData.field110" :style="{width: '100%'}" clearable placeholder="请输入">
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="出库参考价" prop="field111">
            <el-input v-model="formData.field111" :style="{width: '100%'}" clearable placeholder="请输入">
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="显示顺序" prop="field112">
            <el-input v-model="formData.field112" :style="{width: '100%'}" clearable placeholder="请输入">
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="状态" prop="field113">
            <el-radio-group v-model="formData.field113" size="medium">
              <el-radio v-for="(item, index) in field113Options" :key="index" :disabled="item.disabled"
                        :label="item.value">{{item.label}}</el-radio>
            </el-radio-group>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="保质期管理" prop="field114">
              <el-checkbox v-for="(item, index) in field114Options" :key="index" :disabled="item.disabled" :label="item.value"
                           checked>{{item.label}}</el-checkbox>
            开启

          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="保质期" prop="field115">
            <el-input v-model="formData.field115" :style="{width: '100%'}" clearable placeholder="请输入">
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="11">
          <el-form-item label="预警天数" prop="field117">
            <el-input v-model="formData.field117" :style="{width: '100%'}" clearable placeholder="请输入预警天数">
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="19">
          <el-form-item label="备注" prop="field118">
            <el-input v-model="formData.field118" :autosize="{minRows: 4, maxRows: 4}" :style="{width: '100%'}"
                      placeholder="请输入" type="textarea"></el-input>
          </el-form-item>
        </el-col>
<!--        <el-col :span="24">-->
<!--          <el-form-item size="large">-->
<!--            <el-button type="primary" @click="submitForm">提交</el-button>-->
<!--            <el-button @click="resetForm">重置</el-button>-->
<!--          </el-form-item>-->
<!--        </el-col>-->

      </el-form>
      </div>
     </el-row>

    <el-row :gutter="20" style="background-color: white;border-radius: 5px;box-shadow: 2px 2px 5px rgba(0,0,0,0.2);
    margin-top: 20px;padding-bottom: 10px;margin-left: 40px;width: 1280px">
      <h3 style="margin-left: 20px">库存信息</h3>
      <div style="border-top: 1px solid #eeeeee;padding-top: 20px;padding-left: 30px">
    <el-table v-loading="loading" :data="goodList" style="margin-top: 50px" @selection-change="handleSelectionChange">
      <el-table-column fixed label="序号" prop="g_code" width="120" />
      <el-table-column :show-overflow-tooltip="true" fixed label="货品名称" prop="g_name" width="150" />
      <el-table-column align="center"  fixed label="状态" width="100">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            :active-value="0"
            :inactive-value="1"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column :show-overflow-tooltip="true" label="货品类型" prop="gt_name" width="150" />
      <el-table-column label="规格型号" prop="spec_code" width="100" />

    </el-table>
      </div>
    </el-row>


    <el-row :gutter="20" style="background-color: white;border-radius: 5px;box-shadow: 2px 2px 5px rgba(0,0,0,0.2);
    margin-top: 20px;padding-bottom: 10px;margin-left: 40px;width: 1280px">
      <h3 style="margin-left: 20px">上传图片</h3>
      <div style="border-top: 1px solid #eeeeee;padding-top: 20px;padding-left: 30px">
        <p style="color: gray">您可以上传png,jpg, gif等格式的文件，单个图片最大不能超过10MB，最多可上传10张。</p>
        <div id="u6249" class="ax_default box_1">
          <img id="u6249_img" class="img" src="images/常用组件/u1467.svg">
          <div id="u6249_text" class="text ">
            <p id="cache32" style="font-size: 28px;"><span id="cache33" style="">✚</span></p><p id="cache34" style="font-size: 14px;"><span id="cache35" style="">图片</span></p>
          </div>
        </div>

      </div>
    </el-row>



    <el-row :gutter="20" style="background-color: white;border-radius: 5px;box-shadow: 2px 2px 5px rgba(0,0,0,0.2);
    margin-top: 20px;padding-bottom: 10px;margin-left: 40px;width: 1280px">
      <h3 style="margin-left: 20px">上传附件</h3>
      <div style="border-top: 1px solid #eeeeee;padding-top: 20px;padding-left: 30px">
        <el-form ref="elForm" :model="formData" :rules="rules" label-width="100px" size="medium">
          <el-upload
            :before-remove="beforeRemove"
            :file-list="fileList"
            :limit="3"
            :on-exceed="handleExceed"
            :on-preview="handlePreview"
            :on-remove="handleRemove"
            action="https://jsonplaceholder.typicode.com/posts/"
            class="upload-demo"
            multiple>
            <el-button size="small" type="primary">点击上传</el-button>
            <span slot="tip" class="el-upload__tip" style="margin-left: 20px">
              您可以上传doc, pdf, rar等格式的文件，单个文件上传最大200MB。</span>
          </el-upload>
        </el-form>
      </div>
    </el-row>

  </div>
</template>
<script>
export default {
  components: {},
  props: [],
  data() {
    return {
      formData: {
        field101: undefined,
        field102: undefined,
        field104: undefined,
        field107: undefined,
        field108: undefined,
        field109: undefined,
        field110: undefined,
        field111: undefined,
        field112: undefined,
        field113: 0,
        field114: [""],
        field115: undefined,
        field117: undefined,
        field118: undefined,
      },
      rules: {
        field101: [{
          required: true,
          message: '请输入',
          trigger: 'blur'
        }],
        field102: [{
          required: true,
          message: '请输入',
          trigger: 'blur'
        }],
        field104: [{
          required: true,
          message: '请选择',
          trigger: 'change'
        }],
        field107: [],
        field108: [],
        field109: [],
        field110: [],
        field111: [],
        field112: [],
        field113: [{
          required: true,
          message: '状态不能为空',
          trigger: 'change'
        }],
        field114: [],
        field115: [],
        field117: [],
        field118: [],
      },
      field104Options: [{
        "label": "选项一",
        "value": 1
      }, {
        "label": "选项二",
        "value": 2
      }],
      field108Options: [{
        "label": "选项一",
        "value": 1
      }, {
        "label": "选项二",
        "value": 2
      }],
      field109Options: [{
        "label": "选项一",
        "value": 1
      }, {
        "label": "选项二",
        "value": 2
      }],
      field113Options: [{
        "label": "启用",
        "value": 0
      }, {
        "label": "停用",
        "value": 1
      }],
      field114Options: [{
        "label": "",
        "value": ""
      }],
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    submitForm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        // TODO 提交表单
      })
    },
    resetForm() {
      this.$refs['elForm'].resetFields()
    },
  }
}

</script>
<style>
</style>
