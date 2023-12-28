<template>
  <div class="app-container">
    <div class="page_head">
      <div class="search_bar clearfix">
        <el-form :model="seekForm" inline label-width="80px">
          <el-form-item>
            <el-button v-if="checkPermission('course:admin:zone:teacher:save')" plain type="success" @click="openEditDialog(initData)">添加</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <el-table v-loading="tableData.loading" :data="tableData.list" border v-if="tableData.list">
      <el-table-column align="center" label="序号" type="index" width="60"/>
      <el-table-column label="讲师名称" prop="name">
        <template #default="scope">
          <span>{{ scope.row.name }}</span><br>
        </template>
      </el-table-column>
      <el-table-column label="头像" prop="imageUrl">
        <template #default="scope">
          <span><el-image :src="scope.row.imageUrl" style="width: 100px;height: 100px"></el-image></span><br>
        </template>
      </el-table-column>
      <el-table-column label="备注" prop="remark">
        <template #default="scope">
          <span>{{ scope.row.remark }}</span><br>
        </template>
      </el-table-column>
      <el-table-column label="排序" prop="sort" :width="100"/>
      <el-table-column label="状态" :width="100">
        <template #default="scope">
          <span :class="{ 'c-danger': scope.row.statusId === 0 }">{{ statusIdEnums[scope.row.statusId] }}</span>
        </template>
      </el-table-column>
      <el-table-column :width="200" fixed="right" label="操作" prop="address">
        <template #default="scope">
          <el-button v-if="checkPermission('course:admin:zone:teacher:update') && scope.row.statusId == 0" plain type="success" @click="handleUpdateStatus(scope.row)">启用</el-button>
          <el-button v-if="checkPermission('course:admin:zone:teacher:update') && scope.row.statusId == 1" plain type="danger" @click="handleUpdateStatus(scope.row)">禁用</el-button>
          <el-button v-if="checkPermission('course:admin:zone:teacher:delete') " plain type="danger" @click="handleDeleted(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination :current-page="page.pageCurrent" :layout="page.layout" :page-size="page.pageSize" :page-sizes="[20, 50, 100, 200]" :total="page.totalCount" background @size-change="handleSizeChange" @current-change="handleCurrentChange"/>
    <edit v-model="editModel.visible" :modelValue="editModel.visible" :form="editModel.form" @updateTable="closeEditDialog"/>
  </div>
</template>
<script>
import UseTable from '@/composables/UseTable.js';
import {ElMessage} from 'element-plus';
import {defineComponent, onMounted, reactive, toRefs} from 'vue';
import {useStore} from 'vuex';
import {zoneTeacherList,zoneTeacherDelete,zoneTeacherUpdate} from '@/api/course.js'
import Edit from './edit.vue';

export default defineComponent({
  components: {
    Edit
  },
  setup() {
    const apis = reactive({
      getList: zoneTeacherList,
      delete: zoneTeacherDelete,
      updateStatus: zoneTeacherUpdate,
    })
    const state = reactive({
      ...UseTable(apis, {}),
      statusIdEnums: {},
      putawayEnums: {}
    });
    const initData = reactive({
      statusId: 1,
      sort: 1
    })
    const store = useStore();
    onMounted(() => {
      store.dispatch('GetOpts', {enumName: 'StatusIdEnum', type: 'obj'}).then((res) => {
        state.statusIdEnums = res;
      });
    });

    const handleUpdateStatus = function(row) {
      state.tableData.loading = true;
      row.statusId = row.statusId ? 0 : 1
      apis.updateStatus({id: row.id, statusId: row.statusId}).then((res) => {
        if (res) {
          ElMessage({
            type: 'success',
            message: '修改成功'
          });
          state.getTableData();
        }
        state.tableData.loading = false;
      });
    };

    const handleDeleted = function (row){
      state.tableData.loading = true;
      apis.delete({id: row.id}).then((res) => {
        if (res) {
          ElMessage({
            type: 'success',
            message: '删除成功'
          });
          state.getTableData();
        }
        state.tableData.loading = false;
      });
    }

    //
    const zoneCourse = function(row) {
      this.$router.push({path: '/common/course', query: {zoneId: row.id}});
    }

    return {
      ...toRefs(state),
      initData,
      handleUpdateStatus,
      zoneCourse,
      handleDeleted
    };
  }
});
</script>
