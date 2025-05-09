# 스프링부트 블로그 만들기 v1
- 익명 블로그 NativeQuery (게시글 CRUD)

# 스프링부트 블로그 만들기 v2

> Get, Post 만 사용!! (x-www-form)
> ajax만 json으로
> ORM

## 1. 유저 (user_tb)

- 회원가입
- 로그인

## 2. 게시글 (board_tb)

- 게시글 쓰기
- 게시글 목록
- 게시글 상세보기
- 삭제
- 수정

## 3. 게시글 고급 기능 (board_tb)

- 게시글 목록 페이징
- 검색하기

## 4. 러브 (love_tb)

- 게시글 좋아요 (ajax)
- 게시글 좋아요 취소 (ajax)

## 5. 댓글

- 댓글 쓰기
- 댓글 삭제
- 댓글 목록보기

## 6. 그 외 기능

- 필터 (Script 보안)
- 유효성검사 자동화 (Valid AOP)
- 인증체크 자동화 (Interceptor)
- 예외처리 (Global Exception Handler)
- 사진 업로드 (MultiPart Form Data)

# 스프링부트 블로그 만들기 v3

- DTO 전환 (Service 레이어 - Stream API, Optional)
- RestAPI 주소 설계 (Post, Get, Put, Delete) -> 뷰를 삭제
- JWT 인증 체계 변경 (jSessionId 사용안함)
```text
1. UTF-8
2. JSON
3. Base64
4. 대칭키, 공개키 (해시, 전자서명)
5. 액세스 토큰
6. 리프레시 토큰
7. 토큰 보안 및 탈취 
8. 레디스 (메모리 세션) - 개념만 설명 (미니프로젝트2에 적용해보기)
9. 서버 확장성 (AWS 배우고)
10. OAuth2.0과 OIDC (AWS 배우고)
```
- CORS (Cross Origin Resource Sharing) -> 모든 서버는 JS요청을 거부한다.
- 통합 테스트 (DS-C-S-R-PC-DB)
- API 문서 만들기

## 기술스택

- Springboot 3.3
- JDK 21
- 인텔리J
- H2

## 의존성

- Lombok
- DevTools
- Spring WEB
- JPA
- h2
- MySQL
- Mustache
