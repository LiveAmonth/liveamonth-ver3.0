<script lang="ts" setup>
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import { reactive } from "vue";
import { useMessageBox } from "@/composables/common/messageBox";
import { useMember } from "@/composables/member/member";
import { useFormValidate } from "@/composables/common/formValidate";
import type { ReconfirmType } from "@/modules/types/member/MemberTypes";

const emits = defineEmits(["update:reChecked"]);

const { isPending, reconfirm } = useMember();
const { isAvailable } = useFormValidate();
const {
  openMessage,
  openMessageBox,
  buttonMsg,
  labelMsg,
  resultMsg,
  titleMsg,
} = useMessageBox();

const reCheckForm = reactive<ReconfirmType>({
  password: "",
});

const submitForm = async () => {
  await reconfirm(reCheckForm).then(() => {
    if (isAvailable.value) {
      openMessage(resultMsg("reconfirm.success"));
      emits("update:reChecked", true);
    } else {
      openMessageBox(resultMsg("wrongPw"));
      reCheckForm.password = "";
    }
  });
};
</script>

<template>
  <div class="d-flex justify-content-center mt-5">
    <div class="container">
      <SmallTitleSlot class="my-3" :title="titleMsg('member.reconfirm')" />
      <el-form
        ref="ruleFormRef"
        :model="reCheckForm"
        label-position="top"
        status-icon
      >
        <p class="info mb-5">
          {{ resultMsg("reconfirm.announce") }}
        </p>
        <el-form-item :label="labelMsg('member.password')" prop="password">
          <el-input
            v-model="reCheckForm.password"
            show-password
            type="password"
            @keydown.enter.prevent="submitForm"
          />
        </el-form-item>
        <el-form-item>
          <el-button
            :loading="isPending"
            color="#0f6778"
            size="large"
            style="width: 100%"
            @click="submitForm"
          >
            {{ buttonMsg("member.reconfirm") }}
          </el-button>
        </el-form-item>
      </el-form>
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
