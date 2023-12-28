<template>
  <section class="vueVideo">
    <video-player
      class="video-player vjs-custom-skin"
      ref="videoPlayer"
      :playsinline="true"
      :options="playerOptions"
      @play="onPlayerPlay($event)"
      @pause="onPlayerPause($event)"
      @ended="onPlayerEnded($event)"
      @loadeddata="onPlayerLoadeddata($event)"
      @waiting="onPlayerWaiting($event)"
      @playing="onPlayerPlaying($event)"
      @timeupdate="onPlayerTimeupdate($event)"
      @canplay="onPlayerCanplay($event)"
      @canplaythrough="onPlayerCanplaythrough($event)"
      @ready="playerReadied"
      @statechanged="playerStateChanged($event)"/>
  </section>
</template>

<script>
import 'video.js/dist/video-js.css'
import 'vue-video-player/src/custom-theme.css'
import { studyProgress } from '~/api/course.js'
export default {
  props: {
    coursePeriodInfo: {}
  },
  data() {
    return {
      playerOptions: {
        playbackRates: [0.7, 1.0, 1.5, 2.0], // 播放速度
        autoplay: true, // 如果true,浏览器准备好时开始回放。
        muted: false, // 默认情况下将会消除任何音频。
        loop: false, // 导致视频一结束就重新开始。
        preload: 'auto', // 建议浏览器在<video>加载元素后是否应该开始下载视频数据。auto浏览器选择最佳行为,立即开始加载视频（如果浏览器支持）
        language: 'zh-CN',
        aspectRatio: '16:9', // 将播放器置于流畅模式，并在计算播放器的动态大小时使用该值。值应该代表一个比例 - 用冒号分隔的两个数字（例如"16:9"或"4:3"）
        fluid: true, // 当true时，Video.js player将拥有流体大小。换句话说，它将按比例缩放以适应其容器。
        sources: [
          {
            type: 'video/mp4', // 这里的种类支持很多种：基本视频格式、直播、流媒体等，具体可以参看git网址项目
            // src: JSON.parse(localStorage.getItem('live')).url, //url地址从上个页面中传入
            src: '' // url地址
          }
        ],
        poster: '', // 你的封面地址 从上个页面中传入
        // width: document.documentElement.clientWidth, //播放器宽度
        notSupportedMessage: '此视频暂无法播放，请稍后再试', // 允许覆盖Video.js无法播放媒体源时显示的默认信息。
        controlBar: {
          timeDivider: true,
          durationDisplay: true,
          remainingTimeDisplay: false,
          fullscreenToggle: true // 全屏按钮
        }
      },
      periodInfo: {},
      progressInterval: ''
    }
  },
  mounted() {
    console.log('this is current player instance object', this.myVideoPlayer)
  },
  methods: {
    changeVideo(data) {
      this.playerOptions['sources'][0]['src'] = data.videoUrl
      this.periodInfo = data
    },
    // 监听播放
    onPlayerPlay(player) {
      console.log('player play!', player)
      this.studyProgressRecord(this.periodInfo)
    },
    // 监听暂停
    onPlayerPause(player) {
      console.log('player pause!', player)
      clearInterval(this.progressInterval)
    },
    // 监听停止
    onPlayerEnded(player) {
      console.log('player ended!', player)
      clearInterval(this.progressInterval)
    },
    // 监听加载完成
    onPlayerLoadeddata(player) {
      // console.log('player Loadeddata!', player)
    },
    // 监听视频缓存等待
    onPlayerWaiting(player) {
      // console.log('player Waiting!', player)
    },
    // 监听视频暂停后播放
    onPlayerPlaying(player) {
      // console.log('player Playing!', player)
    },
    // 监听视频播放时长更新
    onPlayerTimeupdate(player) {
      // console.log('player Timeupdate!', player.currentTime())
    },
    onPlayerCanplay(player) {
      console.log('player Canplay!', player)
    },
    onPlayerCanplaythrough(player) {
      // console.log('player Canplaythrough!', player)
    },
    // 监听状态改变
    playerStateChanged(playerCurrentState) {
      // console.log('player current update state', playerCurrentState)
    },
    // 监听播放器准备就绪
    playerReadied(player) {
      // console.log('example 01: the player is readied', player)
    },
    studyProgressRecord(data) {
      console.log('记录学习进度')
      this.progressInterval = setInterval(() => {
        console.log('记录')
        studyProgress(data).then(res => {
          if (res === 'OK') {
            // 完成，暂停同步
            clearInterval(this.progressInterval)
          }
        })
      }, 30000)
    }
  }
}
</script>

<style lang="scss" scoped>
.vueVideo{
  width: 100%;
  height: 100%;
}
</style>
