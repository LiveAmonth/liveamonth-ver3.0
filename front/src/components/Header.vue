<script setup lang="ts">
import NavMenu from "@/components/NavMenu.vue";
import LogoIcon from "../components/image/LogoIcon.vue";
</script>

<template>
  <el-header class="header" height="auto">
    <el-menu
      class="header-content"
      mode="horizontal"
      text-color="#004A55"
      active-text-color="#004A55"
      background-color="#F6F6F6"
      :ellipsis="false"
      router
    >
      <el-menu-item class="p-0 m-0" index="/">
        <LogoIcon />
      </el-menu-item>
      <div class="flex-grow" />
      <el-menu-item index="/login">{{ $t("member.login") }}</el-menu-item>
      <el-menu-item index="/sign-up">{{ $t("member.signUp") }}</el-menu-item>
    </el-menu>
  </el-header>
  <el-header class="nav-header">
    <NavMenu />
  </el-header>
</template>

<style scoped lang="scss">
.header {
  padding: 0;
  height: 60px;
  @media screen and (min-width: 1200px) {
    .header-content {
      display: flex;
      padding: 0 15%;
    }
  }
}

.nav-header {
  padding: 0;
  height: 60px;
  @media screen and (min-width: 1200px) {
    .header-content {
      display: flex;
      padding: 0 15%;
    }
  }
}

.flex-grow {
  flex-grow: 1;
}
</style>
