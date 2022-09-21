<script setup lang="ts">
import { useMemberStore } from "@/stores/member";
const store = useMemberStore();
</script>
<template>
  <div class="result-content mb-4">
    <p class="result-info">{{ $t("form.message.findId.title") }}</p>
    <div class="result-box d-flex justify-content-between p-2">
      <p class="result-id">{{ store.foundId.loginId }}</p>
      <p class="created-date">
        {{ $t("form.label.findId.created") }} : {{ store.foundId.created }}
      </p>
    </div>
  </div>
</template>
<style scoped lang="scss">
.result-content {
  .result-info {
    font-size: 0.8rem;
  }

  .result-box {
    border: 0.12rem solid #bbbbbb;
    align-content: center;
    .result-id {
      font-weight: bold;
    }
    .created-date {
      padding-top: 0.5rem;
      font-size: 0.75rem;
      color: #383838;
    }
  }
}
</style>
