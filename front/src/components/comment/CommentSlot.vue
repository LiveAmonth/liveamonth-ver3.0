<script lang="ts" setup>
import BootstrapIcon from "@/components/common/BootstrapIcon.vue";
import { ref, watch } from "vue";
import { useAuth } from "@/composables/member/auth";
import { useInteraction } from "@/composables/interaction/interaction";
import { useMessageBox } from "@/composables/common/messageBox";
import type { PropType, Ref, UnwrapRef } from "vue";
import type { ReactedCommentType } from "@/modules/types/interaction/InteractionType";
import type {
  BestCommentType,
  CommentType,
} from "@/modules/types/comment/CommentTypes";

const props = defineProps({
  comment: {
    type: Object as PropType<CommentType | BestCommentType>,
    required: true,
  },
  isReply: {
    type: Boolean,
    required: true,
  },
  isBest: {
    type: Boolean,
    required: false,
    default: false,
  },
  rank: {
    type: Number,
    required: false,
    default: 0,
  },
  isWriter: {
    type: Boolean,
    required: true,
  },
  editable: {
    type: Boolean,
    required: false,
    default: false,
  },
});
const emits = defineEmits(["reactComment", "edit", "delete"]);

const { isLoggedIn } = useAuth();
const { reactedComments, getReactedComment, checkReacted } = useInteraction();
const { buttonMsg, openMessageBox, labelMsg } = useMessageBox();

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
      option ? fillThumbs(thumbsUp) : fillThumbs(thumbsDown);
      emits(
        "reactComment",
        props.comment.commentId,
        option,
        !!reactedComment.value
      );
    })
    .catch((error) => {
      openMessageBox(error);
    });
};

watch(
  () => reactedComments.value,
  async () => {
    await getReactedComment(props.comment.commentId).then((response) => {
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
  <div>
    <div class="sub d-flex justify-content-start">
      <i v-if="isReply" class="bi bi-arrow-return-right me-2"></i>
      <el-badge v-if="isBest" class="me-1 mb-2" :value="`TOP ${rank}`" />
      <div :class="`avatar ${isReply ? 'pt-1' : ''}`">
        <el-avatar :size="25" :src="comment.profile.image" />
      </div>
      <div class="writer">
        {{ comment.profile.nickname }}
      </div>
      <div :class="`elapsedTime ${isReply ? 'pb-1' : 'pb-2'}`">
        {{ comment.elapsedTime }}
      </div>
      <div class="like ms-2">
        <span class="icon">
          <i
            v-if="isBest"
            :class="`bi ${thumbsUp}`"
            class="me-1"
            style="color: #535252"
          />
          <BootstrapIcon
            v-else
            :class="{ active: !isBest }"
            :icon="thumbsUp"
            class="me-1"
            @click="reactComment(true)"
          />
          {{ comment.numberOfLikes }}
        </span>
        <span class="icon">
          <i
            v-if="isBest"
            :class="`bi ${thumbsDown}`"
            class="me-1"
            style="color: #535252"
          />
          <BootstrapIcon
            v-else
            :icon="thumbsDown"
            class="me-1"
            @click="reactComment(false)"
          />
          {{ comment.numberOfDislikes }}
        </span>
      </div>
      <el-tag v-if="isWriter" size="small">
        {{ labelMsg("comment.writer") }}
      </el-tag>
      <div class="flex-grow-1"></div>
      <div class="edit-button" v-if="!isBest && isLoggedIn && editable">
        <el-button class="button" text size="small" @click="emits('edit')">
          {{ buttonMsg("edit") }}
        </el-button>
        <el-button class="button" text size="small" @click="emits('delete')">
          {{ buttonMsg("delete") }}
        </el-button>
      </div>
    </div>
    <div
      class="description"
      :style="{
        marginLeft: isBest ? '50px' : '',
      }"
    >
      {{ comment.comment }}
    </div>
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

    .icon {
      margin-right: 5px;
    }
  }

  .edit-button {
    display: flex;
    justify-content: center;

    .button {
      margin: 0;
    }
  }
}

.description {
  font-size: 1rem;
  margin-top: 8px;
  color: #737373;
}
</style>
