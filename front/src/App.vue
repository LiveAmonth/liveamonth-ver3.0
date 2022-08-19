<script lang="ts" setup>
import Content from "@/components/Content.vue";
import Header from "@/components/Header.vue";
</script>

<template>
  <Header />
  <Content />
</template>

<style lang="scss">
body {
  font-family: Pretendard, -apple-system, BlinkMacSystemFont, system-ui, Roboto,
    "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo", "Noto Sans KR",
    "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji", "Segoe UI Symbol",
    sans-serif;
}

.el-icon {
  vertical-align: middle;
}

a {
  text-decoration: none;
  color: inherit;

  &:hover {
    cursor: pointer;
    font-weight: bold;
  }

  &:visited {
    color: inherit;
  }
}

@keyframes fadeInMove {
  0% {
    opacity: 0;
    left: -300px;
  }
  100% {
    opacity: 1;
    left: 0;
  }
}

@keyframes fadeIn {
  0% {
    opacity: 0;
  }
  100% {
    opacity: 1;
  }
}

@keyframes mvTop {
  0% {
    top: -150px;
  }
  100% {
    top: 40px;
  }
}

@keyframes dsTop {
  0% {
    top: -150px;
  }
  100% {
    top: 0;
  }
}
</style>
