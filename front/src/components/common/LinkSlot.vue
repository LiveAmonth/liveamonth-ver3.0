<script setup lang="ts">
defineProps({
  label: {
    type: String,
    required: false,
  },
  link: {
    type: String,
    required: true,
  },
});
</script>

<template>
  <el-link :underline="false">
    <router-link :to="link">
      <slot>{{ label }}</slot>
    </router-link>
  </el-link>
</template>

<style scoped>
a {
  color: inherit;
  text-decoration: none;
  display: flex;
  justify-items: center;
  align-items: center;
}
</style>
