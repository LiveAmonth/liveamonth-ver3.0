<script lang="ts" setup></script>

<template>
  <div class="login-content d-flex justify-content-center mt-5">
    <el-col :lg="8" :md="8" :sm="10" :xl="6" :xs="12">
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
          <div class="d-flex justify-content-end">
            <el-link :underline="false">
              <router-link to="/login">
                {{ $t("member.login") }}
              </router-link>
            </el-link>
            <el-divider direction="vertical" />
            <el-link :underline="false">
              <router-link to="/find-pw">
                {{ $t("member.findPw") }}
              </router-link>
            </el-link>
          </div>
        </el-card>
      </el-space>
    </el-col>
  </div>
</template>
