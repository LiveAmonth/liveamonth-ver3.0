import { ElMessageBox } from "element-plus/es";
import { useRouter } from "vue-router";

export const useMessageBox = () => {
  const router = useRouter();

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
    return ElMessageBox.confirm(
      "로그인 하시겠습니까? 확인을 누르시면 로그인 페이지로 이동합니다.",
      "로그인",
      {
        confirmButtonText: "OK",
        cancelButtonText: "Cancel",
      }
    )
      .then(async () => {
        await router.push({ name: "login" });
      })
      .catch(() => {
        console.log("취소");
      });
  };

  return {
    openMessageBox,
    openConfirmMessageBox,
    requireLoginMessageBox,
  };
};
