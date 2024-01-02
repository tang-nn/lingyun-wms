<script>

export default {
  name: "SelectorUser",
  data() {
    return {
      deptFilterText: '',
      currentUser: undefined,
      currentDept: undefined,
      defaultProps: {
        children: 'children',
        label: 'label'
      }
    }
  },
  props: {
    dialogSelectorVisible: {
      type: Boolean,Boolean,
      default: false
    },
    userList: {
      type: Array,
      default: ()=>[],
    },
    deptList: {
      type: Array,
      default: ()=>[],
    },
  },
  methods: {
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    }
  }
}

</script>
<template>
  <el-dialog :visible.sync="dialogSelectorVisible" title="收货地址" width="460px">
    <div class="box-sel">
      <div class="dept-sel">
        <el-input
            v-model="deptFilterText"
            placeholder="输入部门名称进行过滤">
        </el-input>
        <el-tree
            ref="tree"
            :data="deptList"
            :filter-node-method="filterNode"
            :props="defaultProps"
            class="filter-tree"
            default-expand-all>
        </el-tree>
      </div>
      <div class="user-sel">
        <div class="user-list">
          <div class="user-item">毛孟杰</div>
          <div class="user-item">毛孟杰</div>
          <div class="user-item">毛孟杰</div>
          <div class="user-item">毛孟杰</div>
        </div>
      </div>
    </div>
    <div slot="footer" class="dialog-footer">
      <el-button @click="$emit('handlerCancel', {user})">取 消</el-button>
      <el-button type="primary" @click="$emit('handlerDefine')">确 定</el-button>
    </div>
  </el-dialog>
</template>
<style scoped>
.box-sel{
  display: flex;
  justify-content: space-between;
}
.user-sel{
  width: 200px;
}
</style>
