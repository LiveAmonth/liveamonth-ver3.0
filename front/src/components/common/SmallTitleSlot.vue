<script lang="ts" setup>
defineProps({
  titleLine: {
    type: Boolean,
    required: false,
    default: true,
  },
});
</script>

<template>
  <div
    class="title"
    :style="{ '--bgColor': titleLine ? '#004a55' : '#ffffff' }"
  >
    <span><slot></slot></span>
  </div>
</template>

<style lang="scss" scoped>
.title {
  margin-bottom: 10px;

  > span {
    font-size: 25px;
    font-weight: 600;
    letter-spacing: 0.5px;
    position: relative;
    text-transform: uppercase;
    padding-left: 10px;

    &:after {
      background-color: var(--bgColor);
      content: "";
      position: absolute;
      left: 0;
      top: 6px;
      width: 4px;
      height: 27px;
    }
  }
}
</style>
