<template>
  <div class="app-container">
    <div class="page_head">
      <div class="search_bar clearfix">
        <el-form :model="seekForm" inline label-width="80px">
          <el-form-item label="专区名称">
            <el-input v-model="seekForm.zoneName" clearable/>
          </el-form-item>
          <el-form-item>
            <el-button @click="seek()" type="primary"> 查询</el-button>
            <el-button @click="resetSeek()">重置</el-button>
            <el-button v-if="checkPermission('course:admin:zone:save')" plain type="success" @click="openEditDialog(initData)">添加</el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
    <el-table v-loading="tableData.loading" :data="tableData.list" border v-if="tableData.list">
      <el-table-column align="center" label="序号" type="index" width="60"/>
      <el-table-column label="专区名称" prop="zoneName">
        <template #default="scope">
          <span>{{ scope.row.zoneName }}</span><br>
        </template>
      </el-table-column>
      <el-table-column label="描述" prop="zoneDesc">
        <template #default="scope">
          <span>{{ scope.row.zoneDesc }}</span><br>
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
          <el-button v-if="checkPermission('course:admin:zone:edit')" plain type="success" @click="zoneCourse(scope.row)">课程</el-button>
          <el-dropdown>
            <el-button> 更多操作<i class="el-icon-arrow-down"/></el-button>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item>
                  <el-button v-if="checkPermission('course:admin:zone:edit')" plain type="primary" @click="openEditDialog(scope.row)">编辑</el-button>
                </el-dropdown-item>
                <el-dropdown-item>
                  <el-button v-if="checkPermission('course:admin:zone:edit') && scope.row.statusId == 0" plain type="success" @click="handleUpdateStatus(scope.row)">启用</el-button>
                  <el-button v-if="checkPermission('course:admin:zone:edit') && scope.row.statusId == 1" plain type="danger" @click="handleUpdateStatus(scope.row)">禁用</el-button>
                </el-dropdown-item>
                <el-dropdown-item>
                  <el-button v-if="checkPermission('course:admin:zone:delete')" plain type="danger" @click="tableDelete(scope.row)">删除</el-button>
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
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
import {zoneDelete, zonePage} from '@/api/course.js'
import Edit from './edit.vue';

export default defineComponent({
  components: {
    Edit
  },
  setup() {
    const apis = reactive({
      getList: zonePage,
      delete: zoneDelete
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
            message: res
          });
          state.getTableData();
        }
        state.tableData.loading = false;
      });
    };

    //
    const zoneCourse = function(row) {
      this.$router.push({path: '/common/course', query: {zoneId: row.id}});
    }

    return {
      ...toRefs(state),
      initData,
      handleUpdateStatus,
      zoneCourse
    };
  }
});
</script>
