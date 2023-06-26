### 매장 테이블 예약 서비스
  
**식당이나 점포를 이용하기 전에 미리 예약을 하여 편하게 식당/점포를 이용할 수 있는 서비스 개발”**
- [x] 매장의 점장은 예약 서비스 앱에 상점을 등록(매장 명, 상점위치, 상점 설명)
- [x] 매장을 등록하기 위해서는 파트너 회원 가입필요 (승인 조건 X ,가입 후 바로 이용 가능)
- [x] 매장 이용자는 앱을 통해서 매장을 검색하고 상세 정보 확인.
- [x] 매장의 상세 정보를 보고, 예약을 진행.(예약을 진행하기 위해서는 회원 가입 필수)
- [x] *예약이 들어오면 점장은 승인/예약거절* ( **마스터반 추가구현 시나리오** )
- [x] 서비스를 통해서 예약한 이후, 예약 10분전에 도착하여 키오스크를 통해서 방문 확인을 진행.
- [x] 예약 및 사용 이후 리뷰 작성가능.

##### <User 관련 함수>
1. 토큰 및 유저정보 검증용 (토큰/유저정보 유효할때만 정상값 반환)<br>
    _Users getUserFromToken(String token);_<br>
2. 사용자의 이름 , 이메일, 비밀번호, 전화번호, 사용자유형(고객/점주/관리자)를 받아 회원가입 진행<br>
    _ServiceResult userRegistration(UsersInput usersInput);_<br>
3. 고객의 이메일 / 비밀번호 검증하여 토큰 발행<br>
    _ServiceResult userLogin(UserLoginInput userLoginInput);_<br>
    _String tokenIssue(Users user); (내부 호출)_<br>

##### <Store 관련 함수>
1. 매장 정보 등록 ( 점주 )<br>
   _ServiceResult registerStore(Users user, StoreInput storeInput);_<br>
2. 모든 매장(검색키워드 미입력시)또는 검색키워드가 포함된 매장 리스트 검색<br>
   _ServiceResult getStoreList(Users user, StoreSearchInput storeSearchInput);_<br>
3. 매장 상세정보 반환하는 함수<br>
   _ServiceResult getStoreDetail(Long id);_<br>

##### <Reservation 관련 함수>
1. 예약 생성용 함수<br>
    _ServiceResult makeAReservation(Long id,Users user, ReservationInput reservationInput);_<br>
2. 매장 방문 후 키오스크 예약 확인용 함수<br>
   _ServiceResult reservationConfirm(Users user,Long id);_<br>
3. 예약 승인 ( 파트너 회원용 )<br>
   _ServiceResult reservationApproval(Long id, Users user);_<br>
4.  예약 거절 ( 파트너 회원용 )<br>
    _ServiceResult reservationDisapproval(Long id, Users user);_<br>
5. 예약 목록 보기 ( 파트너 회원용)<br>
    _ServiceResult listReservation(Users user, ReservationListInput reservationListInput);_<br>

##### <Review 관련 함수>
1. 리뷰 작성 (별점, 코멘트)<br>
    _ServiceResult reviewPost(Users user, Long id , ReviewInput reviewInput);_<br>

2. 스토어의 평균 리뷰점수, 각 유저들이 남긴 리뷰(코멘트,별점)리스트로 가져오는 함수<br>
    _ServiceResult getStoreReviews(Long id);_<br>

3. 리뷰 등록 시 별점 계산을 위해 사용되는함수 (2번 함수 내부에서 호출)<br>
    _int getAverageRateOfTheStore(int rate, Store store);_<br>