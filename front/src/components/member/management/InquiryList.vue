<script setup lang="ts">
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import CustomPagination from "@/components/common/CustomPagination.vue";
import { useMessageBox } from "@/composables/messageBox";
import { useInquiry } from "@/composables/inquiry";
import { usePagination } from "@/composables/pagination";
import { onMounted, ref } from "vue";
import type { InquiryTableType } from "@/modules/types/common/TableType";

const type = "INQUIRY";
const { inquiryPage, inquiries, currInquiry, getInquires, getInquiry } =
  useInquiry();
const { pageable, mappingPagination, movePage } = usePagination(type);
const { titleMsg, labelMsg } = useMessageBox();
const tableData = ref<InquiryTableType[]>([]);
const isDetail = ref<boolean>(false);

onMounted(async () => {
  await getInquires(pageable.value).then(() => {
    mappingPagination(inquiryPage.value);
    tableData.value = setTableData();
  });
});

const setTableData = (): InquiryTableType[] => {
  const data: InquiryTableType[] = [];
  inquiries.value.forEach((value) => {
    data.push({
      id: value.id,
      title: value.title,
      writer: value.writer,
      category: value.category.value,
      date: value.lastModifiedDate,
      state: value.isAnswered
        ? labelMsg("inquiry.complete")
        : labelMsg("inquiry.standBy"),
    });
  });
  return data;
};

const pageClick = async (page: number) => {
  movePage(page);
  await getInquires(pageable.value).then(() => {
    tableData.value = setTableData();
    mappingPagination(inquiryPage.value);
  });
};

const select = async (cell?: InquiryTableType) => {
  await getInquiry(Number(cell?.id)).then(() => {
    isDetail.value = true;
  });
};
</script>

<template>
  <div class="my-4" v-if="!isDetail">
    <SmallTitleSlot>
      {{ titleMsg("member.inquiry.answer") }}
    </SmallTitleSlot>
    <div class="inquiry-list">
      <el-table :data="tableData" style="width: 100%" @cell-click="select">
        <el-table-column
          prop="category"
          :label="labelMsg('inquiry.category')"
          width="75"
        />
        <el-table-column
          prop="title"
          :label="labelMsg('inquiry.title')"
          width="280"
        />
        <el-table-column
          prop="writer"
          :label="labelMsg('inquiry.writer')"
          width="100"
        />
        <el-table-column
          prop="date"
          :label="labelMsg('inquiry.date')"
          width="110"
        />
        <el-table-column
          prop="state"
          :label="labelMsg('inquiry.state')"
          width="80"
        />
      </el-table>
    </div>
    <CustomPagination class="me-3" :pagination-type="type" @click="pageClick" />
  </div>
  <div v-else>{{ currInquiry }}</div>
</template>

<style lang="scss">
.el-table__row {
  cursor: pointer;
}
</style>
