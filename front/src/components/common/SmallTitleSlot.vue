<script lang="ts" setup></script>

<template>
  <div class="title">
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
      background-color: #004a55;
      content: "";
      position: absolute;
      left: 0;
      top: 3px;
      width: 4px;
      height: 25px;
    }
  }
}
</style>
