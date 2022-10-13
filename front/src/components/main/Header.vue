<script lang="ts" setup>
import NavMenu from "@/components/main/NavMenu.vue";
import LogoIcon from "@/components/image/LogoIcon.vue";
import HeaderBanner from "@/components/main/HeaderBanner.vue";
import { useAuth } from "@/composables/member/auth";
import { ref } from "vue";

const { isLoggedIn, logoutBtn } = useAuth();
const pageName = ref();
const menuClick = (name: string) => {
  pageName.value = name;
};
</script>

<template>
  <el-header class="header" height="auto">
    <el-menu
      :ellipsis="false"
      active-text-color="#0f6778"
      background-color="#fff"
      class="header-content"
      mode="horizontal"
      router
      text-color="#0f6778"
    >
      <router-link :to="{ name: 'home' }">
        <LogoIcon class="logo" />
      </router-link>
      <div class="flex-grow" />
      <el-menu-item v-if="isLoggedIn" index="#" @click="logoutBtn">
        {{ $t("member.logout") }}
      </el-menu-item>
      <template v-else>
        <el-menu-item index="/login">{{ $t("member.login") }}</el-menu-item>
        <el-menu-item index="/sign-up">{{ $t("member.signUp") }}</el-menu-item>
      </template>
    </el-menu>
  </el-header>
  <el-header class="nav-header">
    <NavMenu @menu-click="menuClick" />
  </el-header>
  <HeaderBanner />
</template>

<style lang="scss" scoped>
.header {
  padding: 0;
  height: 60px;

  @media screen and (min-width: 1200px) {
    .header-content {
      display: flex;
      padding: 0 20%;
    }
  }

  .logo {
    padding-top: 5px;
    cursor: pointer;
  }
}

.nav-header {
  padding: 0;
  height: 60px;

  @media screen and (min-width: 1200px) {
    .header-content {
      display: flex;
      padding: 0 20%;
    }
  }
}

.flex-grow {
  flex-grow: 1;
}
</style>
