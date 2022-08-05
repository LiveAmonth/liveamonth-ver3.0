<script lang="ts" setup>
import { computed, ref } from "vue";

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
const events = ref([
  {
    start: "2022-8-20 14:00",
    end: "2022-8-20 18:00",
    title: "Need to go shopping",
    icon: "shopping_cart", // Custom attribute.
    content: "Click to see my shopping list",
    contentFull:
      "My shopping list is rather long:<br><ul><li>Avocados</li><li>Tomatoes</li><li>Potatoes</li><li>Mangoes</li></ul>", // Custom attribute.
    class: "leisure",
  },
]);
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
                <vue-cal
                  :disable-views="['week']"
                  :events="events"
                  :time="false"
                  active-view="month"
                  click-to-navigate
                  events-count-on-year-view
                  hide-view-selector
                  locale="ko"
                  xsmall
                >
                </vue-cal>
              </el-col>
              <el-col :span="12">
                <el-card>
                  <router-link
                    :to="{ name: 'read-schedule', params: { scheduleId: 1 } }"
                  >
                    {{ i }} 번 스케줄 보기
                  </router-link>
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

.vuecal__cell--has-events {
  background-color: #004a55;
}

.vuecal__cell-events-count {
  display: none;
}

.vuecal {
  box-shadow: var(--el-box-shadow-light);
  height: 300px;
  width: auto;
}

.vuecal__title-bar {
  background-color: #004a55 !important;
  font-size: 0.8rem;
}
</style>
