<script lang="ts" setup>
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import CustomPagination from "@/components/common/CustomPagination.vue";
import ReadInquiry from "@/components/member/management/ReadInquiry.vue";
import WriteInquiry from "@/components/member/management/WriteInquiry.vue";
import { onMounted, ref } from "vue";
import { useInquiry } from "@/composables/member/inquiry";
import { usePagination } from "@/composables/common/pagination";
import { useMessageBox } from "@/composables/common/messageBox";
import type {
  InquiryEditor,
  InquiryTableType,
} from "@/modules/types/member/MemberTypes";

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
    <div v-else-if="inquiryType === 'list'" class="list-container">
      <SmallTitleSlot :title="titleMsg('member.inquiry.list')" />
      <div class="inquiry-list">
        <el-table
          :cell-style="{ cursor: 'pointer' }"
          :data="tableData"
          selectable
          style="width: 100%"
          @cell-click="select"
        >
          <el-table-column
            :label="labelMsg('category')"
            align="center"
            prop="category"
            width="80"
          />
          <el-table-column
            :label="labelMsg('title')"
            align="left"
            header-align="center"
            prop="title"
            width="280"
          />
          <el-table-column
            :label="labelMsg('writer')"
            align="center"
            prop="writer"
            width="100"
          />
          <el-table-column
            :label="labelMsg('inquiry.date')"
            align="center"
            prop="date"
            width="110"
          />
          <el-table-column
            :label="labelMsg('inquiry.state')"
            align="center"
            prop="state"
            width="75"
          />
        </el-table>
      </div>
      <CustomPagination
        :pagination-type="type"
        class="me-3"
        @click="pageClick"
      />
    </div>
  </div>
</template>

<style lang="scss" scoped></style>
