<script lang="ts" setup>
import { useMessageBox } from "@/composables/common/messageBox";

const { resultMsg } = useMessageBox();
</script>
<template>
  <div class="result-content mb-4">
    <div class="result-box p-2">
      <p class="result-content">{{ resultMsg("findPw.title") }}</p>
      <p class="result-content">{{ resultMsg("findPw.content") }}</p>
    </div>
  </div>
</template>
<style lang="scss" scoped>
.result-content {
  font-size: 0.9rem;
}

.result-box {
  border: 0.12rem solid #bbbbbb;
  align-content: center;

  .result-content {
    font-weight: bold;
  }
}
</style>
