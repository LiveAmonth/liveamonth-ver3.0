<script lang="ts" setup>
import { computed, ref } from "vue";
import SmallTitleSlot from "@/components/common/SmallTitleSlot.vue";

const count = ref(10);
const loading = ref(false);
const noMore = computed(() => count.value >= 20);
const disabled = computed(() => loading.value || noMore.value);
const load = () => {
  loading.value = true;
  setTimeout(() => {
    count.value += 2;
    loading.value = false;
  }, 2000);
};
const todos = ref([
  {
    description: "테스트1테스트1테스트1테스트1테스트1",
    dates: new Date("2022-08-02"),
    highlight: "",
  },
  {
    description: "테스트2",
    dates: new Date("2022-08-02"),
    highlight: "",
  },
]);
const attrs = computed(() =>
  todos.value.map((todo) => ({
    dates: todo.dates,
    popover: {
      label: todo.description,
    },
    highlight: "teal",
    customData: todo,
  }))
);
</script>

<template>
  <el-row>
    <el-col>
      <div>
        <span> 여기는 스케줄 리스트 화면 입니다.</span>
      </div>
      <div class="infinite-list-wrapper" style="overflow: auto">
        <ul
          v-infinite-scroll="load"
          :infinite-scroll-disabled="disabled"
          class="list"
        >
          <li v-for="i in count" :key="i" class="list-item p-4">
            <el-row :gutter="5">
              <el-col :lg="8" :md="8" :sm="8" :xl="6" :xs="8">
                <v-calendar :attributes="attrs" trim-weeks>
                  <template #day-popover="{ day, attributes }">
                    <el-row>
                      {{ day.ariaLabel }}
                    </el-row>
                    <el-row class="mt-2 mx-1">
                      <el-col>
                        <el-timeline class="px-2">
                          <el-timeline-item
                            v-for="({ customData }, index) in attributes"
                            :key="index"
                            color="#004a55"
                            :hollow="true"
                            class="pb-1"
                          >
                            <span style="color: white" class="p-0 m-0">
                              {{ customData.description }}
                            </span>
                          </el-timeline-item>
                        </el-timeline>
                      </el-col>
                    </el-row>
                  </template>
                </v-calendar>
              </el-col>
              <el-col :span="12">
                <el-card>
                  <div class="profile-title d-flex">
                    <div class="profile">
                      <el-avatar
                        :size="100"
                        :src="`/src/assets/image/default.jpg`"
                      />
                      <div class="nickname mt-3">닉네임</div>
                    </div>
                    <div class="title-info">
                      <SmallTitleSlot
                        ><router-link
                          :to="{
                            name: 'read-schedule',
                            params: { scheduleId: 1 },
                          }"
                        >
                          {{ i }} 번 스케줄
                        </router-link></SmallTitleSlot
                      >
                      <div class="date-range">
                        <span class="p-0"
                          ><el-icon style="vertical-align: top"
                            ><Calendar
                          /></el-icon>
                          2022-02-02 ~ 2022-02-02</span
                        >
                      </div>
                    </div>
                  </div>
                  <div class="d-flex justify-content-between">
                    <div></div>
                  </div>
                </el-card>
              </el-col>
            </el-row>
          </li>
        </ul>
        <p v-if="loading">Loading...</p>
        <p v-if="noMore">No more</p>
      </div>
    </el-col>
  </el-row>
</template>

<style lang="scss" scoped>
.infinite-list-wrapper {
  height: 700px;
  text-align: center;

  .list {
    padding: 0;
    margin: 0;
    list-style: none;
  }

  .list-item + .list-item {
    margin-top: 10px;
  }
}

.title-info {
  margin-left: 25px;
}

a {
  text-decoration: none;
  &:hover {
    font-weight: bold;
  }
  &:visited {
    color: inherit;
  }
}
</style>
