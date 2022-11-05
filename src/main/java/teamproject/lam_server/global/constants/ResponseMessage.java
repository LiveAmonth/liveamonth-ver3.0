package teamproject.lam_server.global.constants;

public abstract class ResponseMessage {

    public static final String SUCCESS = "성공";

    /**
     * Domain: Member
     */
    public static final String LOGIN_SUCCESS = "로그인 성공";
    public static final String LOGOUT_SUCCESS = "로그아웃 성공";
    public static final String REISSUE_TOKEN_SUCCESS = "토큰 재발급 성공";
    public static final String READ_MEMBER = "회원 정보 조회 성공";
    public static final String FIND_MEMBER_LOGIN_ID = "회원 아이디 찾기 성공";
    public static final String FIND_MEMBER_PASSWORD = "회원 비밀번호 재발급 성공";
    public static final String CREATED_MEMBER = "회원 가입 성공";
    public static final String UPDATE_MEMBER = "회원 정보 수정 성공";
    public static final String DROP_MEMBER = "회원 탈퇴 성공";
    public static final String DELETE_MEMBER = "회원 데이터 삭제 성공";
    public static final String DUPLICATE_CHECK = "회원 정보 중복 확인";
    public static final String RECONFIRM = "비밀번호 재확인";
    public static final String SUCCESS_RECONFIRM = "비밀번호 재확인 성공";
    public static final String FAIL_RECONFIRM = "비밀번호가 다릅니다.";
    public static final String DUPLICATE_EMAIL = "이미 사용중인 이메일 입니다.";
    public static final String AVAILABLE_EMAIL = "사용 가능한 이메일 입니다.";
    public static final String DUPLICATE_LOGIN_ID = "이미 사용중인 아이디 입니다.";
    public static final String AVAILABLE_LOGIN_ID = "사용 가능한 아이디 입니다.";
    public static final String DUPLICATE_NICKNAME = "이미 사용중인 닉네임 입니다.";
    public static final String AVAILABLE_NICKNAME = "사용 가능한 닉네임 입니다.";

    /**
     * Domain: Inquiry
     */
    public static final String READ_INQUIRY = "1:1 문의 조회 성공";
    public static final String CREATE_INQUIRY = "1:1 문의 저장 성공";
    public static final String DELETE_INQUIRY = "1:1 문의 삭제 성공";
    public static final String UPDATE_INQUIRY = "1:1 문의 업데이트 성공";
    public static final String READ_INQUIRY_ANSWER = "1:1문의 답변 조회 성공";
    public static final String CREATE_INQUIRY_ANSWER = "1:1문의 답변 저장 성공";
    public static final String DELETE_INQUIRY_ANSWER = "1:1문의 답변 삭제 성공";
    public static final String UPDATE_INQUIRY_ANSWER = "1:1문의 답변 업데이트 성공";


    /**
     * Domain: Schedule
     */
    public static final String READ_SCHEDULE = "스케줄 조회 성공";
    public static final String CREATE_SCHEDULE = "스케줄 저장 성공";
    public static final String DELETE_SCHEDULE = "스케줄 삭제 성공";
    public static final String UPDATE_SCHEDULE = "스케줄 업데이트 성공";
    public static final String READ_SCHEDULE_CONTENT = "스케줄 컨텐츠 조회 성공";
    public static final String CREATE_SCHEDULE_CONTENT = "스케줄 컨텐츠 저장 성공";
    public static final String DELETE_SCHEDULE_CONTENT = "스케줄 컨텐츠 삭제 성공";

    /**
     * Domain: City
     */
    public static final String CREATE_CITY = "도시정보 저장 성공";
    public static final String READ_CITY = "도시정보 조회 성공";
    public static final String UPDATE_CITY = "도시정보 변경 성공";
    public static final String DELETE_CITY = "도시정보 삭제 성공";


    /**
     * Domain: Review
     */
    public static final String CREATE_REVIEW = "후기글 저장 성공";
    public static final String UPDATE_REVIEW = "후기글 변경 성공";
    public static final String DELETE_REVIEW = "후기글 삭제 성공";
    public static final String READ_REVIEW = "후기글 조회 성공";
    public static final String READ_TAG = "태그 조회 성공";

    /**
     * Domain: Comment
     */
    public static final String CREATE_COMMENT = "댓글 저장 성공";
    public static final String UPDATE_COMMENT = "댓글 변경 성공";
    public static final String DELETE_COMMENT = "댓글 삭제 성공";
    public static final String READ_COMMENT = "댓글 조회 성공";

    /**
     * Domain: Interaction
     */
    public static final String CREATE_INTERACTION = "좋아요 저장 성공";
    public static final String UPDATE_INTERACTION = "좋아요 변경 성공";
    public static final String DELETE_INTERACTION = "좋아요 삭제 성공";
    public static final String READ_INTERACTION = "좋아요 조회 성공";

    /**
     * Category
     */
    public static final String READ_CATEGORY = " 카테고리 조회 성공";

}
