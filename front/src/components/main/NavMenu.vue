<script lang="ts" setup>
import { useRouter } from "vue-router";
import { useHome } from "@/composables/home/home";
import { useMessageBox } from "@/composables/common/messageBox";

const emits = defineEmits(["menuClick"]);
const router = useRouter();

const { mainMenus } = useHome();
const { menuMsg } = useMessageBox();

const handleSelect = (key: string, keyPath: string[]) => {
  const menu = mainMenus[Number(keyPath[0])];
  if (keyPath.length === 1) {
    emits("menuClick", menu.name);
    router.push(menu.route);
  } else {
    const sub = menu.sub[Number(keyPath[1].split("-")[1])];
    emits("menuClick", sub.name);
    router.push(sub.route);
  }
};
</script>
<template>
  <el-menu
    active-text-color="#0f6778"
    class="header-content"
    mode="horizontal"
    text-color="#0f6778"
    @select="handleSelect"
  >
    <template v-for="(menu, idx) in mainMenus" :key="menu.name">
      <el-sub-menu v-if="menu.sub.length" :index="String(idx)">
        <template #title>{{ menuMsg(menu.name) }}</template>
        <template v-for="(sub, sIdx) in menu.sub" :key="sub.name">
          <el-menu-item :index="`${idx}-${sIdx}`">
            {{ menuMsg(sub.name) }}
          </el-menu-item>
        </template>
      </el-sub-menu>
      <el-menu-item v-else :index="String(idx)">
        {{ menuMsg(menu.name) }}
      </el-menu-item>
    </template>
  </el-menu>
</template>

<style scoped></style>
