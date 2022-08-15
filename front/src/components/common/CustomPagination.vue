<script setup lang="ts">
import type { CustomPaginationType } from "@/modules/types/common/PageableType";
import { usePagination } from "@/composables/pagination";

const { isCurrPage, calcNumberOfPageGroup, getCurrPageNumber } =
  usePagination();

defineProps({
  pagination: {
    type: Object as () => CustomPaginationType,
    required: true,
  },
});
const emit = defineEmits(["click"]);
const onClick = (page: number) => {
  emit("click", page);
};
</script>

<template>
  <div class="paging">
    <ul class="paging_point">
      <template v-if="!pagination.isFirst.value">
        <li class="paging-side">
          <button class="btn-paging prev" @click="onClick(1)" type="button">
            <el-icon>
              <DArrowLeft />
            </el-icon>
          </button>
        </li>
        <li class="paging-side">
          <button
            class="btn-paging prev"
            @click="onClick(pagination.currentPage.value - 1)"
            type="button"
          >
            <el-icon>
              <ArrowLeft />
            </el-icon>
          </button>
        </li>
      </template>
      <li
        v-for="page in calcNumberOfPageGroup(
          pagination.pageGroup.value,
          pagination.numberOfPageGroup.value,
          pagination.numberOfPages.value
        )"
        :key="page"
        :class="
          isCurrPage(
            pagination.pageGroup.value,
            pagination.currentPage.value,
            page
          )
            ? 'on'
            : ''
        "
      >
        <a
          @click="onClick(getCurrPageNumber(pagination.pageGroup.value, page))"
          :title="
            getCurrPageNumber(pagination.pageGroup.value, page) + '페이지 선택'
          "
          style="cursor: pointer"
        >
          {{ getCurrPageNumber(pagination.pageGroup.value, page) }}</a
        >
      </li>
      <template v-if="!pagination.isLast.value">
        <li class="paging-side">
          <button
            class="btn-paging next"
            @click="onClick(pagination.currentPage.value + 1)"
            type="button"
          >
            <el-icon>
              <ArrowRight />
            </el-icon>
          </button>
        </li>
        <li class="paging-side">
          <button
            class="btn-paging next"
            @click="onClick(pagination.numberOfPages.value)"
            type="button"
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

<style scoped lang="scss">
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
