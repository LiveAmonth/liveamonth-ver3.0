import { ElMessageBox } from "element-plus/es";

export const useMessageBox = () => {
  const openMessageBox = async (message: string) => {
    await ElMessageBox.alert(message);
  };

  return {
    openMessageBox,
  };
};
