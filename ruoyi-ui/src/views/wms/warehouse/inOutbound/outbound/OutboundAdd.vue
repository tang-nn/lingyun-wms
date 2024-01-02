<template>
  <OutboundForm @submitForm="submitForm"></OutboundForm>
</template>

<script>
import OutboundForm from "@/views/wms/warehouse/inOutbound/outbound/OutboundForm.vue";
import {addOutbound} from "@/api/wms/inboundOutbound/outbound";



export default {
  name: "OutboundAdd",
  components: {
    OutboundForm
  },
  data() {
    return {}
  },
  created() {
  },
  methods: {
    submitForm(data) {
      console.log("submitForm data: ", data)
      addOutbound(data).then(({code, message}) => {
        if (code == 200) {
          this.$message.success("添加成功");
          this.$tab.closeOpenPage({path: '/inOutbound/outbound'});
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
