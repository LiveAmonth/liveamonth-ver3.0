<script lang="ts" setup>
import { useRouter } from "vue-router";
import { useHome } from "@/composables/home/home";
import { useMessageBox } from "@/composables/common/messageBox";

const emits = defineEmits(["menuClick"]);

const router = useRouter();
const { mainMenus } = useHome();
const { menuMsg } = useMessageBox();

const handleSelect = (key: string, keyPath: string[]) => {
  const menu = mainMenus.value[Number(keyPath[0])];
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
    background-color="#fafafa"
    class="header-content"
    mode="horizontal"
    style="
      --el-menu-hover-bg-color: #fafafa;
      --el-menu-hover-text-color: #007f95;
    "
    text-color="#5d5d5d"
    @select="handleSelect"
  >
    <template v-for="(menu, idx) in mainMenus" :key="menu.name">
      <el-sub-menu v-if="menu.sub.length" :index="String(idx)">
        <template #title>{{ menuMsg(`header.${menu.name}`) }}</template>
        <template v-for="(sub, sIdx) in menu.sub" :key="sub.name">
          <el-menu-item
            :index="`${idx}-${sIdx}`"
            style="
              --el-menu-hover-bg-color: #fafafa;
              --el-menu-hover-text-color: #007f95;
            "
          >
            {{ menuMsg(`header.${sub.name}`) }}
          </el-menu-item>
        </template>
      </el-sub-menu>
      <el-menu-item v-else :index="String(idx)">
        {{ menuMsg(`header.${menu.name}`) }}
      </el-menu-item>
    </template>
  </el-menu>
</template>

<style lang="scss">
.header-content {
  box-shadow: 0 2px 4px 0 hsl(0deg 0% 81% / 50%);
  border-top: 1px solid #f1f3f5;

  .el-menu-item {
    font-size: 1rem;
    font-weight: 400;

    &:hover {
      font-weight: bold;
      color: #007f95;
    }
  }
}

.el-sub-menu {
  .el-sub-menu__title {
    font-size: 1rem;
    font-weight: 400;
  }
}
</style>
