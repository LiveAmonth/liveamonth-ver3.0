import { createApp } from "vue";
import { createPinia } from "pinia";

import App from "./App.vue";
import router from "./router";

import "normalize.css";

import ElementPlus from "element-plus";
import "element-plus/dist/index.css";
import koKR from "element-plus/es/locale/lang/ko";
import * as ElementPlusIconsVue from "@element-plus/icons-vue";

import "bootstrap/dist/css/bootstrap-utilities.min.css";
import "bootstrap-icons/font/bootstrap-icons.css";
import i18n from "@/i18n";

import VCalender from "v-calendar";
import "v-calendar/dist/style.css";

const app = createApp(App);

app.use(createPinia());
app.use(VCalender, {});
app.use(ElementPlus, {
  locale: koKR,
});
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component);
}
app.use(router);
app.use(i18n);
app.config.globalProperties.$filters = {
  makeComma(value: number) {
    return String(value).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
  },
};
app.mount("#app");
