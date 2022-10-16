import { createApp } from "vue";

import { createPinia } from "pinia";
import piniaPluginPersistedstate from "pinia-plugin-persistedstate";

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

import { QuillEditor } from "@vueup/vue-quill";
import "@vueup/vue-quill/dist/vue-quill.snow.css";
import "@vueup/vue-quill/dist/vue-quill.bubble.css";

import { numberFormat } from "@/global/unit";

const pinia = createPinia();
pinia.use(piniaPluginPersistedstate);

const app = createApp(App);
app.use(pinia);
app.use(VCalender, {});
app.use(ElementPlus, {
  locale: koKR,
});
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component);
}
app.use(router);
app.use(i18n);
app.component("QuillEditor", QuillEditor);
app.mount("#app");
app.config.globalProperties.$comma = numberFormat.comma;
app.config.globalProperties.$count = numberFormat.count;
app.config.globalProperties.$won = numberFormat.won;
