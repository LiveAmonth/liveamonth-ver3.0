<script lang="ts" setup>
import { useAuthStore } from "@/stores/auth";
import { computed, ref } from "vue";
import { useAuth } from "@/composables/auth";
import { useRouter } from "vue-router";
import type { UploadInstance, UploadProps } from "element-plus";
import { ElMessage } from "element-plus";

const router = useRouter();
const store = useAuthStore();
const { error, logout } = useAuth();
const loggedIn = computed((): boolean => store.loggedIn);

const logoutBtn = async () => {
  await logout();
  if (!store.loggedIn) {
    await router.push({ name: "login" });
  }
};

const imageUrl = ref("");
const upload = ref<UploadInstance>();
const handleAvatarSuccess: UploadProps["onSuccess"] = (
  response,
  uploadFile
) => {
  imageUrl.value = URL.createObjectURL(uploadFile.raw!);
};

const beforeAvatarUpload: UploadProps["beforeUpload"] = (rawFile) => {
  if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error("Avatar picture size can not exceed 2MB!");
    return false;
  }
  return true;
};
</script>

<template>
  <el-card class="p-0">
    <div class="ds-top"></div>
    <template v-if="loggedIn">
      <div class="avatar-holder">
        <el-avatar v-if="imageUrl" :size="100" :src="imageUrl" />
        <el-avatar v-else :size="100" :src="`/src/assets/image/default.jpg`" />
        <el-upload
          ref="upload"
          class="uploader"
          action="/"
          :limit="1"
          :show-file-list="false"
          :on-change="handleAvatarSuccess"
          :before-upload="beforeAvatarUpload"
        >
          <div class="overlay">
            <div class="text">{{ $t("member.imageChange") }}</div>
          </div>
        </el-upload>
      </div>
      <div class="name">
        <router-link :to="{ path: '/my-schedule/2' }" target="_blank"
          >{{ store.memberInfo.nickname }}
        </router-link>
      </div>
      <div class="button d-flex justify-content-center">
        <a class="btn" href="#" @click="logoutBtn">
          {{ $t("menu.myPage") }}
          <el-icon>
            <Avatar />
          </el-icon>
        </a>
        <a class="btn" href="#" @click="logoutBtn"
          >{{ $t("member.logout") }}
          <el-icon>
            <Unlock />
          </el-icon>
        </a>
      </div>
      <div class="ds-info d-flex justify-content-between">
        <div class="ds follower">
          <h6>
            {{ $t("member.follower") }} <el-icon><User /></el-icon>
          </h6>
          <p>29</p>
        </div>
        <div class="ds schedules">
          <h6>
            {{ $t("member.schedule") }} <el-icon><Calendar /></el-icon>
          </h6>
          <p>29</p>
        </div>
        <div class="ds reviews">
          <h6>
            {{ $t("member.review") }} <el-icon><Notebook /></el-icon>
          </h6>
          <p>0</p>
        </div>
      </div>
    </template>
    <template v-else>
      <div class="logo-holder">
        <el-image :size="100" :src="`/src/assets/image/logo.png`" />
      </div>
      <div class="name">
        <h5 class="m-0 p-0">{{ $t("member.askLogin") }}</h5>
      </div>
      <div class="button">
        <router-link :to="{ name: 'login' }" class="btn">
          {{ $t("member.login") }}
          <el-icon>
            <Lock />
          </el-icon>
        </router-link>
        <router-link :to="{ name: 'sign-up' }" class="btn">
          {{ $t("member.signUp") }}
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
    height: 20px;
    text-align: center;
    animation: fadeIn 2s ease-in;
    outline: none;

    a {
      width: 30%;
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
    top: 150px;
    bottom: 0;
    right: 0;
    left: 0;
    width: inherit;
    height: 40px;

    .follower,
    .schedules,
    .reviews {
      position: relative;
      width: 33%;
      text-align: center;
      color: black;
      animation: fadeInMove 2s;
      animation-fill-mode: forwards;

      h6 {
        margin-bottom: 10px;
        text-transform: uppercase;
        color: #004a55;
      }

      p {
        margin: 0;
        font-size: 12px;
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
