import { nextTick, ref } from "vue";
import type { ElInput } from "element-plus";
import { useReview } from "@/composables/review/review";

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

  const handleClose = (tag: string) => {
    dynamicTags.value.splice(dynamicTags.value.indexOf(tag), 1);
  };
  const validationCheck = (input: string) => {
    return !/([^a-zA-Z0-9가-힣\x20])/i.test(input);
  };

  const duplicationCheck = (input: string) => {
    return !dynamicTags.value.includes(input);
  };

  const handleInputConfirm = () => {
    if (
      tagInput.value &&
      duplicationCheck(tagInput.value) &&
      validationCheck(tagInput.value)
    ) {
      dynamicTags.value.push(tagInput.value);
    }
    tagInputVisible.value = false;
    tagInput.value = "";
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
    handleClose,
    handleInputConfirm,
    clearTags,
  };
};
