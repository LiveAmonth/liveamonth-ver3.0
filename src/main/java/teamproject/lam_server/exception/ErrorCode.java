package teamproject.lam_server.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /**
     * <p>
     * Status: 400 BAD_REQUEST
     * <p>
     * Detail: 유효성 검증 실패
     */
    ARGUMENTS_NOT_VALID(400, "유효성 검증 실패"),
    BAD_CREDENTIALS(400, "잘못된 인증정보 입니다."),
    /**
     * <p>
     * Status: 500 INTERNAL_SERVER_ERROR
     * <p>
     * Detail: 서버 오류
     */
    UNKNOWN_SERVER_ERROR(500, "알 수 없는 오류가 발생했습니다."),

    /**
     * <p>
     * Status: 400 BAD_REQUEST
     * <p>
     * Detail: 잘못된 요청
     */
    PERMISSION_NOT_ACCESSIBLE(400, "해당 리소스에 대한 권한이 없습니다."),
    INVALID_REFRESH_TOKEN(400, "리프레시 토큰이 유효하지 않습니다."),
    NOT_CORRESPOND_MEMBER(400, "회원 정보가 일치하지 않습니다"),
    ALREADY_USED_TOKEN(400, "이미 사용 후 반납된 토큰입니다."),
    ALREADY_DROP_MEMBER(400, "이미 탈퇴한 회원입니다."),
    ALREADY_FOLLOW_MEMBER(400, "이미 '팔로우'한 회원입니다."),
    ALREADY_LIKE_SCHEDULE(400, "이미 '좋아요'한 스케줄입니다."),
    ALREADY_DISLIKE_COMMENT(400, "이미 비공감한 글입니다."),
    ALREADY_LIKE_COMMENT(400, "이미 공감한 글입니다."),
    ALREADY_LIKE_REVIEW(400, "이미 '좋아요'한 후기입니다."),
    NOT_DROP_MEMBER(400, "탈퇴하지 않은 회원입니다."),
    INVALID_SORT_OPTION(400, "정렬 조건이 올바르지 않습니다."),
    INVALID_OAUTH2_PROVIDER(400, "제공자(소셜)가 올바르지 않습니다."),
    ILLEGAL_ARGUMENT(400, "적절하지 않은 인자입니다."),

    /**
     * <p>
     * Status: 403 FORBIDDEN
     * <p>
     * Detail: 해당 Access에 접근할 수 없음
     */
    EXPIRED_JWT(403, "해당 토큰이 만료되었습니다."),
    INVALID_TOKEN(403, "유효하지 않은 토큰입니다."),
    UNSUPPORTED_TOKEN(403, "지원하지 않는 토큰 유형입니다."),
    EMPTY_TOKEN(403, "토큰 정보가 비어있습니다."),

    DROP_MEMBER(403, "탈퇴한 회원입니다"),

    /**
     * <p>
     * Status: 404 NOT_FOUND
     * <p>
     * Detail: Resource 를 찾을 수 없음
     */
    MEMBER_NOT_FOUND(404, "해당 사용자를 찾을 수 없습니다."),
    FOLLOW_NOT_FOUND(404, "팔로우 정보를 찾을 수 없습니다."),
    COMMENT_NOT_FOUND(404, "댓글 정보를 찾을 수 없습니다."),
    CITY_NOT_FOUND(404, "해당 도시 정보를 찾을 수 없습니다."),
    SCHEDULE_NOT_FOUND(404, "해당 스케줄를 찾을 수 없습니다."),
    REVIEW_NOT_FOUND(404, "해당 리뷰를 찾을 수 없습니다."),

    SERVICE_NOT_FOUND(404, "해당 타입에 맞는 서비스를 찾을 수 없습니다."),

    /**
     * <p>
     * Status: 409 CONFLICT
     * <p>
     * Detail: Resource 충돌, 중복 데이터 존재
     */
    DUPLICATED_RESOURCE(409, "DB에 해당 데이터가 이미 존재합니다.");

    private final int statusCode;
    private final String message;
}
