<template>
  <div>
    <y-header />
    <div class="person_body container account_cont clearfix">
      <y-side :type="'kszx'" />
      <div class="main_box">
        <ul class="tabs clearfix">
          <a class="tab on">我的考试</a>
        </ul>
        <div class="main_cont">
          <div v-if="notdata" class="notdata">
            <i class="iconfont">&#xe6be;</i>暂时没有数据
          </div>
          <table v-else class="course_table table">
            <h1>考试中心</h1>
          </table>
          <d-page v-if="pageObj.totalPage > 1" :page="pageObj" @btnClick="getPage" />
        </div>
      </div>
    </div>
    <!-- <bottom /> -->
  </div>
</template>
<script>
import YSide from '~/components/account/Side'
import YHeader from '~/components/common/Header'
import DPage from '~/components/common/Page'
import { userCoursePage } from '@/api/user.js'
// import Bottom from '@/components/common/Bottom'

export default {
  components: {
    // Bottom,
    YHeader,
    YSide,
    DPage
  },
  data() {
    return {
      notdata: true,
      pageCurrent: 1,
      pageObj: {}
    }
  },
  head() {
    return {
      title: '我的课程-' + this.$store.state.websiteInfo.websiteName
    }
  },
  mounted() {
    this.getStudyList()
  },
  methods: {
    getPage(int) {
      this.pageCurrent = int
      this.getStudyList()
    },
    getStudyList() {
      userCoursePage({ pageCurrent: this.pageCurrent, pageSize: 20 }).then(res => {
        if (res.totalCount > 0) {
          this.notdata = false
          this.pageObj = res
        }
      })
    }
  }
}
</script>
<style lang="scss">
@import '~/assets/css/account.scss';
</style>
