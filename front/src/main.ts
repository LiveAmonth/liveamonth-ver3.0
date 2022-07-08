import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
import router from "./router";

import "normalize.css";

import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
import { ElConfigProvider } from "element-plus";
import koKR from "element-plus/lib/locale/lang/ko";

import "bootstrap/dist/css/bootstrap-utilities.min.css";

import { createI18n } from "vue-i18n";
import messages from "./i18n";

const app = createApp(App);
const i18n = createI18n({
  locale: "ko",
  fallbackLocale: "en",
  messages,
});

app.use(createPinia());
app.use(ElementPlus, {
  locale: koKR
});
app.use(router);
app.use(i18n);
app.mount("#app");
