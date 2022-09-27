export default class ReviewSearch {
  input: string;
  type: string;

  constructor(type: string) {
    this.input = "";
    this.type = type;
  }

  clear() {
    this.input = "";
  }
}
