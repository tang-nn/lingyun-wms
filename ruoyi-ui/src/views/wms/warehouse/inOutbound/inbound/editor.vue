<template>
  <From v-if="rendering" :inboundData="inboundData" :is-editor="true" @submitForm="submitForm"></From>
</template>

<script>
import From from './From.vue'
import {getPurchaseInf} from "@/api/wms/order/purchase";
import {getInbound, putInbound} from "@/api/wms/inboundOutbound/inboundMgt";

export default {
  name: "InboundEditor",
  components: {
    From,
  },
  data() {
    return {
      rendering: false,
      inboundData: undefined
    }
  },
  created() {
    let id = this.$route?.params?.inid;
    if (!id) this.goToInboundListPage();
    getInbound(id).then(({code, data}) => {
      if (code === 200) {
        this.inboundData = data;
        this.inboundData.selectedOrder = {
          orderNo: data.relatedOrder,
          orderId: data.orderId
        };
        // 状态: 已完成，审核通过
        let status = ['is_done', 'approved'];
        if (status.includes(this.inboundData.status)) {
          this.$message.error("当前状态无法编辑！")
          // 禁止编辑
          this.goToInboundListPage();
        }
        this.rendering = true;
        // console.log("inboundData: ", this.inboundData)
      } else {
        // this.goToInboundListPage();
      }
    }).catch((err) => {
      console.log("获取入库单失败：", err)
      console.log("获取入库单失败，入库 ID：", id)
      this.goToInboundListPage()
    });
  },
  methods: {
    goToInboundListPage() {
      // this.$message.error("获取入库单失败，请稍后再试")
      try {
        this.$tab.closeOpenPage({path: '/inboundOutbound/inbound'});
      } catch (err) {
        console.log("关闭入库单失败：", err)
      }
      // this.$tab.closePage(undefined, '/inboundOutbound/inbound');
    },
    submitForm(data) {
      console.log("exit 入库 data: ", data)
      putInbound(data).then(({code, msg}) => {
        if (code === 200) {
          this.$message.success("更新成功！");
          this.goToInboundListPage();
        } else {
          this.$message.error(msg);
        }
      });
    }
  }
}
</script>

<style>

</style>
