<script lang="ts" setup>
import LogoIcon from "@/components/image/LogoIcon.vue";
import FindIdForm from "@/components/member/account/FindIdForm.vue";
import FindIdResult from "@/components/member/account/FindIdResult.vue";
import LinkSlot from "@/components/common/LinkSlot.vue";
import { ref } from "vue";
import { useMessageBox } from "@/composables/common/messageBox";

const { buttonMsg } = useMessageBox();

const isFind = ref<boolean>(false);
</script>

<template>
  <div class="login-content d-flex justify-content-center mt-5">
    <el-col :xs="16" :sm="10" :md="8" :lg="10" :xl="8">
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
          <FindIdForm v-if="!isFind" v-model:is-find="isFind" />
          <FindIdResult v-else />
          <div class="d-flex justify-content-end">
            <LinkSlot :label="buttonMsg('member.login')" link="/login" />
            <el-divider direction="vertical" />
            <LinkSlot
              v-if="!isFind"
              :label="buttonMsg('member.signUp')"
              link="/sign-up"
            />
            <LinkSlot
              v-else
              :label="buttonMsg('member.findPw')"
              link="/find-pw"
            />
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
</style>
