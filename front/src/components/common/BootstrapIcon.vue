<script setup lang="ts">
import { ref } from "vue";

const props = defineProps({
  icon: {
    type: String,
    required: true,
  },
});
const iconClass = ref<string>(props.icon);
const changeColor = () => {
  if (iconClass.value.includes("-fill")) {
    emptyColor();
  } else {
    fillColor();
  }
};

const fillColor = () => {
  iconClass.value = iconClass.value.concat("-fill");
};
const emptyColor = () => {
  iconClass.value = iconClass.value.slice(0, iconClass.value.length - 5);
};
</script>

<template>
  <i
    :class="`bi ${iconClass}`"
    @mouseenter="changeColor"
    @mouseleave="changeColor"
    style="cursor: pointer; color: #535252"
  />
</template>
