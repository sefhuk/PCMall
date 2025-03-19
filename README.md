<!-- ## 배포 도메인
http://htproject.shop
-->
## ERD
<img width="880" alt="스크린샷 2024-06-20 오후 12 59 16" src="https://github.com/user-attachments/assets/121c63c8-e954-4dd0-a86c-db5892dec2a6">

## API 명세서

- 유저
    
    
    | 기능 | api 경로 | 요청 body | 응답 | 메서드 |
    | --- | --- | --- | --- | --- |
    | 로그인 페이지 | / |  |  | GET |
    | 회원가입 페이지 | /sign-up |  |  | GET |
    | 마이 페이지 | /user/myPage |  |  | GET |
    | 관리자 페이지 | /user/myPage |  |  | GET |
    | 수정 페이지 | /user/editPage |  |  | GET |
    | 삭제 페이지 | /user/deletePage |  |  | GET |
    | 전체 유저 목록 페이지 | /admin/user |  |  | GET |
    | 로그인 | /login | {”email”: “String, ”password”:”String} |  | POST |
    | 회원가입 | /sign-up | {”name”: “String”, ”email”: “String”, ”password”: ”String”, ”confirmPassword”: “String”, ”phoneNumber”: “String”} |  | POST |
    | 이름 수정 | /user/editName | {”name”: “String”} | 200 OK | PUT |
    | 이메일 수정 | /user/editEmail | {”email”: “String”} | 200 OK | PUT |
    | 비밀번호 수정 | /user/editPassword | {”oldPassword”: “String”, ”newPassword”: “String”} | 200 OK | PUT |
    | 전화번호 수정 | /user/editPhoneNumber | {”phoneNumber”: “String”} | 200 OK | PUT |
    | 주소 수정 | /user/editAddress | {”address”: “String”} | 200 OK | PUT |
    | 유저 삭제 | /user/deleteUser |  |  | GET |
    | 인증 메일 발송 | /mail | {”email”: “String”} | 200 OK | POST |
    | 인증 코드 확인 | /mailCheck | {”email”: “String”, ”code”:  “String”) | 200 OK | POST |
  
- 상품
    ### 관리자
    | 기능 | API 경로 | 요청 Param | 요청 Body | 응답 | HTTP 메소드 |
    | --- | --- | --- | --- | --- | --- |
    | 상품 등록 | /admin/product | {"brand": String, "name": String, "part": String, "stock": Long, "price": Integer, "description": String(json), "images": List }  |  |  | POST |
    | 상품 수정 | /admin/product | {"productId": Long, "brand": String, "name": String, "part": String, "stock": Long, "price": Integer, "description": String(json), "images": List } |  |  | PUT |
    | 상품 삭제 | /admin/product | {"id": Long } |  |  | DELETE |
    | 상품 관리 페이지 | /admin/product |  |  |  | GET |
    | 상품 등록 페이지 | /admin/product/new |  |  |  | GET |
    | 상품 수정/삭제 페이지 | /admin/product/{id}/edit | {"id": Long } | { "cartId": "number", "itemId": "number" } |  | GET |

    ### 유저
    | 기능 | API 경로 | 요청 Param | 요청 Body | 응답 | HTTP 메소드 |
    | --- | --- | --- | --- | --- | --- |
    | 상품 목록 페이지 | /user/product | { "category": String, "page": Integer, "size": Integer }  |  |  | GET |
    | 상품 상세 페이지 | /user/product/{id} |   |  |  | GET |
    
- 장바구니    
    | 기능 | API 경로 | 요청 Body | 응답 | HTTP 메소드 |
    | --- | --- | --- | --- | --- |
    | 전체 카트 조회 | /admin/carts |  |  | GET |
    | 특정 카트 조회 | /admin/carts/{cartId} |  |  | GET |
    | 카트 항목 추가 | /admin/carts/addItem | { "cartId": "number", "itemId": "number", "quantity": "number" } |  | POST |
    | 카트 항목 수정 | /admin/carts/editItem | { "cartId": "number", "itemId": "number", "quantity": "number" } |  | PUT |
    | 카트 항목 삭제 | /admin/carts/deleteItem | { "cartId": "number", "itemId": "number" } |  | DELETE |
  
- 카테고리
    | 기능 | API 경로 | 요청 Body | 응답 | HTTP 메소드 |
    | --- | --- | --- | --- | --- |
    | 전체 카테고리 조회 | /admin/categories |  | 200 OK | GET |
    | 서브 카테고리 조회 | /admin/categories/{parentId}/manufacturers |  | 200 OK | GET |
    | 카테고리 생성 | /admin/categories/add | { "name": "string", "parentId": "number", "type": "MANUFACTURER" } | 200 OK | POST |
    | 카테고리 수정 | /admin/categories/edit | { "id": "number", "name": "string" } | 200 OK | PUT |
    | 카테고리 삭제 | /admin/categories/delete | { "id": "number" } | 200 OK | DELETE |

- 주문  
    | 기능 | 메서드 | API 경로 | 요청 | 응답 |
    | --- | --- | --- | --- | --- |
    | 전체 주문목록 | GET | /api/order |  | 200 |
    | 유저 주문생성 | POST | /api/order/{userId} | {"name" : String, "address" : String, "phoneNumber" : String, "orderDetailDtos": List<OrderDetailDto> } | 200 |
    | 유저 주문조회 | GET | /api/order/{userId} |  | 200 |
    | 주문 상태변경 | PUT | /api/order/{orderId} | Status { "orderStatus" : String } | 200 |
    | 주문 삭제 | DELETE | /api/order/{orderId} |  | 200 |
    | 유저 주문 목록 페이지 | GET | /user/order |  |  |
    | 주문 페이지 | GET | /user/order/sheet | (RequestParam) List<Long> productIds, List<Long> counts |  |
    | 주문상세 페이지 | GET | /user/order/{orderId} |  |  |
    | 관리자 페이지 | GET | /admin/order |  |  |
