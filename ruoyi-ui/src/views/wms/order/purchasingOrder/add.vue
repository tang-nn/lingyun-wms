<template>
  <PurchaseFrom @submitForm="submitForm"></PurchaseFrom>
</template>

<script>
import PurchaseFrom from '@/views/wms/order/purchasingOrder/PurchaseFrom.vue'
import {addPurchase} from "@/api/wms/order/purchase";

export default {
  name: 'addPurchaseFrom',
  components: {
    PurchaseFrom
  },
  methods:{
    async submitForm(data) {
      console.log("purchaseOrderInf: ", data)
      let {code, message} = (await addPurchase(data));
      if (code === 200) {
        this.$message.success(message);
        this.$tab.closeOpenPage({path: '/order/purchase'});
      } else {
        this.$message.error(message);
      }
    }
  }

}

</script>

<style scoped>

</style>
