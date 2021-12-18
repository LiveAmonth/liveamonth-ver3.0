package teamproject.lam_simple.constants;

public class CategoryConstants {
    public enum CityNames {SE,GN,GJ,BS,YS,JJ}
    public enum CityInfoCategory {INTRO,FOOD,VIEW}
    public enum CityTransportGrade{T_GOOD,T_FAIR,T_POOR}
    public enum Month {JAN,FEB,MAR,APR,MAY,JUN,JUL,AUG,SEP,OCT,NOV,DEC}
    public enum TransportCategory {
        T_SUBWAY(4),
        T_BUS(5),
        T_BICYCLE(6),
        T_BUS_TERMINAL(3),
        T_TRAIN(2),
        T_AIRPORT(1);
        private final int score;
        TransportCategory(int score) {this.score = score;}
        public int getScore() {
            return this.score;
        }
    }
    public enum CustomerCenterCategory{faq,personalTerms,termsAndConditions,notice}
    public interface MyPageCategory{}
    public enum AccountCategory implements MyPageCategory {modify,drop}
    public enum CommunityCategory implements MyPageCategory{review,schedule}
    public enum InquiryCategory implements MyPageCategory{writeInquiry,inquiryAnswer}
    public enum GenderTypes {MALE,FEMALE}
    public enum EmailDomains{NONE,NAVER,NATE,GMAIL,DAUM,SELF}
}
