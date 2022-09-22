<script lang="ts" setup>
import MemberEditor from "@/modules/class/member/MemberEditor";
import { onMounted, reactive, ref } from "vue";
import { useMember } from "@/composables/member";
import { useMessageBox } from "@/composables/messageBox";
import { useRouter } from "vue-router";
import { useType } from "@/composables/type";
import type { FormInstance } from "element-plus";
import type { SignUpCheckType } from "@/modules/types/form/FormType";

const router = useRouter();
const { openMessageBox, buttonMsg, labelMsg, resultMsg } = useMessageBox();
const { error, isPending, signUp, checkField, resetField } = useMember();
const { getGenderType, genderType } = useType();

onMounted(async () => {
  await getGenderType();
});

const signUpForm = reactive<MemberEditor>(new MemberEditor());
const signUpCheckForm = reactive<SignUpCheckType>(signUpForm.checkForm);
const ruleFormRef = ref<FormInstance>();

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate((valid, fields) => {
    if (valid) {
      signUp(signUpForm);
      if (!error.value) {
        openMessageBox(resultMsg("signUp.result")).then(() => {
          router.replace({ name: "login" });
        });
      } else {
        console.log(error.value);
        openMessageBox(resultMsg("unknown"));
      }
    } else {
      openMessageBox(resultMsg("reWrite"));
    }
  });
};
</script>

<template>
  <el-form
    ref="ruleFormRef"
    :model="signUpForm"
    :rules="signUpForm.getRules()"
    label-position="top"
    label-width="120px"
    status-icon
  >
    <el-form-item :label="labelMsg('member.loginId')" prop="loginId">
      <el-input
        v-model="signUpForm.loginId"
        :disabled="signUpCheckForm.loginId"
      >
        <template #append>
          <el-button
            v-if="!signUpCheckForm.loginId"
            @click="checkField(ruleFormRef, signUpForm, 'loginId')"
            >{{ buttonMsg("member.duplication") }}
          </el-button>
          <el-button
            v-else
            color="#004A55"
            @click="resetField(signUpForm, 'loginId')"
            >{{ buttonMsg("reset") }}
          </el-button>
        </template>
      </el-input>
    </el-form-item>
    <el-form-item :label="labelMsg('member.password')" prop="password">
      <el-input v-model="signUpForm.password" show-password type="password" />
    </el-form-item>
    <el-form-item
      :label="labelMsg('member.passwordCheck')"
      prop="passwordCheck"
    >
      <el-input
        v-model="signUpForm.passwordCheck"
        show-password
        type="password"
      />
    </el-form-item>
    <el-form-item :label="labelMsg('member.name')" prop="name">
      <el-input v-model="signUpForm.name" />
    </el-form-item>
    <el-form-item :label="labelMsg('member.nickname')" prop="nickname">
      <el-input
        v-model="signUpForm.nickname"
        :disabled="signUpCheckForm.nickname"
      >
        <template #append>
          <el-button
            v-if="!signUpCheckForm.nickname"
            @click="checkField(ruleFormRef, signUpForm, 'nickname')"
            >{{ $t("validation.duplication.button") }}
          </el-button>
          <el-button
            v-else
            color="#004A55"
            @click="resetField(signUpForm, 'nickname')"
            >{{ $t("form.button.reset") }}
          </el-button>
        </template>
      </el-input>
    </el-form-item>
    <el-form-item :label="labelMsg('member.email')" prop="email">
      <el-input v-model="signUpForm.email" :disabled="signUpCheckForm.email">
        <template #append>
          <el-button
            v-if="!signUpCheckForm.email"
            @click="checkField(ruleFormRef, signUpForm, 'email')"
            >{{ buttonMsg("member.duplication") }}
          </el-button>
          <el-button
            v-else
            color="#004A55"
            @click="resetField(signUpForm, 'email')"
            >{{ buttonMsg("reset") }}
          </el-button>
        </template>
      </el-input>
    </el-form-item>
    <el-form-item :label="labelMsg('member.birth')" required>
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
    <el-form-item :label="labelMsg('member.gender.title')" prop="gender">
      <el-radio-group v-model="signUpForm.gender">
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
        color="#004A55"
        size="large"
        style="width: 100%"
        @click="submitForm(ruleFormRef)"
        >{{ buttonMsg("member.signUp") }}
      </el-button>
    </el-form-item>
  </el-form>
</template>
<style></style>
