import { ElMessageBox } from "element-plus/es";

export const useMessageBox = () => {
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

  return {
    openMessageBox,
    openConfirmMessageBox,
  };
};
