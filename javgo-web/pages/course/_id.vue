<template>
  <div class="course_detail">
    <y-study v-if="courseInfo.allowStudy" ref="watchVideo" :course-info="courseInfo" :play-period="playPeriod" @playfunc="videoPlay" />
    <y-detail v-else :course-info="courseInfo" :teacher-info="teacherInfo" />
    <div class=" detail_info detail_box clearfix">
      <div class="layout_left">
        <ul class="course_tab clearfix">
          <li :class="{on: tab == 'info'}"><a href="javascript:" @click="tab = 'info'">课程介绍</a></li>
          <li :class="{on: tab == 'big'}"><a href="javascript:" @click="tab = 'big'">课程目录</a></li>
          <li :class="{on: tab == 'comment'}"><a href="javascript:" @click="tab = 'comment'">课程评论</a></li>
        </ul>
        <div v-if="tab == 'info'" class="content_info">
          <div class="introduce" v-html="courseInfo.introduce" />
        </div>
        <div v-if="tab == 'big'" class="content_info">
          <y-catalog :list="courseInfo.chapterRespList" :play-period="playPeriod" @playfunc="videoPlay" />
        </div>
        <div v-if="tab == 'comment'" class="content_info">
          <y-comment :id="courseInfo.id" />
        </div>
      </div>
      <div class="layout_right">
        <div class="teacher_info clearfix">
          <span class="head">讲师简介</span>
          <div class="teacher_msg">
            <div v-if="teacherInfo" class="teacher_msg_right">
              <img v-if="teacherInfo.lecturerHead" class="teacher_phone" :src="teacherInfo.lecturerHead" alt="">
              <img v-else class="teacher_phone" src="~/assets/image/friend.jpg" alt="">
              <div class="teacher_name">{{ teacherInfo.lecturerName }}</div>
              <div class="teacher_position">{{ teacherInfo.lecturerPosition }}</div>
              <div class="info_box" v-html="teacherInfo.introduce" />
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- <bottom /> -->
  </div>
</template>
<script>
import YDetail from '@/components/course/Detail'
import YCatalog from '@/components/course/Catalog'
import YStudy from '@/components/course/Study'
import YComment from '@/components/course/Comment'
import cookies from '@/utils/cookies'
import { courseDetail, studyProgress, userCourseDetail } from '~/api/course.js'
// import Bottom from '@/components/common/Bottom'

export default {
  components: {
    // Bottom,
    YDetail,
    YCatalog,
    YStudy,
    YComment
  },
  async asyncData(context) {
    try {
      const result = {}
      const token = cookies.getInServerToken(context.req)
      if (token) {
        // 登录
        const courseData = await userCourseDetail({ courseId: context.params.id }, token)
        result.courseInfo = courseData
        if (courseData.lecturerResp) {
          result.teacherInfo = courseData.lecturerResp
        }
      } else {
        // 没登录
        const courseData = await courseDetail({ courseId: context.params.id })
        result.courseInfo = courseData
        if (courseData.lecturerResp) {
          result.teacherInfo = courseData.lecturerResp
        }
      }
      return result
    } catch (e) {
      console.error(e)
      context.error({ message: 'Data not found', statusCode: 404 })
    }
  },
  data() {
    return {
      tab: 'info',
      playPeriod: '', // 当前播放课时
      userStudy: {} // 进度
    }
  },
  head() {
    return {
      title: this.courseInfo.courseName + '-' + this.$store.state.websiteInfo.websiteName
    }
  },
  mounted() {
    if (this.courseInfo.allowStudy) {
      this.tab = 'big'
    }

    if (this.courseInfo.allowStudy) {
      // 可以播放，自动获取最后学习的课程

    }
  },
  methods: {
    videoPlay(data) {
      if (this.courseInfo.allowStudy) {
        window.scrollTo(0, 0)
        this.$refs.watchVideo.playVideo(data)
      } else {
        this.$msgBox({
          content: '购买后才可以学习',
          isShowCancelBtn: false
        })
        return false
      }
    },
    // 记录进度
    studyRecord(data) {
      this.userStudy.courseId = data.courseId
      this.userStudy.chapterId = data.chapterId
      this.userStudy.periodId = data.id
      // 播放
      this.progressInterval = setInterval(() => {
        console.log('记录学习记录' + this.userStudy.periodId)
        studyProgress(this.userStudy).then(res => {
          if (res === 'OK') {
            // 完成，暂停同步
            clearInterval(this.progressInterval)
          }
        })
      }, 3000)
    }
  }
}
</script>
<style lang="scss" rel="stylesheet/scss">
.course_detail {
  .detail_info {
    margin: 20px auto 60px;
    width: 1200px;
  }

  .layout_left {
    width: 920px;
    float: left;

    .info_body {
      margin-bottom: 30px;
    }
  }

  .course_tab {
    padding-left: 30px;
    background: #fff;
    border-radius: 8px;
    margin-bottom: 20px;

    li {
      float: left;
      height: 66px;
      line-height: 66px;
      margin-right: 80px;

      &.on {
        border-bottom: 2px solid #D51423;

        a {
          color: #D51423;
        }
      }

      a {
        color: #000;
        font-size: 14px;
        font-weight: 700;

        &:hover {
          text-decoration: none;
          color: #D51423;
        }
      }
    }
  }

  .content_info {
    padding: 30px;
    background: #fff;
    border-radius: 8px;
    min-height: 400px;

    .title {
      border-left: 3px solid #000;
      padding-left: 12px;
      font-size: 16px;
      color: #000;
      font-weight: 700;
      margin-bottom: 25px;
    }

    .introduce {
      font-size: 14px;
      line-height: 30px;
      color: #333;
      padding-left: 8px;
    }
  }

  .layout_right {
    width: 260px;
    float: right;
  }

  .teacher_info {
    background: #fff;
    border-radius: 8px;
    position: relative;
    min-height: 180px;

    .head {
      display: block;
      line-height: 60px;
      height: 60px;
      padding-left: 20px;
      font-size: 14px;
      color: #333;
      border-bottom: 1px solid rgb(228, 228, 228);
    }

    .teacher_phone {
      width: 50px;
      height: 50px;
      border-radius: 50%;
      background: rgb(228, 228, 228);
      text-align: center;
      line-height: 46px;
      font-size: 13px;
      color: #999;
      float: left;
      margin: 0px 10px 0 10px;

      img {
        width: 46px;
        height: 46px;
        border-radius: 50%;
      }
    }

    .teacher_msg_right {
      width: 235px;
      float: right;
      margin: 12px 15px 12px 12px;
      line-height: 20px;
    }

    .teacher_name {
      font-size: 18px;
      font-weight: 700;
      color: #333;
      margin: 5px 0;
    }

    .teacher_position {
      font-size: 12px;
      color: #333;
      margin-bottom: 5px;
    }
  }
}
</style>
