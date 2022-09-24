<script lang="ts" setup>
import { useAuth } from "@/composables/auth";
import { useMember } from "@/composables/member";

const { isLoggedIn } = useAuth();
const { simpleProfile } = useMember();
</script>
<template>
  <el-menu
    active-text-color="#0f6778"
    class="header-content"
    mode="horizontal"
    router
    text-color="#0f6778"
  >
    <el-menu-item index="/city">{{ $t("menu.city") }}</el-menu-item>
    <el-sub-menu index="/schedules">
      <template #title>{{ $t("menu.schedule") }}</template>
      <el-menu-item index="/schedules"
        >{{ $t("menu.otherSchedule") }}
      </el-menu-item>
      <el-menu-item
        :index="isLoggedIn ? `/my-schedule/${simpleProfile.loginId}` : '/login'"
        >{{ $t("menu.mySchedule") }}
      </el-menu-item>
    </el-sub-menu>
    <el-menu-item index="/review">
      {{ $t("menu.review") }}
    </el-menu-item>
    <el-menu-item index="/my-page/schedule">
      {{ $t("menu.myPage") }}
    </el-menu-item>
  </el-menu>
</template>

<style scoped></style>
