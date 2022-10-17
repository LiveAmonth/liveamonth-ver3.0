<script lang="ts" setup>
import { reactive } from "vue";
import { useMessageBox } from "@/composables/common/messageBox";
import { useMember } from "@/composables/member/member";
import type { FindPwType } from "@/modules/types/member/MemberTypes";

const emit = defineEmits(["update:isFind"]);

const { error, isPending, findPw } = useMember();
const { openMessageBox, buttonMsg, labelMsg, resultMsg } = useMessageBox();

const form = reactive<FindPwType>({
  loginId: "",
  email: "",
});

const submitForm = async () => {
  await findPw(form);
  if (error.value) {
    await openMessageBox(resultMsg("noMember"));
    for (const key in form) {
      form[key] = "";
    }
  } else {
    emit("update:isFind", true);
  }
};
</script>

<template>
  <el-form ref="ruleFormRef" :model="form" label-position="top" status-icon>
    <el-form-item :label="labelMsg('member.loginId')" prop="loginId">
      <el-input v-model="form.loginId" />
    </el-form-item>
    <el-form-item :label="labelMsg('member.email')" prop="email">
      <el-input v-model="form.email" />
    </el-form-item>
    <el-form-item>
      <el-button
        :loading="isPending"
        color="#0f6778"
        size="large"
        style="width: 100%"
        @click="submitForm"
      >
        {{ buttonMsg("member.findPw") }}
      </el-button>
    </el-form-item>
  </el-form>
</template>
