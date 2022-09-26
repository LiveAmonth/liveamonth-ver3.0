<script lang="ts" setup>
import { usePagination } from "@/composables/common/pagination";
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
    <ul class="paging-point">
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
        class="paging-num"
      >
        <span class="num-box">
          <a
            :title="getCurrentPageNumber(page) + '페이지 선택'"
            style="cursor: pointer"
            @click="onClick(getCurrentPageNumber(page))"
          >
            {{ getCurrentPageNumber(page) }}
          </a>
        </span>
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
.paging {
  text-align: center;

  li {
    list-style: none;
  }

  .paging-point {
    display: flex;
    justify-content: center;

    .paging-num {
      display: flex;
      padding: 0 8px;
      font-family: Verdana, Geneva, sans-serif;
      font-weight: 400;
      font-size: 16px;
      color: #000;
      line-height: 40px;

      .num-box {
        width: 15px;
        height: 20px;
      }

      &.on,
      a:hover,
      a:focus {
        color: teal;
        font-size: 19px;
      }
    }

    .paging-side {
      .btn-paging {
        width: 20px;
        height: 40px;
        margin-top: 2px;
        border: none;
        background-color: inherit;

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
    }
  }
}
</style>
