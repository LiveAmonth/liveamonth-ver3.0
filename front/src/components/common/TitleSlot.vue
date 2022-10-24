<script lang="ts" setup>
defineProps({
  title: {
    type: String,
    required: false,
    default: "제목을 입력해주세요",
  },
});
</script>

<template>
  <div class="title">
    <span>
      <slot>{{ title }}</slot>
    </span>
  </div>
</template>

<style lang="scss" scoped>
.title {
  margin-bottom: 10px;

  > span {
    font-size: 32px;
    font-weight: 600;
    letter-spacing: 0.5px;
    position: relative;
    text-transform: uppercase;
    padding-left: 10px;

    &:after {
      background-color: #004a55;
      content: "";
      position: absolute;
      left: 0;
      top: 3px;
      width: 4px;
      height: 32px;
    }
  }
}
</style>
