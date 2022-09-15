<script lang="ts" setup>
import { usePagination } from "@/composables/pagination";
import {
  DArrowLeft,
  ArrowLeft,
  DArrowRight,
  ArrowRight,
} from "@element-plus/icons-vue";

const props = defineProps({
  paginationType: {
    type: String,
    required: true,
  },
});

const {
  pagination,
  getCurrentPageGroupPages,
  isCurrentPage,
  getCurrentPageNumber,
} = usePagination(props.paginationType);

const emit = defineEmits(["click"]);
const onClick = (page: number) => {
  emit("click", page);
};
</script>

<template>
  <div class="paging">
    <ul class="paging_point">
      <template v-if="!pagination.isFirst">
        <li class="paging-side">
          <button class="btn-paging prev" type="button" @click="onClick(1)">
            <el-icon>
              <DArrowLeft />
            </el-icon>
          </button>
        </li>
        <li class="paging-side">
          <button
            class="btn-paging prev"
            type="button"
            @click="onClick(pagination.currentPage - 1)"
          >
            <el-icon>
              <ArrowLeft />
            </el-icon>
          </button>
        </li>
      </template>
      <li
        v-for="page in getCurrentPageGroupPages()"
        :key="page"
        :class="isCurrentPage(page) ? 'on' : ''"
      >
        <a
          :title="getCurrentPageNumber(page) + '페이지 선택'"
          style="cursor: pointer"
          @click="onClick(getCurrentPageNumber(page))"
        >
          {{ getCurrentPageNumber(page) }}</a
        >
      </li>
      <template v-if="!pagination.isLast">
        <li class="paging-side">
          <button
            class="btn-paging next"
            type="button"
            @click="onClick(pagination.currentPage + 1)"
          >
            <el-icon>
              <ArrowRight />
            </el-icon>
          </button>
        </li>
        <li class="paging-side">
          <button
            class="btn-paging next"
            type="button"
            @click="onClick(pagination.numberOfPages)"
          >
            <el-icon>
              <DArrowRight />
            </el-icon>
          </button>
        </li>
      </template>
    </ul>
  </div>
</template>

<style lang="scss" scoped>
/* Paging Button */
.btn-paging {
  display: inline-block;
  padding: 0;
  min-width: 20px;
  min-height: 20px;
  background-color: white;
  border: none;

  .el-icon {
    font-size: 20px;
  }

  &:hover,
  &:active {
    cursor: pointer;

    .el-icon {
      color: teal;
      font-size: 25px;
    }
  }
}

/* =============================
 Paging Style
============================= */
.paging {
  text-align: center;
  margin-top: 25px;

  > ul {
    display: inline-block;
    vertical-align: top;
  }

  li {
    display: inline-block;
    padding: 0 8px;
    font-family: Verdana, Geneva, sans-serif;
    font-weight: 400;
    font-size: 16px;
    color: #000;
    line-height: 40px;

    &.on,
    a:hover,
    a:focus {
      color: teal;
      font-size: 19px;
    }

    &.paging-side {
      padding: 0;
      vertical-align: top;
    }
  }
}
</style>
