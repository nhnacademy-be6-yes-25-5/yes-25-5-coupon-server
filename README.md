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

<img width="890" alt="image" src="https://github.com/user-attachments/assets/b7dc6985-a737-4eea-8d87-e97eba1ece4e">

> 쿠폰 관리 

<br/>

<img width="1363" alt="image" src="https://github.com/user-attachments/assets/c960b700-b5f3-4d40-859f-e0a73d5cf53d">

> 쿠폰 적용


<br/>
<br/>

## ✅ 주요 기능

### 원하는 도서를 선택하여, 주문하는 서비스 제공
- 주문 시, Redis를 통해 가주문 저장하여 분산처리 구현
- 도서별 쿠폰 적용 가능

### 주문 금액에 맞춰 배송비 결정
- 주문 금액에 따라 적용되는 배송비 정책을 결정
- 무료 배송비까지 남은 금액을 시각적으로 표시

### 메세지 기반의 결제 서비스 제공
- TossPayments를 통한 결제 서비스 제공
- 결제 처리에 대한 순서를 보장하여, 데이터 일관성을 유지하고 트랜잭션 무결성을 확보함
- 전략 패턴을 활용한 유연하고 유지보수성이 좋은 아키텍처 구현

### 배송시작 전이라면 언제든 결제 취소 가능
- 배송시작 전, 언제든 주문서 페이지에서 결제 취소 가능
- 배송이 완료됐다면 반품이 이루어진 후, 환불 가능
- 환불은 관리자의 승인이 필요


## 🔫 트러블 슈팅

> 추가예정 !!!!


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




