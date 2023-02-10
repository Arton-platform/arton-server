
# arton-server
server

# 20230107

## 헥사고널 규칙
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



# 20230110
## 공연에 아티스트는 여러명이 출연 가능하다.
한 공연에 여러 아티스트가 출연할 수 있다. 뮤지컬을 예를 들면 실제로 여러 출연자가 존재함.
그런데 아티스트 또한 여러 공연에 출연할 수 있다. 그러면 다대다 관계가 되는데...
JPA에서 ManyToMany는 중간에 숨겨진 테이블로 인해 예상치 못한 쿼리가 나가는등 문제가 발생한다.
따라서 실무에서는 절대 사용하면 안된다. 그럼 어떻게 해결할까 하다가 
수면에 숨겨져 만들어지는 중간 테이블을 수면위로 끌어올려 해결하면 된다고 함.
Artist <---> Performer OnyToMany ManyToOne
Performance <---> Performer OnyToMany ManyToOne
이러면 Performer(출연자) 엔터티가 수면위에 떠오르기 때문에 숨겨진 버그를 예방할 수 있다.

## 20230114
JPA Entity와 domain 분리 

## 20230117
ManyToOne에 !contains 설정 꼭 해주자. 무한루프에 걸릴수있음.
Entity 간 관계를 매핑할때는 entity, entity가 가능하다. 하지만 도메인 간 관계를 설정할때는 되도록 
entity의 id를 쓰고 도메인은 사용을 자제하자. 무한루프에 걸릴 수 있다. 실제로 무한 루프 걸림...

## 
공연 리스트를 회원가입 찜 페이지에 보여주기. 하나 고르고 비슷한 애들을 보여주려면.. 공연의 종류라던지 이런것들이 있어야 추천이 가능할 것 같음.

## 20230126

### 티켓 오픈 날자 알림 기능 구현

* 배치 프로그램으로 하루에 한번 브로드캐스팅하는 방식(SSE 사용)

접속중인 대상만으로 실시간 알림 기능을 제공하고 그외의 경우에는 특정 테이블을 사용

알림 테이블이 있어야 실시간 알림, 누적알림 둘다 가능하며 유지보수성 향상
하루 한번 중계 테이블에 데이터를 삽입하고 브로드 캐스팅 하는 식으로 개발
```
/performance-sse/subscribe-performance-info
    -> 유저가 어플리케이션을 켤때 전체 찜 피드를 구독
/performance-sse/broadcast-performance
    -> 1일 1회 12시에 구독한 유저에게 갱신된 정보를 전달 
    전체 찜목록에서 필터링...(서버)
    cpu 사용량 급격한 증가로 예상됨 -> 서버 부하
    프론트에서 전체 목록중에 찜한것만 필터링 (프론트)
```