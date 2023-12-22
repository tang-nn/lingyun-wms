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
              <el-input  disabled :style="{width: '100%'}" clearable placeholder="请输入">
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
              <el-select  v-model="formData.sId" placeholder="请选择">
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
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="保质期管理" prop="hasShelfLife">
              <el-checkbox v-model="formData.hasShelfLife" @change="toggleRow"></el-checkbox> 开启
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
    margin-top: 20px;padding-bottom: 135px;margin-left:200px;width: 1280px">
      <h3 style="margin-left: 20px">上传附件</h3>
      <div style="border-top: 1px solid #eeeeee;padding-top: 20px;padding-left: 30px">
        <el-form ref="elForm" :model="formData" :rules="rules" label-width="100px" size="medium">
<!--          <el-upload-->
<!--            :before-remove="beforeRemove"-->
<!--            :file-list="fileList"-->
<!--            :limit="3"-->
<!--            :on-exceed="handleExceed"-->
<!--            :on-preview="handlePreview"-->
<!--            :on-remove="handleRemove"-->
<!--            action="https://jsonplaceholder.typicode.com/posts/"-->
<!--            class="upload-demo"-->
<!--            multiple>-->
<!--            <el-button size="small" type="primary">点击上传</el-button>-->
<!--            <span slot="tip" class="el-upload__tip" style="margin-left: 20px">-->
<!--              您可以上传doc, pdf, rar等格式的文件，单个文件上传最大200MB。</span>-->
<!--          </el-upload>-->
        </el-form>
      </div>
    </el-row>


      <div class="footer-container" style=" position: fixed;
          box-shadow: 2px 2px 5px rgba(0,0,0,0.9);  background-color:white;border-radius: 2px;
          width: 89.5%; height: 60px; bottom: 0; right: 0;  right: 0; z-index: 9999;">
        <el-footer>
          <!-- 底部导航栏内容 -->
          <el-button  v-hasPermi="['gd:good:add']"
            plain
            size="medium" style="float:right;margin-right: 5px;margin-top: 12px"
            type="success" @click="handleAdd" >保存</el-button>

          <el-button v-hasPermi="['gd:good:add']"
            plain size="medium" style="float:right;margin-right: 5px;margin-top: 12px" @click="cancle" >关闭</el-button>
        </el-footer>
      </div>



  </div>
</template>
<script>

import { TypesList,SupplierList,addGoods} from "@/api/wms/good/goodsinfo";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  components: {Treeselect},
  dicts: ['sys_normal_disable'],
  props: [],
  data() {
    return {
      //货品类型树选项
      goodsTypes: undefined,
      // 保质期管理默认显示下方的行
      showRow: true,
      hasShelfLife: true, // 默认选中
      defaultProps: {
        children: "children",
        label: "label"
      },
      formData: {
      },
      //供应商
      supplier:[],
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
              const pattern =/^[0-9]+(\.[0-9]{1,2})?$/; // 正则表达式，限制小数位数为两位
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
              const pattern =/^[0-9]+(\.[0-9]{1,2})?$/; // 正则表达式，限制小数位数为两位
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
              const pattern =/^[-]?\d+$/;
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
              const pattern =/^[-]?\d+$/;
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
              const pattern =/^[-]?\d+$/;
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
  computed: {},
  watch: {},
  created() {
    this.goodsTypesList();
    this.getSupplierList();
  },
  mounted() {},
  methods: {

    /* 查看所有商品分类 */
    goodsTypesList(){
      TypesList().then(response => {
        this.goodsTypes = response.data;
        console.info(this.goodsTypes);
      });
    },


    //供应商
    getSupplierList(){
      SupplierList().then(response => {
        this.supplier = response.data;
        console.info(this.supplier);
      });
    },
    toggleRow(){
      this.showRow = !this.showRow; // 切换保质期管理显示下方的行
        if (this.hasShelfLife) {
          // 当复选框被选中时，将hasShelfLife设置为1
          this.$set(this.formData, 'hasShelfLife', 1);
        } else {
          // 当复选框不被选中时，将hasShelfLife设置为0
          this.$set(this.formData, 'hasShelfLife', 0);
        }
    },


    //关闭窗口
    cancle(){
      this.$router.push(`/goods`);
    },


    //保存
    handleAdd() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
        console.log("goodsadd",this.formData)
        addGoods(this.formData).then(response => {
          this.$modal.msgSuccess("新增成功");
          // this.$router.push(`/transfer`);
        })
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
