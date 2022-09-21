import type { NameIconType } from "@/modules/types/member/MemberType";
import { useI18n } from "vue-i18n";

export const tabs = () => {
  const { t } = useI18n();

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

  const getMyPageMenuCategory = (code: string, icon: string): NameIconType => {
    return {
      code: code,
      value: t(`myPage.${code}.title`),
      icon: icon,
    };
  };

  return {
    getTabsItem,
    getMyPageMenuCategory,
  };
};
