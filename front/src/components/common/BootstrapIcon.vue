<script setup lang="ts">
import { ref, watch } from "vue";

const props = defineProps({
  icon: {
    type: String,
    required: true,
  },
});
const iconClass = ref<string>(props.icon);

watch(
  () => props.icon,
  (newValue) => {
    iconClass.value = newValue;
  }
);
</script>

<template>
  <i :class="`bi ${iconClass}`" style="cursor: pointer; color: #535252" />
</template>
