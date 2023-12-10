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
      purchaseInf: {}
    }
  },
  async created() {
    let poId = this.$route?.params?.poId;
    if (!poId) {
      this.$tab.openPage("进货单据信息", '/order/purchase/add');
    }
    ({data: this.purchaseInf} = (await getPurchaseInf(poId)));
    console.log("purchaseInf: ", this.purchaseInf)
    // console.log("route: ", this.$route)
  }

}

</script>

<style scoped>

</style>
