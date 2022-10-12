import { nextTick, ref } from "vue";
import { useReview } from "@/composables/review/review";
import type { ElInput } from "element-plus";

export const useSearch = () => {
  const { request } = useReview();
  const dynamicTags = ref<string[]>(request.value.tags);
  const tagInput = ref<string>("");
  const tagInputVisible = ref(false);
  const InputRef = ref<InstanceType<typeof ElInput>>();

  const showInput = async () => {
    tagInputVisible.value = true;
    await nextTick(() => {
      InputRef.value?.input?.focus();
    });
  };

  const pushTag = (tag: string) => {
    dynamicTags.value.push(tag);
  };

  const handleClose = (tag: string) => {
    dynamicTags.value.splice(dynamicTags.value.indexOf(tag), 1);
  };

  const validationCheck = (input: string) => {
    return !/([^a-zA-Z0-9가-힣\x20])/i.test(input);
  };

  const duplicationCheck = (tags: string[], input: string) => {
    return !tags.includes(input);
  };

  const clearTags = () => {
    request.value.tags = [];
    dynamicTags.value = [];
  };

  return {
    dynamicTags,
    tagInput,
    tagInputVisible,
    showInput,
    pushTag,
    handleClose,
    duplicationCheck,
    validationCheck,
    clearTags,
  };
};
