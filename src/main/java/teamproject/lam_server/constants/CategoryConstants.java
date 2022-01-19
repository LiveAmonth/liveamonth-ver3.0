package teamproject.lam_server.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;


public class CategoryConstants {
    public interface CodeValue {
        String getCode();
        String getValue();
    }

    public enum CityName implements CodeValue {
        SEOUL("SE", "서울"),
        GANGNEUNG("GN", "강릉"),
        GYEONGJU("GJ", "경주"),
        BUSAN("BS", "부산"),
        YEOSU("YS", "여수"),
        JEJU("JJ", "제주");
        private String code;
        private String value;

        CityName(String code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public String getCode() {return code;}
        @Override
        public String getValue() {return value;}
    }

    public enum CityInfoCategory implements CodeValue {
        INTRO("I", "소개"),
        FOOD("F", "먹거리"),
        VIEW("V", "볼거리");
        private String code;
        private String value;

        CityInfoCategory(String code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public String getCode() {return code;}
        @Override
        public String getValue() {return value;}
    }

    public enum CityTransportGrade {T_GOOD, T_FAIR, T_POOR}

    public enum Month implements CodeValue{
        JANUARY("JAN"),
        FEBRUARY("FEB"),
        MARCH("MAR"),
        APRIL("APR"),
        MAY("MAY"),
        JUNE("JUN"),
        JULY("JUL"),
        AUGUST("AUG"),
        SEPTEMBER("SEP"),
        OCTOBER("OCT"),
        NOVEMBER("NOV"),
        DECEMBER("DEC");
        private String code;
        private String value;
        Month(String code) {
            this.code = code;
            this.value = String.valueOf(this.ordinal()+1);
        }
        @Override
        public String getCode() {return code;}
        @Override
        public String getValue() {return value;}
    }

    public enum TransportCategory implements CodeValue {
        T_SUBWAY("SUB", "지하철역", 4),
        T_BUS("BUS", "버스정류장", 5),
        T_BICYCLE("BYC", "공공자전거", 6),
        T_BUS_TERMINAL("B_TER", "버스터미널", 3),
        T_TRAIN("TRA", "기차역", 2),
        T_AIRPORT("AIR", "공항", 1);
        private String code;
        private String value;
        private int score;

        TransportCategory(String code, String value, int score) {
            this.code = code;
            this.value = value;
            this.score = score;
        }
        @Override
        public String getCode() {
            return code;
        }
        @Override
        public String getValue() {
            return value;
        }
        public int getScore(){return score;}
    }

    public enum CustomerCenterCategory {faq, personalTerms, termsAndConditions, notice}

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

    public enum GenderTypes implements CodeValue {
        MALE("M", "남성"),
        FEMALE("F", "여성");

        private String code;
        private String value;

        GenderTypes(String code, String value) {
            this.code = code;
            this.value = value;
        }

        @Override
        public String getCode() {
            return code;
        }
        @Override
        public String getValue() {
            return value;
        }
    }

    public enum EmailDomains {NONE, NAVER, NATE, GMAIL, DAUM, SELF}
}
