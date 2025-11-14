## SilverNavi Backend

노인 대상 요양시설/프로그램 탐색과 신청, 상담, 리뷰, 즐겨찾기 등을 제공하는 SilverNavi의 백엔드 서비스입니다. Spring Boot 기반 모놀리식 애플리케이션으로, JWT 인증/인가, S3 파일 업로드, Swagger 기반 API 문서화를 지원합니다.


### 주요 기능
- **회원/관리자 인증**: 사용자 회원가입·로그인, 시설 관리자 가입·로그인, JWT 발급/검증
- **요양시설/프로그램 관리**: 시설/프로그램 등록·조회·수정·삭제, 검색/목록/상세 조회
- **프로그램 신청/상담**: 사용자 신청 내역, 관리자 승인/관리, 상담 문의/답변
- **리뷰/북마크**: 리뷰 조회/삭제/답글(관리자), 시설/프로그램 즐겨찾기
- **파일 업로드**: AWS S3 연동
- **문서화**: Swagger UI에서 API 확인 (`/docs`)


### 기술 스택
- **Language/Runtime**: Java 21
- **Framework**: Spring Boot 3.5.7, Spring Web, Spring Data JPA, Spring Security, Validation
- **DB**: MySQL
- **Auth**: JWT (jjwt 0.12.3)
- **Storage**: AWS S3 (spring-cloud-aws, aws-java-sdk-s3)
- **Docs**: springdoc-openapi (Swagger UI)
- **Etc.**: Lombok, JSON, Scheduling, JPA Auditing


### 패키지/모듈 구조 개요
```
src/main/java/org/likelion/_thon/silver_navi
├─ domain
│  ├─ user, manager, nursingfacility, program, consult, review, bookmark, caretarget
│  └─ 각 도메인별 entity/repository/service/web(controller/dto)/exception
├─ global
│  ├─ auth (jwt, security, exception, UserRole)
│  ├─ config (Security, Swagger, S3, Web, App, Encrypt)
│  ├─ response (Base/Success/Error, code)
│  ├─ util (geo, s3), init, constant, exception, entity(BaseEntity)
│  └─ s3 (S3Service, S3TestController)
└─ SilverNaviApplication (JPA Auditing, Scheduling 활성화)
```


### 보안/인가 요약
- **JWT 인증 필터**: `Authorization: Bearer <token>` 헤더 사용
- **권한 공개 엔드포인트**: 
  - `/docs`, `/swagger-ui/**`, `/v3/api-docs/**`
  - 사용자: `/api/users/signup`, `/api/users/signin`
  - 관리자: `/api/managers/check-id`, `/api/managers/signup`, `/api/managers/signin`
  - 제휴코드: `/api/code/create`
- 그 외 대부분의 엔드포인트는 `USER` 또는 `ADMIN` 롤 필요
  - 예) 프로그램 목록/상세 일부는 `USER`/`ADMIN` 접근, 시설/프로그램 관리/상담/리뷰 관리 등은 `ADMIN` 전용


### 데이터베이스
- MySQL 사용
- 기본 설정: `ddl-auto=update` (개발 편의용, 운영에서는 마이그레이션 도구 권장)


### 도메인 개요
- **user**: 사용자, 회원가입/로그인, 역할 부여(`USER`)
- **manager**: 시설 관리자, 가입/로그인, 시설 기준 권한(`ADMIN`)
- **nursingfacility**: 요양시설 엔티티/조회/관리
- **program**: 프로그램 엔티티/신청/검색/관리
- **consult**: 상담 문의/답변/관리
- **review**: 리뷰 조회/삭제/답글(관리자)
- **bookmark**: 시설/프로그램 즐겨찾기
- **caretarget**: 보호 대상자 정보


### 라이선스
내부 프로젝트로 별도 라이선스 명시가 없다면 사내 정책을 따릅니다.