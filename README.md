
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
  * 상품 ID를 외래키로 참조하여 해당 상품의 입고 수량, 입고 가격 및 입고 날짜를 저장할 수 있으며, 이를 통해 입고 후 재고 수량을 파악할 수 있습니다.
  ![image](https://github.com/user-attachments/assets/560c40bf-0b6c-44a3-b2aa-1bccc0eea961)
