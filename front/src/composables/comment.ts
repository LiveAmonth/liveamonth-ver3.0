import { computed, ref } from "vue";
import { useCommentStore } from "@/stores/comment";
import type { PageableRequestType } from "@/modules/types/common/PageableType";
import type { CommentType } from "@/modules/types/comment/CommentTypes";

export const useComment = () => {
  const store = useCommentStore();
  const error = ref();
  const isPending = ref<boolean>(false);
  const comments = computed((): CommentType[] => store.comments);
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

  return {
    error,
    isPending,
    comments,
    getComments,
  };
};
