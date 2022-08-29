import { computed, ref } from "vue";
import { useCommentStore } from "@/stores/comment";
import type { PageableRequestType } from "@/modules/types/common/PageableType";
import type { CommentType } from "@/modules/types/comment/CommentTypes";
import type { CommentFormType } from "@/modules/types/form/FormType";

export const useComment = () => {
  const store = useCommentStore();
  const error = ref();
  const isPending = ref<boolean>(false);
  const comments = computed((): CommentType[] => store.comments);
  const commentsCount = computed(
    (): number => store.pageableComments.totalElements
  );

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
    contentId: number,
    commentId: number,
    request: CommentFormType
  ) => {
    try {
      await store.writeComment(type, contentId, commentId, request);
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
    getComments,
    writeComment,
  };
};
