import DecimalFormat from "decimal-format";

const countDf = new DecimalFormat("#,##0.#");
const wonDf = new DecimalFormat("#,##0원");
const MILLION = "만";
const H_MILLION = "억";

declare module "@vue/runtime-core" {
  interface ComponentCustomProperties {
    $comma<Key extends number>(key: Key): string;
    $count<Key extends number>(key: Key): string;
    $won<Key extends number>(key: Key): string;
  }
}

export const numberFormat = {
  comma(key: number): string {
    return countDf.format(key);
  },

  count(key: number): string {
    if (key < 1e4) return countDf.format(key);
    if (key < 1e8) return countDf.format(key / 1e4).concat(MILLION);
    else return countDf.format(key / 1e8).concat(H_MILLION);
  },

  won(key: number): string {
    return wonDf.format(key);
  },
};
