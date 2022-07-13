<script setup lang="ts">
import { ref } from "vue";
import { useCityStore } from "@/stores/city";
import type { ImageContentType } from "@/modules/types/common/ImageContentType";

const store = useCityStore();

const cityIntroDetail = ref<ImageContentType>(store.introDetail);
</script>

<template>
  <el-row>
    <el-col :span="6">
      <el-image
        style="width: auto; height: auto;box-shadow: var(--el-box-shadow)"
        :src="`/src/assets/image/intro/${cityIntroDetail.image}`"
        fit="cover"
      />
    </el-col>
    <el-col :span="18">
      <div class="content">
        {{ cityIntroDetail.content }}
      </div>
    </el-col>
  </el-row>
</template>

<style scoped lang="scss">
.content {
  font-size: 0.95rem;
  margin-left: 10px;
  color: #535252;
  white-space: break-spaces;
  line-height: 1.8;
}
</style>
