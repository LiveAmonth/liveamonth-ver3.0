<script setup lang="ts">
import TitleSlot from "@/components/common/TitleSlot.vue";
import InquiryEditor from "@/modules/class/member/InquiryEditor";
import { reactive, ref } from "vue";
import { useMember } from "@/composables/member";
import { useInquiry } from "@/composables/inquiry";
import { useMessageBox } from "@/composables/messageBox";
import type { FormInstance } from "element-plus";

const { memberProfile } = useMember();
const { isPending, writeInquiry } = useInquiry();
const { titleMsg, labelMsg, buttonMsg, resultMsg, openMessageBox } =
  useMessageBox();

const form = reactive<InquiryEditor>(
  new InquiryEditor(memberProfile.value.nickname)
);
const ruleFormRef = ref<FormInstance>();

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate(async (valid) => {
    if (valid) {
      await writeInquiry(form).then(() => {
        openMessageBox(resultMsg("inquiry.success"));
      });
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
      <el-form-item :label="labelMsg('inquiry.title')" prop="title">
        <el-input v-model="form.title">
          <template #append>
            <el-select
              v-model="form.category"
              placeholder="Select"
              style="width: 115px"
            >
              <el-option label="Restaurant" value="1" />
              <el-option label="Order No." value="2" />
              <el-option label="Tel" value="3" />
            </el-select>
          </template>
        </el-input>
      </el-form-item>
      <el-form-item :label="labelMsg('inquiry.content')" prop="content">
        <el-input
          type="textarea"
          :autosize="{ minRows: 6 }"
          v-model="form.content"
        />
      </el-form-item>
      <el-form-item>
        <el-button
          :loading="isPending"
          color="#004A55"
          size="large"
          style="width: 100%"
          @click="submitForm(ruleFormRef)"
        >
          {{ buttonMsg("write") }}
        </el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<style scoped lang="scss"></style>
