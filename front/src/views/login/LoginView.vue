<script lang="ts" setup>
import LoginForm from "@/components/member/account/LoginForm.vue";
import LogoIcon from "@/components/image/LogoIcon.vue";
import LinkSlot from "@/components/common/LinkSlot.vue";
import { useMessageBox } from "@/composables/common/messageBox";

const { buttonMsg } = useMessageBox();
</script>

<template>
  <div class="login-content d-flex justify-content-center mt-5">
    <el-col :lg="10" :md="8" :sm="12" :xl="8" :xs="12">
      <el-space
        :fill="true"
        direction="vertical"
        size="small"
        style="width: 100%"
      >
        <el-card class="box-card">
          <template #header>
            <div class="card-header d-flex justify-content-center">
              <router-link class="home-btn" to="/">
                <LogoIcon class="mt-4" />
              </router-link>
            </div>
          </template>
          <LoginForm />
          <div class="d-flex justify-content-end">
            <LinkSlot :label="buttonMsg('member.findId')" link="/find-id" />
            <el-divider direction="vertical" />
            <LinkSlot :label="buttonMsg('member.findPw')" link="/find-pw" />
            <el-divider direction="vertical" />
            <LinkSlot :label="buttonMsg('member.signUp')" link="/sign-up" />
          </div>
        </el-card>
      </el-space>
    </el-col>
  </div>
</template>

<style lang="scss" scoped>
.card-header {
  background-color: #f6f6f6;
  background-clip: border-box;
  height: 100px;
  padding: 0;
  box-shadow: var(--el-box-shadow);

  &:hover {
    cursor: pointer;
    box-shadow: var(--el-box-shadow-dark);
  }
}

a {
  color: inherit;
  text-decoration: none;
}
</style>
