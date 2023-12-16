<template>
  <div class="common-layout">
    <el-container>
      <el-header class="i01">
        <div class="i01_01">
          <el-row>
            <el-col :span="7" class="i01_01_col">
              <label>货品</label>
              <el-input
                placeholder="请输入内容"
                v-model="i_goods"
                clearable>
              </el-input>
            </el-col>
            <el-col :span="7" class="i01_01_col">
              <label>货品类型</label>
              <treeselect v-model="queryParams.gt_id"
                          :defaultExpandLevel="Infinity"
                          :options="goodsTypes"
                          :show-count="false"
                          placeholder="请输入"
                          style="width: 380px;font-size: 14px"/>
            </el-col>
            <el-col :span="7" class="i01_01_col">
              <label>单位</label>
              <el-select v-model="i_unit" placeholder="请输入">
                <el-option
                  v-for="dict in dict.type.g_unit_goods"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="7" class="i01_01_col">
              <label>仓库名称</label>
              <el-select v-model="i_warehouse_name" placeholder="请输入">
                <el-option
                  v-for="wh in lists"
                  :key="wh.wId"
                  :label="wh.wName"
                  :value="wh.wId"
                />
              </el-select>
            </el-col>
            <el-col :span="7" class="i01_01_col">
              <label>货品条码</label>
              <el-input
                placeholder="请输入内容"
                v-model="i_product_barcode"
                clearable>
              </el-input>
            </el-col>
            <el-col :span="7" class="i01_01_col">
              <label>选择日期</label>
              <el-date-picker
                v-model="i_select_date"
                type="daterange"
                align="right"
                unlink-panels
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                :picker-options="pickerOptions2">
              </el-date-picker>
            </el-col>
          </el-row>
        </div>
      </el-header>
      <el-main class="i02">
        <div>

        </div>
      </el-main>
    </el-container>
  </div>
</template>

<script>
import {TypesList,listsWarehouse} from "@/api/wms/standingbook/Inventoryquery/index";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  dicts: ['g_unit_goods'],
  components: {Treeselect},
  data() {
    return {
      i_goods: '',
      i_product_type: '',
      i_unit: '',
      i_warehouse_name:'',
      i_product_barcode: '',
      i_select_date: '',
      pickerOptions2: {
        shortcuts: [{
          text: '最近一周',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 7);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 30);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近三个月',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 90);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近半年',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 182);
            picker.$emit('pick', [start, end]);
          }
        }, {
          text: '最近一年',
          onClick(picker) {
            const end = new Date();
            const start = new Date();
            start.setTime(start.getTime() - 3600 * 1000 * 24 * 365);
            picker.$emit('pick', [start, end]);
          }
        }]
      },
      goodsTypes: undefined,
      lists: undefined,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 5,
        g_name: undefined,
        status: undefined,
        unit: undefined,
        type: undefined,
        gt_id: undefined,
      },
    }
  },
  mounted() {
    this.goodsTypesList();
    this.listsWarehouse();
  },
  methods: {
    /* 查看所有商品分类 */
    goodsTypesList(){
      TypesList().then(response => {
        this.goodsTypes = response.data;
      });
    },
    listsWarehouse() {
      listsWarehouse().then(response => {
        this.lists = response;
      });
    },
  }
}
</script>

<style scoped>
.i01_01 {
  padding: 16px;
}

.i01_01_col > .el-select {
  width: 380px;
}

.i01_01_col {
  width: 380px;
  display: flex;
  align-items: center;
  margin: 10px;

}

.i01_01_col > label {
  width: 80px;
  text-align: center;
  font-size: 12px;
  margin-right: 6px;
}

.i02 {
  padding: 16px;
}
</style>
