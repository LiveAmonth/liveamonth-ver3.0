import {fileURLToPath, URL} from "url";

import {defineConfig} from "vite";
import vue from "@vitejs/plugin-vue";
import vueJsx from "@vitejs/plugin-vue-jsx";

// https://vitejs.dev/config/
export default defineConfig({
    plugins: [vue(), vueJsx()],
    resolve: {
        alias: {
            "@": fileURLToPath(new URL("./src", import.meta.url)),
        },
    },
    server: {
        proxy: {
            "/lam-api": {
                target: "http://localhost:8080/api/v1",
                rewrite: (path) => path.replace(/^\/lam-api/, "")
            },
        }
    }
});
