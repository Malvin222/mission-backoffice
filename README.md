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
