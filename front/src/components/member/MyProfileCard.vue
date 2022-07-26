<script lang="ts" setup>
import { useAuthStore } from "@/stores/auth";
import { computed, ref } from "vue";
import { useAuth } from "@/composables/auth";
import { useRouter } from "vue-router";

const router = useRouter();
const store = useAuthStore();
const { error, logout } = useAuth();
const loggedIn = computed((): boolean => store.loggedIn);

const activeName = ref<string>("schedule");

const logoutBtn = async () => {
  await logout();
  if (!store.loggedIn) {
    await router.push({ name: "login" });
  }
};
</script>

<template>
  <el-card class="p-0">
    <div class="ds-top"></div>
    <div class="avatar-holder">
      <el-avatar :size="100" :src="`/src/assets/image/default.jpg`" />
    </div>
    <template v-if="loggedIn">
      <div class="name">
        <router-link :to="{ path: '/my-schedule/2' }" target="_blank"
          >{{ store.profile.nickname }}
        </router-link>
      </div>
      <div class="button">
        <a class="btn" href="#" @click="logoutBtn">
          {{ $t("menu.myPage") }}
          <el-icon style="vertical-align: middle">
            <Avatar />
          </el-icon>
        </a>
        <a class="btn" href="#" @click="logoutBtn"
          >{{ $t("member.logout") }}
          <el-icon style="vertical-align: middle">
            <Unlock />
          </el-icon>
        </a>
      </div>
      <div class="ds-info">
        <el-tabs v-model="activeName" class="demo-tabs">
          <el-tab-pane :label="$t('menu.schedule')" name="schedule"
            >내 스케줄!!
          </el-tab-pane>
          <el-tab-pane :label="$t('menu.review')" name="review"
            >내 후기글!!
          </el-tab-pane>
        </el-tabs>
      </div>
    </template>
    <template v-else>
      <div class="name">
        <h5 class="m-0 p-0">{{ $t("member.askLogin") }}</h5>
      </div>
      <div class="button">
        <router-link :to="{ name: 'login' }" class="btn">
          {{ $t("member.login") }}
          <el-icon style="vertical-align: middle">
            <Lock />
          </el-icon>
        </router-link>
        <router-link :to="{ name: 'sign-up' }" class="btn">
          {{ $t("member.signUp") }}
          <el-icon style="vertical-align: middle">
            <UserFilled />
          </el-icon>
        </router-link>
      </div>
    </template>
  </el-card>
</template>

<style lang="scss" scoped>
.el-card {
  position: relative;
  padding: 0;
  margin: auto;
  width: auto;
  height: 400px;
  border-radius: 10px;
  background: white;
  overflow: hidden;

  .ds-top {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    margin: 0;
    width: 100%;
    height: 80px;
    background: #004a55;
    animation: dsTop 1.5s;
  }

  .avatar-holder {
    position: absolute;
    top: 35px;
    right: 0;
    left: 0;
    margin: auto;
    width: 100px;
    height: 100px;
    border-radius: 50%;
    background: white;
    box-shadow: 0 0 0 5px #ffffff, inset 0 0 0 5px #ffffff,
      inset 0 0 0 5px #effafa, inset 0 0 0 5px #ffffff, inset 0 0 0 5px #ffffff;
    overflow: hidden;
    animation: mvTop 1.5s;

    .el-avatar {
      width: 100%;
      height: 100%;
      object-fit: cover;
    }
  }

  .name {
    position: absolute;
    margin: auto;
    top: -70px;
    right: 0;
    bottom: 0;
    left: 0;
    width: inherit;
    height: 20px;
    text-align: center;
    animation: fadeIn 2s ease-in;

    a {
      color: black;
      text-decoration: none;
      font-weight: 700;
      font-size: 1.2rem;

      &:hover {
        text-decoration: underline;
        color: #004a55;
      }
    }

    h6 {
      position: absolute;
      margin: auto;
      left: 0;
      right: 0;
      bottom: 0;
      color: black;
      width: 40px;
    }
  }

  .button {
    position: absolute;
    margin: auto;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    width: inherit;
    height: 20px;
    text-align: center;
    animation: fadeIn 2s ease-in;
    outline: none;

    a {
      margin: 5px;
      padding: 5px 20px;
      border-radius: 10px;
      color: #004a55;
      letter-spacing: 0.05em;
      text-decoration: none;
      font-size: 0.78rem;
      transition: all 1s;

      &:hover {
        color: white;
        background: #004a55;
      }
    }
  }

  .ds-info {
    position: absolute;
    margin: auto;
    top: 80px;
    bottom: 0;
    right: 0;
    left: 0;
    width: inherit;
    height: 40px;
    display: flex;

    .el-tabs {
      --el-tabs-header-height: 30px;
      height: 100%;
      padding-left: 10px;
      text-align: center;
      color: black;
      animation: fadeInMove 2s;
      animation-fill-mode: forwards;

      .demo-tabs {
        .el-tabs__header.is-top {
          color: #004a55;
        }
      }

      .el-tabs-pane {
        width: calc(250px / 3);

        h6 {
          margin: 0;
          text-transform: uppercase;
          color: crimson;
        }

        p {
          font-size: 12px;
        }
      }
    }

    .ds {
      &:nth-of-type(2) {
        animation-delay: 0.5s;
      }

      &:nth-of-type(1) {
        animation-delay: 1s;
      }
    }
  }
}
</style>
