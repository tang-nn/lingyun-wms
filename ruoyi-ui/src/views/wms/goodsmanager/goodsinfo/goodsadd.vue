<template>
  <div class="app-container" style="background-color: #F3F6F8">

    <el-row :gutter="20" style="background-color: white;border-radius: 5px;box-shadow: 2px 2px 5px rgba(0,0,0,0.2);
   padding-bottom: 10px;margin-left: 200px;width: 1280px">
      <h3 style="margin-left: 20px">基础信息</h3>
      <div style="border-top: 1px solid #eeeeee;padding-top: 30px;padding-left: 50px">
        <el-form ref="elForm" :model="formData" :rules="rules" label-width="95px" size="medium">
          <el-row>
            <el-col :span="19">
              <el-form-item label="货品名称" prop="gName">
                <el-input v-model="formData.gName" :style="{width: '100%'}" clearable placeholder="请输入">
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="货品编号" prop="gCode">
                <el-input disabled :style="{width: '100%'}" clearable placeholder="请输入">
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11" style="margin-left: 10px">
              <el-form-item label="货品类型" prop="gtId">
                <treeselect v-model="formData.gtId"
                            :defaultExpandLevel="Infinity"
                            :options="goodsTypes"
                            :show-count="false"
                            placeholder="请选择"
                            style="width: 240px"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="规格型号" prop="specCode">
                <el-input v-model="formData.specCode" :style="{width: '100%'}" clearable placeholder="请输入">
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item label="供应商" prop="sId">
                <template slot-scope="scope">
                  <el-select v-model="formData.sId" placeholder="请选择">
                    <el-option v-for="s in supplier"
                               :key="s.sId" :label="s.sName" :value="s.sId"/>
                  </el-select>
                </template>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="入库参考价" prop="wrPrice">
                <el-input v-model="formData.wrPrice" :style="{width: '100%'}" clearable placeholder="请输入">
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8" style="margin-left: 26px">
              <el-form-item label="出库参考价" prop="orPrice">
                <el-input v-model="formData.orPrice" :style="{width: '100%'}" clearable placeholder="请输入">
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="11">
              <el-form-item label="显示顺序" prop="sort">
                <el-input v-model="formData.sort" :style="{width: '100%'}" clearable placeholder="请输入">
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="11">
              <el-form-item label="状态">
                <el-radio-group v-model="formData.status">
                  <el-radio
                    v-for="dict in dict.type.sys_normal_disable"
                    :key="dict.value"
                    :label="dict.value"
                  >{{ dict.label }}
                  </el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>


            <el-col :span="11">
              <el-form-item label="保质期管理" prop="hasShelfLife">
                <el-checkbox :checked="formData.hasShelfLife" @change="toggleRow"></el-checkbox>
                开启
              </el-form-item>
            </el-col>
          </el-row>

          <el-row v-if="showRow">
            <el-col :span="8">
              <el-form-item label="保质期" prop="shelfLife">
                <el-input v-model="formData.shelfLife" :style="{width: '100%'}" clearable placeholder="请输入">
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="11">
              <el-form-item label="预警天数" prop="wDays">
                <el-input v-model="formData.wDays" :style="{width: '100%'}" clearable placeholder="请输入预警天数">
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-col :span="19">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="formData.remark" :autosize="{minRows: 4, maxRows: 4}" :style="{width: '100%'}"
                        placeholder="请输入" type="textarea"></el-input>
            </el-form-item>
          </el-col>

        </el-form>
      </div>
    </el-row>

    <!--    <el-row :gutter="20" style="background-color: white;border-radius: 5px;box-shadow: 2px 2px 5px rgba(0,0,0,0.2);-->
    <!--    margin-top: 20px;padding-bottom: 10px;margin-left: 200px;width: 1280px">-->
    <!--      <h3 style="margin-left: 20px">库存信息</h3>-->
    <!--      <div style="border-top: 1px solid #eeeeee;padding-top: 20px;padding-left: 30px">-->
    <!--    <el-table v-loading="loading" :data="goodList" style="margin-top: 50px" @selection-change="handleSelectionChange">-->
    <!--      <el-table-column fixed label="序号" prop="g_code" width="120" />-->
    <!--      <el-table-column :show-overflow-tooltip="true" fixed label="仓库名称" prop="g_name" width="150" />-->
    <!--      <el-table-column label="库存上限" align="center" prop="quantity">-->
    <!--        <template slot-scope="scope">-->
    <!--          <el-input />-->
    <!--        </template>-->
    <!--      </el-table-column>-->
    <!--      <el-table-column label="库存下线" align="center" prop="quantity">-->
    <!--        <template slot-scope="scope">-->
    <!--          <el-input />-->
    <!--        </template>-->
    <!--      </el-table-column>-->

    <!--    </el-table>-->
    <!--      </div>-->
    <!--    </el-row>-->


    <el-row :gutter="20" style="background-color: white;border-radius: 5px;box-shadow: 2px 2px 5px rgba(0,0,0,0.2);
    margin-top: 20px;padding-bottom: 10px;margin-left: 200px;width: 1280px">
      <h3 style="margin-left: 20px">上传图片</h3>
      <div style="border-top: 1px solid #eeeeee;padding-top: 20px;padding-left: 30px">
        <p style="color: gray">您可以上传png,jpg, gif等格式的文件，单个图片最大不能超过2MB。</p>
        <el-upload
          class="upload-demo"
          :on-preview="handlePictureCardPreview"
          :action="baseApi + '/file/upload'"
          :headers="token"
          ref="uploadImage"
          :file-list="formData.goodsImages"
          :on-error="handlerUploadAnnexError"
          :on-success="uploadImageComplete"
          :on-change="goodsImageChange"
          list-type="picture-card"
          :accept="'image/png, image/jpeg, image/gif'"
          :before-upload="beforeImageUpload"
          :auto-upload="false">
          <i slot="default" class="el-icon-plus"></i>
          <div slot="file" slot-scope="{file}">
            <img
              class="el-upload-list__item-thumbnail"
              :src="file.url" alt=""
            >
            <span class="el-upload-list__item-actions">
        <span
          class="el-upload-list__item-preview"
          @click="handlePictureCardPreview(file)"
        >
          <i class="el-icon-zoom-in"></i>
        </span>
        <span
          v-if="!disabled"
          class="el-upload-list__item-delete"
          @click="handleImageRemove(file)"
        >
          <i class="el-icon-delete"></i>
        </span>
      </span>
          </div>
        </el-upload>
        <el-dialog :visible.sync="dialogVisible">
          <img width="100%" :src="dialogImageUrl" alt="">
        </el-dialog>
        <el-button size="small" style="margin-left: 47px;" type="success" @click="submitImageUpload">
         上传到服务器
        </el-button>
      </div>
    </el-row>


    <el-row :gutter="20" style="background-color: white;border-radius: 5px;box-shadow: 2px 2px 5px rgba(0,0,0,0.2);
    margin-top: 20px;padding-bottom: 135px;margin-left:200px;width: 1280px">
      <h3 style="margin-left: 20px">上传附件</h3>
      <el-divider></el-divider>
      <el-upload
        ref="upload"
        :action="baseApi + '/file/upload'"
        :auto-upload="false"
        :file-list="formData.goodsAnnex"
        :headers="token"
        :multiple="true"
        :on-error="handlerUploadAnnexError"
        :on-remove="handleUpdataAnnexRemove"
        :on-success="uploadAnnexComplete"
        :on-change="goodsAnnexChange"
        :show-file-list="true"
        :accept="'.doc,.pdf,.rar,.zip'"
        :before-upload="beforeAnnexUpload"
        class="upload-demo">
        <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
        <el-button size="small" type="success" @click="submitAnnexUpload">上传到服务器
        </el-button>
        <div slot="tip" class="el-upload__tip">
          <p style="color: gray;font-size: 16px">您可以上传 doc, pdf, rar,zip 等格式的文件，单个文件上传最大 10MB。</p>

        </div>
      </el-upload>
    </el-row>


    <div class="footer-container" style=" position: fixed;
          box-shadow: 2px 2px 5px rgba(0,0,0,0.9);  background-color:white;border-radius: 2px;
          width: 89.5%; height: 60px; bottom: 0; right: 0;  right: 0; z-index: 9999;">
      <el-footer>
        <!-- 底部导航栏内容 -->
        <el-button v-hasPermi="['gd:good:add']"
                   plain
                   size="medium" style="float:right;margin-right: 5px;margin-top: 12px"
                   type="success" @click="handleAdd">保存
        </el-button>

        <el-button v-hasPermi="['gd:good:add']"
                   plain size="medium" style="float:right;margin-right: 5px;margin-top: 12px" @click="cancle">关闭
        </el-button>
      </el-footer>
    </div>


  </div>
