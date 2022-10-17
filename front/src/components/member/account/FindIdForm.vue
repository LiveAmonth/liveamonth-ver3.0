<script lang="ts" setup>
import { reactive } from "vue";
import { useMessageBox } from "@/composables/common/messageBox";
import { useMember } from "@/composables/member/member";
import type { FindIdType } from "@/modules/types/member/MemberTypes";

const emit = defineEmits(["update:isFind"]);

const { error, isPending, findId } = useMember();
const { openMessageBox, buttonMsg, labelMsg, resultMsg } = useMessageBox();

const form = reactive<FindIdType>({
  name: "",
  email: "",
});

const submitForm = async () => {
  await findId(form);
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
    <el-form-item :label="labelMsg('member.name')" prop="name">
      <el-input v-model="form.name" />
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
        {{ buttonMsg("member.findId") }}
      </el-button>
    </el-form-item>
  </el-form>
</template>
