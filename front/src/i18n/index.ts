import ko from "@/locales/ko_KR.json";
import en from "@/locales/en_US.json";
import {createI18n} from "vue-i18n";

const i18n = createI18n({
    locale: "ko",
    fallbackLocale: "en",
    messages: {ko, en}
});
export default i18n;