</template>
<script>

import {TypesList, SupplierList, addGoods} from "@/api/wms/good/goodsinfo";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {getToken} from "@/utils/auth";

export default {
  components: {Treeselect},
  dicts: ['sys_normal_disable'],
  props: [],
  data() {
    return {
      //上传图片
      baseApi: process.env.VUE_APP_BASE_API,
      dialogImageUrl: '',
      dialogVisible: false,
      disabled: false,
      //货品类型树选项
      goodsTypes: undefined,
      // 保质期管理默认显示下方的行
      showRow: true,
      defaultProps: {
        children: "children",
        label: "label"
      },
      formData: {
        hasShelfLife: true,
        status: "0",
        goodsAnnex: [],
        goodsImages: []
      },
      //供应商
      supplier: [],
      rules: {
        gName: [
          {required: true, message: "货品名称不能为空", trigger: "blur"}
        ],
        gtId: [
          {required: true, message: "货品类型不能为空", trigger: "blur"}
        ],
        specCode: [
          {required: true, message: "规格型号不能为空", trigger: "blur"}
        ],
        wrPprice: [
          {required: true, message: "入库参考价不能为空", trigger: "blur"},
          {
            validator: (rule, value, callback) => {
              const pattern = /^[0-9]+(\.[0-9]{1,2})?$/; // 正则表达式，限制小数位数为两位
              if (!value || pattern.test(value)) {
                callback();
              } else {
                callback(new Error('入库参考价格式不正确'));
              }
            },
            trigger: 'blur',
          },
        ],
        orPrice: [
          {required: true, message: "出库参考价不能为空", trigger: "blur"},
          {
            validator: (rule, value, callback) => {
              const pattern = /^[0-9]+(\.[0-9]{1,2})?$/; // 正则表达式，限制小数位数为两位
              if (!value || pattern.test(value)) {
                callback();
              } else {
                callback(new Error('出库参考价格式不正确'));
              }
            },
            trigger: 'blur',
          },
        ],
        sort: [
          {required: true, message: "显示顺序不能为空", trigger: "blur"},
          {
            validator: (rule, value, callback) => {
              const pattern = /^[-]?\d+$/;
              if (!value || pattern.test(value)) {
                callback();
              } else {
                callback(new Error('显示顺序格式不正确'));
              }
            },
            trigger: 'blur',
          },
        ],
        shelfLife: [
          {required: true, message: "保质期不能为空", trigger: "blur"},
          {
            validator: (rule, value, callback) => {
              const pattern = /^[-]?\d+$/;
              if (!value || pattern.test(value)) {
                callback();
              } else {
                callback(new Error('保质期格式不正确'));
              }
            },
            trigger: 'blur',
          },
        ],
        wDays: [
          {required: true, message: "预警天数不能为空", trigger: "blur"},
          {
            validator: (rule, value, callback) => {
              const pattern = /^[-]?\d+$/;
              if (!value || pattern.test(value)) {
                callback();
              } else {
                callback(new Error('预警天数格式不正确'));
              }
            },
            trigger: 'blur',
          },
        ],

      },
      field114Options: [{
        "label": "",
        "value": ""
      }],
    }
  },
  computed: {
    token() {
      return {
        Authorization: `Bearer ${getToken()}`
      }
    }
  },
  watch: {},
  created() {
    this.goodsTypesList();
    this.getSupplierList();
  },
  mounted() {
  },
  methods: {

    /* 查看所有商品分类 */
    goodsTypesList() {
      TypesList().then(response => {
        this.goodsTypes = response.data;
        console.info(this.goodsTypes);
      });
    },

    //供应商
    getSupplierList() {
      SupplierList().then(response => {
        this.supplier = response.data;
        console.info(this.supplier);
      });
    },
    toggleRow() {
      this.showRow = !this.showRow; // 切换保质期管理显示下方的行
      this.formData.hasShelfLife = !this.formData.hasShelfLife; // 切换复选框的勾选状态
      // if (this.hasShelfLife) {
      //   // 当复选框被选中时，将hasShelfLife设置为1
      //   this.$set(this.formData, 'hasShelfLife', 1);
      // } else {
      //   // 当复选框不被选中时，将hasShelfLife设置为0
      //   this.$set(this.formData, 'hasShelfLife', 0);
      // }

    },
    //关闭窗口
    cancle() {
      this.$router.push(`/goods`);
    },

    //保存
    handleAdd() {
      console.log("formData: ", this.formData)
      this.$refs['elForm'].validate(valid => {
        if (!valid){
          this.$modal.msgError("请填写完整的货品信息数据！");
          return
        }
        // 判断当前勾选状态并转换为0或1
        const hasShelfLifeValue = this.formData.hasShelfLife ? 1 : 0;
        console.log(hasShelfLifeValue);
        this.formData.hasShelfLife = hasShelfLifeValue;
        console.log("goodsadd", this.formData);
        addGoods(this.formData).then(response => {
          this.$modal.msgSuccess("新增成功");
          this.$router.push(`/goods`);
        })
      })
    },
    resetForm() {
      this.$refs['elForm'].resetFields();
    },


    //图片上传
    handleImageRemove(file) {
      let l = this.formData.goodsImages?.length;
      if (l) {
        this.formData.goodsImages = this.formData.goodsImages.filter(e => e.name !== file.name)
      } else {
        this.$refs.uploadImage.uploadFiles = this.$refs.uploadImage.uploadFiles.filter(e => e.name !== file.name)
      }
      // console.log(" this.$refs.uploadImage.uploadFiles: ",  this.$refs.uploadImage.uploadFiles)
      // console.log("this.formData.goodsImages: ",this.formData.goodsImages)
      // let exist  = this.formData.goodsImages.find(e => e.name === file.name)
    },
    submitImageUpload() {
      this.$refs.uploadImage.submit();
    },
    uploadImageComplete(res, file, fileList){
      console.log("uploadImageComplete: ", res, file, fileList)
      let {code, data} = res;
      if (code === 200) {
        // this.purchaseOrderInf.selectAnnex = fileList;
        this.formData.goodsImages.push({name: file.name, url: data.url, content: data.url })
        this.$message.success(`${file.name}附件上传完成！`);
        console.log("uploadImageComplete: ", this.formData.goodsImages)
      } else {
        this.$message.error(`[${file.name}]附件上传失败！`);
      }
    },
    beforeImageUpload(file) {
      const isLt2M = file.size / 1024 / 1024 < 2;
      if (!isLt2M) {
        this.$message.error('单个图片最大不能超过2MB');
      }
      return isLt2M;
    },
    goodsImageChange(file, fileList) {
      // console.log("goodsImageChange: ", file, fileList)
    },

    // 附件
    handleUpdataAnnexRemove(file) {
      let l = this.formData.goodsAnnex?.length;
      if (l) {
        this.formData.goodsAnnex = this.formData.goodsAnnex.filter(e => e.name !== file.name)
      } else {
        this.$refs.upload.uploadFiles = this.$refs.upload.uploadFiles.filter(e => e.name !== file.name)
      }
    },
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    handlerUploadAnnexError(err, file) {
      console.log("handlerUploadAnnexError err: ", err)
      this.$message.error(`[${file.name}] 文件上传失败！`);
    },
    beforeAnnexUpload(file) {
      const maxSize = 10 * 1024 * 1024; // 10MB
      const isLt10M = file.size / 1024 / 1024 < 10;
      if (!isLt10M) {
        this.$message.error('单个文件最大不能超过10MB');
      }
      return isLt10M;
    },
    uploadAnnexComplete(res, file) {
      let {code, data} = res;
      if (code === 200) {
        // this.purchaseOrderInf.selectAnnex = fileList;
        this.formData.goodsAnnex.push({name: file.name, url: data.url, content: data.url})
        this.$message.success(`${file.name}附件上传完成！`);
      } else {
        this.$message.error(`[${file.name}]附件上传失败！`);
      }
    },
    goodsAnnexChange(file, fileList) {
      console.log("goodsAnnexChange: ", file, fileList)
    },
    submitAnnexUpload() {
      this.$refs.upload.submit();
    },
  }
}

</script>
<style scoped>
::v-deep {
  .upload-demo {
    margin-left: 30px;
  }
  .el-button.el-button--success.el-button--small{
   margin-top: 25px;margin-left: 20px;
  }
}
</style>
