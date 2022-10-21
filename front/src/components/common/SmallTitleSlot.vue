<script lang="ts" setup>
defineProps({
  titleLine: {
    type: Boolean,
    required: false,
    default: true,
  },
  title: {
    type: String,
    required: false,
    default: "제목을 입력해주세요",
  },
});
</script>

<template>
  <div
    :style="{ '--bgColor': titleLine ? '#004a55' : 'inherit' }"
    class="title"
  >
    <span>
      <slot>{{ title }}</slot>
    </span>
  </div>
</template>

<style lang="scss" scoped>
.title {
  margin-bottom: 10px;

  > span {
    font-size: 1.3rem;
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
      height: 20px;
    }
  }
}
</style>
