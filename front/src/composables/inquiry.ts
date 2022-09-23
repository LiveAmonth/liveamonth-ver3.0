import { computed, ref } from "vue";
import { useInquiryStore } from "@/stores/inquiry";
import type InquiryEditor from "@/modules/class/member/InquiryEditor";
import type {
  PageableRequestType,
  PageableType,
} from "@/modules/types/common/PageableType";
import type { EnumType } from "@/modules/types/common/EnumType";
import type {
  InquiryListType,
  InquiryType,
} from "@/modules/types/member/MemberType";

export const useInquiry = () => {
  const store = useInquiryStore();
  const error = ref();
  const isPending = ref(false);

  const category = computed((): EnumType[] => store.inquiryCategory);
  const inquiries = computed((): InquiryListType[] => store.inquiries);
  const inquiryPage = computed((): PageableType => store.inquiryPage);
  const currInquiry = computed((): InquiryType => store.currInquiry);

  const getCategory = async () => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getInquiryCategory();
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const writeInquiry = async (request: InquiryEditor) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.writeInquiry(request);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const getInquires = async (pageable: PageableRequestType) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getInquiries(pageable);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const getInquiry = async (id: number) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getInquiry(id);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const editInquiry = async (inquiryId: number, form: InquiryEditor) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.editInquiry(inquiryId, form);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const deleteInquiry = async (inquiryId: number) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.deleteInquiry(inquiryId);
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
    category,
    inquiries,
    inquiryPage,
    currInquiry,
    getCategory,
    writeInquiry,
    getInquires,
    getInquiry,
    editInquiry,
    deleteInquiry,
  };
};
