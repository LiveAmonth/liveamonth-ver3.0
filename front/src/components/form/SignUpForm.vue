<script lang="ts" setup>
import { onMounted, reactive, ref } from "vue";
import type { FormInstance, FormRules } from "element-plus";
import type {
  SignUpType,
  SignUpCheckType,
} from "@/modules/types/form/FormType";
import { useFormValidate } from "@/composables/formValidate";
import { useMember } from "@/composables/member";
import { useMemberStore } from "@/stores/member";
import { useI18n } from "vue-i18n";
import { useMessageBox } from "@/composables/messageBox";

const store = useMemberStore();
const { t } = useI18n();
const { openMessageBox, openConfirmMessageBox } = useMessageBox();
const {
  isPending,
  submitForm,
  validateRequire,
  validateSelection,
  validatePattern,
  validateRange,
  validatePassword,
  validateBirth,
  duplicateCheck,
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

const signUpCheckForm = reactive<SignUpCheckType>({
  loginId: false,
  nickname: false,
  email: false,
  duplicationCheck: false,
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

const checkField = async (formEl: FormInstance, field: string) => {
  const value = signUpForm[field];
  if (value.length === 0) {
    await openMessageBox(
      t("validation.require.text", { field: t(`member.${field}`) })
    );
  } else if (!(await isValidate(formEl, field))) {
    await openMessageBox(t(`validation.pattern.${field}`));
  } else {
    await duplicateCheck(field, value);
    if (!store.isAvailable) {
      await openMessageBox(
        t("validation.duplication.duplicated", {
          value: value,
          field: t(`member.${field}`),
        })
      );
      signUpForm[field] = "";
    } else {
      await openConfirmMessageBox("중복 확인", "해당 @@@를 사용하시겠습니까?")
        .then(() => {
          signUpCheckForm[field] = true;
        })
        .catch(() => {
          signUpForm[field] = "";
        });
    }
    signUpCheckForm.duplicationCheck = false;
  }
};
const resetField = async (field: string) => {
  signUpCheckForm[field] = !signUpCheckForm[field];
  signUpForm[field] = "";
};
const isValidate = async (
  formEl: FormInstance,
  field: string
): Promise<boolean> => {
  return await formEl
    .validateField(field)
    .then(() => {
      return true;
    })
    .catch(() => {
      return false;
    });
};
</script>

<template>
  <el-form
    ref="ruleFormRef"
    :model="signUpForm"
    :rules="rules"
    label-position="top"
    label-width="120px"
    status-icon
  >
    <el-form-item :label="$t('member.loginId')" prop="loginId">
      <el-input
        v-model="signUpForm.loginId"
        :disabled="signUpCheckForm.loginId"
      >
        <template #append>
          <el-button
            v-if="!signUpCheckForm.loginId"
            @click="checkField(ruleFormRef, 'loginId')"
            >{{ $t("validation.duplication.button") }}
          </el-button>
          <el-button v-else color="#004A55" @click="resetField('loginId')"
            >{{ $t("validation.reset") }}
          </el-button>
        </template>
      </el-input>
    </el-form-item>
    <el-form-item :label="$t('member.password')" prop="password">
      <el-input v-model="signUpForm.password" show-password type="password" />
    </el-form-item>
    <el-form-item :label="$t('member.passwordCheck')" prop="passwordCheck">
      <el-input
        v-model="signUpForm.passwordCheck"
        show-password
        type="password"
      />
    </el-form-item>
    <el-form-item :label="$t('member.name')" prop="name">
      <el-input v-model="signUpForm.name" />
    </el-form-item>
    <el-form-item :label="$t('member.nickname')" prop="nickname">
      <el-input
        v-model="signUpForm.nickname"
        :disabled="signUpCheckForm.nickname"
      >
        <template #append>
          <el-button
            v-if="!signUpCheckForm.nickname"
            @click="checkField(ruleFormRef, 'nickname')"
            >{{ $t("validation.duplication.button") }}
          </el-button>
          <el-button v-else color="#004A55" @click="resetField('nickname')"
            >{{ $t("validation.reset") }}
          </el-button>
        </template>
      </el-input>
    </el-form-item>
    <el-form-item :label="$t('member.email')" prop="email">
      <el-input v-model="signUpForm.email" :disabled="signUpCheckForm.email">
        <template #append>
          <el-button
            v-if="!signUpCheckForm.email"
            @click="checkField(ruleFormRef, 'email')"
            >{{ $t("validation.duplication.button") }}
          </el-button>
          <el-button v-else color="#004A55" @click="resetField('email')"
            >{{ $t("validation.reset") }}
          </el-button>
        </template>
      </el-input>
    </el-form-item>
    <el-form-item :label="$t('member.birth')" required>
      <el-form-item prop="birth">
        <el-date-picker
          v-model="signUpForm.birth"
          label="Pick a date"
          placeholder="Pick a date"
          style="width: 100%"
          type="date"
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
<style></style>
