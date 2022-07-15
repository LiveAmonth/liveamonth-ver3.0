<script lang="ts" setup>
import { onMounted, reactive, ref } from "vue";
import type { FormInstance, FormRules } from "element-plus";
import type { SignUpType } from "@/modules/types/form/FormType";
import { useFormValidate } from "@/composables/formValidate";
import { useMember } from "@/composables/member";
import { useMemberStore } from "@/stores/member";

const store = useMemberStore();

const {
  submitForm,
  validateRequire,
  validateSelection,
  validatePattern,
  validateRange,
  validatePassword,
  validateBirth,
} = useFormValidate();
const { getGenderType } = useMember();

onMounted(async () => {
  await getGenderType();
});

const signUpForm = reactive<SignUpType>({
  loginId: "",
  password: "",
  passwordCheck: "",
  name: "",
  nickname: "",
  email: "",
  birth: "",
  gender: "",
});

const ruleFormRef = ref<FormInstance>();

const rules = reactive<FormRules>({
  loginId: [
    validateRequire("member.loginId"),
    validatePattern("[a-zA-Z0-9]{3,20}", "validation.pattern.loginId"),
  ],
  password: [
    validateRequire("member.password"),
    validatePattern(
      "(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@!%*#?&])[A-Za-z\\d$@!%*#?&]{8,20}",
      "validation.pattern.password"
    ),
  ],
  passwordCheck: [
    validateRequire("member.passwordCheck"),
    validatePassword(signUpForm),
  ],
  name: [
    validateRequire("member.name"),
    validatePattern("[a-zA-Z가-힣]{2,20}", "validation.pattern.name"),
  ],
  nickname: [
    validateRequire("member.nickname"),
    validateRange("member.nickname", 1, 20),
  ],
  email: [
    validateRequire("member.email"),
    validatePattern(
      "[a-z0-9A-Z._-]*@[a-z0-9A-Z]*.[a-zA-Z.]*",
      "validation.pattern.email"
    ),
  ],
  birth: [validateSelection("member.birth"), validateBirth(signUpForm)],
  gender: [validateSelection("member.gender.title")],
});
</script>

<template>
  <el-form
    ref="ruleFormRef"
    label-width="120px"
    :model="signUpForm"
    :rules="rules"
    status-icon
    label-position="top"
  >
    <el-form-item :label="$t('member.loginId')" prop="loginId">
      <el-input v-model="signUpForm.loginId" />
    </el-form-item>
    <el-form-item :label="$t('member.password')" prop="password">
      <el-input v-model="signUpForm.password" type="password" show-password />
    </el-form-item>
    <el-form-item :label="$t('member.passwordCheck')" prop="passwordCheck">
      <el-input
        v-model="signUpForm.passwordCheck"
        type="password"
        show-password
      />
    </el-form-item>
    <el-form-item :label="$t('member.name')" prop="name">
      <el-input v-model="signUpForm.name" />
    </el-form-item>
    <el-form-item :label="$t('member.nickname')" prop="nickname">
      <el-input v-model="signUpForm.nickname" />
    </el-form-item>
    <el-form-item :label="$t('member.email')" prop="email">
      <el-input v-model="signUpForm.email" />
    </el-form-item>
    <el-form-item :label="$t('member.birth')" required>
      <el-form-item prop="birth">
        <el-date-picker
          v-model="signUpForm.birth"
          type="date"
          label="Pick a date"
          placeholder="Pick a date"
          style="width: 100%"
        />
      </el-form-item>
    </el-form-item>
    <el-form-item :label="$t('member.gender.title')" prop="gender">
      <el-radio-group v-model="signUpForm.gender">
        <template v-for="type in store.genderType" :key="type.code">
          <el-radio :label="$t(`member.gender.${type.code}`)" />
        </template>
      </el-radio-group>
    </el-form-item>
    <el-form-item>
      <el-button
        :loading="false"
        color="#004A55"
        size="large"
        style="width: 100%"
        @click="submitForm(ruleFormRef)"
        >{{ $t("member.signUp") }}
      </el-button>
    </el-form-item>
  </el-form>
</template>
