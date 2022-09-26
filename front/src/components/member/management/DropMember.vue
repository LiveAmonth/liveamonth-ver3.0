<script setup lang="ts">
import TitleSlot from "@/components/common/TitleSlot.vue";
import Reconfirm from "@/components/member/management/ReconfirmPassword.vue";
import { ref } from "vue";
import { useMessageBox } from "@/composables/common/messageBox";
import { useMember } from "@/composables/member/member";
import { useAuth } from "@/composables/member/auth";
import { useRouter } from "vue-router";

const router = useRouter();
const { isLoggedIn, logout } = useAuth();
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
    await logout().then(() => {
      if (!isLoggedIn.value) {
        router.push({ name: "home" });
      }
    });
  });
};
</script>

<template>
  <Reconfirm v-if="!reChecked" v-model:re-checked="reChecked" />
  <div v-else class="d-flex justify-content-center mt-5">
    <div class="container">
      <TitleSlot>{{ titleMsg("member.dropMember") }}</TitleSlot>
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
