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

### 조회 REST API 만들기

#### /api/products, /api/brands, /api/categories, /api/stocks 각각의 조회 API 테스트 코드 작성

* 강의 코드를 참고하면서 구현하는 도중 ProductDTO에서 brand와 category 관련 null 처리 에러가 발생했다.
* 이 문제는 **?.let {}**을 사용해 null-safe하게 처리하여 해결했다.
* 또한, cascade = PERSIST를 사용해 영속성을 설정했고, 테스트 코드가 문제없이 성공적으로 실행되었다.

1. ?.let {}의 역할 <br>
    * **?.let {}**은 null이 아닐 때만 블록 내부 코드를 실행한다.
    * 예를 들어, stock.product가 null이 아닐 때만 다음 블록이 실행된다:
      product = stock.product?.let { ProductDTO(it) }
      <br><br>
2. ?: 엘비스 연산자 사용
    * **?:(엘비스 연산자)**는 왼쪽 값이 null이면 오른쪽 값을 반환한다.
    * 코드에서는 stock.product가 null이면 IllegalArgumentException을 던지도록 처리했다:
      <br><br>
3. 재고 수량 설정과 서비스 계층 분리 계획
    * 현재 재고 테이블의 재고 수량(stockCount) 설정을 Stock 엔티티의 메서드를 통해 처리하고 있다.
    * 추후 삽입 및 수정 API 생성 시, 이 로직을 서비스 계층으로 옮겨 더 깔끔하고 유지보수하기 좋은 구조로 개선할 예정이다.
      <br><br>