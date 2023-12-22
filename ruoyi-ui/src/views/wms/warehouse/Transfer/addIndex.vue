<template>
  <div class="app-container" style="background-color: #F3F6F8">

    <el-row :gutter="20" style="background-color: white;border-radius: 5px;box-shadow: 2px 2px 5px rgba(0,0,0,0.2);
   padding-bottom: 10px;margin-left: 200px;width: 1280px">
      <h3 style="margin-left: 20px">基础信息</h3>
      <div style="border-top: 1px solid #eeeeee;padding-top: 30px;padding-left: 30px">
        <el-form ref="elForm" :rules="rules" :model="formData" label-width="95px" size="medium">
          <el-row>
            <el-col :span="9">
              <el-form-item label="调拨单号" prop="tdCode">
                <el-input v-model="formData.tdCode" :style="{width: '100%'}" clearable placeholder="自动获取系统编码"
                          disabled>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="9" style="margin-left: 180px">
              <el-form-item label="调拨类型">
                <el-select :style="{width: '100%'}"
                           v-model="formData.type"
                           placeholder="请选择"
                           clearable
                           style="width: 240px"
                >
                  <el-option
                    v-for="dict in dict.type.transfer_type"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                  />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="9">
              <el-form-item label="调拨申请日期" prop="date" label-width="100">
                <el-date-picker clearable
                                v-model="formData.date"
                                type="date"
                                value-format="yyyy-MM-dd mm:HH:ss"
                                placeholder="请选择盘点开始时间">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="9" style="margin-left: 180px">
              <el-form-item label="经办人" prop="usera">
                <el-input
                  v-model="formData.usera"
                  placeholder="请输入"
                  clearable
                  @focus="handleUsera"
                  suffix-icon="el-icon-more"
                />
              </el-form-item>
            </el-col>

          </el-row>
          <el-row>
            <el-col :span="9">
              <el-form-item label="调入仓库" prop="inWId">
                <el-select :style="{width: '100%'}"
                           v-model="formData.inWId"
                           placeholder="请选择"
                           clearable
                           style="width: 240px"
                           @change="handleSelectChangeIn"
                >
                  <el-option
                    v-for="wn in wareNameList"
                    :key="wn.w_id"
                    :label="wn.w_name"
                    :value="wn.w_id"
                  />
                </el-select>
              </el-form-item>
              <el-link type="primary" @click="gotoaddwarehouse" style="position: relative;left: 491px;top: -48px  ">
                设置
              </el-link>
            </el-col>
            <el-col :span="9" style="margin-left: 180px">
              <el-form-item label="调出仓库" prop="outWId">
                <el-select :style="{width: '100%'}"
                           v-model="formData.outWId"
                           placeholder="请选择"
                           clearable
                           style="width: 240px"
                           @change="handleSelectChangeOut"
                >
                  <el-option
                    v-for="wn in wareNameList"
                    :key="wn.w_id"
                    :label="wn.w_name"
                    :value="wn.w_id"
                  />
                </el-select>
              </el-form-item>
              <el-link type="primary" @click="gotoaddwarehouse" style="position: relative;left: 491px;top: -48px  ">
                设置
              </el-link>
            </el-col>

          </el-row>
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
      <h3 style="margin-left: 20px">调拨明细</h3>
      <div style="border-top: 1px solid #eeeeee;padding-top: 20px;padding-left: 30px">

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              v-hasPermi="['gd:good:add']"
              icon="el-icon-plus"
              plain
              size="mini"
              type="primary"
              @click="addGoodsPage"
            >添加
            </el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              v-hasPermi="['gd:good:delete']"
              :disabled="multiple"
              icon="el-icon-minus"
              plain
              size="mini"
              type="danger"
              @click="removeItems"
            >移除
            </el-button>
          </el-col>
        </el-row>

        <el-table :loading="loading" style="margin-top: 50px"
                  :data="inventorysheetInf.selectGoods"
                  max-height="520px"
                  row-key="g_id"
                  @selection-change="handleSelectionChange"
                  :row-class-name="rowStorageLocationIndex">
          <el-table-column type="selection" fixed="left" width="55" align="center"/>
          <el-table-column label="序号" fixed="left" align="center" prop="index" width="50"/>
          <el-table-column label="货品名称" fixed="left" prop="g_name" width="100"/>
          <el-table-column label="货品编号" fixed="left" align="center" prop="g_code"/>
          <el-table-column label="规格型号" align="center" prop="spec_code"/>
          <el-table-column label="单位" align="center">
            <template slot-scope="scope">
              <dict-tag :options="dict.type.g_unit_goods" :value="scope.row.unit"/>
            </template>
          </el-table-column>
          <el-table-column label="货品类型" align="center" width="100" prop="gt_name"/>
          <el-table-column label="调出仓库当前库存" width="130" align="center" prop="item_quantity"/>
          <el-table-column label="调出仓库当前库位" align="center" width="130px" prop="sl_name"/>
          <el-table-column label="调入仓库当前库存" align="center" prop="stockQuantity" width="130px"/>
          <el-table-column label="调入仓库当前库位" align="center" width="130px">
            <template slot-scope="scope">
              <el-select v-model="scope.row.inLocationId" placeholder="请选择">
                <el-option v-for="location in slNameList"
                           :key="location.slId" :label="location.slName" :value="location.slId"/>
              </el-select>
            </template>
          </el-table-column>

          <el-table-column label="调拨数量" align="center" prop="quantity">
            <template slot-scope="scope">
              <el-input v-model="scope.row.quantity" @blur="tquantityNums(scope.row)"></el-input>
            </template>
          </el-table-column>
          <el-table-column label="入库单价" :formatter="wrPrice" align="center" prop="wr_price"/>
          <el-table-column label="调拨金额" align="center" prop="transferAccount" width="100px"/>
          <el-table-column label="备注" align="center" prop="remark" width="140px">
            <template slot-scope="scope">
              <el-input v-model="scope.row.remark"></el-input>
            </template>
          </el-table-column>
          <el-table-column align="center" class-name="small-padding fixed-width" fixed="right" label="操作">
            <template slot-scope="scope">
              <el-button
                v-hasPermi="['system:role:edit']"
                icon="el-icon-edit"
                size="mini"
                type="text"
                @click="removeItems(scope.row)"
              >移除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <div id="count" style="border:1px solid  #eeeeee;height: 38px">
          <el-row>
            <span>合计</span>
            <el-col>
              <div class="down" style="float:right;margin-right: 5px;margin-top:-14px;font-size: 14px">

                <span>调拨数量:<em>{{ totaltransferCount }}</em></span>
                <span>调拨金额:<em>{{ totaltransferAccount }}</em></span>
              </div>
            </el-col>


          </el-row>


        </div>
      </div>
    </el-row>


    <div class="footer-container" style=" position: fixed;
          box-shadow: 2px 2px 5px rgba(0,0,0,0.9);  background-color:white;border-radius: 2px;
          width: 89.5%; height: 60px; bottom: 0; right: 0;  right: 0;z-index: 1000">
      <el-footer>
        <!-- 底部导航栏内容 -->
        <el-button v-hasPermi="['gd:good:add']"
                   plain
                   size="medium" style="float:right;margin-top: 12px"
                   type="success" @click="submitForm">保存
        </el-button>
        <el-button
          plain size="medium" style="float:right;margin-right: 20px;margin-top: 12px" @click="cancle">关闭
        </el-button>
      </el-footer>
    </div>


    <!--    经办热对话框-->
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


    <!--添加货品窗口-->
    <el-dialog :visible.sync="dialogTableVisible" title="添加货品" width="750px">
      <el-form ref="queryGoodsForm" :inline="true" :model="queryGoodsParams" size="small">
        <el-form-item label-width="86px" prop="codeOrName">
          <el-input
            v-model.trim="queryGoodsParams.codeOrName"
            clearable
            placeholder="请输入货品编号/名称"
            style="width: 200px"
          />
        </el-form-item>
        <el-form-item>
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
          <el-button icon="el-icon-search" size="mini" type="primary" @click="getGoodsList()">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table ref="goodsTable" v-loading="loading" :data="goodsList"
                row-key="sId" @selection-change="handlerSelectionChange">
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
        <el-table-column align="center" label="当前库位" prop="sl_name" width="100"/>
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

