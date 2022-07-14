<template>
  <Header v-if="!isLoginPage" />
  <el-main class="content">
    <el-row>
      <el-col>
        <RouterView />
      </el-col>
    </el-row>
  </el-main>
</template>

<script lang="ts" setup>
import { RouterView, useRoute } from "vue-router";
import { ref, watchEffect } from "vue";
import Header from "@/components/Header.vue";

const route = useRoute();
const isLoginPage = ref(false);
watchEffect(() => {
  isLoginPage.value = ["login", "sign-up", "find-id", "find-pw"].includes(
    String(route.name)
  );
});
</script>

<style scoped>
@media screen and (min-width: 1200px) {
  .content {
    margin: 0 15%;
  }
}
</style>
