<template>
  <div class="container">
    <el-form ref="purchaseOrderInf" :model="purchaseOrderInf" :rules="rules"
             class="demo-purchaseOrderInf"
             label-width="100px">
      <div class="group">
        <el-row>
          <el-col :span="3">
            基础信息
          </el-col>
        </el-row>
        <el-divider></el-divider>
        <el-row>
          <el-col :span="12">
            <el-form-item label="进货单号" prop="poCode">
              <el-input v-model="purchaseOrderInf.poCode" disabled placeholder="自动获取系统编号" readonly
                        style="width: 432px;"></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="进货日期" prop="purchaseDate" required>
              <el-date-picker
                v-model="purchaseOrderInf.purchaseDate"
                placeholder="选择日期"
                type="datetime">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="供应商" prop="sid">
              <el-select v-model="purchaseOrderInf.sid"
                         placeholder="请选择供应商" @change="handlerSupplierChange">
                <el-option
                  v-for="item in supplierList"
                  :key="item.sId"
                  :label="item.sName"
                  :value="item.sId">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="联系人">
              <el-input v-model="supplier.contactPerson" disabled/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="联系方式">
          <el-input v-model="supplier.contactNumber" disabled/>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="进货部门" prop="purDeptId">
              <treeselect v-model="purchaseOrderInf.purDeptId" :options="deptOptions" :show-count="true"
                          placeholder="请选择归属部门" @select="getUserList"/>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="进货人" prop="purchaser">
              <el-select v-model="purchaseOrderInf.purchaser" filterable placeholder="请选择">
                <el-option
                  v-for="item in userList"
                  :key="item.userId"
                  :label="item.nickName"
                  :value="item.userId">
                </el-option>
                <el-option
                  v-if="!userList || userList.length == 0"
                  :key="0"
                  :value="0"
                  disabled label="请选择部门">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="purchaseOrderInf.remark" :autosize="{ minRows: 2, maxRows: 6 }" :rows="6"
                    type="textarea"></el-input>
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
          <el-button type="primary" @click="()=>{dialogTableVisible = true;getGoodsList()}">添加</el-button>
          <el-button @click="removeItems(gids)">移除</el-button>
        </el-form-item>
        <el-table :cell-style="{ padding:'0px' }"
                  :data="purchaseOrderInf.selectGoods"
                  :row-style="{ height:'70px' }"
                  @selection-change="handleSelectionGoodsChange">
          <el-table-column align="center" fixed type="selection" width="50"/>
          <el-table-column align="center" fixed label="序号" type="index" width="60"/>
          <el-table-column align="center" fixed label="货品名称" prop="g_name" width="80"/>
          <el-table-column align="center" fixed label="货品编号" prop="g_code" width="110"/>
          <el-table-column align="center" label="规格型号" prop="spec_code" width="100"/>
          <el-table-column align="center" label="单位" prop="unit" width="100">
            <template slot-scope="scope">
              <el-form-item
                :prop="`selectGoods.${scope.$index}.unit`"
                :rules="rules.unit"
                style="margin: 0;"
              >
                <el-select
                  v-model="scope.row.unit"
                  placeholder="请选择"
                  style="width: 90px"
                >
                  <el-option
                    v-for="dict in dict.type.g_unit_goods"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                  />
                </el-select>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column align="center" label="货品类型" prop="gt_name" width="100"/>
          <el-table-column :formatter="(row)=>(row.item_quantity || '无货')" align="center" label="当前库存"
                           prop="item_quantity" width="100"/>
          <el-table-column align="center" label="进货数量" prop="purchaseQuantity" width="120">
            <template slot-scope="scope">
              <el-form-item
                :prop="`selectGoods.${scope.$index}.purchaseQuantity`"
                :rules="rules.purchaseQuantity"
              >
                <el-input v-model="scope.row.purchaseQuantity" placeholder="进货数量"
                ></el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column :rules="rules.wr_price" align="center" label="进货单价" prop="wr_price" width="100">
            <template slot-scope="scope">
              <el-form-item
                :prop="`selectGoods.${scope.$index}.wr_price`"
                :rules="rules.wr_price"
              >
                <el-input v-model="scope.row.wr_price" placeholder="进货单价"></el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column :formatter="row=>row.wr_price * (row.purchaseQuantity || 0)" align="center" label="金额"
                           prop="lumpSum" width="120"/>
          <el-table-column align="center" label="备注" prop="remark">
            <template slot-scope="scope">
              <el-form-item
                :prop="`selectGoods.${scope.$index}.remark`"
              >
                <el-input v-model="scope.row.remark" placeholder="请输入备注"></el-input>
              </el-form-item>
            </template>
          </el-table-column>
          <el-table-column align="center" class-name="small-padding fixed-width" fixed="right" label="操作" width="120">
            <template slot-scope="scope">
              <el-button
                icon="el-icon-delete"
                size="mini"
                type="text"
                @click="removeItems([scope.row.g_id])"
              >移除
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
        <el-divider></el-divider>
        <el-upload
          ref="upload"
          :action="baseApi + '/file/upload'"
          :auto-upload="false"
          :file-list="purchaseOrderInf.selectAnnex"
          :headers="token"
          :multiple="true"
          :on-error="handlerUploadError"
          :on-remove="handleRemove"
          :on-success="uploadComplete"
          :show-file-list="true"
          accept="*.doc,*.pdf,*.rar,*.zip"
          class="upload-demo">
          <el-button slot="trigger" size="small" type="primary">选取文件</el-button>
          <el-button size="small" style="margin-left: 10px;" type="success" @click="submitUpload">上传到服务器
          </el-button>
          <div slot="tip" class="el-upload__tip">您可以上传 doc, pdf, rar 等格式的文件，单个文件上传最大 200MB。</div>
        </el-upload>
      </div>
      <div style="display: flex;justify-content: flex-end;">
        <el-button type="primary" @click="submitForm('purchaseOrderInf')">提交</el-button>
        <el-button @click="cancel()">取消</el-button>
      </div>
    </el-form>
    <el-dialog :visible.sync="dialogTableVisible" title="添加货品" width="750px">
      <el-form ref="queryGoodsForm" :inline="true" :model="queryGoodsParams" size="small">
        <el-form-item label-width="86px" prop="unit">
          <el-select
            v-model="queryGoodsParams.unit"
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
            v-model.trim="queryGoodsParams.codeOrName"
            clearable
            placeholder="请输入货品编号/名称"
            style="width: 200px"
            @keyup.enter.native="handleGoodsQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button icon="el-icon-search" size="mini" type="primary" @click="getGoodsList()">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetForm('queryGoodsForm')">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table ref="goodsTable" v-loading="loading" :data="goodsList"
                row-key="g_id" @selection-change="handlerSelectionChange">
        <el-table-column :reserve-selection="true" align="center" fixed type="selection" width="50"/>
        <el-table-column align="center" fixed label="序号" type="index" width="60"/>
        <el-table-column align="center" fixed label="货品编号" prop="g_code" width="80"/>
        <el-table-column align="center" fixed label="货品名称" prop="g_name" width="110"/>
        <el-table-column align="center" label="规格型号" prop="spec_code" width="100"/>
        <el-table-column align="center" label="单位" width="60">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.g_unit_goods" :value="scope.row.unit"/>
          </template>
        </el-table-column>
        <el-table-column align="center" label="入库单价（元）" prop="wr_price" width="100"/>
        <el-table-column :formatter="(row)=>(row.item_quantity || '无货')" align="center" label="当前库存"
                         prop="item_quantity" width="100"/>
      </el-table>
      <pagination
        v-show="goodsTotal>0"
        :limit.sync="queryGoodsParams.pageSize"
        :page.sync="queryGoodsParams.pageNum"
        :total="goodsTotal"
        @pagination="getGoodsList"
      />
      <el-button type="primary" @click="handleGoodsDefine">确定</el-button>
      <el-button @click="closeGoodsSelect">取消</el-button>
    </el-dialog>
  </div>
