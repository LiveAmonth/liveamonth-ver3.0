<script lang="ts" setup>
import CommentSlot from "@/components/comment/CommentSlot.vue";
import { useMessageBox } from "@/composables/common/messageBox";
import type { PropType } from "vue";
import type { CommentType } from "@/modules/types/comment/CommentTypes";

defineProps({
  postWriter: {
    type: String,
    required: true,
  },
  comment: {
    type: Object as PropType<CommentType>,
    required: true,
  },
});

const { labelMsg, resultMsg } = useMessageBox();
</script>

<template>
  <el-card v-if="comment" class="reply mb-2">
    <SmallTitleSlot class="mb-3">
      {{ labelMsg("comment.best") }}
    </SmallTitleSlot>
    <CommentSlot
      :id="comment.commentId"
      :avatar-url="'/src/assets/image/default.jpg'"
      :is-best="true"
      :is-reply="false"
      :is-writer="postWriter === comment.profile.nickname"
    >
      <template v-slot:writer>
        {{ comment.profile.nickname }}
      </template>
      <template v-slot:elapsedTime>
        {{ comment.elapsedTime }}
      </template>
      <template v-slot:comment>
        {{ comment.comment }}
      </template>
      <template v-slot:likeCount>
        {{ $count(comment.numberOfLikes) }}
      </template>
      <template v-slot:dislikeCount>
        {{ $count(comment.numberOfDislikes) }}
      </template>
    </CommentSlot>
  </el-card>
  <el-card v-else class="reply mb-0">
    {{ resultMsg("comment.empty") }}
  </el-card>
</template>

<style lang="scss" scoped></style>
