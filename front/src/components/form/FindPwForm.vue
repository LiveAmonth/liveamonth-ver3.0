<script lang="ts" setup>
import { reactive } from "vue";
import { useI18n } from "vue-i18n";
import { useMessageBox } from "@/composables/messageBox";
import { useMember } from "@/composables/member";
import type { FindPwType } from "@/modules/types/form/FormType";

const { error, isPending, foundId, findPw } = useMember();
const { openMessageBox } = useMessageBox();
const { t } = useI18n();

const emit = defineEmits(["findPw"]);

const findPwForm = reactive<FindPwType>({
  loginId: "",
  email: "",
});

const submitForm = async () => {
  await findPw(findPwForm);
  if (foundId.value) {
    emit("findPw");
  }
  if (error.value) {
    await openMessageBox(t("form.message.noMember"));
    for (const key in findPwForm) {
      findPwForm[key] = "";
    }
  }
};
</script>

<template>
  <el-form
    ref="ruleFormRef"
    :model="findPwForm"
    label-position="top"
    status-icon
  >
    <el-form-item :label="$t('member.loginId')" prop="loginId">
      <el-input v-model="findPwForm.loginId" />
    </el-form-item>
    <el-form-item :label="$t('member.email')" prop="email">
      <el-input v-model="findPwForm.email" />
    </el-form-item>
    <el-form-item>
      <el-button
        :loading="isPending"
        color="#004A55"
        size="large"
        style="width: 100%"
        @click="submitForm"
        >{{ $t("member.findPw") }}
      </el-button>
    </el-form-item>
  </el-form>
</template>
