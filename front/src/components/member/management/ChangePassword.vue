<script setup lang="ts">
import Reconfirm from "@/components/member/management/ReconfirmPassword.vue";
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import ChangePasswordEditor from "@/modules/class/member/ChangePasswordEditor";
import { reactive, ref } from "vue";
import { useMessageBox } from "@/composables/messageBox";
import { useMember } from "@/composables/member";
import { useAuth } from "@/composables/auth";
import { useRouter } from "vue-router";
import type { FormInstance } from "element-plus";

const router = useRouter();
const { logout } = useAuth();
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
      await logout().then(() => {
        router.push({ name: "home" });
      });
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
          status-icon
          label-position="top"
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
              color="#004A55"
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

<style scoped lang="scss">
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
