<template>
  <PurchaseFrom :purchaseInf="purchaseInf" @submitForm="submitForm"></PurchaseFrom>
</template>

<script>
import PurchaseFrom from '@/views/wms/order/purchasingOrder/PurchaseFrom.vue'
import {getPurchaseInf} from "@/api/wms/order/purchase";

export default {
  name: 'addPurchaseFrom',
  components: {
    PurchaseFrom
  },
  methods: {
    async submitForm(data) {
      console.log("purchaseOrderInf: ", data)
    }
  },
  data() {
    return {
      purchaseInf: {
        poCode: undefined,
        purchaseDate: undefined,
        sid: undefined,
        purDeptId: undefined,
        purchaser: undefined,
        remark: undefined,
        selectAnnex: undefined,
        selectGoods: undefined,
      }
    }
  },
  async created() {
    let poId = this.$route?.params?.poId;
    if (!poId) {
      // this.$tab.closeOpenPage({path: '/order/purchase'});
      this.$tab.openPage("进货单据信息", '/order/purchase/add');
    }
    getPurchaseInf(poId).then(({code, data}) => {
      if (code === 200) {
        let selectAnnex = data?.annexes?.map(e => {
          let url = e.content;
          const filename = url.substring(url.lastIndexOf('/') + 1); // 获取URL中的文件名部分
          const parts = filename.split('_'); // 使用下划线将文件名分割成部分
          const result = parts[0] + '.' + parts[1].split('.')[1]; // 按照您的要求拼接文件名和后缀名
          return {
            name: result,
            url
          }
        })
        let selectGoods = data?.purchaseDetailsList?.map(e => ({
          g_name: e.goods.g_name,
          g_code: e.goods.g_code,
          spec_code: e.specCode,
          unit: e.unit,
          gt_name: e.goods.gt_name,
          purchaseQuantity: e.purchaseQuantity,
          wr_price: e.puPrice,
          remark: e.goods.remark,
        }));
        this.purchaseInf.poCode = data.poCode;
        this.purchaseInf.purchaseDate = data.purchaseDate;
        this.purchaseInf.sid = data.sId;
        this.purchaseInf.purDeptId = data.purchasingDept;
        this.purchaseInf.purchaser = data.purchaserId;
        this.purchaseInf.remark = data.remark;
        this.purchaseInf.selectAnnex = selectAnnex;
        this.purchaseInf.selectGoods = selectGoods;
      }
    });
  }

}

</script>

<style scoped>

</style>
