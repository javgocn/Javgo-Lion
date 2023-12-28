<template>
  <el-dialog :model-value="visible" :append-to-body="true" :title="formModel.data.chapterId ? '节修改' : '节添加'"
             :width="600" center @close="cloneDialog">
    <el-form ref="ruleForm" :model="formModel.data" :rules="formModel.rules" class="demo-ruleForm" label-width="80px"
             @submit.prevent>
      <el-form-item class="form-group" label="节名称" prop="periodName">
        <el-input v-model="formModel.data.periodName" maxlength="100" show-word-limit></el-input>
      </el-form-item>
      <el-input v-model="formModel.data.videoName" type="hidden" style="width: 160px; margin-right: 20px;"></el-input>
      <el-input v-model="formModel.data.videoLength" type="hidden" style="width: 160px; margin-right: 20px;"></el-input>
      <el-form-item class="form-group" label="视频" prop="resourceName">
        <el-input v-model="formModel.data.resourceName" style="width: 140px; margin-right: 20px;"></el-input>
        <el-button plain type="primary" @click="resourceSelect" style="margin-right: 10px">搜索</el-button>
        <el-upload
          class="upload-demo"
          name="docFile"
          :headers="headers"
          action="/gateway/system/admin/upload/video"
          :before-upload="handleBeforeUpload"
          :on-success="uploadSuccess"
          :show-file-list=false
          multiple
          :limit="5">
          <el-button plain type="primary"  :loading="uploadLoading">上传</el-button>
        </el-upload>
      </el-form-item>
      <el-form-item class="form-group" label="课件" prop="docFileName">
        <el-input v-model="formModel.data.docFileName"  style="width: 140px; margin-right: 20px;"></el-input>
        <el-input v-model="formModel.data.docFileUrl" type="hidden" style="width: 140px; margin-right: 20px;"></el-input>
        <el-upload
          class="upload-demo"
          name="docFile"
          :headers="headers"
          action="/gateway/system/admin/upload/doc"
          :before-upload="handleBeforeUploadDoc"
          :on-success="uploadSuccessDoc"
          :show-file-list=false
          multiple
          :limit="5">
          <el-button plain type="primary"  :loading="docUploadLoading">课件上传</el-button>
        </el-upload>
      </el-form-item>
      <el-form-item class="form-group" label="收费" prop="isFree">
        <el-radio-group v-model="formModel.data.isFree">
          <template v-for="item in freeEnums" :key="item.code">
            <el-radio :label="item.code">{{ item.desc }}</el-radio>
          </template>
        </el-radio-group>
      </el-form-item>
      <el-form-item class="form-group" label="排序" prop="sort">
        <el-input-number v-model="formModel.data.sort"/>
      </el-form-item>
    </el-form>
    <template #footer>
      <span class="dialog-footer">
        <el-button @click="cloneDialog()">取消</el-button>
        <el-button type="primary" @click="onSubmit()">确定</el-button>
      </span>
    </template>
    <select-resource v-if="resource.visible" :visible="resource.visible" :info="resource.info"
                     @close="resourceCallback"/>
  </el-dialog>
</template>

<script>
import {ElMessage} from 'element-plus';
import {defineComponent, onMounted, reactive, ref, toRefs, watch} from 'vue';
import {useStore} from 'vuex';
import {courseChapterPeriodEdit, courseChapterPeriodSave} from '@/api/course.js';
import editor from '@/components/Wangeditor/index.vue';
import upload from '@/components/Upload/image.vue';
import SelectResource from '@/components/Selects/SelectResource.vue';
import {getToken} from '@/utils/auth'

