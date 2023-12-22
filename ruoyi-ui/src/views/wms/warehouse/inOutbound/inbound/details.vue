<template>
  <ItemGroupLayout>
    <template slot="default">
      <ItemGroup>
        <template slot="title">
          <el-row class="row-bg" justify="space-between" type="flex">
            <el-col :span="9">
              <div style="display: flex;align-items: center;">
                <span style="margin-right:20px;">进货单号：{{ this.purchaseInf.poCode }}</span>
                <i class="el-icon-document-copy"></i>
              </div>
            </el-col>
            <el-col :span="7">
              <el-button
                icon="el-icon-edit"
                plain
                size="mini"
                type="success"
              >编辑
              </el-button>
              <el-button
                plain
                size="mini"
              >撤销
              </el-button>
              <el-button
                plain
                size="mini"
              >删除
              </el-button>
              <el-button
                plain
                size="mini"
              >关闭
              </el-button>
              <el-button
                plain
                size="mini"
              >打印
              </el-button>
            </el-col>
          </el-row>
        </template>
        <template slot="body">
          <div class="purchase-inf">
            <div class="group">
              <div>
                <span>制单人</span>
                <span class="value">{{ this.purchaseInf.creator.nickName }}</span>
              </div>
              <div>
                <span>进货日期</span>
                <span class="value">{{ this.purchaseInf.purchaseDate }}</span>
              </div>
              <div>
                <span>联系方式</span>
                <span class="value">{{ this.purchaseInf.supplier.contactNumber }}</span>
              </div>
              <div>
                <span>备注</span>
                <span class="value">{{ this.purchaseInf.remark }}</span>
              </div>
            </div>
            <div class="group">
              <div>
                <span>所在部门</span>
                <span class="value">{{ this.purchaseInf.creator.dept.deptName }}</span>
              </div>
              <div>
                <span>供应商</span>
                <span class="value">{{ this.purchaseInf.supplier.sName }}</span>
              </div>
              <div>
                <span>进货部门</span>
                <span class="value">{{ this.purchaseInf.purchaser.dept.deptName }}</span>
              </div>
            </div>
            <div class="group">
              <div>
                <span>制单时间</span>
                <span class="value">{{ this.purchaseInf.createTime }}</span>
              </div>
              <div>
                <span>联系人</span>
                <span class="value">{{ this.purchaseInf.supplier.contactPerson }}</span>
              </div>
              <div>
                <span>进货人</span>
                <span class="value">{{ this.purchaseInf.purchaser.nickName }}</span>
              </div>
            </div>
            <div class="group">
              <div>
                <span>单据状态</span>
                <span class="value"><dict-tag :options="dict.type.ord_doc_status"
                                              :value="this.purchaseInf.status"/></span>
              </div>
            </div>
          </div>
        </template>
      </ItemGroup>
      <ItemGroup>
        <template slot="title">
          <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="其他信息" name="0">
              <!--              <h1>附件</h1>-->
              <div>
                <el-table
                  ref="singleTable"
                  :data="this.purchaseInf.selectAnnex"
                  highlight-current-row
                  style="width: 100%">
                  <el-table-column
                    label="附件"
                    prop="name">
                  </el-table-column>
                </el-table>
              </div>
            </el-tab-pane>
            <el-tab-pane label="进货明细" name="1">
              <el-table :cell-style="{ padding:'0px' }"
                        :data="purchaseInf.selectGoods"
                        :row-style="{ height:'70px' }">
                <el-table-column align="center" fixed label="序号" type="index" width="60"/>
                <el-table-column align="center" fixed label="货品名称" prop="g_name" width="80"/>
                <el-table-column align="center" fixed label="货品编号" prop="g_code" width="110"/>
                <el-table-column align="center" label="规格型号" prop="spec_code" width="100"/>
                <el-table-column align="center" label="单位" prop="unit" width="100">
                  <template slot-scope="scope">
                    <dict-tag :options="dict.type.g_unit_goods" :value="scope.row.unit"/>
                  </template>
                </el-table-column>
                <el-table-column align="center" label="货品类型" prop="gt_name" width="100"/>
                <el-table-column :formatter="(row)=>(row.item_quantity || '无货')" align="center" label="当前库存"
                                 prop="item_quantity" width="100"/>
                <el-table-column align="center" label="进货数量" prop="purchaseQuantity" width="120">

                </el-table-column>
                <el-table-column align="center" label="进货单价" prop="wr_price" width="100">

                </el-table-column>
                <el-table-column :formatter="row=>row.wr_price * (row.purchaseQuantity || 0)" align="center"
                                 label="金额"
                                 prop="lumpSum" width="120"/>
                <el-table-column align="center" label="备注" prop="remark">

                </el-table-column>
              </el-table>
            </el-tab-pane>
            <el-tab-pane label="入库记录" name="2">

            </el-tab-pane>
            <el-tab-pane label="退货记录" name="3">

            </el-tab-pane>
            <el-tab-pane label="操作记录" name="4">

            </el-tab-pane>
            <el-tab-pane label="审核记录" name="5">

            </el-tab-pane>
          </el-tabs>
        </template>
        <template slot="body">

        </template>
      </ItemGroup>
    </template>
  </ItemGroupLayout>
