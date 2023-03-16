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
![image](https://user-images.githubusercontent.com/78252188/225604965-ca383703-e2cb-41d1-a097-7c1acb819c31.png)


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
  <img src="https://user-images.githubusercontent.com/78252188/225615550-027f74d2-c807-4378-97bd-3ee49ef35159.png" width ="50%" align="left">
  <img src="https://user-images.githubusercontent.com/78252188/225623792-9e1bacfe-a6b0-4c6d-842f-99bd18b2738a" width ="50%" align="right">
</p>
<br/>
<br/> 

## 성능측정 및 개선 내용


## 기술적으로 고민한 부분들

