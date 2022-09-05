import { computed, ref } from "vue";
import { useCommentStore } from "@/stores/comment";
import type { PageableRequestType } from "@/modules/types/common/PageableType";
import type {
  CommentType,
  WriteCommentType,
} from "@/modules/types/comment/CommentTypes";

export const useComment = () => {
  const store = useCommentStore();
  const error = ref();
  const isPending = ref<boolean>(false);

  const comments = computed((): CommentType[] => store.comments);
  const commentsCount = computed(
    (): number => store.pageableComments.totalElements
  );

  const extractIds = (arrays: CommentType[]) => {
    const ids: number[] = [];
    arrays.forEach((value) => ids.push(value.commentId));
    arrays.map((value) =>
      value.commentReplies.forEach((value1) => ids.push(value1.commentId))
    );
    return ids;
  };

  const getComments = async (
    type: string,
    contentId: number,
    pageable: PageableRequestType
  ) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getComments(type, contentId, pageable);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const writeComment = async (
    type: string,
    loginId: string,
    request: WriteCommentType
  ) => {
    try {
      await store.writeComment(type, loginId, request);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };
  return {
    error,
    isPending,
    comments,
    commentsCount,
    extractIds,
    getComments,
    writeComment,
  };
};
