import { computed, ref } from "vue";
import { useMemberStore } from "@/stores/member/member";
import { useFormValidate } from "@/composables/common/formValidate";
import { useMessageBox } from "@/composables/common/messageBox";
import type {
  FindIdType,
  FindPwType,
  ReconfirmType,
  FoundIdType,
  ProfileType,
  SimpleProfileType,
  ChangePasswordEditor,
  ProfileEditor,
  MemberCreate,
} from "@/modules/types/member/MemberTypes";
import type { FormInstance } from "element-plus";
import { DomainType } from "@/modules/enums/constants";

export const useMember = () => {
  const type: string = DomainType.MEMBER;
  const store = useMemberStore();
  const { isAvailable, duplicateCheck } = useFormValidate();
  const { openMessageBox, labelMsg, validationMsg } = useMessageBox();

  const error = ref();
  const isPending = ref(false);

  const simpleProfile = computed((): SimpleProfileType => store.simpleProfile);
  const memberProfile = computed((): ProfileType => store.memberProfile);
  const foundId = computed((): FoundIdType => store.foundId);

  const checkField = async (
    formEl: FormInstance,
    form: MemberCreate | ProfileEditor,
    field: string
  ) => {
    const value = form[field];
    if (value.length === 0) {
      await openMessageBox(
        validationMsg("require.text", {
          field: labelMsg(`member.${field}`),
        })
      );
    } else if (!(await isValidate(formEl, field))) {
      await openMessageBox(validationMsg(`pattern.${field}`));
    } else {
      await duplicateCheck(field, value);
      if (!isAvailable.value) {
        await openMessageBox(
          validationMsg("duplication.duplicated", {
            value: value,
            field: labelMsg(`member.${field}`),
          })
        );
        form[field] = "";
      } else {
        form.checkForm[field] = true;
      }
    }
  };

  const resetField = async (
    form: MemberCreate | ProfileEditor,
    field: string
  ) => {
    form.checkForm[field] = !form.checkForm[field];
    form[field] = "";
  };

  const isValidate = async (
    formEl: FormInstance,
    field: string
  ): Promise<boolean> => {
    return await formEl
      .validateField(field)
      .then(() => {
        return true;
      })
      .catch(() => {
        return false;
      });
  };

  const signUp = async (request: MemberCreate) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.signUp(request);
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const editProfile = async (request: ProfileEditor) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.editProfile(request);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const findId = async (request: FindIdType) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.findId(request);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const reconfirm = async (request: ReconfirmType) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.reconfirm(request);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const changePassword = async (request: ChangePasswordEditor) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.changePassword(request);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const dropMember = async () => {
    error.value = null;
    isPending.value = true;
    try {
      await store.dropMember();
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const findPw = async (request: FindPwType) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.findPw(request);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const getMember = async () => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getMember();
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const getSimpleProfile = async () => {
    error.value = null;
    isPending.value = true;
    try {
      await store.getSimpleProfile();
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const editProfileImage = async (form: FormData) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.editProfileImage(form);
      error.value = null;
    } catch (err) {
      error.value = err;
    } finally {
      isPending.value = false;
    }
  };

  const isLoggedInMemberPost = (writerId: number) => {
    return writerId == simpleProfile.value.id;
  };
  return {
    error,
    isPending,
    type,
    simpleProfile,
    memberProfile,
    foundId,
    signUp,
    reconfirm,
    changePassword,
    dropMember,
    checkField,
    resetField,
    isValidate,
    editProfile,
    findId,
    findPw,
    getMember,
    getSimpleProfile,
    editProfileImage,
    isLoggedInMemberPost,
  };
};
