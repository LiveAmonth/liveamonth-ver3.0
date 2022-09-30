export interface CommentType {
  commentId: number;
  content: string;
  profile: CommentProfileType;
  commentReplies: CommentReplyType[];
  elapsedTime: string;
  likes: number;
  dislikes: number;
}

export interface CommentProfileType {
  image: string;
  nickname: string;
}

export interface CommentReplyType {
  commentId: number;
  parentId: number;
  content: string;
  profile: CommentProfileType;
  elapsedTime: string;
  likes: number;
  dislikes: number;
}
