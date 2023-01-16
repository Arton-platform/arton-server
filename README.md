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


## 
공연 리스트를 회원가입 찜 페이지에 보여주기. 하나 고르고 비슷한 애들을 보여주려면.. 공연의 종류라던지 이런것들이 있어야 추천이 가능할 것 같음.

--------------------------------------------------
객체 구성
```
entity
├── board
│   ├── Announcement.java // 공지사항
│   ├── Board.java        // 게시판 공통    
│   ├── Comment.java      // 댓글
│   ├── FAQ.java          // FAQ
│   └── Review.java       // 리뷰
├── image
│   └── Image.java        // 이미지
└── performance
    └── Performance.java  // 공연/콘서트 공통

``` 

- 요청 접근 순서

``` 
    adapter - in - web => Controller
    
    application - port - in => UseCase
    
    application - service => Service
    
    application - port - out => Port
    
    adapter - out - persistence - PersistenceAdapter
    domain
    
    // 어댑터 -> 어플리케이션 -> 어뎁터
``` 

- 작동 방식

```
도메인은 순수한 자바코드로 구성되어야함.
도메인은 도메인끼리
엔터티는 엔터티끼리

서비스, 데이터베이스 포트 작동 도메인을 받고 리턴한다.
내부에서 엔터티 호출하여 처리함.
```