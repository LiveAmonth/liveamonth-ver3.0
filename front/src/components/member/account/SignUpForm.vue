<script lang="ts" setup>
import { onMounted, reactive, ref } from "vue";
import { useMember } from "@/composables/member/member";
import { useMessageBox } from "@/composables/common/messageBox";
import { useRouter } from "vue-router";
import { useCategory } from "@/composables/common/category";
import { MemberCreate } from "@/modules/types/member/MemberTypes";
import type { FormInstance } from "element-plus";
import type { SignUpCheckType } from "@/modules/types/member/MemberTypes";

const router = useRouter();
const { openMessageBox, buttonMsg, labelMsg, resultMsg, categoryMsg } =
  useMessageBox();
const { error, isPending, signUp, checkField, resetField } = useMember();
const { genderType, hasGenderType, getGenderType } = useCategory();

onMounted(async () => {
  await getGenderType();
});

const form = reactive(new MemberCreate()) as MemberCreate;
const checkForm = reactive(form.checkForm) as SignUpCheckType;
const ruleFormRef = ref<FormInstance>();

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate(async (valid) => {
    if (valid) {
      await signUp(form);
      if (!error.value) {
        openMessageBox(resultMsg("signup")).then(() => {
          router.replace({ name: "login" });
        });
      } else {
        await openMessageBox(resultMsg("unknown"));
      }
    } else {
      await openMessageBox(resultMsg("reWrite"));
    }
  });
};
</script>

<template>
  <el-form
    ref="ruleFormRef"
    :model="form"
    :rules="form.getRules()"
    label-position="left"
    label-width="120px"
    status-icon
  >
    <el-form-item :label="labelMsg('member.loginId')" prop="loginId">
      <el-input
        v-model="form.loginId"
        :disabled="checkForm.loginId"
        @change="checkField(ruleFormRef, form, 'loginId')"
      >
        <template v-if="checkForm.loginId" #append>
          <el-button color="#0f6778" @click="resetField(form, 'loginId')">
            {{ buttonMsg("reset") }}
          </el-button>
        </template>
      </el-input>
    </el-form-item>
    <el-form-item :label="labelMsg('member.password')" prop="password">
      <el-input v-model="form.password" show-password type="password" />
    </el-form-item>
    <el-form-item
      :label="labelMsg('member.passwordCheck')"
      prop="passwordCheck"
    >
      <el-input v-model="form.passwordCheck" show-password type="password" />
    </el-form-item>
    <el-form-item :label="labelMsg('member.name')" prop="name">
      <el-input v-model="form.name" />
    </el-form-item>
    <el-form-item :label="labelMsg('member.nickname')" prop="nickname">
      <el-input
        v-model="form.nickname"
        :disabled="checkForm.nickname"
        @change="checkField(ruleFormRef, form, 'nickname')"
      >
        <template v-if="checkForm.nickname" #append>
          <el-button color="#0f6778" @click="resetField(form, 'nickname')">
            {{ buttonMsg("reset") }}
          </el-button>
        </template>
      </el-input>
    </el-form-item>
    <el-form-item :label="labelMsg('member.email')" prop="email">
      <el-input
        v-model="form.email"
        :disabled="checkForm.email"
        @change="checkField(ruleFormRef, form, 'email')"
      >
        <template v-if="checkForm.email" #append>
          <el-button color="#0f6778" @click="resetField(form, 'email')">
            {{ buttonMsg("reset") }}
          </el-button>
        </template>
      </el-input>
    </el-form-item>
    <el-form-item :label="labelMsg('member.birth')" prop="birth">
      <el-date-picker v-model="form.birth" style="width: 100%" type="date" />
    </el-form-item>
    <el-form-item :label="labelMsg('member.gender.title')" prop="gender">
      <el-radio-group v-if="hasGenderType" v-model="form.gender">
        <template v-for="type in genderType" :key="type.code">
          <el-radio :label="type.code">
            {{ categoryMsg("member.gender", type.code.toLowerCase()) }}
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
        {{ buttonMsg("member.signUp") }}
      </el-button>
    </el-form-item>
  </el-form>
</template>
<style></style>
