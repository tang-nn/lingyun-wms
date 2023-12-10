<template>
  <PurchaseFrom @submitForm="submitForm"></PurchaseFrom>
</template>

<script>
import PurchaseFrom from '@/views/wms/order/purchasingOrder/PurchaseFrom.vue'
import {getPurchaseInf} from "@/api/wms/order/purchase";

export default {
  name: 'addPurchaseFrom',
  components: {
    PurchaseFrom
  },
  methods:{
    async submitForm(data) {
      console.log("purchaseOrderInf: ", data)
    }
  },
  async created() {
    let poId = this.$route?.params?.poId;
    if (!poId){
      this.$tab.openPage("添加进货单据", '/order/purchase/add');
    }
    let {data:purchaseInf} = (await getPurchaseInf(poId));
    console.log("purchaseInf: ", purchaseInf)
    console.log("route: ", this.$route)
  }

}

</script>

<style scoped>

</style>