export default defineComponent({
  components: {
    editor, upload, SelectResource
  },
  props: {
    modelValue: {
      type: Boolean,
      default: () => {
        return false;
      }
    },
    form: {
      type: Object,
      default: () => {
        return {};
      }
    }
  },
  emits: ['update:modelValue', 'updateTable'],
  setup(props, {emit}) {
    const visible = ref(false);
    const ruleForm = ref(null);
    const loading = ref(false);
    const state = reactive({
      freeEnums: {},
      headers: {},
      uploadType: 0, //0-选择 1-上传
      uploadLoading: false,
      docUploadLoading: false
    });
    const store = useStore();
    onMounted(() => {
      store.dispatch('GetOpts', {enumName: 'FreeEnum'}).then((res) => {
        state.freeEnums = res;
      });
      state.headers={'token':getToken()}
    });
    let resource = reactive({
      visible: false,
      info: {}
    })

    let formModel = reactive({
      data: {},
      rules: {
        periodName: [{required: true, message: '不能为空', trigger: 'blur'}]
      }
    });

    let {modelValue, form} = toRefs(props);
    if (modelValue.value) {
      visible.value = modelValue.value;
    }

    // 弹窗是否要打开监控
    watch(modelValue, async (val) => {
      visible.value = val;
    });
    // form 数据监控
    watch(form, async (val) => {
      formModel.data = {
        ...val
      };
    });

    const resetForm = () => {
      ruleForm['value'].resetFields();
      formModel.data = {};
    };

    const cloneDialog = () => {
      visible.value = false;
      emit('update:modelValue', false);
    };

    const onSubmit = () => {
      if (loading.value === true) {
        ElMessage({type: 'warning', message: '正在保存...'});
        return;
      }
      ruleForm['value'].validate(async (valid) => {
        if (valid) {
          loading.value = true;
          let d = null;
          const data = {
            ...formModel.data
          };
          if (data.chapterId) {
            if(state.uploadType == 1){
              data.resourceId = 0
            }
            d = await courseChapterPeriodEdit(data);
          } else {
            data.chapterId = data.id
            data.id = ''
            data.periodViewRespList = {}
            d = await courseChapterPeriodSave(data);
          }
          if (d) {
            ElMessage({type: 'success', message: data.id ? '修改成功' : '保存成功'});
            emit('updateTable', d);
            cloneDialog();
          }
        }
        loading.value = false;
      });
    };

    const resourceSelect = () => {
      resource.visible = true;
    }

    const resourceCallback = (info) => {
      resource.visible = false
      console.log(info)
      if (info != null) {
        formModel.data.resourceName = info.resourceName;
        formModel.data.resourceId = info.resourceId;
        state.uploadType = 0
      }
    }

    const handleBeforeUpload = (file) =>{
      const uploadLimit = 1024
      const uploadTypes = ['mp4','flv']
      const filetype = file.name.replace(/.+\./, '')
      const isRightSize = (file.size || 0) / 1024 / 1024 < uploadLimit
      if (!isRightSize) {
        ElMessage({type: 'warning', message: '文件大小超过 ' + uploadLimit + 'MB'});
        return false
      }

      if (uploadTypes.indexOf(filetype.toLowerCase()) === -1) {
        ElMessage({type: 'warning', message: '请上传后缀名为mp4、flv的视频'});
        return false
      }
      state.uploadLoading  =true
      return true
    }

    const handleBeforeUploadDoc = (file) =>{
      console.log(file)
      state.docUploadLoading  =true
      return true
    }

    const uploadSuccessDoc = (res,file,fileList) => {
      formModel.data.docFileName = file.name
      formModel.data.docFileUrl = res.data
      state.docUploadLoading  = false
    }

    const uploadSuccess = (res,file,fileList) => {
      console.log(res)
      console.log(file)
      console.log(fileList)
      var url = URL.createObjectURL(file.raw);
      //经测试，发现audio也可获取视频的时长
      var audioElement = new Audio(url);
      let duration;
      audioElement.addEventListener("loadedmetadata", function (_event) {
        duration = audioElement.duration;
        console.log('时长：'+duration);
        console.log(typeof duration);
        formModel.data.videoLength = duration
      });
      formModel.data.resourceName = file.name
      formModel.data.videoUrl = res.data
      formModel.data.videoName = file.name
      state.uploadType = 1
      state.uploadLoading  = false
    }


    return {
      ...toRefs(state),
      visible,
      loading,
      formModel,
      ruleForm,
      cloneDialog,
      onSubmit,
      resource,
      resourceSelect,
      resourceCallback,
      handleBeforeUpload,
      uploadSuccess,
      handleBeforeUploadDoc,
      uploadSuccessDoc
    };
  }
});
</script>


