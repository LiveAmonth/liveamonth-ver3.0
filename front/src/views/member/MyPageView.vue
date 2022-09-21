<script setup lang="ts">
import { useMember } from "@/composables/member";
import { onMounted } from "vue";

const { isPending, getMember } = useMember();

onMounted(async () => {
  await getMember();
});
</script>

<template>
  <router-view v-if="!isPending"></router-view>
</template>

<style scoped lang="scss"></style>
