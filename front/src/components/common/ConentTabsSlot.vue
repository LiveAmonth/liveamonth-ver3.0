<script setup lang="ts">
import { ref } from "vue";
import type { PropType } from "vue";
import type { NameIconType } from "@/modules/types/common/MenuType";

const props = defineProps({
  tabs: {
    type: Object as PropType<NameIconType[]>,
    required: true,
  },
  activeName: {
    props: String,
    required: true,
  },
  headerWidth: {
    props: String,
    required: false,
    default: "100px",
  },
  borderPosition: {
    props: ["top", "bottom"],
    required: false,
    default: "top",
  },
});

const emits = defineEmits(["update:activeName"]);

const headerBorderStyle = ref(
  props.borderPosition === "top"
    ? { borderTop: "#bab7b7 0.5px solid" }
    : {
        borderRadius: "50px",
        backgroundColor: "#fafafa",
        padding: "2px",
        boxShadow:
          "0 0 1px 0 rgba(0, 109, 125, 0.16), 0 6px 12px 0 rgba(0, 109, 125, 0.16)",
      }
);

const gliderStyle = ref(
  props.borderPosition === "top"
    ? { borderTop: "#016d7d 0.2rem solid", width: props.headerWidth }
    : {
        backgroundColor: "rgba(0,109,125,0.16)",
        borderRadius: "99px",
        width: props.headerWidth,
      }
);

const contentStyle = ref(
  props.borderPosition !== "top" ? { marginTop: "-20px" } : {}
);

const initialTabIdx = () => {
  const findIndex = props.tabs.findIndex(
    (value) => value.code === props.activeName
  );
  return findIndex ? findIndex : 0;
};
</script>

<template>
  <div class="posts">
    <div class="posts-header" :style="headerBorderStyle">
      <div class="tabs">
        <template v-for="(tab, index) in tabs" :key="tab.code">
          <input
            type="radio"
            :id="`radio-${index + 1}`"
            name="tabs"
            :checked="index === initialTabIdx()"
            :value="tab.code"
            @click="emits('update:activeName', tab.code)"
          />
          <label
            class="tab"
            :for="`radio-${index + 1}`"
            :style="{ width: headerWidth }"
          >
            <el-icon class="me-1">
              <component :is="tab.icon" />
            </el-icon>
            {{ tab.value }}
          </label>
        </template>
        <span class="glider" :style="gliderStyle"></span>
      </div>
    </div>
    <el-row class="posts-content" :style="contentStyle">
      <el-col>
        <slot v-for="num in tabs.length" :key="num" :name="`tab-${num}`"></slot>
      </el-col>
    </el-row>
  </div>
</template>

<style scoped lang="scss">
.posts {
  .posts-header {
    display: flex;
    justify-content: center;
    margin-bottom: 30px;

    .tabs {
      display: flex;
      position: relative;
      height: 40px;

      * {
        z-index: 2;
      }

      input[type="radio"] {
        display: none;
      }

      .tab {
        display: flex;
        align-items: center;
        justify-content: center;
        height: 40px;
        width: 100px;
        color: #646363;
        font-size: 1rem;
        cursor: pointer;
        transition: color 0.15s ease-in;
      }

      input[type="radio"] {
        &:checked {
          & + label {
            color: #0f6778;
            font-size: 1.05rem;
            font-weight: 600;
          }
        }
      }

      input[id="radio-1"] {
        &:checked {
          & ~ .glider {
            transform: translateX(0);
          }
        }
      }

      input[id="radio-2"] {
        &:checked {
          & ~ .glider {
            transform: translateX(100%);
          }
        }
      }

      input[id="radio-3"] {
        &:checked {
          & ~ .glider {
            transform: translateX(200%);
          }
        }
      }

      .glider {
        position: absolute;
        display: flex;
        height: 40px;
        width: 100px;
        z-index: 1;
        transition: 0.25s ease-out;
      }
    }
  }
}
</style>
