<script lang="ts" setup>
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import CommentSlot from "@/components/comment/CommentSlot.vue";
import { useMessageBox } from "@/composables/common/messageBox";
import { useComment } from "@/composables/common/comment";

defineProps({
  writer: {
    type: String,
    required: true,
  },
});

const { labelMsg } = useMessageBox();
const { bestComments } = useComment();
</script>

<template>
  <SmallTitleSlot class="mb-3">
    {{ labelMsg("comment.best") }}
  </SmallTitleSlot>
  <CommentSlot
    class="mb-4"
    v-for="(comment, idx) in bestComments"
    :key="comment"
    :comment="comment"
    :avatar-url="'/src/assets/image/default.jpg'"
    :is-best="true"
    :is-reply="false"
    :rank="idx + 1"
    :is-writer="writer === comment.profile.nickname"
  />
</template>

<style lang="scss" scoped></style>
