package teamproject.lam_server.constants;


public class CategoryConstants {
    public interface CodeValue {
        String getCode();

        String getValue();
    }




    public enum EmailDomains {NONE, NAVER, NATE, GMAIL, DAUM, SELF}


    public enum OrderByStatus {VIEW, LIKE}
}
