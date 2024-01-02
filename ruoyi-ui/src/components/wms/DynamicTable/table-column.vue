<template>
  <el-table-column
    align="center"
    v-bind="columnHeader"
  >
    <!--columnHeader对应:column-header-->
    <template v-for="item in columnHeader.children">
      <tableColumn
        v-if="item.children && item.children.length"
        :key="item.id"
        :column-header="item"
      />
      <el-table-column
        v-else
        align="center"
        v-bind="item"
      />
    </template>
  </el-table-column>
</template>

<script>
export default {
  name: 'TableColumn',
  props: {
    columnHeader: {
      type: Object,
      required: true
    }
  }
}
</script>

<style scoped>

</style>
