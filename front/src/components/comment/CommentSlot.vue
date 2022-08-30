<script lang="ts" setup>
import type { PropType } from "vue";
import BootstrapIcon from "@/components/common/BootstrapIcon.vue";
import type { ReactedCommentType } from "@/modules/types/interaction/InteractionType";

const props = defineProps({
  id: {
    type: Number,
    required: true,
  },
  avatarUrl: {
    type: String,
    required: true,
  },
  isReply: {
    type: Boolean,
    required: true,
  },
  isReacted: {
    type: Object as PropType<ReactedCommentType>,
    required: false,
    default: null,
  },
});
const emit = defineEmits(["reactComment"]);
const reactComment = (option: boolean) => {
  emit("reactComment", props.id, option, props.isReacted);
};
const setIconClass = (option: boolean) => {
  const thumbsUp = "bi-hand-thumbs-up";
  const thumbsDown = "bi-hand-thumbs-down";
  const fillSuffix = "-fill";
  if (props.isReacted && option) {
    // react가 있거나 추천 옵션인 경우
    return props.isReacted.type === "LIKE"
      ? thumbsUp + fillSuffix
      : thumbsDown + fillSuffix;
  } else {
    return option ? thumbsUp : thumbsDown;
  }
};
</script>
<template>
  <div class="sub d-flex justify-content-start">
    <i v-if="isReply" class="bi bi-arrow-return-right me-2"></i>
    <div :class="`avatar ${isReply ? 'pt-1' : ''}`">
      <el-avatar :size="25" :src="avatarUrl" />
    </div>
    <div class="writer">
      <slot name="writer"></slot>
    </div>
    <div :class="`elapsedTime ${isReply ? 'pb-1' : 'pb-2'}`">
      <slot name="elapsedTime"></slot>
    </div>
    <div class="like ms-2">
      <span class="me-2">
        <el-tooltip
          class="box-item"
          effect="dark"
          :content="$t('comment.react.like')"
          placement="top"
        >
          <BootstrapIcon
            :icon="setIconClass(true)"
            class="me-1"
            @click="reactComment(true)"
          />
        </el-tooltip>
        <slot name="likeCount"></slot>
      </span>
      <span>
        <el-tooltip
          class="box-item"
          effect="dark"
          :content="$t('comment.react.dislike')"
          placement="top"
        >
          <BootstrapIcon
            :icon="setIconClass(false)"
            class="me-1"
            @click="reactComment(false)"
          />
        </el-tooltip>
        <slot name="dislikeCount"></slot>
      </span>
    </div>
  </div>
  <div class="content">
    <slot name="content"></slot>
  </div>
</template>

<style lang="scss" scoped>
.sub {
  margin-top: 7px;
  font-size: 1rem;
  align-items: center;

  .avatar {
    margin-left: 0;
  }

  .writer {
    margin-left: 10px;
  }

  .elapsedTime {
    margin-left: 10px;
    color: #6b6b6b;
    font-size: 0.7rem;
  }

  .like {
    font-size: 0.85rem;

    .bi-hand-thumbs-up:hover,
    .bi-hand-thumbs-down:hover {
      cursor: pointer;
    }
  }
}

.content {
  font-size: 1rem;
  margin-top: 8px;
  color: #737373;
}
</style>
