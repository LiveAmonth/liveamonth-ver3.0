import { useI18n } from "vue-i18n";
import type { NameIconType } from "@/modules/types/common/MenuTypes";
import { useMessageBox } from "@/composables/common/messageBox";

export const useMenuTab = () => {
  const { t } = useI18n();
  const { menuMsg } = useMessageBox();

  const getTabsItem = (
    dir: string,
    code: string,
    icon: string
  ): NameIconType => {
    return {
      code: code,
      value: t(`tabs.${dir}.${code}`),
      icon: icon,
    };
  };

  const getMenuCategory = (
    dir: string,
    code: string,
    icon: string
  ): NameIconType => {
    return {
      code: code,
      value: menuMsg(`${dir}.${code}.title`),
      icon: icon,
    };
  };

  return {
    getTabsItem,
    getMenuCategory,
  };
};
