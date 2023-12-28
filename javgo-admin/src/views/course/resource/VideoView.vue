<template>
  <el-dialog :title="data.resourceName" :model-value="visible" :append-to-body="true"  :width="1000" center @close="cloneDialog" class="my-cust-class">
    <video v-if="data.resourceType ===1 " controls :src=data.resourceUrl style="width:100%" autoplay="autoplay" id="videoPlayDom">
      <source src="movie.ogg" type="video/ogg"> -->
      您的浏览器不支持 HTML5 video 标签。
    </video>
    <el-image v-if="data.resourceType ===4 " style="width: 100%" :src="data.resourceUrl"></el-image>
    <span v-else>文件不支持预览，请下载</span>
  </el-dialog>
</template>

<script>
import {defineComponent, ref, toRefs, watch} from 'vue';

export default defineComponent({
  props: {
    modelValue: {
      type: Boolean,
      default: () => {
        return false;
      }
    },
    data: {
      type: Object,
      default: () => {
        return {};
      }
    }
  },
  emits: ['update:modelValue', 'updateTable'],
  setup(props, {emit}) {

    const visible = ref(false);
    let videoData = ref(null);

    let {modelValue, data} = toRefs(props);
    if (modelValue.value) {
      visible.value = modelValue.value;
    }

    // 弹窗是否要打开监控
    watch(modelValue, async(val) => {
      console.log('open')
      visible.value = val;
      if(val){
        console.log('play')
        console.log(videoData.resourceUrl)
      }
    });
    // form 数据监控
    watch(data, async(val) => {
      videoData = {
        ...val
      };
    });

    const cloneDialog = () => {
      data.value.resourceUrl = ''
      visible.value = false;
      emit('update:modelValue', false);
      console.log('close')
    };

    return {
      visible,
      videoData,
      cloneDialog
    }
  }
});
</script>

<style lang="scss">
.my-cust-class{
  border-radius: 12px;
  .el-dialog__body{
    padding: 0 ;
  }
}
</style>


