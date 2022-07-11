<template>
  <el-menu
    class="header-content"
    mode="horizontal"
    text-color="#004A55"
    active-text-color="#004A55"
    router
  >
    <el-menu-item index="/city">{{ $t("menu.city") }}</el-menu-item>
    <el-sub-menu index="/schedule">
      <template #title>{{ $t("menu.schedule") }}</template>
      <el-menu-item index="/schedule/other">{{
        $t("menu.otherSchedule")
      }}</el-menu-item>
      <el-menu-item index="/schedule/my">{{
        $t("menu.mySchedule")
      }}</el-menu-item>
    </el-sub-menu>
    <el-menu-item index="/review">{{ $t("menu.review") }}</el-menu-item>
    <el-menu-item index="/myPage">{{ $t("menu.myPage") }}</el-menu-item>
  </el-menu>
</template>
<script setup lang="ts"></script>

<style scoped></style>
