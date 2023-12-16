<template>
  <From :inboundData="inboundData"></From>
</template>

<script>
import From from './From.vue'
import {getPurchaseInf} from "@/api/wms/order/purchase";
import {getInventory} from "@/api/wms/inboundOutbound/inboundMgt";

export default {
  name: "InboundEditor",
  components: {
    From,
  },
  data() {
    return {
      inboundData: {

      }
    }
  },
  created() {
    let id = this.$route?.params?.inid;
    if (!id) this.goInboundList();
    getInventory(id).then(({code, data}) => {
      if (code === 200) {
        this.inboundData = data;
      } else this.goInboundList();
    }).catch(() => {
      console.log("获取入库单失败，入库 ID：", id)
      this.goInboundList()
    });
  },
  methods: {
    goInboundList() {
      // this.$message.error("获取入库单失败，请稍后再试")
      try {
        this.$tab.closeOpenPage({path: '/inboundOutbound/inbound'});
      } catch (err) {
        console.log("关闭入库单失败：", err)
      }
      // this.$tab.closePage(undefined, '/inboundOutbound/inbound');
    }
  }
}
</script>

<style>

</style>
