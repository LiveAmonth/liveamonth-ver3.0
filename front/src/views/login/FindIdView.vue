<script setup lang="ts">
import LogoIcon from "@/components/image/LogoIcon.vue";
import FindIdForm from "@/components/form/FindIdForm.vue";
import LinkSlot from "@/components/common/LinkSlot.vue";
import FindIdResult from "@/components/form/FindIdResult.vue";
import { ref } from "vue";
import { useMemberStore } from "@/stores/member";

const isFind = ref<boolean>(false);
const store = useMemberStore();

const findId = () => {
  isFind.value = true;
};
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
          <FindIdForm v-if="!isFind" @find-id="findId" />
          <FindIdResult v-else />
          <div class="d-flex justify-content-end">
            <LinkSlot :label="$t('member.login')" link="/login" />
            <el-divider direction="vertical" />
            <LinkSlot
              v-if="!isFind"
              :label="$t('member.signUp')"
              link="/sign-up"
            />
            <LinkSlot v-else :label="$t('member.findPw')" link="/find-pw" />
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
</style>
