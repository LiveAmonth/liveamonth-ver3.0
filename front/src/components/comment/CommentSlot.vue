<script lang="ts" setup>
import BootstrapIcon from "@/components/common/BootstrapIcon.vue";
import { ref, watch } from "vue";
import { useInteraction } from "@/composables/interaction";
import { useMessageBox } from "@/composables/messageBox";
import type { Ref, UnwrapRef } from "vue";
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
});
const emit = defineEmits(["reactComment"]);
const { reactedComments, getReactedComment, checkReacted } = useInteraction();
const { openMessageBox } = useMessageBox();
const thumbsUp = ref<string>("bi-hand-thumbs-up");
const thumbsDown = ref<string>("bi-hand-thumbs-down");
const fillSuffix = "-fill";
const reactedComment = ref<ReactedCommentType>();
const fillThumbs = (thumbs: Ref<UnwrapRef<string>>) => {
  thumbs.value = thumbs.value.concat(fillSuffix);
};

const reactComment = (option: boolean) => {
  checkReacted(option, reactedComment.value?.type)
    .then(() => {
      emit("reactComment", props.id, option, !!reactedComment.value);
      option ? fillThumbs(thumbsUp) : fillThumbs(thumbsDown);
    })
    .catch((error) => {
      openMessageBox(error);
    });
};

watch(
  () => reactedComments.value,
  async () => {
    await getReactedComment(props.id).then((response) => {
      if (response) {
        reactedComment.value = response;
        reactedComment.value.type === "LIKE"
          ? fillThumbs(thumbsUp)
          : fillThumbs(thumbsDown);
      }
    });
  }
);
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
            :icon="thumbsUp"
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
            :icon="thumbsDown"
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
