<script setup lang="ts"></script>

<template>
  <div class="login-content d-flex justify-content-center mt-5">
    <el-col :span="6">
      <el-space
        direction="vertical"
        style="width: 100%"
        :fill="true"
        size="small"
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
