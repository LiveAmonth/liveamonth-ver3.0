<script lang="ts" setup>
import { useAuth } from "@/composables/member/auth";
import { useRouter } from "vue-router";
import { useMember } from "@/composables/member/member";
import { Avatar, Unlock, UserFilled, Lock } from "@element-plus/icons-vue";
import { useMyPage } from "@/composables/member/mypage";
import { useMessageBox } from "@/composables/common/messageBox";

const router = useRouter();
const { isLoggedIn, logoutBtn } = useAuth();
const { simpleProfile } = useMember();
const { profileTabs, getPostCount } = useMyPage();
const { buttonMsg, menuMsg, labelMsg } = useMessageBox();

const goProfile = (post: string) => {
  router.push({ name: "profile", params: { post: post } });
};
</script>

<template>
  <el-card class="p-0">
    <div class="ds-top"></div>
    <template v-if="isLoggedIn && simpleProfile.id">
      <div class="avatar-holder">
        <el-avatar :size="100" :src="`/src/assets/image/default.jpg`" />
        <div class="overlay">
          <div class="text">{{ buttonMsg("member.image") }}</div>
        </div>
      </div>
      <div class="name">
        <router-link :to="{ name: 'profile', params: { post: 'schedule' } }">
          {{ simpleProfile.nickname }}
        </router-link>
      </div>
      <div class="button d-flex justify-content-center">
        <router-link :to="{ name: 'profile', params: { post: 'schedule' } }">
          <span>{{ menuMsg("header.myPage") }}</span>
          <el-icon>
            <Avatar />
          </el-icon>
        </router-link>
        <a @click="logoutBtn">
          <span>{{ buttonMsg("member.logout") }}</span>
          <el-icon>
            <Unlock />
          </el-icon>
        </a>
      </div>
      <div class="ds-info d-flex justify-content-between">
        <div
          v-for="tab in profileTabs"
          :key="tab.code"
          :class="tab.code"
          class="ds"
        >
          <h6>
            {{ tab.value }}
            <el-icon>
              <component :is="tab.icon" />
            </el-icon>
          </h6>
          <p @click="tab !== profileTabs[0] ? goProfile(tab.code) : null">
            {{ getPostCount(tab.code, simpleProfile, null) }}
          </p>
        </div>
      </div>
    </template>
    <template v-else>
      <div class="logo-holder">
        <el-image :size="100" :src="`/src/assets/image/logo.png`" />
      </div>
      <div class="name">
        <h5 class="m-0 p-0">{{ labelMsg("member.askLogin") }}</h5>
      </div>
      <div class="button d-flex justify-content-center">
        <router-link :to="{ name: 'login' }">
          {{ buttonMsg("member.login") }}
          <el-icon>
            <Lock />
          </el-icon>
        </router-link>
        <router-link :to="{ name: 'sign-up' }">
          {{ buttonMsg("member.signUp") }}
          <el-icon>
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
  height: 300px;
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

  .logo-holder {
    position: absolute;
    top: 40px;
    right: 0;
    left: 0;
    margin: auto;
    width: 160px;
    height: 140px;
    border-radius: 20px;
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

    &:hover .overlay {
      opacity: 0.5;
      cursor: pointer;
    }

    .overlay {
      position: absolute;
      top: 0;
      bottom: 0;
      left: 0;
      right: 0;
      height: 100%;
      width: 100%;
      opacity: 0;
      border-radius: 50%;
      transition: 0.5s ease;
      background-color: #004a55;

      .text {
        color: white;
        font-size: 1.2rem;
        position: absolute;
        line-height: 1.6rem;
        top: 50%;
        left: 50%;
        -webkit-transform: translate(-50%, -50%);
        -ms-transform: translate(-50%, -50%);
        transform: translate(-50%, -50%);
        text-align: center;
      }
    }
  }

  .name {
    position: absolute;
    margin: auto;
    top: 15px;
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
    top: 90px;
    right: 0;
    bottom: 0;
    left: 0;
    width: inherit;
    height: 25px;
    text-align: center;
    animation: fadeIn 2s ease-in;
    outline: none;

    a {
      display: flex;
      justify-content: center;
      width: 30%;
      border-radius: 10px;
      color: #004a55;
      letter-spacing: 0.05em;
      text-decoration: none;
      font-size: 0.78rem;
      transition: all 1s;
      padding: 5px 0;
      margin: 0 5px;

      &:hover {
        color: white;
        font-weight: lighter;
        background-color: rgba(112, 161, 170, 0.8);
      }

      span {
        margin-right: 2px;
      }
    }
  }

  .ds-info {
    position: absolute;
    margin: auto;
    top: 150px;
    bottom: 0;
    right: 0;
    left: 0;
    width: inherit;
    height: 40px;

    .follower,
    .schedule,
    .review {
      position: relative;
      width: 33%;
      text-align: center;
      color: black;
      animation: fadeInMove 2s;
      animation-fill-mode: forwards;

      h6 {
        display: flex;
        justify-content: center;
        margin-top: 2rem;
        margin-bottom: 10px;
        text-transform: uppercase;
        font-weight: 500;
        font-size: 0.75rem;
        color: #004a55;

        .el-icon {
          margin-left: 3px;
        }
      }

      p {
        margin: 0;
        font-weight: bold;
        font-size: 13px;
        cursor: pointer;
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
