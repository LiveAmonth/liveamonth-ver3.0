<script setup lang="ts">
import Reconfirm from "@/components/member/management/ReconfirmPassword.vue";
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import { useMessageBox } from "@/composables/common/messageBox";
import { useMember } from "@/composables/member/member";
import { useCategory } from "@/composables/common/category";
import { reactive, ref } from "vue";
import { ProfileEditor } from "@/modules/types/member/MemberTypes";
import type { FormInstance } from "element-plus";
import type { SignUpCheckType } from "@/modules/types/member/MemberTypes";

const { openMessageBox, labelMsg, buttonMsg, titleMsg, resultMsg } =
  useMessageBox();
const {
  isPending,
  memberProfile,
  getMember,
  editProfile,
  checkField,
  resetField,
} = useMember();
const { genderType } = useCategory();

const form = reactive(new ProfileEditor(memberProfile.value)) as ProfileEditor;
const checkForm = reactive(form.checkForm) as SignUpCheckType;
const ruleFormRef = ref<FormInstance>();
const reChecked = ref<boolean>(false);

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate(async (valid) => {
    if (valid) {
      await editProfile(form).then(async () => {
        await getMember();
        await openMessageBox(resultMsg("edit"));
      });
    } else {
      await openMessageBox(resultMsg("reWrite"));
    }
  });
};
</script>

<template>
  <Reconfirm v-if="!reChecked" v-model:re-checked="reChecked" />
  <div v-else class="mt-3">
    <SmallTitleSlot :title="titleMsg('member.editProfile')" />
    <el-row class="d-flex justify-content-center">
      <el-col>
        <el-form
          ref="ruleFormRef"
          :model="form"
          :rules="form.getRules()"
          label-position="top"
          label-width="120px"
          status-icon
        >
          <el-form-item :label="labelMsg('member.loginId')" prop="loginId">
            <el-input v-model="form.loginId" disabled />
          </el-form-item>
          <el-form-item :label="labelMsg('member.name')" prop="name">
            <el-input v-model="form.name" disabled />
          </el-form-item>
          <el-form-item :label="labelMsg('member.nickname')" prop="nickname">
            <el-input v-model="form.nickname" :disabled="checkForm.nickname">
              <template #append>
                <el-button
                  v-if="!checkForm.nickname"
                  @click="checkField(ruleFormRef, form, 'nickname')"
                >
                  {{ buttonMsg("member.duplication") }}
                </el-button>
                <el-button
                  v-else
                  color="#0f6778"
                  @click="resetField(form, 'nickname')"
                >
                  {{ buttonMsg("modify") }}
                </el-button>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item :label="labelMsg('member.email')" prop="email">
            <el-input v-model="form.email" :disabled="checkForm.email">
              <template #append>
                <el-button
                  v-if="!checkForm.email"
                  @click="checkField(ruleFormRef, form, 'email')"
                >
                  {{ buttonMsg("member.duplication") }}
                </el-button>
                <el-button
                  v-else
                  color="#0f6778"
                  @click="resetField(form, 'email')"
                >
                  {{ buttonMsg("modify") }}
                </el-button>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item :label="labelMsg('member.birth')" prop="birth">
            <el-date-picker
              v-model="form.birth"
              label="Pick a date"
              placeholder="Pick a date"
              style="width: 100%"
              type="date"
              disabled
            />
          </el-form-item>
          <el-form-item :label="labelMsg('member.gender.title')" prop="gender">
            <el-radio-group v-model="form.gender" disabled>
              <template v-for="type in genderType" :key="type.code">
                <el-radio :label="type.code">
                  {{ labelMsg(`member.gender.${type.code}`) }}
                </el-radio>
              </template>
            </el-radio-group>
          </el-form-item>
          <el-form-item>
            <el-button
              :loading="isPending"
              color="#0f6778"
              size="large"
              style="width: 100%"
              @click="submitForm(ruleFormRef)"
            >
              {{ buttonMsg("member.edit") }}
            </el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped lang="scss"></style>
