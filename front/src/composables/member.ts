import { computed, ref } from "vue";
import { useMemberStore } from "@/stores/member";
import { useAuth } from "@/composables/auth";
import type {
  EditProfileType,
  FindIdType,
  FindPwType,
  SignUpType,
} from "@/modules/types/form/FormType";
import type {
  FoundIdType,
  ProfileType,
  SimpleProfileType,
} from "@/modules/types/member/MemberType";
import type { FormInstance } from "element-plus";
import { useFormValidate } from "@/composables/formValidate";
import { useMessageBox } from "@/composables/messageBox";
import { useI18n } from "vue-i18n";
import type ProfileEditor from "@/modules/class/member/ProfileEditor";
import type MemberEditor from "@/modules/class/member/MemberEditor";

export const useMember = () => {
  const store = useMemberStore();
  const { isAvailable, duplicateCheck } = useFormValidate();
  const { openMessageBox, openConfirmMessageBox } = useMessageBox();
  const { t } = useI18n();

  const error = ref();
  const isPending = ref(false);
  const { bearerToken } = useAuth();

  const simpleProfile = computed((): SimpleProfileType => store.simpleProfile);
  const memberProfile = computed((): ProfileType => store.memberProfile);

  const foundId = computed((): FoundIdType => store.foundId);

  const checkField = async (
    formEl: FormInstance,
    form: MemberEditor | ProfileEditor,
    field: string
  ) => {
    const value = form[field];
    if (value.length === 0) {
      await openMessageBox(
        t("validation.require.text", { field: t(`member.${field}`) })
      );
    } else if (!(await isValidate(formEl, field))) {
      await openMessageBox(t(`validation.pattern.${field}`));
    } else {
      await duplicateCheck(field, value);
      if (!isAvailable.value) {
        await openMessageBox(
          t("validation.duplication.duplicated", {
            value: value,
            field: t(`member.${field}`),
          })
        );
        form[field] = "";
      } else {
        await openConfirmMessageBox(
          t("validation.duplication.button"),
          t("validation.duplication.confirm", {
            field: t(`member.${field}`),
          })
        )
          .then(() => {
            form.checkForm[field] = true;
          })
          .catch(() => {
            form[field] = "";
          });
      }
    }
  };

  const resetField = async (
    form: SignUpType | EditProfileType,
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

  const signUp = async (request: SignUpType) => {
    error.value = null;
    isPending.value = true;
    try {
      await store.signUp(request);
      error.value = null;
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
      await store.getMember(bearerToken.value);
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
      await store.getSimpleProfile(bearerToken.value);
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
    simpleProfile,
    memberProfile,
    foundId,
    signUp,
    checkField,
    resetField,
    editProfile,
    findId,
    findPw,
    getMember,
    getSimpleProfile,
  };
};
