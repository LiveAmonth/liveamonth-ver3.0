<script setup lang="ts">
import { useAuthStore } from "@/stores/auth";
import { computed } from "vue";
const store = useAuthStore();

const loggedIn = computed((): boolean => store.loggedIn);
</script>

<template>
  <div>여기는 홈 화면입니다.</div>
  <div v-if="loggedIn">
    {{ store.profile.nickName }}
  </div>
</template>

<style scoped lang="scss"></style>
