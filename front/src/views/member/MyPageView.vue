<script setup lang="ts">
import { useMemberStore } from "@/stores/member";
import { computed, onMounted } from "vue";
import { useMember } from "@/composables/member";
import { useAuthStore } from "@/stores/auth";
import type { ProfileType } from "@/modules/types/member/MemberType";
import type { TokenType } from "@/modules/types/auth/AuthType";

const memberStore = useMemberStore();
const authStore = useAuthStore();
const { error, isPending, getMember } = useMember();
const memberProfile = computed((): ProfileType => memberStore.memberProfile);

onMounted(async () => {
  if (authStore.loggedIn) {
    await getMember(authStore.tokenInfo.data as TokenType);
  }
});
</script>
<template>
  <div class="container" v-if="isPending">
    <el-row>
      <el-col :span="10" class="menu"> asd </el-col>
      <el-col :span="14" class="content"> asd </el-col>
    </el-row>
  </div>
</template>
<style scoped lang="scss"></style>
