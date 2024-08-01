# Yes25.5 coupon-server

Yes25.5의 메세지 기반 쿠폰발급 및 쿠폰 관리를 담당하는 서버입니다.

<br/>

## 🛠️ Stacks

### Environment
<img src="https://img.shields.io/badge/git-F05032?style=for-the-badge&logo=git&logoColor=white"/> <img src="https://img.shields.io/badge/github-181717?style=for-the-badge&logo=github&logoColor=white"/>

### Development
<img src="https://img.shields.io/badge/java-ff7f00?style=for-the-badge&logo=java&logoColor=white"/> <img src="https://img.shields.io/badge/springboot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/> <img src="https://img.shields.io/badge/JPA-6DB33F?style=for-the-badge&logo=JPA&logoColor=white"> 
<img src="https://img.shields.io/badge/Spring Security-6DB33F?style=for-the-badge&logo=Spring Security&logoColor=white">
<img src="https://img.shields.io/badge/spring cloud-6DB33F?style=for-the-badge&logo=spring&logoColor=white"/>

### Cloud
<img src="https://img.shields.io/badge/nhn cloud-blue?style=for-the-badge&logo=nhncloud&logoColor=white"/>

### Database
<img src="https://img.shields.io/badge/mysql-4479A1?style=for-the-badge&logo=mysql&logoColor=white"/> <img src="https://img.shields.io/badge/redis-d1180b?style=for-the-badge&logo=redis&logoColor=white"/> 

### MessageQueue
<img src="https://img.shields.io/badge/RabbitMQ-ff7f00?style=for-the-badge&logo=rabbitmq&logoColor=white">


<br/>
<br/>

## 🖥️ 화면 구성

<img width="1352" alt="image" src="https://github.com/user-attachments/assets/18082743-11b2-4f19-be83-a735b23ecec7">

> 회원 쿠폰함

<br/>

<img width="1350" alt="image" src="https://github.com/user-attachments/assets/b7dc6985-a737-4eea-8d87-e97eba1ece4e">

> 쿠폰 관리 

<br/>

<img width="1363" alt="image" src="https://github.com/user-attachments/assets/c960b700-b5f3-4d40-859f-e0a73d5cf53d">

> 쿠폰 적용

<br/>
<br/>

## ✅ 주요 기능

### 쿠폰 관리
- 쿠폰 정책을 통한 일반, 도서, 카테고리 쿠폰 생성
- 쿠폰 사용기간 만료시 만료된 쿠폰으로 처리
- 마이페이지의 쿠폰함에서 사용가능, 사용한, 만료된 쿠폰 조회

### 특정 쿠폰
- 특정 도서에 대해 사용할 수 있는 도서 쿠폰
- 특정 카테고리에 대해 사용할 수 있는 카테고리 쿠폰

### 메세지 기반의 쿠폰 서비스 제공
- 회원가입시 웰컴쿠폰 발급
- 사용자의 생일인 달에 매월 1일에 생일 쿠폰 발급
- 쿠폰 발급 처리에 대한 순서를 보장하여, 데이터 일관성을 유지하고 트랜잭션 무결성을 확보함
- 전략 패턴을 활용한 유연하고 유지보수성이 좋은 아키텍처 구현

### 배송시작 전이라면 언제든 결제 취소 가능
- 배송시작 전, 언제든 주문서 페이지에서 결제 취소 가능
- 배송이 완료됐다면 반품이 이루어진 후, 환불 가능
- 환불은 관리자의 승인이 필요


## 🔫 트러블 슈팅

> 추가예정


<br/>
<br/>

## ⚙️ 패키지 구조

```
couponapi
├── service
│   ├── impl
│   │   ├── CouponCreationUtil.java
│   │   ├── CouponPolicyBookServiceImpl.java
│   │   ├── CouponPolicyCategoryServiceImpl.java
│   │   ├── CouponPolicyServiceImpl.java
│   │   └── CouponServiceImpl.java
│   ├── CouponPolicyBookService.java
│   ├── CouponPolicyCategoryService.java
│   ├── CouponPolicyService.java
│   └── CouponService.java
├── common
│   ├── advice
│   │   └── GlobalRestControllerAdvice.java
│   ├── config
│   │   ├── EurekaConfig.java
│   │   ├── SchedulerConfig.java
│   │   ├── SecurityConfig.java
│   │   └── SwaggerConfig.java
│   ├── exception
│   │   ├── payload
│   │   │   ├── ErrorStatus.java
│   │   ├── ApplicationException.java
│   │   ├── CouponCreationException.java
│   │   ├── CouponNotFoundException.java
│   │   ├── CouponPolicyBookServiceException.java
│   │   ├── CouponPolicyCategoryServiceException.java
│   │   ├── CouponPolicyServiceException.java
│   │   └── JwtException.java
├── jwt
│   ├── annotation
│   │   └── CurrentUser.java
│   ├── JwtFilter.java
│   ├── JwtProvider.java
│   └── JwtUserDetails.java
├── adapter
│   ├── AuthAdaptor.java
│   └── BookAdapter.java
├── logging
│   └── HttpAppender.java
├── persistence
│   ├── domain
│   │   ├── Coupon.java
│   │   ├── CouponPolicy.java
│   │   ├── CouponPolicyBook.java
│   │   └── CouponPolicyCategory.java
│   ├── repository
│   │   ├── CouponPolicyBookRepository.java
│   │   ├── CouponPolicyCategoryRepository.java
│   │   ├── CouponPolicyRepository.java
│   │   └── CouponRepository.java
├── presentation
│   ├── controller
│   │   ├── CouponController.java
│   │   ├── CouponPolicyBookController.java
│   │   ├── CouponPolicyCategoryController.java
│   │   └── CouponPolicyController.java
│   ├── dto
│   │   ├── request
│   │   │   ├── CouponPolicyBookRequestDTO.java
│   │   │   ├── CouponPolicyCategoryRequestDTO.java
│   │   │   ├── CouponPolicyRequestDTO.java
│   │   │   └── CouponRequestDTO.java
│   │   └── response
│   │       ├── BookDetailCouponResponseDTO.java
│   │       ├── CouponInfoResponse.java
│   │       ├── CouponPolicyBookResponseDTO.java
│   │       ├── CouponPolicyCategoryResponseDTO.java
│   │       ├── CouponPolicyResponseDTO.java
│   │       ├── CouponResponseDTO.java
│   │       └── ExpiredCouponUserResponse.java
│   │       └── JwtAuthResponse.java
├── validation
│   ├── EitherOr.java
│   └── EitherOrValidator.java
└── CouponApiApplication.java
```




