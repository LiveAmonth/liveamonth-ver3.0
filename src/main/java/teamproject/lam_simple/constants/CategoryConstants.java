package teamproject.lam_simple.constants;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    public enum MyPageCategory {
        modify("account"),
        drop("account"),
        review("community"),
        schedule("community"),
        writeInquiry("inquiry"),
        inquiryAnswer("inquiry");
        private final String category;
        MyPageCategory(String category) {this.category = category;}
        public String getCategory() {
            return this.category;
        }
    }
    public List<MyPageCategory> getAccountCategory(){
        List<MyPageCategory> temp = new ArrayList<>();
        temp.add(MyPageCategory.modify);
        temp.add(MyPageCategory.drop);
        return temp;
    }
    public List<MyPageCategory> getCommunityCategory(){
        List<MyPageCategory> temp = new ArrayList<>();
        temp.add(MyPageCategory.review);
        temp.add(MyPageCategory.schedule);
        return temp;
    }
    public List<MyPageCategory> getInquiryCategory(){
        List<MyPageCategory> temp = new ArrayList<>();
        temp.add(MyPageCategory.writeInquiry);
        temp.add(MyPageCategory.inquiryAnswer);
        return temp;
    }
    public enum GenderTypes {MALE,FEMALE}
    public enum EmailDomains{NONE,NAVER,NATE,GMAIL,DAUM,SELF}
}
