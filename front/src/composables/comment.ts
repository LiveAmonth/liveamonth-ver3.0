import { computed, ref } from "vue";
import { useCommentStore } from "@/stores/comment";
import type { PageableRequestType } from "@/modules/types/common/PageableType";
import type { CommentType } from "@/modules/types/comment/CommentTypes";
import type { TokenType } from "@/modules/types/auth/AuthType";
import type { WriteCommentType } from "@/modules/types/comment/CommentTypes";
import { useAuth } from "@/composables/auth";
import type { CommentFormType } from "@/modules/types/form/FormType";

export const useComment = () => {
  const store = useCommentStore();
  const error = ref();
  const { getTokenInfo } = useAuth();
  const isPending = ref<boolean>(false);
  const comments = computed((): CommentType[] => store.comments);
  const commentsCount = computed(
    (): number => store.pageableComments.totalElements
  );

  const getComments = async (
    path: string,
    contentId: number,
    pageable: PageableRequestType
  ) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getComments(path, contentId, pageable);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const writeComment = async (
    path: string,
    contentId: number,
    commentId = 0,
    request: CommentFormType
  ) => {
    try {
      await store.writeComment(
        path,
        contentId,
        commentId,
        getTokenInfo.value,
        request
      );
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
