<script lang="ts" setup>
import { reactive, ref } from "vue";
import { useFormValidate } from "@/composables/formValidate";
import type { FormInstance, FormRules } from "element-plus/es";
import type { FindIdType } from "@/modules/types/form/FormType";
import { useRouter } from "vue-router";
import { useMessageBox } from "@/composables/messageBox";
import { useMember } from "@/composables/member";
import { useI18n } from "vue-i18n";
import { useMemberStore } from "@/stores/member";

const store = useMemberStore();
const { t } = useI18n();
const { openMessageBox } = useMessageBox();
const { error, isPending, findId } = useMember();
const { validateRequire } = useFormValidate();

const emit = defineEmits(["find-id"]);
const ruleFormRef = ref<FormInstance>();
const rules = reactive<FormRules>({
  name: [validateRequire("member.name")],
  email: [validateRequire("member.email")],
});

const findIdForm = reactive<FindIdType>({
  name: "",
  email: "",
});

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate(async (valid) => {
    if (valid) {
      await findId(findIdForm);
      if (error.value) {
        await openMessageBox(t("form.message.noMember"));
        for (const key in findIdForm) {
          findIdForm[key] = "";
        }
      } else if (store.foundId) {
        emit("find-id");
      }
    } else {
      await openMessageBox(t("form.message.reWrite"));
    }
  });
};
</script>

<template>
  <el-form
    ref="ruleFormRef"
    :model="findIdForm"
    :rules="rules"
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
        @click="submitForm(ruleFormRef)"
        >{{ $t("member.findId") }}
      </el-button>
    </el-form-item>
  </el-form>
</template>
