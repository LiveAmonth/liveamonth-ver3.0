<script lang="ts" setup>
import TitleSlot from "@/components/common/TitleSlot.vue";
import Reconfirm from "@/components/member/management/ReconfirmPassword.vue";
import { ref } from "vue";
import { useMessageBox } from "@/composables/common/messageBox";
import { useMember } from "@/composables/member/member";
import { useAuth } from "@/composables/member/auth";

const { logoutBtn } = useAuth();
const { dropMember } = useMember();
const { openConfirmMessageBox, buttonMsg, titleMsg, resultMsg } =
  useMessageBox();
const reChecked = ref(false);

const btnClick = async () => {
  await openConfirmMessageBox(
    buttonMsg("member.dropMember"),
    resultMsg("dropMember.success")
  ).then(async () => {
    await dropMember();
    await logoutBtn();
  });
};
</script>

<template>
  <Reconfirm v-if="!reChecked" v-model:re-checked="reChecked" />
  <div v-else class="d-flex justify-content-center mt-5">
    <div class="container">
      <TitleSlot :title="titleMsg('member.dropMember')" />
      <p class="my-5">
        {{ resultMsg("dropMember.announce") }}
      </p>
      <el-button size="large" @click="btnClick">
        {{ buttonMsg("member.dropMember") }}
      </el-button>
    </div>
  </div>
</template>

<style scoped lang="scss">
p {
  width: 420px;
  font-size: 1.2rem;
}

.el-button {
  font-size: 1.1rem;
}
</style>
