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
import i18n from "@/i18n";

// import VueCookies from "vue3-cookies";

const app = createApp(App);

app.use(createPinia());
// app.use(VueCookies, {
//   expireTimes: "7d",
//   secure: true,
// });
app.use(ElementPlus, {
  locale: koKR,
});
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component);
}
app.use(router);
app.use(i18n);
app.mount("#app");
