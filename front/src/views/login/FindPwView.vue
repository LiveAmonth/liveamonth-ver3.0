<script setup lang="ts">
import LogoIcon from "@/components/image/LogoIcon.vue";
import FindPwForm from "@/components/form/FindPwForm.vue";
import LinkSlot from "@/components/common/LinkSlot.vue";
</script>

<template>
  <div class="login-content d-flex justify-content-center mt-5">
    <el-col :xs="16" :sm="8" :md="7" :lg="7" :xl="6">
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
          <FindPwForm />
          <div class="d-flex justify-content-end">
            <LinkSlot :label="$t('member.findId')" link="/find-id" />
            <el-divider direction="vertical" />
            <LinkSlot :label="$t('member.signUp')" link="/sign-up" />
          </div>
        </el-card>
      </el-space>
    </el-col>
  </div>
</template>

<style scoped lang="scss">
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
