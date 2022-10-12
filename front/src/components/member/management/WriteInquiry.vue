<script lang="ts" setup>
import TitleSlot from "@/components/common/TitleSlot.vue";
import { onMounted, reactive, ref } from "vue";
import { useMember } from "@/composables/member/member";
import { useInquiry } from "@/composables/member/inquiry";
import { useMessageBox } from "@/composables/common/messageBox";
import { useMyPage } from "@/composables/member/mypage";
import { useQuillEditor } from "@/composables/common/quilleditor";
import { InquiryEditor } from "@/modules/types/member/MemberTypes";
import type { FormInstance } from "element-plus";

const props = defineProps({
  isEdit: {
    type: Boolean,
    required: false,
    default: false,
  },
});
const emits = defineEmits(["goBack", "edit"]);

const { memberProfile } = useMember();
const { isPending, category, currInquiry, writeInquiry, getCategory } =
  useInquiry();
const { goManagement } = useMyPage();
const { titleMsg, labelMsg, buttonMsg, resultMsg, openMessageBox } =
  useMessageBox();
const { toolbarOptions, onEditorReady } = useQuillEditor();

const form = reactive<InquiryEditor>(
  new InquiryEditor(memberProfile.value.nickname)
);
const ruleFormRef = ref<FormInstance>();

onMounted(async () => {
  await getCategory();
  if (props.isEdit) {
    form.setForm(currInquiry.value);
  }
});

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate(async (valid) => {
    if (valid) {
      if (props.isEdit) {
        emits("edit", currInquiry.value.id, form);
      } else {
        await writeInquiry(form).then(() => {
          openMessageBox(resultMsg("inquiry.success"));
          goManagement(JSON.stringify({ category: "inquiry", menu: "answer" }));
        });
      }
    }
  });
};
</script>

<template>
  <div class="my-5">
    <TitleSlot class="my-5">
      {{ titleMsg("member.inquiry.write") }}
    </TitleSlot>
    <el-form
      ref="ruleFormRef"
      :model="form"
      :rules="form.getRules()"
      label-position="top"
      label-width="80px"
      status-icon
    >
      <el-form-item :label="labelMsg('inquiry.writer')" prop="writer">
        <el-input v-model="form.writer" disabled />
      </el-form-item>
      <el-form-item :label="labelMsg('title')" prop="title">
        <el-input v-model="form.title">
          <template #append>
            <el-select
              v-model="form.category"
              placeholder="Select"
              style="width: 120px"
            >
              <el-option
                v-for="item in category"
                :key="item.code"
                :label="item.value"
                :value="item.code"
              />
            </el-select>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item :label="labelMsg('content')" prop="content">
        <div class="quill-editor">
          <QuillEditor
            theme="snow"
            v-model:content="form.content"
            :toolbar="toolbarOptions"
            contentType="html"
            @ready="onEditorReady($event, currInquiry.content, isEdit)"
          />
        </div>
      </el-form-item>
      <el-row v-if="!isEdit">
        <el-col>
          <div class="d-flex justify-content-end">
            <el-form-item>
              <el-button
                :loading="isPending"
                color="#0f6778"
                @click="submitForm(ruleFormRef)"
              >
                {{ buttonMsg("write") }}
              </el-button>
            </el-form-item>
          </div>
        </el-col>
      </el-row>
      <el-row v-else>
        <el-col>
          <div class="d-flex justify-content-end">
            <el-form-item>
              <el-button
                :loading="isPending"
                class="me-2"
                color="#0f6778"
                @click="submitForm(ruleFormRef)"
              >
                {{ buttonMsg("edit") }}
              </el-button>
            </el-form-item>
            <el-button type="info" @click="emits('goBack')">
              {{ buttonMsg("back") }}
            </el-button>
          </div>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<style lang="scss">
.quill-editor {
  height: 550px;
  width: 700px;

  .ql-container {
    height: 450px;
  }
}
</style>
