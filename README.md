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

### 예시
- 예시 (추가예정)


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




