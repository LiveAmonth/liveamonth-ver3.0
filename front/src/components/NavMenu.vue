<template>
  <el-menu
    active-text-color="#004A55"
    class="header-content"
    mode="horizontal"
    router
    text-color="#004A55"
  >
    <el-menu-item index="/city">{{ $t("menu.city") }}</el-menu-item>
    <el-sub-menu index="/schedule">
      <template #title>{{ $t("menu.schedule") }}</template>
      <el-menu-item index="/schedule"
        >{{ $t("menu.otherSchedule") }}
      </el-menu-item>
      <el-menu-item index="/my-schedule/1"
        >{{ $t("menu.mySchedule") }}
      </el-menu-item>
    </el-sub-menu>
    <el-menu-item index="/review">{{ $t("menu.review") }}</el-menu-item>
    <el-menu-item index="/my-page">{{ $t("menu.myPage") }}</el-menu-item>
  </el-menu>
</template>
<script lang="ts" setup></script>

<style scoped></style>
