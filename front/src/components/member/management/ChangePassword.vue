<script lang="ts" setup>
import Reconfirm from "@/components/member/management/ReconfirmPassword.vue";
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import ChangePasswordEditor from "@/modules/class/member/ChangePasswordEditor";
import { reactive, ref } from "vue";
import { useMessageBox } from "@/composables/common/messageBox";
import { useMember } from "@/composables/member/member";
import { useAuth } from "@/composables/member/auth";
import type { FormInstance } from "element-plus";

const { logoutBtn } = useAuth();
const { isPending, changePassword } = useMember();
const { openMessageBox, labelMsg, buttonMsg, titleMsg, resultMsg } =
  useMessageBox();
const form = reactive<ChangePasswordEditor>(new ChangePasswordEditor());
const ruleFormRef = ref<FormInstance>();
const reChecked = ref(false);

const submitForm = async (formEl: FormInstance | undefined) => {
  if (!formEl) return;
  await formEl.validate(async (valid) => {
    if (valid) {
      await changePassword(form);
      await openMessageBox(resultMsg("changePassword.success"));
      await logoutBtn();
    } else {
      await openMessageBox(resultMsg("reWrite"));
    }
  });
};
</script>

<template>
  <Reconfirm v-if="!reChecked" v-model:re-checked="reChecked" />
  <div v-else class="d-flex justify-content-center mt-5">
    <div class="container">
      <div class="mt-3">
        <SmallTitleSlot>{{ titleMsg("member.changePassword") }}</SmallTitleSlot>
        <el-form
          ref="ruleFormRef"
          :model="form"
          :rules="form.getRules()"
          label-position="top"
          status-icon
        >
          <p class="info mb-4">
            {{ resultMsg("changePassword.announce") }}
          </p>
          <el-form-item :label="labelMsg('member.password')" prop="password">
            <el-input v-model="form.password" show-password type="password" />
          </el-form-item>
          <el-form-item
            :label="labelMsg('member.passwordCheck')"
            prop="passwordCheck"
          >
            <el-input
              v-model="form.passwordCheck"
              show-password
              type="password"
            />
          </el-form-item>
          <el-form-item>
            <el-button
              :loading="isPending"
              color="#0f6778"
              size="large"
              style="width: 100%"
              @click="submitForm(ruleFormRef)"
            >
              {{ buttonMsg("member.changePassword") }}
            </el-button>
          </el-form-item>
        </el-form>
      </div>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.container {
  display: flex;
  justify-content: center;
  flex-direction: column;

  .el-form {
    width: 400px;

    .info {
      color: #6b6b6b;
    }
  }
}
</style>
