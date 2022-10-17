<script lang="ts" setup>
import LogoIcon from "@/components/image/LogoIcon.vue";
import SignUpForm from "@/components/member/account/SignUpForm.vue";
</script>

<template>
  <el-row class="login-content d-flex justify-content-center mt-5">
    <el-col :xs="16" :sm="14" :md="12" :lg="16" :xl="12">
      <el-card class="box-card">
        <template #header>
          <div class="card-header d-flex justify-content-center">
            <router-link class="home-btn" to="/">
              <LogoIcon class="mt-4" />
            </router-link>
          </div>
        </template>
        <SignUpForm />
      </el-card>
    </el-col>
  </el-row>
</template>

<style lang="scss" scoped>
.card-header {
  background-color: #ffffff;
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
