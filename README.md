# Liveamonth ver.2.0.0

### [Liveavmonth Ver 1.0](https://github.com/LiveAmonth/LiveAmonth-ver.1.0.0)

___

## 목차
1. [📢 프로젝트 기획](#-프로젝트-기획)
2. [🛠 기술 스택](#-기술-스택)
3. [🎞 리버먼스 시연](#-리버먼스-시연)
    - [🏠 메인 화면](#-메인-화면)
    - [🏞 도시 소개](#-도시-소개)
    - [🔨 계정](#-계정)
    - [🔨 마이페이지](#-마이페이지)
    - [📅 스케줄](#-스케줄)
    - [📑 후기글](#-후기글)
    - [👍 좋아요&팔로우](#-좋아요&팔로우)
    - [✏ 댓글](#-댓글)




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
### BackEnd
|  Spring   |  Java   |
|:---------:|:-------:|
| ![spring] | ![java] |

### DataBase
|  MySql   |  Redis   |
|:--------:|:--------:|
| ![mysql] | ![redis] |

### Front End
| vue.js3 | Typescript |
|:-------:|:----------:|
| ![vue]  |   ![ts]    |


[spring]: /images/stack/spring.svg
[ts]: /images/stack/typescript.svg
[vue]: /images/stack/vuedotjs.svg
[mysql]: /images/stack/mysql.svg
[security]: /images/stack/springsecurity.svg
[redis]: /images/stack/redis.svg
[java]: /images/stack/java.svg

# E-R 다이어그램
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


# 🎞 구현 기능
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

4. #### 회원정보 수정
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

5. #### 비밀번호 변경
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

6. #### 1대1 문의
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

<details>
   <summary>
      <span style="font-style: italic;cursor: pointer">
         내 스케줄 관리
      </span>
   </summary>

   ```
   - 내가 작성한 스케줄 리스트 표시
   - 삭제 및 내 스케줄 페이지(선택한 스케줄이 초기값)로 이동 가능
   ```
   
   ![MyPage 스케줄 관리](https://user-images.githubusercontent.com/48740872/199483166-daf95357-a09d-4d05-8968-4c71b965785c.gif)
   
</details>

<details>
   <summary>
      <span style="font-style: italic;cursor: pointer">
         내 후기글 관리
      </span>
   </summary>

   ```
   - 내가 작성한 후기글 리스트 표시
   - 삭제 및 해당 후기글로 이동 가능
   ```
   
   ![MyPage 후기글 관리](https://user-images.githubusercontent.com/48740872/199483173-09969a29-e446-4749-ad09-d0045c931a87.gif)

</details>

### 📅 스케줄

<details>
   <summary>
      <span style="font-style: italic;cursor: pointer">
         다른 사람 스케줄 - 검색
      </span>
   </summary>
   
   ```
   - 닉네임, 제목으로 검색 가능
   - 스케줄 도시, 시작 날짜로 필터 가능
   ```
   
   ![Schedule 검색](https://user-images.githubusercontent.com/48740872/199483943-6c3d562a-e071-4d96-a6c2-f2bf4530c504.gif)
   ![Schedule 검색2](https://user-images.githubusercontent.com/48740872/199483956-458fcbec-90b1-4e6f-b09c-bdcf2f5eab52.gif)

</details>

<details>
   <summary>
      <span style="font-style: italic;cursor: pointer">
         다른 사람 스케줄 - 정렬
      </span>
   </summary>

   ```
   - 정렬 기능(최신순, 조회순, 인기순, 댓글순, 비용순)
   ```
   
   ![Schedule 정렬](https://user-images.githubusercontent.com/48740872/199483989-78f71768-4460-46c5-961b-f0888088042d.gif)

</details>

<details>
   <summary>
      <span style="font-style: italic;cursor: pointer">
         내 스케줄 - 생성&수정
      </span>
   </summary>

   ```
   - 제목, 도시, 기간, 공개 여부 설정 및 수정
   ```
   ![Schedule 생성 수정](https://user-images.githubusercontent.com/48740872/199483976-554e73e6-dc7c-4cfb-8281-f0bb0e745182.gif)

</details>

<details>
   <summary>
      <span style="font-style: italic;cursor: pointer">
         내 스케줄 - 컨텐츠 추가
      </span>
   </summary>

   ```
   - 오른쪽 컨텐츠 추가를 눌러 컨텐츠 추가
   - 달력 칸을 눌러 컨텐츠 추가
   - 제목, 내용, 시작, 종료시간, 금액 설정
   ```
   ![Schedule 컨텐츠 추가1](https://user-images.githubusercontent.com/48740872/199484008-0ddd95fd-9a37-44ba-af6e-a8a628e1e839.gif)
   ![Schedule 컨텐츠 추가2](https://user-images.githubusercontent.com/48740872/199484016-04f88da9-4d7c-4d6c-8b71-94fe801de5a8.gif)

</details>

<details>
   <summary>
      <span style="font-style: italic;cursor: pointer">
         내 스케줄 - 컨텐츠 수정&삭제
      </span>
   </summary>
   
   ![Schedule 컨텐츠 수정](https://user-images.githubusercontent.com/48740872/199484002-1e34c93d-5879-4c00-9fb0-aca75c216faf.gif)
   ![Schedule 컨텐츠 삭제](https://user-images.githubusercontent.com/48740872/199483996-f906b60b-f091-4cf0-a06b-ae4e24228712.gif)

</details>

### 📑 후기글

<details>
   <summary>
      <span style="font-style: italic;cursor: pointer">
       후기글 검색
      </span>
   </summary>

   ```
   - 리버먼스 지역 카테고리의 경우 도시 이름으로 필터링 가능
   ```
   
   ![Review 검색3](https://user-images.githubusercontent.com/48740872/199486817-20d9a87b-42c0-4860-9e11-caf053b0e14a.gif)
   ![Review 검색_리버먼스지역](https://user-images.githubusercontent.com/48740872/199486835-e7761e5b-164f-4171-b4e8-fe0972ae2170.gif)

</details>

<details>
   <summary>
      <span style="font-style: italic;cursor: pointer">
         후기글 검색 - 태그
      </span>
   </summary>

   ```
   - 검색 창 아래 태그 추가 버튼으로 태그 검색
   - 오른쪽 카드의 추천 태그로 태그 검색
   ```

   ![Review 검색_태그](https://user-images.githubusercontent.com/48740872/199486809-93ad56fb-9910-4ac9-929b-1ce6f0138b91.gif)

</details>

<details>
   <summary>
      <span style="font-style: italic;cursor: pointer">
         후기글 작성
      </span>
   </summary>

   ```
   - 제목, 카테고리, 태그, 내용 작성
   ```

   ![Review 작성](https://user-images.githubusercontent.com/48740872/199486829-464cbdb8-68aa-47c3-a839-cae147d578a8.gif)

</details>

<details>
   <summary>
      <span style="font-style: italic;cursor: pointer">
         후기글 수정
      </span>
   </summary>

   ![Review 수정](https://user-images.githubusercontent.com/48740872/199486823-740177fe-df81-4f7e-99bf-adea6b5c0f52.gif)

</details>

### 👍 좋아요&팔로우

<details>
   <summary>
      <span style="font-style: italic;cursor: pointer">
         스케줄
      </span>
   </summary>

   ```
   - 작성자 프로필 아래 팔로우
   - 제목 오른쪽 하트 좋아요
   ```

   ![Interaction 스케줄](https://user-images.githubusercontent.com/48740872/199488036-1ef5b3a3-ca76-48cc-ba2f-53f314a7ee97.gif)

</details>

<details>
   <summary>
      <span style="font-style: italic;cursor: pointer">
         후기글
      </span>
   </summary>

   ```
   - 제목 아래 작성자 마우스 호버시 popover 프로필팔로우
   - 본문 아래 중앙 하트 좋아요
   ```

   ![Interaction 후기글](https://user-images.githubusercontent.com/48740872/199488039-67b8f8ff-473c-4435-b08b-3f7ec082c493.gif)

</details>

### ✏ 댓글

<details>
   <summary>
      <span style="font-style: italic;cursor: pointer">
         댓글 작성 - 스케줄
      </span>
   </summary>

   ![Comment 작성_스케줄](https://user-images.githubusercontent.com/48740872/199488605-ca6ca5c5-29b9-40ff-8dd8-ee2779299cd4.gif)

</details>

<details>
   <summary>
      <span style="font-style: italic;cursor: pointer">
         댓글 작성 - 후기글
      </span>
   </summary>

   ![Comment 작성_후기글](https://user-images.githubusercontent.com/48740872/199488612-6b31f63f-73bf-4c92-ba3c-1497e85b6ab2.gif)

</details>

<details>
   <summary>
      <span style="font-style: italic;cursor: pointer">
         댓글 수정 & 삭제
      </span>
   </summary>

   ![Comment 수정 삭제](https://user-images.githubusercontent.com/48740872/199488593-429d4d1b-e2f0-4430-8d27-9a9ef5e848f8.gif)

</details>

<details>
   <summary>
      <span style="font-style: italic;cursor: pointer">
         베스트 댓글 & 추천,비추천
      </span>
   </summary>

   ![Comment 배댓 추천](https://user-images.githubusercontent.com/48740872/199488584-82bc9ccd-f980-4df1-91ba-fdc74da120dd.gif)

</details>


