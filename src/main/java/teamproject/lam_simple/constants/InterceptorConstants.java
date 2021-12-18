package teamproject.lam_simple.constants;

public class InterceptorConstants {
    public enum Whitelist {
        ALL("/"),
        USER_ADD("/signUp"),
        USER_ADD_SIGN("/user/signUp"),
        USER_FIND_ID("/findId"),
        USER_FIND_PW("/findPw"),
        USER_FIND_ID_RESULT("/findIdResult"),
        USER_FIND_PW_RESULT("/findPwResult"),
        CUSTOMER_CENTER("/customerCenter/**"),
        CITY("/city/**"),
        HOME("/main/**"),
        OTHER_SCHEDULE("/schedule/**"),
        REVIEW("/review/**"),
        LOGIN("/login"),
        LOGOUT("/logout"),
        CSS("/css/**"),
        ICO("/*.ico"),
        JS("/js/**"),
        FONT("/fonts/**"),
        IMG("/img/**"),
        ERROR("/error");

        private String path;

        Whitelist(String path) {
            this.path = path;
        }

        public String getPath() {
            return path;
        }
    }
}
