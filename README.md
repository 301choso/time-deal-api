# 넘블 타임딜 서버 구축하기 


## 개요
정해진 시간에만 구매할 수 있는 타임딜 서버를 구현하였습니다.
## 작업기간  
약 3주 (23.02.24 ~ 03.16)

## 사용스택
- Java 11
- Spring Boot 2.7.9
- JPA
- MySql
- Redis
- Docker
- Github Action
- NCloud

## 시스템 구조도
![image](https://user-images.githubusercontent.com/78252188/225650636-d789411b-7c3a-4291-ab56-2b67cb1db857.png)


## 와이어프레임
![image](https://user-images.githubusercontent.com/78252188/225586938-ea671feb-46b3-48e5-97c4-156ad4141198.png)


## ERD
- member & session (일대다)
- orders & prduct / orders & member (다대일)
- 엔티티는 단방향으로 작성
- 주문하나에 상품종류는 하나만 있도록 작성

<p align="center">
  <img src="https://user-images.githubusercontent.com/78252188/225586956-108c47b3-6cb1-4082-99d3-30b423e0592c.png" width ="70%" >
</p> 


## API 명세서 (Swagger)
<p>
  <img src="https://user-images.githubusercontent.com/78252188/225615550-027f74d2-c807-4378-97bd-3ee49ef35159.png" height="300" width ="50%" align="left">
  <img src="https://user-images.githubusercontent.com/78252188/225624381-05781925-b315-467a-b20e-8b05ea4d0c39.png" height="300" width ="50%" align="right">
</p>


## 고민한 부분들
- 재고 감소시 동시성에 대한 고민 : 분산 락을 제공하는 Redisson을 사용하여 락을 획득 및 제거하여 순차적 처리되도록 시도해 보았습니다.
- [전역적 예외처리 : @ControllerAdvice와 상수를 사용하여 처리하였습니다.](https://sohyun1489.tistory.com/46)
- [더 나은 entity 구성하기 위한 노력](https://sohyun1489.tistory.com/48)
  - 데이터 정합성을 지키기 위해서 entity를 보수적으로 설계하였습니다. 
  - (@Setter대신 생성자에 빌더패턴 사용,기본생성자 선언은  AccessLevel.PROTECTED로 하기 등)
