

# **💰 Account Management - 계좌 관리 시스템**

- 사용자 계좌 생성, 잔액 관리, 거래 기록 등을 관리하는 서비스입니다. **계좌 생성, 해지, 조회, 잔액 사용 및 취소, 거래 조회** 등의 기능을 제공하며, 동시성 문제를 해결하여 안정적으로 동작합니다.

<br>

## ⚙ 기술 스택

- **Language**: `Java 11`
- **Framework**: `Spring Boot 2.6.8`
- **Database**: `H2 Database (In-Memory)`
- **Cache**: `Embedded Redis`, `Redisson`
- **Build Tool**: `Gradle`
- **Library**: `Lombok`, `Spring Data JPA`, `Spring Validation`, `Spring Web`
- **Testing**: `Spring Boot Starter Test`
- **IDE**: `IntelliJ`

<br>



## 📑 목차
1. [프로젝트 주요 기능](#💡-프로젝트-주요-기능)
2. [API 명세](#💬-api-명세)
   - [계좌 생성](#계좌-생성)
   - [계좌 해지](#계좌-해지)
   - [계좌 확인](#계좌-확인)
   - [잔액 사용](#잔액-사용)
   - [잔액 사용 취소](#잔액-사용-취소)
   - [거래 확인](#거래-확인)



<br>



## 💡 프로젝트 주요 기능

1. **계좌 생성**: 사용자 ID와 초기 잔액을 입력받아 계좌를 생성합니다
2. **계좌 해지**: 잔액이 0인 계좌만 해지 가능하며, 상태는 `UNREGISTERED`로 변경됩니다
3. **계좌 확인**: 사용자 ID를 기반으로 현재 사용 중인 계좌(`IN_USE` 상태)만 조회합니다
4. **잔액 사용**: 계좌 잔액을 차감하고 거래를 기록합니다
5. **잔액 사용 취소**: 기존 거래를 취소하고 거래 금액을 계좌에 복구합니다
6. **거래 확인**: 특정 거래의 상세 정보를 확인할 수 있습니다    



<br>

## 💬 API 명세


## 계좌 생성

- **Endpoint**: `POST /account`
- **기능**: 사용자 ID와 초기 잔액을 입력받아 새로운 계좌를 생성합니다
  
![계좌생성_createAccount_api](https://github.com/user-attachments/assets/60be921c-7827-4911-867e-1bd0857b2c0e)

- **Request**
  
![계좌생성_request](https://github.com/user-attachments/assets/27d716b9-ba9d-4986-83e0-ee4ef4e02f34)

- **Response**

![계좌생성_response](https://github.com/user-attachments/assets/4f1ba910-518a-4b73-8726-8aa8f20cc524)


### 예외 처리

1. 사용자가 없는 경우

![계좌생성_오류1_사용자가없는경우](https://github.com/user-attachments/assets/d2387354-93e8-4e6f-a400-4448a440edd5)


2. 계좌가 10개인 경우

![계좌생성_오류2_계좌10넘음](https://github.com/user-attachments/assets/9b1a2113-8445-4447-b65f-f9ed270da991)



## 계좌 해지

- **Endpoint**: `DELETE /account`
- **기능**: 잔액이 0인 계좌를 해지하며, 상태를 `UNREGISTERED`로 변경합니다

![계좌해지_deleteAccount_api](https://github.com/user-attachments/assets/1aa9712f-7f1c-4016-9f18-2e4362f8b2f9)


- **Request**

![계좌해지_request](https://github.com/user-attachments/assets/4137bdad-8ed0-42a6-a13b-87bcda3c0a18)


- **Response**

![계좌해지_response](https://github.com/user-attachments/assets/b13b1f31-ce03-4525-a843-d02e8db6895d)


### 예외 처리

1. 사용자가 없는 경우
  
![계좌해지_오류1_사용자가없는경우](https://github.com/user-attachments/assets/87c698d8-2d11-4768-a3c4-4443aeaad551)


2. 계좌가 없는 경우

![계좌해지_오류1_계좌없는경우](https://github.com/user-attachments/assets/028a524a-24c8-471f-9b4c-3a587f5b786a)

3. 사용자 아이디와 계좌 소유주가 다른 경우

![계좌해지_오류2_사용자아이디랑계좌소유주가다른경우](https://github.com/user-attachments/assets/462a51ca-200c-4207-bbbb-41184e3b2c50)


4. 계좌가 이미 해지상태인 경우

![계좌해지_오류3_계좌가이미해지상태인경우](https://github.com/user-attachments/assets/1e11ea5b-6f85-4eca-9a92-dd2e7f539f3d)


5. 계좌 잔액이 있는 경우

![계좌해지_오류4_계좌잔액이있는경우](https://github.com/user-attachments/assets/79e78dd8-b472-434a-a829-33cb43cab76c)


## 계좌 확인

- **Endpoint**: `GET /account?user_id={userId}`
- **기능**: 사용자 ID를 기반으로 현재 사용 중인 계좌 정보를 조회합니다

- **Request**

![계좌확인_getAccount_api_request](https://github.com/user-attachments/assets/1599999f-0ce1-4c01-aa85-2f6931be303d)


- **Response**

![계좌확인_response_사용중인계좌만보여줌](https://github.com/user-attachments/assets/609b901c-4bf7-46fa-83f6-f268d0a35f3b)




### 예외 처리

1. 사용자가 없는 경우

![계좌확인_오류1_사용자없는경우](https://github.com/user-attachments/assets/a43011a4-34e7-4f04-88b8-253988f6cf9f)




## 잔액 사용

- **Endpoint**: `POST /transaction/use`
- **기능**: 계좌의 잔액을 차감하고 거래를 기록합니다


![잔액사용_useBalance_api](https://github.com/user-attachments/assets/69237d06-f82f-4c7f-afb4-9305a065e9fc)


- **Request**

![잔액사용_request](https://github.com/user-attachments/assets/942b3706-aa12-498e-acb3-88daf01d6dbc)


- **Response**

![잔액사용_response](https://github.com/user-attachments/assets/f855d152-852a-424f-9f1f-bfdf09eca796)



### 예외 처리
1. 사용자가 없는 경우

![잔액사용_오류1_사용자없는경우](https://github.com/user-attachments/assets/fbf0abe7-04c5-4467-bf6c-7e0265dc1603)


2. 거래 금액이 잔액보다 큰 경우

![잔액사용_오류4_거래금액이잔액보다큰경우](https://github.com/user-attachments/assets/48498249-92a6-476c-9399-d9176729dab6)



## 잔액 사용 취소

- **Endpoint**: `POST /transaction/cancel`
- **기능**: 기존 거래를 취소하고 거래 금액을 계좌에 복구합니다


![잔액사용취소_cancelBalnce_api](https://github.com/user-attachments/assets/c3d4ff12-0282-446d-b0eb-a1ac235377ae)


- **Request**

![잔액사용취소_request](https://github.com/user-attachments/assets/9de460af-5ab6-4873-88a8-c14858e8e955)


- **Response**

![잔액사용취소_response](https://github.com/user-attachments/assets/850fd0a1-4386-4886-8722-d68459776f90)



### 예외 처리
1. 거래 금액과 거래 취소 금액 불일치(부분 취소 불가능)

![잔액사용취소_오류1_거래금액과취소금액이다른경우부분취소안됨](https://github.com/user-attachments/assets/2be3c91b-2824-4182-bf8b-eb74bbe1e608)


2. 거래와 계좌가 불일치

![잔액사용취소_오류2_트랜잭션이해당계좌의거래가아닌경우](https://github.com/user-attachments/assets/5614e380-f348-4b21-9d2d-2c30e51363d5)


## 거래 확인

- **Endpoint**: `GET /transaction/{transactionId}`
- **기능**: 특정 거래의 상세 정보를 조회합니다

- **Request (잔액 사용 확인)**

![거래확인_잔액사용확인_api](https://github.com/user-attachments/assets/6b585c11-eba8-4676-9859-2f8e690da45e)

- **Response (잔액 사용 확인)**

![거래확인_잔액사용확인_response](https://github.com/user-attachments/assets/38e30bd5-2a6f-4216-84a2-28673878925d)



- **Request (잔액 사용 취소 확인)**

![거래확인_잔액사용취소확인_api](https://github.com/user-attachments/assets/ec05a840-da76-403d-b5bb-bd0e48e7d30b)



- **Response (잔액 사용 취소 확인)**

![거래확인_잔액사용취소확인_response](https://github.com/user-attachments/assets/5173a21d-34e8-4e50-918e-09ea063600c2)




### 예외 처리
1. 해당 거래 아이디의 거래가 없는 경우

![거래확인_오류1_해당transactionid없는경우](https://github.com/user-attachments/assets/db247011-3adb-48b2-8201-2ed74d481d71)








  
