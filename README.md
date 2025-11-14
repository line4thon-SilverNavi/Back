## 🦋 SilverNavi Backend

노인 대상 요양시설/프로그램 탐색과 신청, 상담, 리뷰, 즐겨찾기 등을 제공하는 SilverNavi의 백엔드 서비스입니다. Spring Boot 기반 모놀리식 애플리케이션으로, JWT 인증/인가, S3 파일 업로드, Swagger 기반 API 문서화를 지원합니다.


## 🔹 주요 기능

### 1. 🔐 회원 / 관리자 인증
- 사용자 회원가입 · 로그인  
- 시설 관리자 가입 · 로그인  
- JWT 기반 인증 (발급 / 검증)

---

### 2. 🏥 요양시설 / 프로그램 관리
- 시설 · 프로그램 등록 / 조회 / 수정 / 삭제  
- 시설 검색, 목록 조회, 상세 조회  
- 프로그램 일정/정보 관리

---

### 3. 📝 프로그램 신청 & 상담 기능
- 사용자 프로그램 신청 내역 조회  
- 관리자 승인 / 거절 / 관리  
- 상담 문의 및 관리자 답변 처리

---

### 4. ⭐ 리뷰 & 북마크
- 리뷰 조회, 삭제, 관리자 답글  
- 시설 / 프로그램 즐겨찾기(북마크)

---

### 5. 📁 파일 업로드 (S3)
- 이미지 · 문서 파일 업로드  
- AWS S3 연동 (spring-cloud-aws)

---

### 6. 📘 API 문서화
- Swagger UI 기반 API 확인  
- `/docs` 엔드포인트 제공


## 🚀 기술 스택

- **Language/Runtime**: Java 21  
  <img src="https://img.shields.io/badge/Java%2021-007396?style=flat-square&logo=openjdk&logoColor=white" />

- **Framework**: Spring Boot 3.5.7, Spring Web, Spring Data JPA, Spring Security, Validation  
  <img src="https://img.shields.io/badge/Spring%20Boot%203.5.7-6DB33F?style=flat-square&logo=springboot&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring%20Web-6DB33F?style=flat-square&logo=spring&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring%20Data%20JPA-59666C?style=flat-square&logo=hibernate&logoColor=white" />
  <img src="https://img.shields.io/badge/Spring%20Security-6DB33F?style=flat-square&logo=springsecurity&logoColor=white" />
  <img src="https://img.shields.io/badge/Validation-0064FF?style=flat-square&logo=json&logoColor=white" />

- **DB**: MySQL  
  <img src="https://img.shields.io/badge/MySQL-4479A1?style=flat-square&logo=mysql&logoColor=white" />

- **Auth**: JWT (jjwt 0.12.3)  
  <img src="https://img.shields.io/badge/JWT%20(jjwt%200.12.3)-000000?style=flat-square&logo=jsonwebtokens&logoColor=white" />

- **Storage**: AWS S3 (spring-cloud-aws, aws-java-sdk-s3)  
  <img src="https://img.shields.io/badge/AWS%20S3-569A31?style=flat-square&logo=amazons3&logoColor=white" />
  <img src="https://img.shields.io/badge/spring--cloud--aws-FF9900?style=flat-square&logo=amazonaws&logoColor=white" />
  <img src="https://img.shields.io/badge/aws--java--sdk--s3-232F3E?style=flat-square&logo=amazonaws&logoColor=white" />

- **Docs**: springdoc-openapi (Swagger UI)  
  <img src="https://img.shields.io/badge/Swagger%20UI-85EA2D?style=flat-square&logo=swagger&logoColor=black" />

- **Etc.**: Lombok, JSON, Scheduling, JPA Auditing  
  <img src="https://img.shields.io/badge/Lombok-BC2323?style=flat-square&logo=lombok&logoColor=white" />
  <img src="https://img.shields.io/badge/JSON-000000?style=flat-square&logo=json&logoColor=white" />
  <img src="https://img.shields.io/badge/Scheduling-FFCC00?style=flat-square&logo=clockify&logoColor=black" />
  <img src="https://img.shields.io/badge/JPA%20Auditing-0FAAFF?style=flat-square&logo=clockify&logoColor=white" />


### 📁 패키지/모듈 구조 개요
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


### 🔐 보안/인가 요약
- **JWT 인증 필터**: `Authorization: Bearer <token>` 헤더 사용
- **권한 공개 엔드포인트**: 
  - `/docs`, `/swagger-ui/**`, `/v3/api-docs/**`
  - 사용자: `/api/users/signup`, `/api/users/signin`
  - 관리자: `/api/managers/check-id`, `/api/managers/signup`, `/api/managers/signin`
  - 제휴코드: `/api/code/create`
- 그 외 대부분의 엔드포인트는 `USER` 또는 `ADMIN` 롤 필요
  - 예) 프로그램 목록/상세 일부는 `USER`/`ADMIN` 접근, 시설/프로그램 관리/상담/리뷰 관리 등은 `ADMIN` 전용


### 📊 데이터베이스
- MySQL 사용
- 기본 설정: `ddl-auto=update` (개발 편의용, 운영에서는 마이그레이션 도구 권장)


### 📌 도메인 개요
- **user**: 사용자, 회원가입/로그인, 역할 부여(`USER`)
- **manager**: 시설 관리자, 가입/로그인, 시설 기준 권한(`ADMIN`)
- **nursingfacility**: 요양시설 엔티티/조회/관리
- **program**: 프로그램 엔티티/신청/검색/관리
- **consult**: 상담 문의/답변/관리
- **review**: 리뷰 조회/삭제/답글(관리자)
- **bookmark**: 시설/프로그램 즐겨찾기
- **caretarget**: 보호 대상자 정보
