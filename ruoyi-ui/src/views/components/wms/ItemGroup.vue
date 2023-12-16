<template>
  <div class="item-group">
    <slot name="title">
      <el-divider content-position="left">{{title}}</el-divider>
    </slot>
    <slot name="body"></slot>
  </div>
</template>

<script>
export default {
  name: "ItemGroup",
  props: {
    title: {
      type: String,
      default: ""
    }
  }
}
</script>
<style scoped>
.item-group {
  background: #FFFFFF;
  box-shadow: 0 0 5px rgba(0, 0, 0, 0.2);
  padding: 20px;
  margin-bottom: 20px;
}
</style>
