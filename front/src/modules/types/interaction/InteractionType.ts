import type { EnumType } from "@/modules/types/common/CommonTypes";

export interface InteractionType {
  from: number;
  to: number;
}

export interface CommentInteractionType {
  id: number;
  state: EnumType;
}
