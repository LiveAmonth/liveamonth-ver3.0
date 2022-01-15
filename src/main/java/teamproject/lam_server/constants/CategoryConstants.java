package teamproject.lam_server.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;


public class CategoryConstants {
    public enum CityNames {SE,GN,GJ,BS,YS,JJ}
    public enum CityInfoCategory {INTRO,FOOD,VIEW}
    public enum CityTransportGrade{T_GOOD,T_FAIR,T_POOR}
    public enum Month {JAN,FEB,MAR,APR,MAY,JUN,JUL,AUG,SEP,OCT,NOV,DEC}
    @Getter
    @AllArgsConstructor
    public enum TransportCategory {
        T_SUBWAY(4),
        T_BUS(5),
        T_BICYCLE(6),
        T_BUS_TERMINAL(3),
        T_TRAIN(2),
        T_AIRPORT(1);
        private int score;
    }
    public enum CustomerCenterCategory{faq,personalTerms,termsAndConditions,notice}
    @Getter
    @AllArgsConstructor
    public enum MyPageCategory {
        modify("account"),
        drop("account"),
        review("community"),
        schedule("community"),
        writeInquiry("inquiry"),
        inquiryAnswer("inquiry");
        private final String category;
    }
    public enum GenderTypes {MALE,FEMALE}
    public enum EmailDomains{NONE,NAVER,NATE,GMAIL,DAUM,SELF}
}
