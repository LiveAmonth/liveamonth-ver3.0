<script setup lang="ts">
import { useMember } from "@/composables/member";
import { onMounted, ref } from "vue";

const { getMember, memberProfile, simpleProfile } = useMember();
const loading = ref<boolean>(false);

onMounted(async () => {
  loading.value = false;
  if (memberProfile.value.id !== simpleProfile.value.id) {
    await getMember();
  }
  loading.value = true;
});
</script>

<template>
  <router-view v-if="loading"></router-view>
</template>

<style scoped lang="scss"></style>
