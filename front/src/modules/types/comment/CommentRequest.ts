export interface CommentCreateType {
  content_id: number;
  comment: string;
  parent_id: number | null;
}

export interface CommentEditType {
  comment: string;
}
