<script lang="ts" setup>
import NavMenu from "@/components/NavMenu.vue";
import LogoIcon from "../components/image/LogoIcon.vue";
import { useAuthStore } from "@/stores/auth";
import { useAuth } from "@/composables/auth";
import { useRouter } from "vue-router";
import { computed } from "vue";

const router = useRouter();
const store = useAuthStore();
const { error, isPending, logout } = useAuth();
const loggedIn = computed((): boolean => store.loggedIn);
const logoutBtn = async () => {
  await logout();
  if (!store.loggedIn) {
    await router.push({ name: "login" });
  }
};
</script>

<template>
  <el-header class="header" height="auto">
    <el-menu
      :ellipsis="false"
      active-text-color="#004A55"
      background-color="#F6F6F6"
      class="header-content"
      mode="horizontal"
      router
      text-color="#004A55"
    >
      <router-link :to="{ name: 'home' }">
        <LogoIcon class="logo" />
      </router-link>
      <div class="flex-grow" />
      <template v-if="loggedIn">
        <el-menu-item index="#" @click="logoutBtn"
          >{{ $t("member.logout") }}
        </el-menu-item>
      </template>
      <template v-else>
        <el-menu-item index="/login">{{ $t("member.login") }}</el-menu-item>
        <el-menu-item index="/sign-up">{{ $t("member.signUp") }}</el-menu-item>
      </template>
    </el-menu>
  </el-header>
  <el-header class="nav-header">
    <NavMenu />
  </el-header>
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
