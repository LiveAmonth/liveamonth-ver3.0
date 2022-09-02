import PageableRequest from "@/modules/class/PageableRequest";

export class CommentPageableRequest extends PageableRequest {
  constructor() {
    super(1, 5, "id,desc");
  }
  getType(): string {
    return "comment";
  }
}

export default CommentPageableRequest;
