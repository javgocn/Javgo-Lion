<template>
  <div>
    <div id="app">
      <nuxt />
    </div>
    <bottom />
  </div>
</template>

<script>
import Bottom from '@/components/common/Bottom'
export default {
  components: { Bottom },
  head() {
    return {
      link: [
        { hid: 'icon', rel: 'icon', type: 'image/x-icon', href: this.$store.state.websiteInfo.websiteIcon }
      ]
    }
  },
  created() {
    if (process.client) {
      this.$store.commit('INIT_WEB')
      if (this.$store.state.tokenInfo) {
        if (!this.$store.state.userInfo) {
          this.$store.dispatch('GET_USERINFO')
        }
      }
    }
    var _hmt = _hmt || []
  },
  mounted() {
    var sc = document.createElement('script')
    sc.src = 'https://player.polyv.net/script/polyvplayer.min.js'
    var sList = document.getElementsByTagName('script')
    var s = sList[sList.length - 1]
    s.parentNode.insertBefore(sc, s)
    var hm = document.createElement('script')
    hm.src = 'https://hm.baidu.com/hm.js?d5b2309e95c3b686b3c8ff6f1abbaef1'
    document.body.insertBefore(hm, s)
  }
}

</script>
<style>
html {
  font-family: 'Source Sans Pro', -apple-system, BlinkMacSystemFont, 'Segoe UI',
  Roboto, 'Helvetica Neue', Arial, sans-serif;
  font-size: 16px;
  word-spacing: 1px;
  -ms-text-size-adjust: 100%;
  -webkit-text-size-adjust: 100%;
  -moz-osx-font-smoothing: grayscale;
  -webkit-font-smoothing: antialiased;
  box-sizing: border-box;
}
#app {
  min-height: calc(100vh - 80px );
}
*,
*:before,
*:after {
  box-sizing: border-box;
  margin: 0;
}

.button--green {
  display: inline-block;
  border-radius: 4px;
  border: 1px solid #3b8070;
  color: #3b8070;
  text-decoration: none;
  padding: 10px 30px;
}

.button--green:hover {
  color: #fff;
  background-color: #3b8070;
}

.button--grey {
  display: inline-block;
  border-radius: 4px;
  border: 1px solid #35495e;
  color: #35495e;
  text-decoration: none;
  padding: 10px 30px;
  margin-left: 15px;
}

.button--grey:hover {
  color: #fff;
  background-color: #35495e;
}
</style>
