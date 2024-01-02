<template>
  <div v-if="rendering">
    <OutboundForm v-loading="!rendering" :is-editor="true" :outbound-data="outboundData"
                  @submitForm="submitForm"></OutboundForm>
  </div>
</template>

<script>
import OutboundForm from "@/views/wms/warehouse/inOutbound/outbound/OutboundForm.vue";
import {addOutbound, getOutbound, updateOutbound} from "@/api/wms/inboundOutbound/outbound";
import {getInbound} from "@/api/wms/inboundOutbound/inboundMgt";


export default {
  name: "OutboundEditor",
  components: {
    OutboundForm
  },
  data() {
    return {
      outboundData: undefined,
      rendering: false,
    }
  },
  created() {
    let id = this.$route?.params?.outId;
    if (!id) this.closeEditor();
    getOutbound(id).then(({code, data}) => {
      if (code === 200) {
        this.outboundData = data;
        this.outboundData.selectedOrder = {
          orderNo: data.relatedOrder,
          orderId: data.orderId
        };
        // 状态: 已完成，审核通过
        let status = ['done'];
        if (status.includes(this.outboundData.status)) {
          this.$message.error("当前订单状态无法编辑！")
          // 禁止编辑
          this.closeEditor()
        }
        this.rendering = true;
        // let outboundDetails = this.outboundData.outboundDetailsList;
        // delete this.outboundData.outboundDetailsList;
        // this.outboundData.outboundDetails = outboundDetails;
        this.rendering = true;
        console.log("outboundData: ", this.outboundData)
      }
    }).catch((err) => {
      console.log("获取入库单失败：", err)
      console.log("获取入库单失败，入库 ID：", id)
      this.closeEditor()
    });
  },
  methods: {
    submitForm(data) {
      console.log("submitForm data: ", data)
      try {
        updateOutbound(data).then(({code, msg}) => {
          if (code == 200) {
            this.$message.success("修改成功");
            this.$tab.closeOpenPage({path: '/inOutbound/outbound'});
          } else {
            this.$message.error("修改成功")
            console.log("编辑出库单是错误: ", msg)
          }
        })
      } catch (e) {
        console.log("编辑出库单是错误: ", e)
      }
    },
    closeEditor() {
      try {
        this.$tab.closeOpenPage({path: '/inOutbound/outbound'});
      } catch (err) {
        console.log("关闭出库单失败：", err)
      }
    }
  }
}
</script>

<style>

</style>
