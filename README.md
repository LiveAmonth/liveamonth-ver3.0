# Liveamonth ver.2.0.0

### [Liveavmonth Ver 1.0](https://github.com/LiveAmonth/LiveAmonth-ver.1.0.0)

___

## 목차
1. [📢 프로젝트 기획](#-프로젝트-기획)
2. [🛠 기술 스택](#-기술-스택)
3. [📝 E-R 다이어그램](#-E-R-다이어그램)
   - [전체](#전체)
   - [CITY](#city)
   - [MEMBER & INQUIRY](#member--inquiry)
   - [SCHEDULE](#schedule)
   - [REVIEW](#review)
   - [FOLLOW](#follow)
4. [🔑구현 기능](#-구현-기능)
   - [보안](#보안)
5. [🎞 UI 기능 및 화면](#-ui-기능-및-화면)
   - [🏠 메인 화면](#-메인-화면)
   - [🏞 도시 소개](#-도시-소개)
   - [🔨 계정](#-계정)
   - [🔨 마이페이지](#-마이페이지)
   - [📅 스케줄](#-스케줄)
   - [📑 후기글](#-후기글)
   - [👍 좋아요&팔로우](#-좋아요&팔로우)
   - [✏ 댓글](#-댓글)
   - [🏷 게시물 리스트](#-게시물-리스트)


# 📢 프로젝트 기획
### 1. 한 달 살기에 대한 선호도 증가
>설문조사 결과 10명중 8명은 살아보는 여행을 꿈꾸고, 15일 이상의 장기 여행을 선호

<details><summary> <strong> 한 달 살기를 시작한 이유</strong>
</summary>

*자연경관이 좋은 곳에서 휴식하고 싶다. (24.8%)<br>나를 돌아보는 시간을 갖기 위해서 (22.3%)
<br>현지인처럼 살아보고 싶어서 (17.4%)
<br>짧은 여행으로는 여행지를 제대로 보기 힘들어서 (14.0%)
<br> 새로운 곳에서 새로운 것들을 배우고 싶어서 (14.0%)*
</details>

<details><summary> <strong>  한 달 살기를 하면서 힘들었던 점</strong>
</summary>

*한 달 동안  생활을 하는 비용이 많이들고 관리가 힘듦*
</details>

<details><summary> <strong>  한 달 동안 한 것</strong>
</summary>

*주로 주변 여행지를 다니거나, 자연속에서 휴식을 즐기며, 지역의 특색에 관련된 활동*
</details>

<details><summary> <strong>  한 달 살기를 해보고 싶은 지역</strong>
</summary>

*제주가 앞도적으로 높은 응답을 보였지만, 전국 곳곳의 여행지도 선호*
</details>

### 2. 사용자들이 원하는 핵심 요소 파악
<details><summary> <strong>장소</strong>
</summary><br>

*스스로 의미있는 시간을 보내고, 충분히 휴식을 하기 위한 장소*
</details>

<details><summary><strong>비용</strong>
</summary><br>

*한 달 동안 생활하기 위한 비용*
</details>

<details><summary><strong>계획</strong>
</summary><br>

*한 달 살기 기간 동안의 생활을 계획*
</details><br>

# 🛠 기술 스택
### Language
*Java, Typescript, SQL*

### Web FE
*HTML, CSS, Vue.js3, Element Plus*

### Web BE
*Spring Framework, Spring Security, Spring Rest Docs, JPA*

### DB
*MySQL, Redis*

### Tool
*IntelliJ, MySQL WorkBench, Git*


# 📝 E-R 다이어그램
### 전체
![liveamonth](https://user-images.githubusercontent.com/48740872/201522000-beee8697-ad81-4be6-a8f9-2f73f3de71f6.png)

### CITY
![city-erd](https://user-images.githubusercontent.com/48740872/201522080-0111272a-e199-4cdb-af5d-e4e776e77e8f.png)

### MEMBER & INQUIRY
![member-erd](https://user-images.githubusercontent.com/48740872/201522215-57032ae3-fcda-431e-9670-6fcf4d6d4e8f.png)

### SCHEDULE
![schedue-erd](https://user-images.githubusercontent.com/48740872/201522312-d1af2efa-4e59-453e-a513-8136f6ce158b.png)

### REVIEW
![review-erd](https://user-images.githubusercontent.com/48740872/201522663-4f6e916e-4008-4e62-9af4-9b51e05e3760.png)

### FOLLOW
![follow-erd](https://user-images.githubusercontent.com/48740872/201522546-589c2303-8a78-4500-a442-34f4b536f7d7.png)

# 🔑구현 기능
### 1. 보안
- Spring Security를 사용하여 인증 및 인가 처리를 하였습니다.
  - 서버의 안정성과 확장성을 고려하여 JWT와 Redis를 사용하여 인증, 인가 처리를 했습니다.
  - 클라이언트가 로그인을 하면 서버에서 해당 사용자의 아이디를 담은 access token을 생성해 응답하도록 구현했습니다.
    - 토큰이 탈취당했을 경우를 대비하여 access token의 만료시간을 짧게 하고 refresh token을 만들어 토큰 만료시 재발급을 할 수 있도록 구현했습니다.
    - refresh token은 쿠키로 전달하며 HttpOnly, Secure 옵션을 주어 Http 공격에도 대비하였습니다.
  - 로그인시 Redis에 {key: 로그인아이디, value: refresh token} 데이터를 저장합니다.(만료시간 : refresh token의 만료시간)
    - 토큰 재발급 시, 쿠키에 담겨 있는 refresh token과 Redis에 저장되어 있는 refresh token을 비교하여 일치하면 새로운 access token을 생성하여 응답하도록 했습니다.
    - 만약, refresh token도 만료되었거나 인증이 되지 않은 경우 클라이언트가 로그아웃 되도록 구현하였습니다.
  - 로그아웃시 요청으로 들어온 access token을 블랙리스트로 관리하였습니다.
    - refresh token이 담긴 쿠키를 삭제했습니다.
    - Redis에 로그인 아이디를 key값으로 하는 데이터를 삭제했습니다.
    - (key: access token, value: 'LOGOUT_TOKEN') 데이터를 저장해 블랙리스트를 구현했습니다.

### 2. 유효성 검증
- Spring Validation과 async-validator를 사용하여 폼 데이터 요청시 데이터가 조작을 방지하고 회원의 사용성을 증대시켰습니다.
  - 일차적으로 element plus에서 지원하는 form을 사용해 폼 데이터 입력시 잘못된 값에 대해 화면에 출력했습니다.
  - 서버에서는 spring validation을 사용해 response body로 사용되는 모든 객체에 검증로직을 구현했습니다.

### 3. Querydsl-JPA
- 대용량의 데이터가 쌓인 테이블 환경에서도 사용ㅡㄸ할 수 있도록 Querydsl-JPA 성능을 개선했습니다.
  - 커버링 인덱스를 사용해 페이징 조회의 성능을 개선 시켰습니다.
    ![image](https://user-images.githubusercontent.com/48740872/203514666-122b6198-2d2e-417f-ba83-98f966b3d5c8.png)
  - 엔티티보다 DTO조회를 우선적으로 고려했습니다. 
    - 응답에 필요한 컴럼만 가져오고 매개변수로 전달 받아 이미 값을 알고 있는 컬럼은 직접 값을 넣어 조회 컬럼수를 최소화하였습니다.

### 

# 🎞 UI 기능 및 화면
### 🏠 메인 화면
1. #### '리버먼스'의 도시 6곳의 요약 정보를 볼 수 있습니다.
   - 도시 이미지, 교통 점수, 평균 기온
   - 각 이미지에 마우르 호버 시 카드가 뒤집히면서 정보가 나타납니다.
   <details>
      <summary>
         <span style="font-style: italic;cursor: pointer">
            도시 요약 정보 화면보기
         </span>
      </summary>
   
      ![Home 도시 정보](https://user-images.githubusercontent.com/48740872/199435645-e600e894-7401-44cd-bd6f-328335574272.gif)
   </details>

2. #### 인기 게시물(스케줄, 후기글)을 볼 수 있습니다.
   - 스케줄은 '좋아요' 기준 TOP5까지 표시됩니다.
   - 후기글은 '좋아요' 기준 TOP7까지 표시됩니다.
   - 로그인을 한 경우 팔로우한 회원의 스케줄이 Infinite Scroll 방식으로 표시됩니다.
   - 각 게시물의 제목을 클릭하면 해당 게시물로 이동이 가능합니다.
   <details>
      <summary>
         <span style="font-style: italic;cursor: pointer">
            인기 게시물 화면보기
         </span>
      </summary>
      
      ![Home 인기게시물](https://user-images.githubusercontent.com/48740872/199434476-a54050e5-cfa0-47e5-be91-4ba97c68d526.gif)
   
   </details>

### 🏞 도시 소개
1. #### '리버먼스'의 추천 도시 6곳에 대한 정보 및 사진을 볼 수 있습니다.
   - 도시 소개, 교통 정보, 전년도 기온 정보 탭을 통해 다양한 정보를 확인할 수 있습니다.
   - 교통 점수는 도시별로 대중 교통, 터미널, 공항 등 여러 교통 수단의 갯수와 이를 상대 평가하여 별점으로 나타냈습니다.
   - 기온 정보는 기상청 홈페이지에서 도시의 전년도 기온 정보를 최저, 최고, 평균 기온으로 표시하였습니다.
   - '리버먼스' 추천 도시 제목 우측에 있는 도시를 선택해 도시별 정보를 볼 수 있습니다.
   - 하단에 '먹거리' & '볼거리'는 각 도시에서 유명한 먹거리와 볼거리 이미지를 슬라이드로 볼 수 있습니다.
   <details>
      <summary>
         <span style="font-style: italic;cursor: pointer">
            도시 소개 화면보기
         </span>
      </summary>
      
      ![도시 소개](https://user-images.githubusercontent.com/48740872/199474304-bacf9ff2-b304-4928-804f-1c985f71c82e.gif)
   </details>

### 🔨 계정
1. #### 회원가입
   - 홈 화면 우측 상단의 회원가입 및 로그인 화면 하단의 회원가입 버튼을 통해 회원가입을 진행할 수 있습니다.
   - 아이디, 비밀번호, 비밀번호 확인, 이름, 닉네임, 이메일, 생년월일, 성별 총 8가지 항목을 입력합니다.
   - 아이디, 닉네임, 이메일은 입력 후 다른 칸으로 이동하면 중복 검사를 합니다.
     - 해당 항목이 중복일 경우 메시지 박스와 함께 초기화 됩니다.
     - 중복이 아닌 경우 해당 항목이 비활성화 되며 입력창 오른쪽 초기화 버튼을 통해 다시 입력할 수 있습니다.
   - 비밀번호는 입력창 오른쪽 눈 아이콘을 통해 보이게 할 수 있습니다.
   - 각 항목 마다 유효성 검사가 있습니다. 적절하지 않은 입력값에 대해서는 입력창 아래에 빨간 글씨로 표시됩니다.
     - 예를 들어, 비밀번호는 비어서는 안되며 8-20자리 영문자,숫자,특수문자를 조합해야 합니다.
   - 회원가입이 완료되면 로그인 화면으로 이동됩니다.
   <details>
      <summary>
         <span style="font-style: italic;cursor: pointer">
            회원가입 화면보기
         </span>
      </summary>
   
      ![Member 회원가입](https://user-images.githubusercontent.com/48740872/199478350-feaf83c0-7369-4d3c-81cd-6a8eeb5e69b4.gif)
   </details>
2. #### 아이디 찾기
   - '로그인 -> 아이디 찾기'를 통해 진입합니다.
   - 사용자의 이름, 이메일을 입력하면 아이디를 찾을 수 있습니다.
   - 사용자의 정보와 일치하는 아이디의 끝 세자리에 '*' 처리가 되어있으며 가입날짜를 확인할 수 있습니다.
   <details>
      <summary>
         <span style="font-style: italic;cursor: pointer">
            아이디 찾기 화면보기
         </span>
      </summary>
      
      ![Member 아이디 찾기](https://user-images.githubusercontent.com/48740872/199478346-f4ad6f9c-743d-4af4-99fe-c357828b1360.gif)
   </details>
3. #### 비밀번호 찾기
   - '로그인 -> 비밀번호 찾기' 또는 '로그인 -> 아이디 찾기 -> 찾기 결과 -> 비밀번호 찾기'를 통해 진입합니다.
   - 사용자의 아이디와 이메일을 입력하면 비밀번호를 찾을 수 있습니다.
   - 사용자의 정보와 일치하는 비밀번호를 찾을 경우 입력한 이메일로 변경된 임시 비밀번호를 발급 받습니다.
   <details>
      <summary>
         <span style="font-style: italic;cursor: pointer">
            비밀번호 찾기 화면보기
         </span>
   
      </summary>
   
      ![Member 비밀번호찾기](https://user-images.githubusercontent.com/48740872/199478325-284728e9-14a1-4365-a83b-e38cec1afce4.gif)
   </details>
4. #### 로그인, 로그아웃
   - 홈화면 우측 상단 혹은 프로필 카드의 버튼을 통해 로그인, 로그아웃을 할 수 있습니다.
   - 로그인을 하지 않은 상태에서 사용자 정보가 필요한 컨텐츠를 이용하면 로그인 창으로 이동됩니다.
     - ex) 내 스케줄 관리, 내정보, 글쓰기, 댓글 달기 등..
   <details>
      <summary>
         <span style="font-style: italic;cursor: pointer">
            로그인 로그아웃 화면보기
         </span>
      </summary>

   ![Member 로그인,로그아웃](https://user-images.githubusercontent.com/48740872/201579808-72737498-2c4d-49e0-9ff2-dfa1a5e06f4e.gif)
   </details>
5. #### 회원정보 수정
   - '내 정보 -> 프로필 편집' 또는 '내 정보 -> 톱니바퀴 버튼 -> 프로필 편집' 으로 진입합니다.
   - 사용자의 비밀번호를 재확인 한 후 확인되면 프로필 편집을 할 수 있습니다.
   - 변경할 수 있는 항목은 닉네임, 이메일이며 회원가입과 마찬가지로 중복 확인, 유효성 검사를 마친 뒤 변경 가능합니다.
   <details>
      <summary>
         <span style="font-style: italic;cursor: pointer">
            회원정보 수정 화면보기
         </span>
      </summary>
      
      ![Member 회원정보 수정](https://user-images.githubusercontent.com/48740872/199478358-1b9205ef-68cd-4576-ad1c-2bf65943e646.gif)
   </details>
6. #### 비밀번호 변경
   - '내 정보 -> 톱니바퀴 버튼 -> 비밀번호 변경'으로 진입합니다.
   - 사용자의 비밀번호를 재확인 한 후 확인되면 비밀번호 변경을 할 수 있습니다.
   - 비밀번호가 변경되면 해당 사용자는 로그아웃 되고 변경된 비밀번호로 다시 로그인을 해야합니다.
   <details>
      <summary>
         <span style="font-style: italic;cursor: pointer">
            비밀번호 변경
         </span>
      </summary>
      
      ![MyPage 비밀번호변경](https://user-images.githubusercontent.com/48740872/199483160-14756639-454e-47fc-93eb-73f707364771.gif)
   </details>
7. #### 1대1 문의
   - '내 정보 -> 톱니바퀴 버튼 -> 문의하기 or 문의목록'으로 진입합니다.
   - '문의 하기'에서는 관리자에게 문의할 내용을 작성할 수 있습니다.
     - 제목, 카테고리, 내용을 입력해야 합니다.
     - 위의 항목 모두 유효성 검사를 하며 빈 항목이 있으면 안됩니다.
   - '문의 하기' 작성이 모두 완료되면 '문의 목록'으로 이동됩니다.
   - '문의 목록'에서는 사용자가 작성한 모든 문의 목록이 페이지 형식으로 보여줍니다.
     - 카테고리, 제목, 작성자, 작성시간, 답변 유무를 확인할 수 있습니다.
   - 원하는 목록을 선택해 관리자의 답변을 확인하거나 문의 내용을 수정, 삭제할 수 있습니다.
   <details>
      <summary>
         <span style="font-style: italic;cursor: pointer">
            1대1 문의
         </span>
      </summary>
      
      ![MyPage 1대1 문의](https://user-images.githubusercontent.com/48740872/199483147-af6cc97a-1b3e-487a-a48f-2dd1b1d71c46.gif)
   </details>

### 🔨 마이페이지
1. #### 내 스케줄 관리
   - '홈화면' 상단의 메뉴 혹은 우측의 프로필 카드의 스케줄을 눌러 진입할 수 있습니다.
   - 사용자의 스케줄 리스트가 Infinite Scroll 형식으로 표시되며 해당 페이지에서 '삭제'할 수 있습니다.
   - 리스트 우측 하단의 '스케줄 관리->' 혹은 스케줄 제목을 클릭해 '내 스케줄 관리'로 이동할 수 있습니다.
   <details>
      <summary>
         <span style="font-style: italic;cursor: pointer">
            내 스케줄 관리 화면보기
         </span>
      </summary>
   
      ![MyPage 스케줄 관리](https://user-images.githubusercontent.com/48740872/199483166-daf95357-a09d-4d05-8968-4c71b965785c.gif)
   </details>

2. #### 내 후기글 관리
   - '홈화면' 상단의 메뉴 혹은 우측의 프로필 카드의 후기글을 눌러 진입할 수 있습니다.
   - 사용자의 후기글 리스트가 Infinite Scroll 형식으로 표시되며 해당 페이지에서 '삭제'할 수 있습니다.
   - 후기글 제목을 클릭해 해당 후기글로 이동할 수 있습니다.
   <details>
      <summary>
         <span style="font-style: italic;cursor: pointer">
            내 후기글 관리 화면보기
         </span>
      </summary>
   
   ![MyPage 후기글 관리](https://user-images.githubusercontent.com/48740872/199483173-09969a29-e446-4749-ad09-d0045c931a87.gif)
   </details>

### 📅 스케줄
1. #### 다른 사람 스케줄 - 검색
   - '검색' & '필터' 기능을 사용할 수 있습니다.
     - '검색'은 작성자 닉네임 혹은 스케줄 제목을 검색할 수 있습니다.
     - '필터'는 도시이름(select) 혹은 스케줄 시작날짜(date select)로 필터링 할 수 있습니다.
   - 검색 버튼 아래의 초기화 버튼을 통해 검색 또는 필터 입력창의 내용을 초기화 할 수 있습니다.
   
   <details>
      <summary>
         <span style="font-style: italic;cursor: pointer">
            다른 사람 스케줄 - 검색 화면보기
         </span>
      </summary>
      
      ![Schedule 검색](https://user-images.githubusercontent.com/48740872/199483943-6c3d562a-e071-4d96-a6c2-f2bf4530c504.gif)
      ![Schedule 검색2](https://user-images.githubusercontent.com/48740872/199483956-458fcbec-90b1-4e6f-b09c-bdcf2f5eab52.gif)
   </details>

2. #### 다른 사람 스케줄 검색 - 정렬
   - 표시된 스케줄 리스트를 '정렬'할 수 있습니다.
   - 라디오 버튼으로 표시되어 있으며 최신순, 조회순, 인기순, 댓글순, 비용순으로 정렬할 수 있습니다.
   <details>
      <summary>
         <span style="font-style: italic;cursor: pointer">
            다른 사람 스케줄 - 정렬 화면보기
         </span>
      </summary>
   
      ![Schedule 정렬](https://user-images.githubusercontent.com/48740872/199483989-78f71768-4460-46c5-961b-f0888088042d.gif)
   </details>
3. #### 다른 사람 스케줄 보기
   - 다른 사람 스케줄 보기에서는 스케줄표에 있는 컨텐츠 제목을 선택하거나 우측의 컨텐츠 제목을 선택해 컨텐츠 내용을 확인할 수 있습니다.
     - 컨텐츠 카드 상단에 있는 모두 보기 버튼을 눌러 모든 컨텐츠 내용을 확인할 수 있습니다.
     - 반대로 모두 닫기를 눌러 모든 컨텐츠 내용을 최소화 할 수 있습니다.
     - 보고 있는 스케줄이 사용자의 스케줄일 경우 프로필 하단에 팔로우 하기 버튼이 사라집니다.
   <details>
      <summary>
         <span style="font-style: italic;cursor: pointer">
            다른 사람 스케줄 화면보기
         </span>
      </summary>

   ![Schedule 보기](https://user-images.githubusercontent.com/48740872/201579820-5749a141-ef4e-4e19-9502-1f7aac57cb65.gif)
   </details>

4. #### 내 스케줄 관리 - 생성 & 수정
   - 우측 상단에 있는 스케줄 추가 버튼을 통해 새 스케줄을 추가할 수 있습니다.
   - 공개 여부, 제목, 도시, 스케줄 기간 항목을 입력 후 스케줄을 생성합니다.
   - 초기화 버튼을 통해 입력중이던 항목을 초기화할 수 있습니다.
   - 스케줄표 상단에 있는 스케줄을 선택하면 우측 스케줄 카드에서 스케줄 정보를 확인할 수 있습니다.
   - 수정 및 삭제를 할 수 있습니다.
     - 수정 버튼을 눌러 내용을 변경하고 업데이트 버튼을 눌러 수정을 완료합니다.
     - 삭제 버튼을 눌러 스케줄을 삭제할 수 있습니다.
   <details>
      <summary>
         <span style="font-style: italic;cursor: pointer">
            내 스케줄 - 생성&수정 화면보기
         </span>
      </summary>
   
      ![Schedule 생성 수정](https://user-images.githubusercontent.com/48740872/199483976-554e73e6-dc7c-4cfb-8281-f0bb0e745182.gif)
   </details>

5. #### 내 스케줄 관리 - 컨텐츠 추가
   - 스케줄 카드 하단에 있는 컨텐츠 추가 버튼 혹은 스케줄표의 날짜를 선택해서 컨텐츠를 추가할 수 있습니다.
   - 제목, 내용, 비용, 시작 및 종료 시간을 입력 후 컨텐츠를 추가합니다.
   - 초기화 버튼을 통해 입력중인 내용을 초기화할 수 있습니다.
   - 스케줄표에 있는 컨텐츠 제목을 선택해 컨텐츠 정보를 확인할 수 있습니다.
     - 스케줄표 우측 상단에 있는 일정 목록을 클릭해 컨텐츠를 선택할 수도 있습니다.
   <details>
      <summary>
         <span style="font-style: italic;cursor: pointer">
            내 스케줄 - 컨텐츠 추가 화면보기
         </span>
      </summary>
   
      ![Schedule 컨텐츠 추가1](https://user-images.githubusercontent.com/48740872/199484008-0ddd95fd-9a37-44ba-af6e-a8a628e1e839.gif)
      ![Schedule 컨텐츠 추가2](https://user-images.githubusercontent.com/48740872/199484016-04f88da9-4d7c-4d6c-8b71-94fe801de5a8.gif)
   </details>

6. #### 내 스케줄 관리 - 컨텐츠 수정&삭제
   - 선택된 컨텐츠의 정보가 컨텐츠 정보 카드에 표시되며 하단의 수정 및 삭제 버튼을 통해 컨텐츠를 관리할 수 있습니다.
   <details>
      <summary>
         <span style="font-style: italic;cursor: pointer">
            내 스케줄 - 컨텐츠 수정&삭제 화면보기
         </span>
      </summary>
      
      ![Schedule 컨텐츠 수정](https://user-images.githubusercontent.com/48740872/199484002-1e34c93d-5879-4c00-9fb0-aca75c216faf.gif)
      ![Schedule 컨텐츠 삭제](https://user-images.githubusercontent.com/48740872/199483996-f906b60b-f091-4cf0-a06b-ae4e24228712.gif)
   </details>

### 📑 후기글
1. #### 후기글 검색
   - '리버먼스 지역', '다른 지역', '질문 게시판', '자유 게시판' 총 4개의 카테고리가 있습니다.
     - '리버먼스 지역'의 경우 검색시 '리버먼스'6개 도시와 전체 총 7개의 소분류로 필터링할 수 있습니다.
   - 후기글 제목과 내용, 작성자 닉네임으로 검색할 수 있습니다.
   - 검색 버튼 하단의 초기화 버튼을 통해 입력중이던 모든 내용을 초기화할 수 있습니다.
   - 검색 레이아웃 하단의 라디오 버튼을 통해 최신순, 조회순, 인기순, 댓글순으로 정렬할 수 있습니다.
   <details>
      <summary>
         <span style="font-style: italic;cursor: pointer">
          후기글 검색 화면보기
         </span>
      </summary>
   
      ![Review 검색3](https://user-images.githubusercontent.com/48740872/199486817-20d9a87b-42c0-4860-9e11-caf053b0e14a.gif)
      ![Review 검색_리버먼스지역](https://user-images.githubusercontent.com/48740872/199486835-e7761e5b-164f-4171-b4e8-fe0972ae2170.gif)
   </details>

2. #### 후기글 검색 - 태그
   - 검색 입력창 하단에 있는 태그 추가 버튼을 눌러 후기글을 태그로 검색할 수 있습니다.
     - 태그 추가는 10개 까지 가능합니다.
   - 화면 우측에 있는 추천태그를 선택해 해당 태그로 바로 검색할 수 있습니다.
   <details>
      <summary>
         <span style="font-style: italic;cursor: pointer">
            후기글 검색 - 태그 화면보기
         </span>
      </summary>
   
      ![Review 검색_태그](https://user-images.githubusercontent.com/48740872/199486809-93ad56fb-9910-4ac9-929b-1ce6f0138b91.gif)
   </details>

3. #### 후기글 작성
   - 글쓰기 버튼으로 후기글을 작성할 수 있습니다.
      - 제목, 카테고리, 태그, 본문 내용을 입력해 작성합니다.
   <details>
      <summary>
         <span style="font-style: italic;cursor: pointer">
            후기글 작성 화면보기
         </span>
      </summary>
   
      ![Review 작성](https://user-images.githubusercontent.com/48740872/199486829-464cbdb8-68aa-47c3-a839-cae147d578a8.gif)
   </details>

4. #### 후기글 보기
   - 후기글 리스트에서 보고 싶은 후기글을 선택해 내용을 확인할 수 있습니다.
   - 작성자의 후기글인 경우 본문 하단에 수정, 삭제 버튼이 생깁니다.
   <details>
      <summary>
         <span style="font-style: italic;cursor: pointer">
            후기글 화면보기
         </span>
      </summary>

   ![후기글 보기](https://user-images.githubusercontent.com/48740872/201579828-f3262091-97b7-4a9e-81d9-8b06dc087a40.gif)
   </details>
5. #### 후기글 수정 & 삭제
   - 수정버튼을 누르면 글을 수정할 수 있는 화면이 나오고 기존의 내용을 수정할 수 있습니다.
   - 본문 내용 우측 상단에 목록으로 돌아가기 버튼을 통해 후기글 목록으로 돌아갈 수 있습니다.
   <details>
      <summary>
         <span style="font-style: italic;cursor: pointer">
            후기글 수정 화면보기
         </span>
      </summary>
   
      ![Review 수정](https://user-images.githubusercontent.com/48740872/199486823-740177fe-df81-4f7e-99bf-adea6b5c0f52.gif)
   </details>

### 👍 좋아요&팔로우
1. #### 스케줄
   - 스케줄 보기에서 제목 옆 하트를 눌러 해당 스케줄에 '좋아요'를 할 수 있습니다.
   - 작성자 프로필 하단에 있는 팔로우 하기 버튼을 눌러 작성자를 팔로우 할 수 있습니다.
   - 좋아요와 팔로우 모두 다시 한 번 클릭하면 취소할 수 있습니다.
   <details>
      <summary>
         <span style="font-style: italic;cursor: pointer">
            스케줄 화면보기
         </span>
      </summary>
   
      ![Interaction 스케줄](https://user-images.githubusercontent.com/48740872/199488036-1ef5b3a3-ca76-48cc-ba2f-53f314a7ee97.gif)
   </details>
2. #### 후기글
   - 후기글 보기에서 본문 내용 하단에 하트를 눌러 '좋아요'를 할 수 있습니다.
   - 후기글 제목 아래 작성자 닉네임에 마우스를 올려 표시되는 프로필창에서 팔로우, 언팔로우를 할 수 있습니다.
   <details>
      <summary>
         <span style="font-style: italic;cursor: pointer">
            후기글 화면보기
         </span>
      </summary>
   
      ![Interaction 후기글](https://user-images.githubusercontent.com/48740872/199488039-67b8f8ff-473c-4435-b08b-3f7ec082c493.gif)
   </details>

### ✏ 댓글
1. #### 댓글 작성
   - 게시물 하단에 있는 댓글 레이아웃에서 TOP3 및 댓글, 대댓글을 쓸 수 있습니다.
   - 프로필 이미지, 닉네임, 댓글 생성 경과시간, 추천, 비추천 수를 확인할 수 있습니다.
   - 사용자 자신의 게시물에 댓글을 달 경우 '작성자 댓글' 태그가 달립니다.
   - 댓글 아래의 '대댓글' 을 눌러 대댓글을 작성할 수 있으며 기능은 댓글과 동일합니다.
   <details>
      <summary>
         <span style="font-style: italic;cursor: pointer">
            댓글 작성 화면보기
         </span>
      </summary>
   
      ![Comment 작성_스케줄](https://user-images.githubusercontent.com/48740872/199488605-ca6ca5c5-29b9-40ff-8dd8-ee2779299cd4.gif)
      ![Comment 작성_후기글](https://user-images.githubusercontent.com/48740872/199488612-6b31f63f-73bf-4c92-ba3c-1497e85b6ab2.gif)
   </details>
2. #### 댓글 수정 & 삭제
   - '수정' 버튼 클릭 시 댓글 입력창에 해당 댓글 내용이 입력되고 댓글을 수정할 수 있습니다.
   - '삭제' 버튼 클릭시 해당 댓글을 삭제할 수 있습니다.
   <details>
      <summary>
         <span style="font-style: italic;cursor: pointer">
            댓글 수정 & 삭제 화면보기
         </span>
      </summary>
   
      ![Comment 수정 삭제](https://user-images.githubusercontent.com/48740872/199488593-429d4d1b-e2f0-4430-8d27-9a9ef5e848f8.gif)
   
   </details>
3. #### 베스트 댓글 & 추천, 비추천
   - 대댓글을 제외하고 댓글 중에서 추천수가 가장 높은 순으로 TOP3가 표시 됩니다.
   - 엄지 아이콘을 클릭해 해당 댓글, 대댓글에 추천, 비추천할 수 있습니다.
   - 추천한 댓글은 비추천 할 수 없으며 마찬가지로 비추천한 댓글은 추천할 수 없습니다.
   - 추천 및 비추천을 한 댓글의 경우 엄지아이콘에 색이 칠해지며 다시 한 번 누를 경우 취소할 수 있습니다.
   <details>
     <summary>
        <span style="font-style: italic;cursor: pointer">
           베스트 댓글 & 추천,비추천
        </span>
     </summary>
 
     ![Comment 배댓 추천](https://user-images.githubusercontent.com/48740872/199488584-82bc9ccd-f980-4df1-91ba-fdc74da120dd.gif)
   </details>
### 🏷 게시물 리스트
1. #### 스케줄 카드
   - 스케줄 카드 좌측에는 해당 스케줄의 기간이 달력에 표시되어 있습니다.
   - 스케줄 기간
      - 스케줄 시작, 종료 날짜
   - 작성자 프로필
      - 프로필 이미지, 닉네임, 스케줄, 후기글, 팔로워 수
   - 스케줄 종합 정보
      - 제목, 댓글수
      - 기간, 총 비용, 지역
      - 조회수, 좋아요수
   <details>
      <summary>
         <span style="font-style: italic;cursor: pointer">
            스케줄 카드 화면보기
         </span>
      </summary>

   ![image](https://user-images.githubusercontent.com/48740872/201597524-01529bfb-538a-49c1-ba9c-2e4fc32373e3.png)
   </details>
2. #### 후기글 카드
   - 후기글 카드에는 제목, 내용, 닉네임, 작성 경과 시간, 조회수, 댓글 수, 좋아요 수가 표시됩니다.
   - 본문 내용은 2줄만 표시되며 뒷 부분은 ... 으로 처리했습니다.
   <details>
      <summary>
         <span style="font-style: italic;cursor: pointer">
            후기글 카드 화면보기
         </span>
      </summary>

   ![image](https://user-images.githubusercontent.com/48740872/201595859-fe3d395c-3f41-405a-b00b-29a72d3e9945.png)
   </details>
3. #### 페이지
   - 스케줄, 후기글, 댓글 리스트에서는 페이지 방식을 사용했습니다.
      - '<<' : 맨 앞으로
      - '<' : 이전 페이지
      - '>' : 다음 페이지
      - '>>' : 맨 마지막으로
   <details>
     <summary>
        <span style="font-style: italic;cursor: pointer">
           베스트 댓글 & 추천,비추천
        </span>
     </summary>

   ![List 스케줄_페이지](https://user-images.githubusercontent.com/48740872/201593430-b241100d-d6e5-4858-bf50-00a1fd94fead.gif)
   ![List 후기글_페이지](https://user-images.githubusercontent.com/48740872/201593435-757c52be-fec9-4c7c-a1e8-83274e7fa8f9.gif)
   </details>
4. #### 무한 스크롤
   - '팔로우 스케줄', '내정보 게시물'에서는 무한 스크롤을 사용해 목록을 표시했습니다.
      - 초기 게시물의 갯수: 10개
      - 추가로 불러오는 게시물 수: 10개
   - 게시물을 불러올 때와 모든 게시물을 불러왔을 때 마지막 리스트 아래에 상태를 표시했습니다.
   
   <details>
     <summary>
        <span style="font-style: italic;cursor: pointer">
           베스트 댓글 & 추천,비추천
        </span>
     </summary>

   ![List 스케줄_무한_스크롤](https://user-images.githubusercontent.com/48740872/201593421-11d19a8a-6331-4e2b-8fbc-7834f7aeac5d.gif)
   ![List 후기글_무한_스크롤](https://user-images.githubusercontent.com/48740872/201593433-6821339b-e2e7-4045-b903-e8385da72667.gif)
   </details>

5. 
