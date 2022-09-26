<script lang="ts" setup>
import { useMessageBox } from "@/composables/messageBox";

const { bannerMsg } = useMessageBox();
</script>

<template>
  <div class="banner">
    <div class="text-banner">
      <p class="title">
        {{ bannerMsg("review.title") }}
      </p>
      <p class="content">{{ bannerMsg("review.content") }}</p>
    </div>
  </div>
</template>

<style lang="scss" scoped>
.banner {
  padding-left: 1%;
  font-family: "Do Hyeon", sans-serif;
  background-color: #006778;
  color: white;
  margin-top: -1px;

  @media screen and (min-width: 1200px) {
    .text-banner {
      padding-left: 20%;
    }
  }

  .text-banner {
    p {
      margin: 0;
      padding-top: 20px;
    }

    .title {
      font-size: 1.5rem;
    }

    .content {
      font-size: 1.2rem;
      padding-bottom: 20px;
    }
  }
}
</style>