</template>

<script>
import ItemGroupLayout from "@/views/components/wms/ItemGroupLayout.vue";
import ItemGroup from "@/views/components/wms/ItemGroup";
import {getPurchaseInf} from "@/api/wms/order/purchase";
import {getUser} from "@/api/system/user";
import {getSupplier} from "@/api/wms/contactUnits/supplier";

export default {
  name: "purchaseDetails",
  dicts: ['ord_doc_status', 'g_unit_goods'],
  components: {
    ItemGroupLayout,
    ItemGroup
  },
  data() {
    return {
      activeName: "1",
      purchaseInf: {
        creator: {
          dept: {}
        },
        supplier: {},
        purchaser: {
          dept: {}
        },
        selectAnnex: []
      }
    }
  },
  methods: {
    handleClick(tab, event) {
      console.log("tab, event: ", tab, event);
    }
  },
  async created() {
    let poId = this.$route?.params?.poId;
    if (!poId) {
      this.$tab.openPage("进货订单", '/order/purchase');
      return
    }
    let code;
    ({code: code, data: this.purchaseInf} = (await getPurchaseInf(poId)));
    if (code !== 200 || !this.purchaseInf.createBy) {
      this.$tab.openPage("进货订单", '/order/purchase');
      return
    }
    this.purchaseInf.creator = {dept: {}};
    this.purchaseInf.purchaser = {dept: {}};
    this.purchaseInf.supplier = {};
    try {
      // 制单人查询
      ({data: this.purchaseInf.creator} = (await getUser(this.purchaseInf.createBy)));
      // 供应商查询
      ({data: this.purchaseInf.supplier} = (await getSupplier(this.purchaseInf.sId)));
      // 进货人查询
      ({data: this.purchaseInf.purchaser} = (await getUser(this.purchaseInf.purchaserId)));
      this.purchaseInf.selectAnnex = this.purchaseInf?.annexes?.map(e => {
        let url = e.content;
        const filename = url.substring(url.lastIndexOf('/') + 1); // 获取URL中的文件名部分
        const parts = filename.split('_'); // 使用下划线将文件名分割成部分
        const result = parts[0] + '.' + parts[1].split('.')[1]; // 按照您的要求拼接文件名和后缀名
        return {
          name: result,
          url
        }
      })
      this.purchaseInf.selectGoods = this.purchaseInf?.purchaseDetailsList?.map(e => ({
        g_name: e.goods.g_name,
        g_code: e.goods.g_code,
        spec_code: e.specCode,
        unit: e.unit,
        gt_name: e.goods.gt_name,
        purchaseQuantity: e.purchaseQuantity,
        wr_price: e.puPrice,
        remark: e.goods.remark,
      }));
      this.$nextTick(() => {
        this._update(this._render());
      });
    } catch (e) {
      console.log("ERROR: ", e);
    }
    console.log("purchaseInf: ", this.purchaseInf);
    // console.log("purchaseInf: ", this.purchaseInf)
    // console.log("route: ", this.$route)
  }
}
</script>
<style scoped>
.purchase-inf {
  display: flex;
  justify-content: space-between;
  margin-top: 20px;
}

.purchase-inf .value {
  color: #888888;
  margin-left: 10px;
}

.purchase-inf .group > div {
  margin: 20px 0;
}

.purchase-inf .group {

}
</style>
