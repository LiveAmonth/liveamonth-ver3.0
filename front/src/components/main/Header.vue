<script lang="ts" setup>
import NavMenu from "@/components/main/NavMenu.vue";
import LogoIcon from "@/components/image/LogoIcon.vue";
import HeaderBanner from "@/components/main/HeaderBanner.vue";
import { useAuth } from "@/composables/member/auth";
import { ref } from "vue";
import { useMessageBox } from "@/composables/common/messageBox";

const { isLoggedIn, logoutBtn } = useAuth();
const { buttonMsg } = useMessageBox();

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
      background-color="#fafafa"
      class="header-content"
      mode="horizontal"
      router
      style="
        --el-menu-hover-bg-color: #fafafa;
        --el-menu-hover-text-color: #007f95;
      "
      text-color="#5d5d5d"
    >
      <router-link :to="{ name: 'home' }">
        <LogoIcon class="logo" />
      </router-link>
      <div class="flex-grow" />
      <el-menu-item v-if="isLoggedIn" index="#" @click="logoutBtn">
        {{ buttonMsg("member.logout") }}
      </el-menu-item>
      <template v-else>
        <el-menu-item index="/login">
          {{ buttonMsg("member.login") }}
        </el-menu-item>
        <el-menu-item index="/sign-up">
          {{ buttonMsg("member.signUp") }}
        </el-menu-item>
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
  font-weight: 600;
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
  font-weight: 600;
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
