# TodayRecipe 프로젝트 정의서
<aside>
💡 원하는 결과
</aside>

1. 회원 가입 - 로그아웃 - 회원탈퇴 - 로그인 기능을 구현하고 싶음
2. 각각의 레시피 게시판을 생성
    1. 게시글 작성/수정/삭제/읽기 등 기초적인 기능 필요
    2. 페이징 처리(게시글 검색후 페이징 처리도 필요)
    3. 게시글 검색
    4. 게시글 전체 목록 조회(게시판 탭 이동 시)
    5. 유저/제목 등으로 검색 가능하게 
    - 6. 댓글
        1. 댓글작성/수정/삭제
        2. 비밀댓글도 가능하면 해보기
3. 요리 테마를 정했을 때 추천을 많이 받은 레시피를 우선적으로 보여주는 것도 괜찮을 듯(그러면 글에다가 추천 기능도 필요할 듯함)
4. 관리자 페이지
    - 회원 목록 가져오기(이 때 당연히 회원 개인 정보는 숨기는 게 필요할 듯?(보이는 것도 선정해야 할 듯 함)
    - 게시글 관리(수정은X 삭제 정도만)
    - 회원 관리 시 추방 기능도 있으면? 좋을 듯(신고 누적 같은)
    - 사실 관리자 페이지는 시간 남으면 추가하는 방향

<aside>
💡 개발 환경

</aside>

- Java 11
- SpringBoot 3.0.2(SNAPSHOT)
- JPA(SpringDataJPA)
- MariaDB
- Thymeleaf
- Gradle-Groovy
- Spring Security

<aside>
💡 DB 구성?

</aside>

1. User

| Column | Type | Condition | About |
| --- | --- | --- | --- |
| id | Long | Primary key | 유저 고유 값 |
| userId | VARCHAR | NOT NULL/unique | 아이디 |
| password | VARCHAR | NOT NULL | 비밀번호 |
| nickname | VARCHAR | NOT NULL/unique | 닉네임 |
| email | VARCHAR | NOT NULL/unique | 이메일 |
| address | VARCHAR | NOT NULL | 주소 |
| indate | VARCHAR | NOT NULL | 가입일 |
2. Admin

| Column | Type | Condition | About |
| --- | --- | --- | --- |
| AdminId | VARCHAR | NOTNULL/PK | 관리자 아이디 |
| AdminPW | VARCHAR | NOTNULL | 관리자 비밀번호 |
3. Post

| Column | Type | Condition | About |
| --- | --- | --- | --- |
| id | Long | Primary key | 게시글 고유값 |
| title | VARCHAR | NOT NULL | 제목 |
| content | TEXT | NOT NULL | 내용 |
| writer | VARCHAR | NOT NULL | 작성자 |
| view | Long | NOT NULL | 조회수 |
| created_date | VARCHAR | NOT NULL | 생성일 |
| modified_date | VARCHAR | NOT NULL | 수정일 |
| user_id | Long | Foreign key/NOT NULL | 계정 고유값 |
4. Comment

| Coulmn | Type | Condition | About |
| --- | --- | --- | --- |
| id | Long | Primary key | 댓글 고유값 |
| comment | text | NOT NULL | 댓글 |
| writer | VARCHAR | NOT NULL | 댓글 작성자 |
| created_date | VARCHAR | NOT NULL | 작성일 |
| modified_date | VARCHAR | NOT NULL | 수정일 |
| post_id | Long | Foreign key/NOT NULL | 게시글 고유번호 |
| user_id | Long | Foreign key/NOT NULL | 계정 고유값 |

<aside>
💡 API 설계

</aside>

1. User 

| 기능 | Method Type | URL | Return |
| --- | --- | --- | --- |
| SignUp 페이지 이동 | GET | /signup | 회원가입 페이지 |
| 회원가입 | POST | /signup | 인덱스 페이지 |
| 로그인 페이지 이동 | GET | /login | 로그인 페이지 |
| 로그인 | POST | /login | 인덱스 페이지 |
| 로그아웃 | GET | /logout | 인덱스 페이지 |
| 마이페이지 이동 | GET | /userpage/{userid} | 유저 마이페이지 |
| 회원탈퇴 | DELETE | /signout | 인덱스 페이지 |
2. Admin

| 기능 | Method Type | URL | Return |
| --- | --- | --- | --- |
| 관리자 페이지 이동 | GET | /admin | 관리자 로그인 페이지 |
| 관리자 로그인 | POST | /admin | 관리자 페이지 |
| 회원목록 조회 | GET | /view/userlist | 회원목록 조회 페이지 |
| 게시글 조회 | GET | /view/postlist | 게시글 목록 조회 페이지 |
| 회원 추방 | DELETE | /admin/user/ban/{userid} | 회원목록 조회 페이지 |
| 게시글 삭제 | DELETE | /admin/post/delete/{postid} | 게시글 목록 조회 페이지 |
3. Post

| 기능 | Method Type | URL | Return |
| --- | --- | --- | --- |
| 게시글 전체 목록 | GET | /post | 게시글 전체 목록 |
| 게시글 페이징 | GET | /?page={number} | 게시글 페이징 목록 |
| 게시글 상세보기 | GET | /post/detail/{post_id} | 게시글 상세보기 |
| 게시글 등록 페이지 | GET | /post/write | 게시글 등록 페이지 |
| 게시글 수정 페이지 | GET | /post/modify/{post_id} | 게시글 수정 페이지 |
| 게시글 검색 | GET | /post/search?keyword={keyword} | 게시글 검색 |
| 게시글 검색 후 페이징 | GET | /post/search?keyword={keyword}&page={number} | 게시글 검색 후 페이징 |
| 게시글 등록 | POST | /api/post | 게시글 전체 목록 |
| 게시글 조회 | GET | /api/post/{post_id} | 게시글 조회 |
| 게시글 수정 | PUT | /api/post/{post_id} | 게시글 수정 |
| 게시글 삭제 | DELETE | /api/post/{post_id} | 게시글 삭제 |
4. Comment

| 기능 | Method Type | URL | Return |
| --- | --- | --- | --- |
| 댓글 조회 | GET | /post/{id}/comment | 현재 게시글 |
| 댓글 등록 | POST | /post/{id}/comment | 현재 게시글 |
| 댓글 수정 | PUT | /post/{id}/comment/{user_id} | 현재 게시글 |
| 댓글 삭제 | DELETE | /post/{id}/comment/{user_id} | 현재 게시글 |

<aside>
💡 AOP

</aside>

1. User
- 로그인을 하지 않은 상태에서는 게시글 작성
- 마찬가지 상태에서 댓글 작성 불가
- 자신이 작성한 댓글/게시글에 대해 수정 및 삭제 불가

2. Admin
- 관리자 로그인을 하지 않은 상태에서는 관리자 기능 이용 불가
- 관리자 로그인을 하지 않은 상태에서 url을 직접 입력하여 이동하려고 시도할 시 리턴은 null

