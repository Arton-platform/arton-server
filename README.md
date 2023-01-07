# arton-server
server

# 헥사고널 규칙
1. Service interface(UseCase)

- 웹으로부터 입력을 받는다.
- 비즈니스 규칙을 검증한다.
- 모델 상태를 조작한다.
- 출력을 반환한다.

2. Adapter(Web Controller)

- HTTP 자바로 매핑하기.
- 권한 검사
- 입력 유효성 검증
- 입력을 Service 입력 모델로 매핑
- Service 호출
- Service 출력을 HTTP로 매핑
- HTTP Response 반환

3. Adapter(Data Port)

- Get a Reqeust
- Mapping request to DB format
- Send request to DB
- return DTO, or Entity
- return value
