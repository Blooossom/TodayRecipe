# TodayRecipe
<aside>
💡 원하는 결과

</aside>

1. 회원 가입 - 로그아웃 - 회원탈퇴 - 로그인 기능을 구현하고 싶음
- 2. 각각의 레시피 게시판을 생성
    1. 게시글 작성/수정/삭제/읽기 등 기초적인 기능 필요
    2. 페이징 처리(게시글 검색후 페이징 처리도 필요)
    3. 게시글 검색
    4. 게시글 전체 목록 조회(게시판 탭 이동 시)
    5. 유저/제목 등으로 검색 가능하게 
    - 6. 댓글
        1. 댓글작성/수정/삭제
        2. 비밀댓글도 가능하면 해보기
1. 요리 테마를 정했을 때 추천을 많이 받은 레시피를 우선적으로 보여주는 것도 괜찮을 듯(그러면 글에다가 추천 기능도 필요할 듯함)
- 4. 관리자 페이지
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
- HTML5
- CSS3
- JavaScript
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
1. Admin

| Column | Type | Condition | About |
| --- | --- | --- | --- |
| AdminId | VARCHAR | NOTNULL/PK | 관리자 아이디 |
| AdminPW | VARCHAR | NOTNULL | 관리자 비밀번호 |
1. Post

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
1. Comment

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
1. Admin

| 기능 | Method Type | URL | Return |
| --- | --- | --- | --- |
| 관리자 페이지 이동 | GET | /admin | 관리자 로그인 페이지 |
| 관리자 로그인 | POST | /admin | 관리자 페이지 |
| 회원목록 조회 | GET | /view/userlist | 회원목록 조회 페이지 |
| 게시글 조회 | GET | /view/postlist | 게시글 목록 조회 페이지 |
| 회원 추방 | DELETE | /admin/user/ban/{userid} | 회원목록 조회 페이지 |
| 게시글 삭제 | DELETE | /admin/post/delete/{postid} | 게시글 목록 조회 페이지 |
1. Post

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
1. Comment

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

1. Admin
- 관리자 로그인을 하지 않은 상태에서는 관리자 기능 이용 불가
- 관리자 로그인을 하지 않은 상태에서 url을 직접 입력하여 이동하려고 시도할 시 리턴은 null

<aside>
💡 2023. 01. 05(목)

</aside>

1. 각 빈 패키지 작성해둠(변경될 가능성 농후함)
2. 테이블 마다 패키지를 분리 → 나중에 혼선이 있을 수 있어서 이것도 변경할 수 있음
3. 이름만 있는 빈 html 작성
4. 페이지 컨트롤러 기초적인 것만 작성해둠
5. 미리 설계해둔 DB 테이블 구조에 따라 Entity 작성해둠
6. Admin/User 로그인, 회원가입, 로그아웃 같은 간단한 기능은 지금 구현해도 문제 없을 듯해서 작성해둠

<aside>
💡 2023. 01. 06(금)

</aside>

- 1. 회원가입 시 유효성 검사
    - 중복 ID, email, 닉네임 검사 후 없을 때는 IllegarArgument Exception
    - 정해진 형식에 맞지 않는 정보 입력 시 가입 불가 추가
    - Validation Annotation을 활용하여 NotBlank, Pattern 어노테이션 사용함
    - 중복 검사에는 JPA에서 지원하는 exist를 통해 DB에 있는 지 확인하는 방식 사용
- 2. 페이징 처리에 필요한 객체 추가
    - Pagination
    - SearchDTO 추가

<aside>
💡 2023. 01. 07(토)

</aside>

1. Swagger API TEST 추가
2. Swagger를 이용한 Annotation 으로 API 명시도 상승

<aside>
💡 2023. 01. 08(일)

</aside>

1. BootStrap을 이용하여 Index, Login, SignUp html작성
2. 각각의 함수는 jQuery로 작성함
3. signup, index, login 페이지 및 페이지 내 함수 - 컨트롤러 내의 API 연결

<aside>
💡 2023. 01. 09(월)

</aside>

1. DB 구성
2. view-controller 연결 시 문제 발생 → 입력값이 null로 들어옴  → 해결함
3. postlist 화면 출력(post가 실제로 들어갔을 때는 확인해봐야함)
4. addpost

<aside>
💡 2023. 01. 10(화)

</aside>

- 1. 진행과정 설계
    - 진행과정 재정의가 필요할 듯함
    1. AOP 로깅 적용 
    2. 게시글 CRUD
    3. 댓글 CRUD
    4. =================== 확장 ===================
    5. Spring Security로 User signup, login 재정의
    6. 소셜 로그인 적용
    7. 게시글에 사진 업로드 기능 추가
    8. 게시글 검색 기능 추가
    9. 게시글, 댓글 작성자만 삭제 가능 기능 추가
    10. 비밀 댓글 추가
    11. 추천 수 추가
    12. 추천 수 높은 순 정렬, 내림차순으로 추천하는 기능 추가
    13. 관리자 기능 추가
    14. AOP 미로그인 시 제한 사항 적용
