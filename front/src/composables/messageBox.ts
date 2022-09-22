import { ElMessageBox } from "element-plus/es";
import { ElMessage } from "element-plus";
import { useRouter } from "vue-router";
import { useI18n } from "vue-i18n";

export const useMessageBox = () => {
  const router = useRouter();
  const { t } = useI18n();
  const openMessage = (message: string) => {
    ElMessage({
      message: message,
      type: "success",
    });
  };
  const openMessageByCode = (key: string) => {
    ElMessage({
      message: t(key),
      type: "success",
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

  const requireLoginMessageBox = () => {
    return ElMessageBox.confirm(t("form.message.login"), t("member.login"), {
      confirmButtonText: "OK",
      cancelButtonText: "Cancel",
    })
      .then(async () => {
        await router.push({ name: "login" });
      })
      .catch(() => {
        console.log("취소");
      });
  };

  const getTitleMsg = (field: string): string => {
    return t(`title.${field}`);
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

  return {
    openMessage,
    openMessageByCode,
    openMessageBox,
    openConfirmMessageBox,
    requireLoginMessageBox,
    labelMsg: getFormLabelMsg,
    buttonMsg: getFormButtonMsg,
    resultMsg: getResultMessage,
    titleMsg: getTitleMsg,
  };
};
