import { computed, ref } from "vue";
import { useCommentStore } from "@/stores/common/comment";
import type {
  PageableRequestType,
  PageableType,
} from "@/modules/types/common/PageableType";
import type {
  CommentType,
  CommentEditor,
} from "@/modules/types/comment/CommentTypes";

export const useComment = () => {
  const store = useCommentStore();
  const error = ref();
  const isPending = ref<boolean>(false);

  const comments = computed((): CommentType[] => store.comments);
  const commentPageable = computed((): PageableType => store.commentPage);

  const writeComment = async (type: string, request: CommentEditor) => {
    try {
      await store.writeComment(type, request);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const editComment = async (
    type: string,
    commentId: number,
    request: CommentEditor
  ) => {
    try {
      await store.editComment(type, commentId, request);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const deleteComment = async (type: string, commentId: number) => {
    try {
      await store.deleteComment(type, commentId);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

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
  return {
    error,
    isPending,
    comments,
    commentPageable,
    writeComment,
    editComment,
    deleteComment,
    extractIds,
    getComments,
  };
};
