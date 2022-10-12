<script lang="ts" setup>
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";
import { useInquiry } from "@/composables/member/inquiry";
import { useMessageBox } from "@/composables/common/messageBox";

defineProps({
  inquiryType: String,
});

const emits = defineEmits(["goEdit", "goBack", "delete"]);
const { titleMsg, buttonMsg, resultMsg, openConfirmMessageBox } =
  useMessageBox();
const { currInquiry } = useInquiry();

const deleteBtn = async () => {
  await openConfirmMessageBox(
    resultMsg("inquiry.delete.title"),
    resultMsg("inquiry.delete.content")
  ).then(() => {
    emits("delete", currInquiry.value.id);
  });
};
</script>

<template>
  <div class="inquiry">
    <el-row class="title-content">
      <el-col class="mt-4">
        <h2 class="title">
          [{{ currInquiry.category.value }}] {{ currInquiry.title }}
        </h2>
        <div class="sub d-flex">
          <div class="writer">
            {{ currInquiry.writer }}
          </div>
          <div class="regDate">
            {{ currInquiry.dateTime }}
          </div>
        </div>
      </el-col>
    </el-row>
    <el-divider />
    <el-row class="mt-3">
      <el-col>
        <div class="content">
          <QuillEditor
            theme="bubble"
            v-model:content="currInquiry.content"
            read-only
            contentType="html"
          />
        </div>
      </el-col>
    </el-row>

    <el-row class="mt-3">
      <el-col>
        <div class="d-flex justify-content-end">
          <el-button color="#0f6778" @click="emits('goEdit')">
            {{ buttonMsg("edit") }}
          </el-button>
          <el-button type="danger" @click="deleteBtn">
            {{ buttonMsg("delete") }}
          </el-button>
        </div>
      </el-col>
    </el-row>
  </div>

  <div class="answer">
    <SmallTitleSlot>{{ titleMsg("member.inquiry.answer") }}</SmallTitleSlot>
    <el-row v-if="currInquiry.isAnswered">
      <el-col>
        <el-row class="title-content">
          <el-col class="mt-4">
            <h2 class="title">
              {{ currInquiry.title }}
            </h2>
            <div class="sub d-flex">
              <div class="writer">
                {{ currInquiry.writer }}
              </div>
              <div class="regDate">
                {{ currInquiry.dateTime }}
              </div>
            </div>
          </el-col>
        </el-row>
        <el-divider />
        <el-row class="mt-3">
          <el-col>
            <div class="content">
              <QuillEditor
                theme="bubble"
                v-model:content="currInquiry.content"
                read-only
                contentType="html"
              />
            </div>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
    <el-row v-else class="mt-3">
      <el-col>{{ resultMsg("inquiry.noAnswer") }}</el-col>
    </el-row>
  </div>

  <el-row class="mt-3">
    <el-col>
      <div class="d-flex justify-content-end">
        <el-button type="info" @click="emits('goBack')">
          {{ buttonMsg("back") }}
        </el-button>
      </div>
    </el-col>
  </el-row>
</template>

<style lang="scss" scoped>
.inquiry {
  .title-content {
    .title {
      font-size: 1.8rem;
      font-weight: 600;
      color: #383838;
      margin: 0;
    }

    .sub {
      margin-top: 20px;
      font-size: 0.85rem;

      .regDate {
        margin-left: 10px;
        color: #8d8d8d;
      }
    }
  }
}

.answer {
  margin-top: 20px;
  background-color: #fafafa;

  .title-content {
    .title {
      font-size: 1.5rem;
      font-weight: 600;
      color: #383838;
      margin: 0;
    }

    .sub {
      margin-top: 10px;
      font-size: 0.75rem;

      .regDate {
        margin-left: 10px;
        color: #8d8d8d;
      }
    }
  }

  .content {
    font-size: 0.9rem;
    margin-top: 8px;
    color: #535252;
    white-space: break-spaces;
    line-height: 1.5;
  }
}
</style>
