## Backoffice

<br>

* 개발기간
    - 2024-10-01 ~
* 목적
    - 전반적 상품 관리 및 재고파악이 가능한 백오피스입니다.
* 기능
    - 상품 관리
    - 상품 재고 관리
* 향후 추가 기능
    - 회원 관리
    - 상품 주문

### 🔧개발환경

* Front-End :  Thymeleaf
* Back-End : kotlin, SpringBoot, JPA
* Database : MYSQL
* Server : ApacheTomcat
* Tool : IntelliJ, Git

### ⚙제약사항

* 상품 분류 및 브랜드의 데이터 삭제시 물리적 삭제가 아니라 사용여부의 데이터가 "N"으로 저장됩니다.

### 테이블 ERD

* 분류와 브랜드 테이블의 데이터를 먼저 입력한 후 상품을 등록할 수 있습니다.<br> 다만, 분류와 브랜드는 필수 입력 항목이 아니므로, 입력하지 않아도 상품을 우선적으로 등록할 수 있습니다.
* 상품 ID를 외래키로 참조하여 해당 상품의 입고 수량, 입고 가격 및 입고 날짜를 저장할 수 있으며, 이를 통해 입고 후 재고 수량을 파악할 수 있습니다.<br>
  ![image](https://github.com/user-attachments/assets/560c40bf-0b6c-44a3-b2aa-1bccc0eea961)

### REST API 설계

#### **상품 (Product) API**

| 기능       | URL                          | Method   |
|----------|------------------------------|----------|
| 상품 등록    | `/api/products`              | `POST`   |
| 상품 목록 조회 | `/api/products`              | `GET`    |
| 상품 상세 조회 | `/api/products/{product_id}` | `GET`    |
| 상품 수정    | `/api/products/{product_id}` | `PUT`    |
| 상품 삭제    | `/api/products/{product_id}` | `DELETE` |

---

#### **분류 (Category) API**

* 분류 삭제 API 대신 `useYn` 필드를 변경하는 분류 비활성화 API 사용

| 기능       | URL                                     | Method  |
|----------|-----------------------------------------|---------|
| 분류 등록    | `/api/categories`                       | `POST`  |
| 분류 목록 조회 | `/api/categories`                       | `GET`   |
| 분류 상세 조회 | `/api/categories/{category_id}`         | `GET`   |
| 분류 수정    | `/api/categories/{category_id}`         | `PUT`   |
| 분류 비활성화  | `/api/categories/{category_id}/disable` | `PATCH` |

---

#### **브랜드 (Brand) API**

* 브랜드 삭제 API 대신 `useYn` 필드를 변경하는 브랜드 비활성화 API 사용

| 기능        | URL                              | Method  |
|-----------|----------------------------------|---------|
| 브랜드 등록    | `/api/brands`                    | `POST`  |
| 브랜드 목록 조회 | `/api/brands`                    | `GET`   |
| 브랜드 상세 조회 | `/api/brands/{brand_id}`         | `GET`   |
| 브랜드 수정    | `/api/brands/{brand_id}`         | `PUT`   |
| 브랜드 비활성화  | `/api/brands/{brand_id}/disable` | `PATCH` |

---

#### **재고 (Stock) API**

* 재고는 삭제 기능을 제공하지 않으며, 재고 수정 시에도 기존 데이터를 수정하는 대신 새로운 데이터를 생성하여 수정 이력을 로그처럼 남길 수 있도록 함

| 기능         | URL                      | Method |
|------------|--------------------------|--------|
| 재고 등록 및 수정 | `/api/stocks`            | `POST` |
| 재고 목록 조회   | `/api/stocks`            | `GET`  |
| 재고 상세 조회   | `/api/stocks/{stock_id}` | `GET`  |

### [미션4] 조회 REST API 만들기

이번 미션에서는 제품, 브랜드, 카테고리, 재고에 대한 조회 API를 개발하고, 각 경로(/api/products, /api/brands, /api/categories, /api/stocks)에 대해 테스트 코드를 작성하였다.<br>

이번 미션에서는 조회 API 개발 및 테스트 코드 작성을 통해 RESTful API의 기본 구조를 익히고, Null 처리와 영속성 관리를 경험하였다.<br>
특히 **?.let {}**과 ?: 엘비스 연산자를 활용해 null-safe한 코드를 작성하는 방법을 익혔으며, 서비스 계층 분리를 통해 더 효율적인 설계를 계획하게 되었다.

#### ProductDTO에서 발생한 Null 처리 이슈와 해결

조회 API를 구현하는 과정에서 ProductDTO 내의 brand와 category가 null일 경우 NullPointerException이 발생하는 문제가 발견되었다.<br>
이를 해결하기 위해 ?.let {} 구문을 사용해 null-safe하게 처리하였다.

1. ?.let {}의 역할 <br>
    * ?.let {}: 객체가 null이 아닐 때만 코드 블록 내부를 실행한다.
    * brand = product.brand?.let { BrandDTO(it) }
    * product.brand가 null이 아닐 경우에만 BrandDTO를 생성하고, 그렇지 않으면 실행되지 않음.
2. ?: 엘비스 연산자 사용
    * **엘비스 연산자 ?:**를 사용하여, 값이 null일 경우 대체 동작을 수행하도록 하였다.
    * ?: 연산자: 왼쪽의 값이 null이면 오른쪽 값을 반환한다.<br>
    product = stock.product ?: throw IllegalArgumentException("제품정보를 찾을 수 없습니다..")
    * 만약 stock.product가 null이면 IllegalArgumentException을 던지도록 처리.
3. 영속성 설정: Cascade = PERSIST
    * 재고와 관련된 엔티티를 영속성 컨텍스트에 포함시키기 위해, cascade = PERSIST 옵션을 사용하였다. <br>
    이 설정은 엔티티가 함께 영속화될 수 있도록 보장하며, 이를 통해 테스트 코드가 문제없이 성공적으로 실행되었다.
4. 재고 수량 설정과 서비스 계층 분리 계획
   * 현재 재고 테이블의 재고 수량(stockCount) 설정은 Stock 엔티티의 메서드를 통해 처리하고 있다.<br>
    하지만, 이 로직을 추후 삽입 및 수정 API에서 더 깔끔하게 관리하기 위해 서비스 계층으로 분리할 계획이다.

### [미션 5] 삽입, 수정, 삭제 REST API 만들기

강의 때는 `service` 테스트를 진행하였는데, 이번 미션에서는 **API 테스트 코드**를 작성하여 진행하였다.

1. **재고 등록 시 로직 변경**  
   - 기존에 재고 수량 로직이 엔티티에 있었으나, 서비스 단으로 이동시켰음.
   - 뷰 페이지를 개발하여 테스트를 한번 진행해봐야 할 것 같음.

2. **코틀린 사용 경험**  
   - 코틀린은 자바 기반이지만 처음 접하는 언어라 예상보다 시간이 많이 소요되었음.
   - **GPT**를 활용하여 테스트 코드를 작성할 수 있었음.

3. **프레젠테이션 구조 변경**  
   - 조회 REST API 개발 시, 기존에 있었던 **프레젠테이션 구조**를 세분화하고 제거하여 각각의 기능을 다시 정리함.

4. **아쉬운 점**  
   - 마감기한이 얼마 남지 않아 코드를 덜 정리한 채로 제출함.
   - 작성한 코드를 제대로 확인하지 못한 점이 아쉬움.
   - 뷰 페이지 개발과 프로젝트 전체를 훑어보며 정리를 다시 해야 할 필요성이 있음.

---

##### 1. `whenever`

`whenever`는 **Kotlin**의 Mockito 확장 라이브러리인 `mockito-kotlin`에서 제공하는 함수.  
Mock 객체가 특정 메서드를 호출할 때 반환할 값을 설정할 수 있음.  
주로 서비스 계층 메서드를 Mocking하여 실제 데이터베이스나 외부 API 호출을 방지하고, 원하는 값을 반환할 때 사용.

---

##### 2. `andExpect`

`andExpect`는 **MockMvc**를 사용하여 HTTP 요청을 테스트할 때 응답을 검증하는 메서드.  
HTTP 응답의 상태 코드, 응답 본문, 헤더 등을 검증할 수 있음.

---

##### 3. `jsonPath`

`jsonPath`는 응답 본문이 **JSON 형식**일 때, 특정 필드 값을 검증하는 데 사용.  
**Jackson** 라이브러리와 함께 사용되며, JSON 응답에서 원하는 필드의 값을 간편하게 확인할 수 있음.  

---

##### 4. `$`, `.`, `stockCount` 설명

- **$**: JSON 데이터의 **최상위 루트 객체**를 가리킴. 모든 탐색은 이 루트 객체에서 시작됨.
- **.**: 객체 내부의 특정 필드를 참조할 때 사용.
