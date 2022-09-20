<script lang="ts" setup>
import { reactive } from "vue";
import { useMessageBox } from "@/composables/messageBox";
import { useMember } from "@/composables/member";
import { useI18n } from "vue-i18n";
import type { FindIdType } from "@/modules/types/form/FormType";

const emit = defineEmits(["findId"]);

const { error, isPending, foundId, findId } = useMember();
const { openMessageBox } = useMessageBox();
const { t } = useI18n();

const findIdForm = reactive<FindIdType>({
  name: "",
  email: "",
});

const submitForm = async () => {
  await findId(findIdForm);
  if (foundId.value) {
    emit("findId");
  }
  if (error.value) {
    await openMessageBox(t("form.message.noMember"));
    for (const key in findIdForm) {
      findIdForm[key] = "";
    }
  }
};
</script>

<template>
  <el-form
    ref="ruleFormRef"
    :model="findIdForm"
    label-position="top"
    status-icon
  >
    <el-form-item :label="$t('member.name')" prop="name">
      <el-input v-model="findIdForm.name" />
    </el-form-item>
    <el-form-item :label="$t('member.email')" prop="email">
      <el-input v-model="findIdForm.email" />
    </el-form-item>
    <el-form-item>
      <el-button
        :loading="isPending"
        color="#004A55"
        size="large"
        style="width: 100%"
        @click="submitForm"
        >{{ $t("member.findId") }}
      </el-button>
    </el-form-item>
  </el-form>
</template>
