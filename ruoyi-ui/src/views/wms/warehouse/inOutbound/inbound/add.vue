<template>
  <From :is-editor="false" @submitForm="submitForm"></From>
</template>

<script>
import From from './From.vue'
import {addInbound} from "@/api/wms/inboundOutbound/inboundMgt";

export default {
  name: "InboundEditor",
  components: {
    From,
  },
  data() {
    return {}
  },
  created() {
  },
  methods: {
    submitForm(data) {
      console.log("submitForm data: ", data)
      addInbound(data).then(({code, message}) => {
        if (code == 200) {
          this.$message.success("添加成功")
          this.$tab.closeOpenPage({path: '/inboundOutbound/inbound'});
        } else {
          this.$message.error("添加失败")
        }
      })
    }
  }
}
</script>

<style>

</style>
