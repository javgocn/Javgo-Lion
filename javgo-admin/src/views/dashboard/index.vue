<template>
  <div class="dashboard-container">
    <div v-if="checkPermission('user:admin:stat:login')" class="title-info">
      <span class="title">基础数据</span>
    </div>
    <div style="margin-left: 100px">
      <el-row>
        <el-col :span="6" style="font-size: 28px">
          <el-statistic title="课程数量" :value="baseData.courseCount" />
        </el-col>
        <el-col :span="6">
          <el-statistic title="用户数量" :value="baseData.userCount" />
        </el-col>
        <el-col :span="6">
          <el-statistic :value="baseData.dayOrderCount">
            <template #title>
              <div style="display: inline-flex; align-items: center">
                订单数量
                <el-icon style="margin-left: 4px" :size="12">
                  <Male />
                </el-icon>
              </div>
            </template>
            <template #suffix>/ {{ baseData.orderCount }}</template>
          </el-statistic>
        </el-col>
        <el-col :span="6">
          <el-statistic :value="baseData.dayOrderMoney">
            <template #title>
              <div style="display: inline-flex; align-items: center">
                订单金额
                <el-icon style="margin-left: 4px" :size="12">
                  <Male />
                </el-icon>
              </div>
            </template>
            <template #suffix>/{{baseData.orderMoney}}</template>
          </el-statistic>
        </el-col>

      </el-row>
    </div>
    <div v-if="checkPermission('user:admin:stat:login')" class="title-info">
      <span class="title">最近登录人数</span>
    </div>
    <login v-if="checkPermission('user:admin:stat:login')" :data="statData"/>
  </div>
</template>
<script>
import {baseData, statLogin} from '@/api/dashboard.js';
import Login from './statLogin.vue'

export default {
  name: 'Dashboard',
  components: { Login},
  data() {
    return {
      baseData: {},
      statData: {}
    }
  },
  mounted() {
    this.getLogin();
    this.getBaseData();
  },
  methods: {
    getLogin() {
      statLogin().then(res => {
        this.statData = res
      })
    },
    getBaseData(){
      baseData().then(res =>{
        this.baseData = res
      })
    }
  }
};
</script>

<style lang="scss" scoped>
.title-info {
  margin: 20px 25px;
  padding: 5px;
  background-color: #ecf8ff;
  border-left: 5px solid #50bfff;
  border-radius: 4px;
  font-size: 16px;
}
</style>
