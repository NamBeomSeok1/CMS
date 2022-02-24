package modoo.module.mber.info.service;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class MberVO extends CommonDefaultSearchVO {

	/** 사용자고유ID */
	private String esntlId;
	/** 사이트고유ID */
	private String siteId;
	/** 회원ID */
	@NotEmpty
	private String mberId;
	/** 비밀번호 */
	@NotEmpty
	@Size(min=8, max=20)
	private String password;
	/** 비밀번호 확인 */
	@NotEmpty
	@Size(min=8, max=20)
	private String repassword;
	/** 회원명 */
	@NotEmpty
	private String mberNm;
	/** 이메일 */
	@NotEmpty
	private String email;
	/** 성별 : M, F, E(기타) */
	private String sexdstn;
	/** 연령대 : 0, 10, 20....90 */
	private String agrde;
	/** 회원상태 */
	private String mberSttus;
	/** 회원유형코드 */
	private String mberTyCode;
	/** 생일 */
	private String birthday;
	/** 핸드폰번호 */
	private String moblphon;
	
	/** 고객사코드 */
	private String clientCd;
	/** 사용자유저키 */
	private String userKey;
	/** 포인트사용여부 */
	private String pointYn;
	/** 적립금사용여부 */
	private String ezmilYn;
	/** 특별포인트사용여부 */
	private String specialUseYn;
	/** 결제수단별_현금영수증발행여부(4자리) */
	private String receiptYn;
	/** (이지웰)추가 authKey */
	private String authKey;

	/**성인인증여부 */
	private String adultCrtYn;
	
	/** 가입일 */
	private java.util.Date sbscrbDe;
	/** 잠김여부 */
	private String lockAt;
	/** 잠김카운트 */
	private Integer lockCnt;
	/** 잠김최종시점 */
	private java.util.Date lockLastPnttm;
	/** 그룹ID */
	private String groupId;
	/** 권한코드 */
	private String authorCode;
	/** 검색 : 사용자 타입  (일반사용자 USER, 관리자 ADMIN, 탈퇴 SECSN)*/
	private String searchUserType; 

	/** 검색 : 사용자 그룹(B2C,이지웰)*/
	private String searchGroupId; 
	
	/** 프로필 이미지*/
	private String profileImg;
	
	public String getAuthKey() {
		return authKey;
	}
	public void setAuthKey(String authKey) {
		this.authKey = authKey;
	}
	public String getSearchGroupId() {
		return searchGroupId;
	}
	public void setSearchGroupId(String searchGroupId) {
		this.searchGroupId = searchGroupId;
	}
	public String getEsntlId() {
		return esntlId;
	}
	public void setEsntlId(String esntlId) {
		this.esntlId = esntlId;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getMberId() {
		return mberId;
	}
	public void setMberId(String mberId) {
		this.mberId = mberId;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRepassword() {
		return repassword;
	}
	public void setRepassword(String repassword) {
		this.repassword = repassword;
	}
	public String getMberNm() {
		return mberNm;
	}
	public void setMberNm(String mberNm) {
		this.mberNm = mberNm;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMberSttus() {
		return mberSttus;
	}
	public void setMberSttus(String mberSttus) {
		this.mberSttus = mberSttus;
	}
	public String getMberTyCode() {
		return mberTyCode;
	}
	public void setMberTyCode(String mberTyCode) {
		this.mberTyCode = mberTyCode;
	}
	public java.util.Date getSbscrbDe() {
		return sbscrbDe;
	}
	public void setSbscrbDe(java.util.Date sbscrbDe) {
		this.sbscrbDe = sbscrbDe;
	}
	public String getLockAt() {
		return lockAt;
	}
	public void setLockAt(String lockAt) {
		this.lockAt = lockAt;
	}
	public Integer getLockCnt() {
		return lockCnt;
	}
	public void setLockCnt(Integer lockCnt) {
		this.lockCnt = lockCnt;
	}
	public java.util.Date getLockLastPnttm() {
		return lockLastPnttm;
	}
	public void setLockLastPnttm(java.util.Date lockLastPnttm) {
		this.lockLastPnttm = lockLastPnttm;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getAuthorCode() {
		return authorCode;
	}
	public void setAuthorCode(String authorCode) {
		this.authorCode = authorCode;
	}
	public String getSearchUserType() {
		return searchUserType;
	}
	public void setSearchUserType(String searchUserType) {
		this.searchUserType = searchUserType;
	}
	public String getClientCd() {
		return clientCd;
	}
	public void setClientCd(String clientCd) {
		this.clientCd = clientCd;
	}
	public String getUserKey() {
		return userKey;
	}
	public void setUserKey(String userKey) {
		this.userKey = userKey;
	}
	public String getPointYn() {
		return pointYn;
	}
	public void setPointYn(String pointYn) {
		this.pointYn = pointYn;
	}
	public String getEzmilYn() {
		return ezmilYn;
	}
	public void setEzmilYn(String ezmilYn) {
		this.ezmilYn = ezmilYn;
	}
	public String getSpecialUseYn() {
		return specialUseYn;
	}
	public void setSpecialUseYn(String specialUseYn) {
		this.specialUseYn = specialUseYn;
	}
	public String getReceiptYn() {
		return receiptYn;
	}
	public void setReceiptYn(String receiptYn) {
		this.receiptYn = receiptYn;
	}
	public String getAdultCrtYn() {
		return adultCrtYn;
	}
	public void setAdultCrtYn(String adultCrtYn) {
		this.adultCrtYn = adultCrtYn;
	}
	public String getSexdstn() {
		return sexdstn;
	}
	public void setSexdstn(String sexdstn) {
		this.sexdstn = sexdstn;
	}
	public String getAgrde() {
		return agrde;
	}
	public void setAgrde(String agrde) {
		this.agrde = agrde;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getMoblphon() {
		return moblphon;
	}
	public void setMoblphon(String moblphon) {
		this.moblphon = moblphon;
	}
	public String getProfileImg() {
		return profileImg;
	}
	public void setProfileImg(String profileImg) {
		this.profileImg = profileImg;
	}
	
	
}
