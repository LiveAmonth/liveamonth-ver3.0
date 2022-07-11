<template>
  <el-row>
    <el-col :span="6">
        <el-image
            style="width: auto; height: auto"
            :src="`/src/assets/image/intro/${props.image}`"
            fit="cover"
        />

    </el-col>
    <el-col :span="18">
      <div class="content">
        {{ props.content }}
      </div>
    </el-col>
  </el-row>
</template>

<script setup lang="ts">
import {defineProps, ref} from "vue";

const props = defineProps({
  content: {
    type: String,
    required: true,
  },
  image: {
    type: String,
    required: true,
  },
});

</script>

<style scoped lang="scss">
.content {
  font-size: 0.95rem;
  margin-left: 10px;
  color: #535252;
  white-space: break-spaces;
  line-height: 1.8;
}
</style>
