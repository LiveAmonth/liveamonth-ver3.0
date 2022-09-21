<script setup lang="ts">
import TitleSlot from "@/components/common/TitleSlot.vue";
import ProfileEditor from "@/modules/class/member/ProfileEditor";
import { useI18n } from "vue-i18n";
import { useMessageBox } from "@/composables/messageBox";
import { useMember } from "@/composables/member";
import { useType } from "@/composables/type";
import { reactive, ref } from "vue";
import type { FormInstance } from "element-plus";
import type { SignUpCheckType } from "@/modules/types/form/FormType";

const { t } = useI18n();
const { openMessageBox } = useMessageBox();
const {
  isPending,
  memberProfile,
  getMember,
  editProfile,
  checkField,
  resetField,
} = useMember();
const { genderType } = useType();

const editProfileForm = reactive<ProfileEditor>(
  new ProfileEditor(memberProfile.value)
);
const signUpCheckForm = reactive<SignUpCheckType>(editProfileForm.checkForm);
const ruleFormRef = ref<FormInstance>();

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate(async (valid, fields) => {
    if (valid) {
      await editProfile(editProfileForm).then(async () => {
        await getMember();
        await openMessageBox(t("form.message.edit"));
      });
    } else {
      await openMessageBox(t("form.message.reWrite"));
    }
  });
};
</script>

<template>
  <div class="mt-3">
    <TitleSlot> 프로필 편집</TitleSlot>
    <el-row class="d-flex justify-content-center">
      <el-col>
        <el-form
          ref="ruleFormRef"
          :model="editProfileForm"
          :rules="editProfileForm.getRules()"
          label-position="top"
          label-width="120px"
          status-icon
        >
          <el-form-item :label="$t('member.loginId')" prop="loginId">
            <el-input v-model="editProfileForm.loginId" disabled />
          </el-form-item>
          <el-form-item :label="$t('member.name')" prop="name">
            <el-input v-model="editProfileForm.name" disabled />
          </el-form-item>
          <el-form-item :label="$t('member.nickname')" prop="nickname">
            <el-input
              v-model="editProfileForm.nickname"
              :disabled="signUpCheckForm.nickname"
            >
              <template #append>
                <el-button
                  v-if="!signUpCheckForm.nickname"
                  @click="checkField(ruleFormRef, editProfileForm, 'nickname')"
                  >{{ $t("validation.duplication.button") }}
                </el-button>
                <el-button
                  v-else
                  color="#004A55"
                  @click="resetField(editProfileForm, 'nickname')"
                  >{{ $t("form.button.edit") }}
                </el-button>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item :label="$t('member.email')" prop="email">
            <el-input
              v-model="editProfileForm.email"
              :disabled="signUpCheckForm.email"
            >
              <template #append>
                <el-button
                  v-if="!signUpCheckForm.email"
                  @click="checkField(ruleFormRef, editProfileForm, 'email')"
                  >{{ $t("validation.duplication.button") }}
                </el-button>
                <el-button
                  v-else
                  color="#004A55"
                  @click="resetField(editProfileForm, 'email')"
                  >{{ $t("form.button.edit") }}
                </el-button>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item :label="$t('member.birth')" prop="birth">
            <el-date-picker
              v-model="editProfileForm.birth"
              label="Pick a date"
              placeholder="Pick a date"
              style="width: 100%"
              type="date"
              disabled
            />
          </el-form-item>
          <el-form-item :label="$t('member.gender.title')" prop="gender">
            <el-radio-group v-model="editProfileForm.gender" disabled>
              <template v-for="type in genderType" :key="type.code">
                <el-radio :label="type.code">
                  {{ $t(`member.gender.${type.code}`) }}
                </el-radio>
              </template>
            </el-radio-group>
          </el-form-item>
          <el-form-item>
            <el-button
              :loading="isPending"
              color="#004A55"
              size="large"
              style="width: 100%"
              @click="submitForm(ruleFormRef)"
              >{{ $t("member.edit") }}
            </el-button>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped lang="scss"></style>
