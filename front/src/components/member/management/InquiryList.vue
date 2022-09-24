<script setup lang="ts">
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import CustomPagination from "@/components/common/CustomPagination.vue";
import ReadInquiry from "@/components/member/management/ReadInquiry.vue";
import WriteInquiry from "@/components/member/management/WriteInquiry.vue";
import { onMounted, ref } from "vue";
import { useInquiry } from "@/composables/inquiry";
import { usePagination } from "@/composables/pagination";
import { useMessageBox } from "@/composables/messageBox";
import type InquiryEditor from "@/modules/class/member/InquiryEditor";
import type { InquiryTableType } from "@/modules/types/common/TableType";

const type = "INQUIRY";
const {
  inquiryPage,
  inquiries,
  getInquires,
  getInquiry,
  editInquiry,
  deleteInquiry,
} = useInquiry();
const { pageable, mappingPagination, movePage } = usePagination(type);
const { titleMsg, labelMsg, resultMsg, openMessage } = useMessageBox();
const tableData = ref<InquiryTableType[]>([]);
const inquiryTypes = ["list", "detail", "edit"];
const inquiryType = ref<string>(inquiryTypes[0]);

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
      date: value.date,
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
    inquiryType.value = "detail";
  });
};
const editBtn = async (id: number, form: InquiryEditor) => {
  await editInquiry(id, form);
  await pageClick(pageable.value.page);
  openMessage(resultMsg("inquiry.edit"));
  inquiryType.value = "list";
};
const deleteBtn = async (id: number) => {
  await deleteInquiry(id);
  await pageClick(pageable.value.page);
  openMessage(resultMsg("inquiry.delete.success"));
  inquiryType.value = "list";
};
const goBackBtn = () => {
  inquiryType.value = "list";
};
</script>

<template>
  <div class="my-4">
    <ReadInquiry
      v-if="inquiryType === 'detail'"
      @delete="deleteBtn"
      @go-edit="inquiryType = 'edit'"
      @go-back="goBackBtn"
    />
    <WriteInquiry
      v-else-if="inquiryType === 'edit'"
      :is-edit="true"
      @edit="editBtn"
      @go-back="goBackBtn"
    />
    <div class="list-container" v-else-if="inquiryType === 'list'">
      <SmallTitleSlot>
        {{ titleMsg("member.inquiry.list") }}
      </SmallTitleSlot>
      <div class="inquiry-list">
        <el-table
          :data="tableData"
          style="width: 100%"
          @cell-click="select"
          :cell-style="{ cursor: 'pointer' }"
          selectable
        >
          <el-table-column
            prop="category"
            align="center"
            :label="labelMsg('inquiry.category')"
            width="80"
          />
          <el-table-column
            prop="title"
            align="left"
            header-align="center"
            :label="labelMsg('inquiry.title')"
            width="280"
          />
          <el-table-column
            prop="writer"
            align="center"
            :label="labelMsg('inquiry.writer')"
            width="100"
          />
          <el-table-column
            prop="date"
            align="center"
            :label="labelMsg('inquiry.date')"
            width="110"
          />
          <el-table-column
            prop="state"
            align="center"
            :label="labelMsg('inquiry.state')"
            width="75"
          />
        </el-table>
      </div>
      <CustomPagination
        class="me-3"
        :pagination-type="type"
        @click="pageClick"
      />
    </div>
  </div>
</template>

<style scoped lang="scss"></style>
