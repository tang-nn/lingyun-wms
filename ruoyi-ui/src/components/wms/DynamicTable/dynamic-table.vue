<script>
import TableColumn from './table-column.vue'

export default {
  name: 'DynamicTable',
  components: {
    TableColumn
  },
  data() {
    return {
      currElName: 'dynamicTableEl',
      tableHeaderTc: {
        id: String,
        label: String,
        prop: String,
        attrs: {},
        children: []
      }
    }
  },
  props: {
    // 表格的数据
    tableData: {
      type: Array,
      required: true
    },
    // 多级表头的数据
    tableHeader: {
      type: Array,
      required: true
    },
    // 是否需要调用 el-table 的函数,
    needCallFunc: {
      type: Boolean,
      default: false
    }
  },
  methods: {},
  created() {
    if(this.needCallFunc) {
      let dynamicTable = this.$refs[this.currElName];
      for (const key in dynamicTable) {
        this[key] = dynamicTable[key]
      }
    }
  }
}
</script>

<template>
  <!-- 动态展示表格 -->
  <el-table :ref="currElName" :data="tableData" border stripe v-bind="$attrs">
    <template v-for="(value, name) in $slots" #[name]="scopeData">
      <slot :name="name" v-bind="scopeData || {}"></slot>
    </template>
    <!-- v-for 循环取表头数据 -->
    <template v-for="item in tableHeader">
      <table-column v-if="item.children && item.children.length" :key="item.id" :column-header="item"/>
      <el-table-column v-else :key="item.id" :label="item.label" :prop="item.prop" align="center" v-bind="item.attrs"/>
    </template>
  </el-table>
</template>