import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {listUser} from "@/api/system/user";
import {listWarehouse} from "@/api/wms/warehouse/warehouse.js";
import {addTransfer, listGoodByWid, listSlName, inNumsPlan} from "@/api/wms/warehouse/transfer/transfer";
import {Empty, MessageBox} from "element-ui";
import item from "@/layout/components/Sidebar/Item.vue";

export default {
  components: {Treeselect},
  dicts: ['transfer_type', 'g_unit_goods'],
  props: [],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 弹出层标题
      title: "",
      ids: [],
      //经办人弹出框
      openUsera: false,
      //经办人name
      userdga: undefined,
      //经办人id
      userIda: undefined,
      //经办人数据
      userLista: [],
      //调入仓库库位
      slNameList: [],
      //仓库名称数据
      wareNameList: [],
      dialogTableVisible: false,
      //货品表格数据
      goodsList: [],
      goodsTotal: 0,
      //选中货品数据
      tempSelectGoodsList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        deptName: undefined,

      },
      queryGoodsParams: {
        pageNum: 1,
        pageSize: 5,
        codeOrName: undefined,
        wn: undefined,
      },
      inventorysheetInf: {
        selectGoods: [],
      },
      // 表单参数
      form: {},
      // 非多个禁用
      multiple: true,
      defaultProps: {
        children: "children",
        label: "label"
      },
      formData: {},
      //调入仓库
      whIn: {},
      //调出仓库
      whOut: {},
      //调入仓库库位
      kwId: [],
      // 调入仓库货品
      goodsId: [],
      //入库仓库计划数量
      inNums: 0,

      // 表单校验
      rules: {
        type: [
          {required: true, message: "调拨类型不能为空", trigger: "blur"}
        ],
        date: [
          {required: true, message: "调拨申请日期不能为空", trigger: "blur"},
          {
            validator: (rule, value, callback) => {
              if (new Date(value) > new Date()) {
                callback(new Error("调拨申请日期不能大于当前日期"));
              } else {
                callback();
              }
            },
            trigger: "blur"
          }
        ],
        creatorName: [
          {required: true, message: "经办人不能为空", trigger: "blur"}
        ],
        outWId: [
          {required: true, message: "调出仓库不能为空", trigger: "blur"}
        ],
        inWId: [
          {required: true, message: "调入仓库不能为空", trigger: "blur"}
        ],
        outStatus: [
          {required: true, message: "出库状态不能为空", trigger: "change"}
        ],

      },
    }
  },
  computed: {
    //总计

    //调拨总数量
    totaltransferCount() {
      return this.inventorysheetInf.selectGoods?.reduce((accumulator, currentValue) => {
        return accumulator + (Number(currentValue.quantity) || 0);
      }, 0);

    },

    //调拨总金额
    totaltransferAccount() {
      let tf = this.inventorysheetInf.selectGoods?.reduce((accumulator, currentValue) => {
        return accumulator + (Number(currentValue.transferAccount) || 0);
      }, 0);
      return tf.toFixed(2);
    },
  },
  watch: {},
  created() {
    this.getUsera();
    this.wareName();

    this.inventorysheetInf = {
      selectGoods: [],
    };
  },
  mounted() {
  },
  methods: {
    /** 库位信息序号 */
    rowStorageLocationIndex({row, rowIndex}) {
      row.index = rowIndex + 1;
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.index)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    //查询仓库名称集合
    wareName() {
      listWarehouse().then(response => {
        this.wareNameList = response.rows;
      });
    },
    /*查询经办人*/
    getUsera() {
      listUser().then(response => {
        this.userLista = response.rows;
      })
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // // 节点单击事件
    handleUseraClick(row) {
      this.userdga = row.userName;
      this.userIda = row.userId;
    },

    handleUsera() {
      this.openUsera = true;
      this.title = "选择人员";
    },
    //确定选中人员
    okUsera() {
      this.formData.usera = this.userdga;
      this.openUsera = false;
    },
    //取消人员显示框
    cancleUsera() {
      this.openUsera = false;
    },
    //关闭窗口
    cancle() {
      this.$router.push(`/transfer`);
    },

    //调入仓库名称下拉事件
    handleSelectChangeIn(e) {
      this.whIn = this.wareNameList.filter(function (w) {
        return w.w_id === e;
      })[0];
      if (this.whIn.w_id == this.whOut.w_id) {
        this.$modal.msgWarning("入库仓库和出库仓库不能为同一仓库!");
        this.formData.inWId = null;
      } else {
        this.getSlName();
      }

    },

    //调出仓库名称下拉事件
    handleSelectChangeOut(e) {
      this.whOut = this.wareNameList.filter(function (w) {
        return w.w_id === e;
      })[0];
      if (this.whIn.w_id == this.whOut.w_id) {
        this.$modal.msgWarning("入库仓库和出库仓库不能为同一仓库!");
        this.formData.outWId = null;
      } else {
        this.tempSelectGoodsList = [],
          this.$set(this.inventorysheetInf, 'selectGoods', []);
      }
    },

    //获取调入仓库所有库位
    getSlName() {
      listSlName(this.whIn.w_id).then(response => {
        this.slNameList = response.data;
        console.log("slNameList", this.slNameList)
      })
    },


    //打开货品窗口
    addGoodsPage() {
      if (this.formData.outWId && this.formData.inWId) {
        this.dialogTableVisible = true;
        this.getGoodsList();
      } else {
        if (this.formData.outWId) {
          this.$modal.msgWarning("请选择调入仓库!");
        } else {
          this.$modal.msgWarning("请选择调出仓库!");
        }

      }

    },
    //货品确定按钮
    handleGoodsDefine() {
      this.getSlName();
      if (this.tempSelectGoodsList.length > 0) {
        if (this.inventorysheetInf.selectGoods.length > 0) {
          // this.tempSelectGoodsList.forEach(obj1 => {
          //   var isNo = false;
          //   this.inventorysheetInf.selectGoods.forEach(item=>{
          //     if (item.sId == obj1.sId){
          //       isNo=true;
          //     }
          //   })
          //   console.log(isNo);
          //   if (!isNo){
          //     this.inventorysheetInf.selectGoods.push(obj1);
          //   }
          // });
          this.tempSelectGoodsList.reduce((result, obj1) => {
            const isExists = this.inventorysheetInf.selectGoods.some(obj2 => obj2.sId === obj1.sId);
            if (!isExists) {
              this.inventorysheetInf.selectGoods.push(obj1);
            }
            return result;
          }, this.inventorysheetInf.selectGoods.slice());

          // this.inventorysheetInf.selectGoods = this.inventorysheetInf.selectGoods.concat(this.tempSelectGoodsList);
          console.info(this.tempSelectGoodsList + "*****************");
          console.log(" this.inventorysheetInf.selectGoods", this.inventorysheetInf.selectGoods)

        } else {
          this.inventorysheetInf.selectGoods = this.tempSelectGoodsList;

        }
      }

      this.closeGoodsSelect();
    },
    //货品取消按钮
    closeGoodsSelect() {
      this.dialogTableVisible = false;
    },
    //货品选中按钮
    handlerSelectionChange(row) {
      this.tempSelectGoodsList = row;
    },
    gotoaddwarehouse() {
      this.$router.push('/addwarehouse');
    },

    //移除盘点明细
    removeItems(row) {
      let indexs = row.index || this.ids;
      if (!Array.isArray(indexs)) {
        indexs = [indexs];
      }
      console.info("indexs: ", indexs);
      this.$modal.confirm('是否确认删除盘点明细编号为"' + indexs + '"的数据项？').then(() => {
        this.inventorysheetInf.selectGoods = this.inventorysheetInf.selectGoods?.filter(e => !indexs.includes(e.index));
      }).then(() => {
        this.$message.success("删除成功");
      }).catch(e => {
      });
    },

    //入库单价
    wrPrice(row) {
      return row.wr_price.toFixed(2);
    },

    //调拨数量失去焦点
    tquantityNums(row) {
      if (row.item_quantity < row.quantity) {
        MessageBox.confirm("该库存不足，不允许调拨出库！", "提示", {
          confirmButtonText: "知道了",
          type: "warning",
        })
          .then(() => {
            MessageBox.close(); // 关闭对话框
            row.quantity = null;
          })
          .catch(() => {
            row.quantity = null;
          })
      } else {
        //调拨金额
        this.$set(row, "transferAccount", (row.quantity * row.wr_price).toFixed(2))
      }

    },


    getGoodsList() {
      this.loading = true;
      listGoodByWid({
        g_name: this.queryGoodsParams.codeOrName,
        unit: this.queryGoodsParams.unit,
        w_id: this.formData.outWId,
        pageNum: this.queryGoodsParams.pageNum,
        pageSize: this.queryGoodsParams.pageSize,
        inWId: this.formData.inWId,
      }).then(response => {
          this.goodsList = response.rows;
          this.goodsTotal = response.total;
          this.$nextTick(() => {
            this.inventorysheetInf.selectGoods.forEach(vl => {
              this.goodsList.forEach(item => {
                if (item.sId === vl.sId) {
                  this.$refs.goodsTable.toggleRowSelection(item, true);
                }
              })
            })
          });
          this.loading = false;
        }
      );
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      let indexs = row.index || this.ids;
      if (!Array.isArray(indexs)) {
        indexs = [indexs];
      }
      console.info("indexs: ", indexs);
      this.$modal.confirm('是否确认删除库位编号为"' + indexs + '"的数据项？').then(() => {
        this.storageListAdd = this.storageListAdd?.filter(e => !indexs.includes(e.index));
      }).then(() => {
        this.$message.success("删除成功");
        console.log("this.storageListAdd: ", this.storageListAdd);
      }).catch(e => {
      });
    },
    /*最终添加*/
    submitForm() {
      this.$refs['elForm'].validate(valid => {
        if (valid) {
          this.formData.manager = this.userIda;
          this.formData.transferDetailsList = this.inventorysheetInf.selectGoods;
          console.log("this.formData，", this.formData)
          if (!this.formData.transferDetailsList && !this.formData.transferDetailsList.length > 0) {
            this.$message.error("请选中调拨明细");
            return;
          }
          addTransfer(this.formData).then(response => {
            this.$modal.msgSuccess("新增成功");
            this.$router.push(`/transfer`);
          })
        }
      })

    },
    //添加仓库货品重置
    resetQuery() {
      this.resetForm("queryGoodsForm");
      this.getGoodsList();
    },


  },


}

</script>
<style scoped>
::v-deep {
  .el-drawer__body {
    margin-left: 27px;
  }

  .demo-drawer__content .el-textarea__inner {
    width: 510px;
    height: 100px
  }

  .down > span {
    margin-right: 35px;

  }

  .down em {
    color: #ffae00;
  }
}
</style>
