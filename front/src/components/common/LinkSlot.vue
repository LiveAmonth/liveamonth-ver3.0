<script setup lang="ts">
const props = defineProps({
  label: {
    type: String,
    required: true,
  },
  link: {
    type: String,
    required: true,
  },
});
</script>

<template>
  <el-link :underline="false">
    <router-link :to="props.link">
      {{ props.label }}
    </router-link>
  </el-link>
</template>

<style scoped>
a {
  color: inherit;
  text-decoration: none;
}
</style>