</template>

<script>
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import Treeselect from "@riophae/vue-treeselect";
import {deptTreeSelect, listUser} from "@/api/system/user";
import {listGood} from "@/api/wms/good/goodsinfo";
import {getToken} from "@/utils/auth";
import {listSupplier} from "@/api/wms/contactUnits/supplier";

export default {
  name: "PurchaseFrom",
  dicts: ['g_unit_goods'],
  components: {
    Treeselect
  },
  data() {
    return {
      // 选择的 goods id
      gids: [],
      baseApi: process.env.VUE_APP_BASE_API,
      rules: {
        supplier: [
          {required: true, message: '请选择供应商', trigger: 'change'}
        ],
        purDeptId: [
          {required: true, message: '请选择进货部门', trigger: 'change'}
        ],
        purchaser: [
          {required: true, message: '请选择进货人', trigger: 'change'}
        ],
        purchaseDate: [
          {required: true, message: '请选择进货日期', trigger: 'change'}
        ],
        type: [
          {type: 'array', required: true, message: '请至少选择一个活动性质', trigger: 'change'}
        ],
        resource: [
          {required: true, message: '请选择活动资源', trigger: 'change'}
        ],
        unit: [
          {required: true, message: '请选择货品单位', trigger: 'blur'}
        ],
        purchaseQuantity: [
          {required: true, message: '请选输入进货数量', trigger: 'blur'},
          {pattern: /^[1-9]\d*$/, message: '请输入正确的数量', trigger: 'blur'}
        ],
        wr_price: [
          {required: true, message: '请选输入进货单价', trigger: 'blur'},
          {pattern: /^\d+(\.\d{1,2})?$/, message: '请输入正确的价格', trigger: 'blur'}
        ]
      },
      // 部门树选项
      deptOptions: undefined,
      loading: false,
      fileList: [],
      dialogTableVisible: false,
      goodsList: [],
      queryGoodsParams: {
        pageNum: 1,
        pageSize: 5,
        codeOrName: undefined
      },
      purchaseOrderInf: {
        selectGoods: [],
        selectAnnex: []
      },
      purchaseDetails: [],
      goodsTotal: 0,
      tempSelectGoodsList: [],
      userList: [],
      supplierList: [],
      supplier: {}
    }
  },
  props: {
    purchaseInf: {
      type: Object,
      default: undefined
    }
  },
  async created() {
    this.getDeptTree();
    this.supplierList = (await listSupplier()).rows;
    if (/\/order\/purchase\/edit\/(\d+)$/.test(this.$route.path)) {
      console.log("进了编辑状态")
      this.initProps();
      console.log("this.purchaseInf", this.purchaseInf)
    }
    console.log("purchaseInf: ", this.purchaseInf)
  },
  computed: {
    token() {
      return {
        Authorization: `Bearer ${getToken()}`
      }
    }
  },
  methods: {
    /** 查询部门下拉树结构 */
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data;
      });
    },
    initProps() {
      this.purchaseOrderInf = this.purchaseInf;
      this.supplier = this.supplierList.filter(e => e.sId === this.purchaseOrderInf.sid)?.[0];
      // let selectAnnex = this.purchaseInf?.annexes?.map(e => {
      //   let url = e.content;
      //   const filename = url.substring(url.lastIndexOf('/') + 1); // 获取URL中的文件名部分
      //   const parts = filename.split('_'); // 使用下划线将文件名分割成部分
      //   const result = parts[0] + '.' + parts[1].split('.')[1]; // 按照您的要求拼接文件名和后缀名
      //   return {
      //     name: result,
      //     url
      //   }
      // })
      // let selectGoods = this.purchaseInf?.purchaseDetailsList?.map(e => ({
      //   g_name: e.goods.g_name,
      //   g_code: e.goods.g_code,
      //   spec_code: e.specCode,
      //   unit: e.unit,
      //   gt_name: e.goods.gt_name,
      //   purchaseQuantity: e.purchaseQuantity,
      //   wr_price: e.puPrice,
      //   remark: e.goods.remark,
      // }));
      // this.purchaseOrderInf = {
      //   poCode: this.purchaseInf.poCode,
      //   purchaseDate: this.purchaseInf.purchaseDate,
      //   sid: this.purchaseInf.sId,
      //   purDeptId: this.purchaseInf.purchasingDept,
      //   purchaser: this.purchaseInf.purchaserId,
      //   remark: this.purchaseInf.remark,
      //   selectAnnex, selectGoods
      // }
      // this.supplierList.forEach(e => (e.sId === this.purchaseInf.sId) ? this.purchaseOrderInf.supplier = e : this.purchaseOrderInf.supplier = {});
      // console.log("this.purchaseOrderInf", this.purchaseOrderInf)
    },
    async getUserList() {
      if (this.purchaseOrderInf.purDeptId) {
        this.userList = (await listUser({deptId: this.purchaseOrderInf?.purDeptId})).rows
        this.purchaseOrderInf.purchaser = undefined;
      }
      console.log("userList: ", this.userList)
    },
    handlerSupplierChange(row) {
      // this.purchaseOrderInf.sid = row.sId;
      this.supplier = this.supplierList.filter(e => e.sId === row)?.[0];
      // console.log("supplier: ", this.supplier)
    },
    closeGoodsSelect() {
      this.dialogTableVisible = false;
      this.tempSelectGoodsList = this.purchaseOrderInf.selectGoods;
    },
    handleGoodsDefine() {
      console.log("handleGoodsDefine")
      this.purchaseOrderInf.selectGoods = this.tempSelectGoodsList;
      // this.tempSelectGoodsList = [];
      this.closeGoodsSelect();
    },
    handlerSelectionChange(row) {
      // console.log("row: ", row)
      this.tempSelectGoodsList = row;
      console.log("selectGoods: ", this.purchaseOrderInf.selectGoods)
      console.log("tempSelectGoodsList：", this.tempSelectGoodsList)
    },
    getGoodsList() {
      this.loading = true;
      listGood({
        g_name: this.queryGoodsParams.codeOrName,
        unit: this.queryGoodsParams.unit,
        pageNum: this.queryGoodsParams.pageNum,
        pageSize: this.queryGoodsParams.pageSize,
      }).then(response => {
          this.goodsList = response.rows;
          this.goodsTotal = response.total;
          this.$nextTick(() => {
            console.log("nextTick selectGoods: ", this.purchaseOrderInf.selectGoods)
            console.log("nextTick tempSelectGoodsList：", this.tempSelectGoodsList)
            this.purchaseOrderInf.selectGoods.map(vl => {
              this.goodsList.map(item => {
                if (item.g_id === vl.g_id) {
                  this.$refs.goodsTable.toggleRowSelection(item, true)
                }
              })
            })
          });
          this.loading = false;
        }
      );
    },
    cancel() {
      this.$confirm('检测到未保存的内容，是否关闭？', '确认信息', {
        distinguishCancelAndClose: true,
        confirmButtonText: '确认关闭',
        cancelButtonText: '取消'
      })
        .then(() => {
          this.$tab.closeOpenPage({path: '/order/purchase'});
        })
        .catch(action => {
          // 未关闭
        });
    },
    submitUpload() {
      this.$refs.upload.submit();
    },
    uploadComplete(res, file, fileList) {
      console.log("fileList: ", fileList)
      let {code, data} = res;
      if (code === 200) {
        // this.purchaseOrderInf.selectAnnex = fileList;
        this.purchaseOrderInf.selectAnnex.push({name: file.name, url: data.url})
        this.$message.success(`${file.name}附件上传完成！`);
      }else{
        this.$message.error(`[${file.name}]附件上传失败！`);
      }
    },
    handleRemove(file) {
      this.purchaseOrderInf.selectAnnex = this.purchaseOrderInf.selectAnnex.filter(e => e.name !== file.name)
    },
    handlerUploadError(err, file) {
      this.$message.error(`[${file.name}]附件上传失败！`);
    },
    async submitForm() {
      this.$refs.purchaseOrderInf.validate(async (valid) => {
        if (valid) {
          if (this.purchaseOrderInf.selectGoods.length === 0) {
            this.$message.error('请选择货品');
            return false;
          }
          if (this.$refs.upload.uploadFiles.filter(e => e.status === 'ready').length > 0) {
            this.$message.error('请选择上传未上传的附件');
            return false;
          }
          let annexes = this.purchaseOrderInf.selectAnnex?.map(e => ({content: e.url}));
          let purchaseDetailsList = this.purchaseOrderInf.selectGoods?.map(e => ({
            goodsId: e.g_id,
            specCode: e.spec_code,
            unit: e.unit,
            purchaseQuantity: e.purchaseQuantity,
            puPrice: e.wr_price,
            remark: e.remark
          }))
          let purchaseOrderInf = {
            purchaseDate: this.purchaseOrderInf.purchaseDate,
            "sId": this.purchaseOrderInf.sid,
            purchaserId: this.purchaseOrderInf.purchaser,
            purchasingDept: this.purchaseOrderInf.purDeptId,
            purchaseDetailsList,
            annexes,
            remark: this.purchaseOrderInf.remark
          }
          this.$emit("submitForm", purchaseOrderInf);
        } else {
          this.$message.warning("请将表单填写完整");
          return false;
        }
      });
    },
    handleSelectionGoodsChange(selection) {
      this.gids = selection.map(item => item.g_id);
    },
    removeItems(gids) {
      this.purchaseOrderInf.selectGoods = this.purchaseOrderInf.selectGoods.filter(e => !gids.includes(e.g_id))
    }
  }
}
</script>

<style scoped>
.container {
  margin: 30px 160px;
  background: #f3f6f8;
}

.container .group {
  background: #FFFFFF;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
  padding: 20px;
  margin-bottom: 20px;
}

.el-table >>> .el-form-item,
.el-table >>> .el-form-item__content {
  margin-bottom: 0 !important;
}

.el-table >>> .el-form-item {
  margin-left: -100px !important;
}
</style>
