<template>
  <InboundForm :is-editor="false" @submitForm="submitForm"></InboundForm>
</template>

<script>
import InboundForm from './InboundForm.vue'
import {addInbound} from "@/api/wms/inboundOutbound/inboundMgt";

export default {
  name: "InboundEditor",
  components: {
    InboundForm,
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
          this.$message.success("添加成功");
          this.$tab.closeOpenPage({path: '/inOutbound/inbound'});
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
