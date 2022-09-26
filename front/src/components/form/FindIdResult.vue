<script lang="ts" setup>
import { useMember } from "@/composables/member/member";
import { useMessageBox } from "@/composables/common/messageBox";

const { labelMsg, resultMsg } = useMessageBox();
const { foundId } = useMember();
</script>
<template>
  <div class="result-content mb-4">
    <p class="result-info">{{ resultMsg("findId.title") }}</p>
    <div class="result-box d-flex justify-content-between p-2">
      <p class="result-id">{{ foundId.loginId }}</p>
      <p class="created-date">
        {{ labelMsg("findId.created") }} : {{ foundId.created }}
      </p>
    </div>
  </div>
</template>
<style lang="scss" scoped>
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
