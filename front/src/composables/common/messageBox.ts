import { ElMessageBox } from "element-plus/es";
import { ElMessage } from "element-plus";
import { useRoute, useRouter } from "vue-router";
import { useI18n } from "vue-i18n";
import type { NamedValue } from "@intlify/core-base";

export const useMessageBox = () => {
  const router = useRouter();
  const route = useRoute();
  const { t } = useI18n();

  const openMessage = (message: string) => {
    ElMessage({
      message: message,
      type: "success",
    });
  };

  const openWarningMessage = (message: string) => {
    ElMessage({
      message: message,
      type: "warning",
    });
  };

  const openMessageByCode = (key: string) => {
    ElMessage({
      message: t(key),
      type: "success",
    });
  };

  const openWarningMessageByCode = (key: string) => {
    ElMessage({
      message: t(key),
      type: "warning",
    });
  };

  const openMessageBox = async (message: string) => {
    await ElMessageBox.alert(message);
  };

  const openConfirmMessageBox = async (title: string, message: string) => {
    return await ElMessageBox.confirm(message, title, {
      confirmButtonText: "OK",
      cancelButtonText: "Cancel",
      type: "info",
    });
  };
  const requireLoginMessageBox = (path = route.path) => {
    return ElMessageBox.confirm(
      t("form.message.login"),
      t("form.button.member.login"),
      {
        confirmButtonText: "OK",
        cancelButtonText: "Cancel",
      }
    )
      .then(async () => {
        await router.push({ name: "login", query: { returnUrl: path } });
      })
      .catch(() => {
        console.log("취소");
      });
  };
  const getCategoryMsg = (field: string, type: string): string => {
    return t(`category.${field}.${type.toLowerCase()}`);
  };
  const getTitleMsg = (field: string, param = ""): string => {
    return t(`title.${field}`, param);
  };
  const getFormLabelMsg = (field: string): string => {
    return t(`form.label.${field}`);
  };
  const getFormButtonMsg = (field: string): string => {
    return t(`form.button.${field}`);
  };
  const getResultMessage = (field: string): string => {
    return t(`form.message.${field}`);
  };
  const getMenuName = (field: string): string => {
    return t(`menu.${field}`);
  };
  const getBannerMessage = (field: string): string => {
    return t(`banner.${field}`);
  };
  const getTabMessage = (field: string): string => {
    return t(`tabs.${field}`);
  };
  const getValidationMessage = (
    field: string,
    param: NamedValue = {}
  ): string => {
    return t(`validation.${field}`, param);
  };

  const getPlaceholder = (field: string) => {
    return t(`placeholder.${field}`);
  };

  const getInputPlaceHolder = (field: string): string => {
    return t("placeholder.please-input", {
      field: field,
    });
  };

  const getBeforeWritePlaceHolder = (field: string): string => {
    return t("placeholder.before-input", {
      field: field,
    });
  };

  const getSelectPlaceHolder = (field: string): string => {
    return t("placeholder.please-select", {
      field: field,
    });
  };

  return {
    openMessage,
    openWarningMessage,
    openMessageByCode,
    openWarningMessageByCode,
    openMessageBox,
    openConfirmMessageBox,
    requireLoginMessageBox,
    categoryMsg: getCategoryMsg,
    labelMsg: getFormLabelMsg,
    buttonMsg: getFormButtonMsg,
    resultMsg: getResultMessage,
    titleMsg: getTitleMsg,
    menuMsg: getMenuName,
    bannerMsg: getBannerMessage,
    tabMsg: getTabMessage,
    inputPhMsg: getInputPlaceHolder,
    selectPhMsg: getSelectPlaceHolder,
    beforeWritePhMsg: getBeforeWritePlaceHolder,
    placeholderMsg: getPlaceholder,
    validationMsg: getValidationMessage,
  };
};
