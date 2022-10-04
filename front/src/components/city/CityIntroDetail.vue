<script lang="ts" setup>
import { useCity } from "@/composables/city/city";

const { hasCityIntro, cityIntroDetail } = useCity();
</script>

<template>
  <el-row v-if="hasCityIntro">
    <template v-for="introDetail in cityIntroDetail" :key="introDetail">
      <el-col :span="6">
        <el-image
          :src="`/src/assets/image/intro/${introDetail.image}`"
          fit="cover"
          style="width: auto; height: auto; box-shadow: var(--el-box-shadow)"
        />
      </el-col>
      <el-col :span="18">
        <div class="content">
          {{ introDetail.content }}
        </div>
      </el-col>
    </template>
  </el-row>
</template>

<style lang="scss" scoped>
.content {
  font-size: 0.95rem;
  margin-left: 10px;
  color: #535252;
  white-space: break-spaces;
  line-height: 1.8;
}
</style>
