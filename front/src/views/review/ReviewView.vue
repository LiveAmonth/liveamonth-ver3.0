<script lang="ts" setup>
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import { useMessageBox } from "@/composables/common/messageBox";

const { titleMsg } = useMessageBox();
</script>

<template>
  <el-row class="container">
    <el-col :span="4">
      <el-menu
        active-text-color="#016D7D"
        background-color="#ffffff"
        class="review-menu"
        text-color="#111111"
      >
        <SmallTitleSlot :title-line="false" class="ps-2">
          <el-icon class="pb-1 me-1">
            <Place />
            <!--            <component :is="cat.category.icon" />-->
          </el-icon>
          {{ $t(`myPage.${cat.category.code}.title`) }}
        </SmallTitleSlot>
      </el-menu>
    </el-col>
    <el-col :span="18" class="main-content">
      <el-row>
        <el-col>
          <el-menu></el-menu>
        </el-col>
      </el-row>
      <el-row>
        <el-col>
          <RouterView />
        </el-col>
      </el-row>
    </el-col>
  </el-row>
</template>

<style lang="scss" scoped>
.container {
  display: flex;
  justify-content: center;

  .main-content {
    display: flex;
    flex-direction: column;
  }
}
</style>
