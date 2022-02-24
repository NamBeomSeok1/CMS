package modoo.module.shop.cmpny.service;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import modoo.module.common.service.CommonDefaultSearchVO;
import modoo.module.shop.hdry.service.HdryCmpnyVO;

@SuppressWarnings("serial")
public class CmpnyVO extends CommonDefaultSearchVO {

	/** 업체고유ID */
	private String cmpnyId;

	/** 업체명 */
	@NotEmpty
	private String cmpnyNm;

	/** 업체사용자고유ID : ESNTL_ID */
	private String cmpnyUserEsntlId;
	
	/** 업체회원ID */
	@NotEmpty
	private String cmpnyMberId;
	/** 업체회원비밀번호 */
	//@NotEmpty
	//@Length(min=8, max=20)
	private String cmpnyMberPassword;
	/** 업체회원비밀번호확인 */
	//@NotEmpty
	//@Length(min=8, max=20)
	private String cmpnyMberRePassword;

	/** 사업자등록번호 */
	@NotEmpty
	private String bizrno;

	/** 사업자주소 */
	private String bsnmAdres;

	/** 대표자명 */
	private String rprsntvNm;

	/** 홈페이지 */
	private String hmpg;
	
	/** CS채널 */
	private String csChnnl;

	/** 입점일 */
	@NotEmpty
	private String opnngDe;

	/** 업체연락처 */
	@NotEmpty
	private String cmpnyTelno;

	/** 담당자명 */
	@NotEmpty
	private String chargerNm;

	/** 담당자연락처 */
	@NotEmpty
	private String chargerTelno;

	/** 담당자이메일 */
	@NotEmpty
	@Email
	private String chargerEmail;

	/** 정산일구분코드 */
	@NotEmpty
	private String stdeSeCode;

	/** 공급가구분코드 */
	private String splpcSeCode;

	/** 은행고유ID */
	private String bankId;

	/** 계좌번호 */
	private String acnutno;

	/** 업체로고경로 */
	private String cmpnyLogoPath;

	/** 입점승인상태코드 : R(요청 or 신청), A(접수 또는 처리), C (완료), D(취소) */
	private String opnngSttusCode;

	/** 관리자메모 */
	private String mngrMemo;
	
	/** 교환반품주소 */
	@NotEmpty
	private String svcAdres;
	
	/** 배송정책내용 */
	private String cmpnyDlvyPolicyCn;

	/** 최초등록시점 */
	private java.util.Date frstRegistPnttm;

	/** 최초등록자ID */
	private String frstRegisterId;

	/** 최종수정시점 */
	private java.util.Date lastUpdtPnttm;

	/** 최종수정자ID */
	private String lastUpduserId;

	/** 사용여부 */
	private String useAt;
	
	/** 업체 상품 수 */
	private String cmpnyGoodsCo;
	
	/** 택배사목록 */
	private List<HdryCmpnyVO> hdryCmpnyList;
	
	/** 택배사고유ID */
	private String hdryId;
	
	/** 제휴사고유ID */
	private String prtnrId;
	
	/** 제휴사매핑목록 */
	private List<PrtnrCmpnyVO> prtnrCmpnyList;

	/** 교환반품 택배사명 */
	@NotEmpty
	private String svcHdryNm;

	/** 반품배송비 */
	@NotNull
	private java.math.BigDecimal rtngudDlvyPc;

	/** 교환배송비 */
	@NotNull
	private java.math.BigDecimal exchngDlvyPc;
	
	/* 등록상태 */
	private String searchOpnngSttusCode;
	
	public String getCmpnyId() {
		return cmpnyId;
	}

	public void setCmpnyId(String cmpnyId) {
		this.cmpnyId = cmpnyId;
	}

	public String getCmpnyNm() {
		return cmpnyNm;
	}

	public void setCmpnyNm(String cmpnyNm) {
		this.cmpnyNm = cmpnyNm;
	}

	public String getCmpnyUserEsntlId() {
		return cmpnyUserEsntlId;
	}

	public void setCmpnyUserEsntlId(String cmpnyUserEsntlId) {
		this.cmpnyUserEsntlId = cmpnyUserEsntlId;
	}

	public String getBizrno() {
		return bizrno;
	}

	public void setBizrno(String bizrno) {
		this.bizrno = bizrno;
	}

	public String getRprsntvNm() {
		return rprsntvNm;
	}

	public void setRprsntvNm(String rprsntvNm) {
		this.rprsntvNm = rprsntvNm;
	}

	public String getHmpg() {
		return hmpg;
	}

	public void setHmpg(String hmpg) {
		this.hmpg = hmpg;
	}

	public String getOpnngDe() {
		return opnngDe;
	}

	public void setOpnngDe(String opnngDe) {
		this.opnngDe = opnngDe;
	}

	public String getCmpnyTelno() {
		return cmpnyTelno;
	}

	public void setCmpnyTelno(String cmpnyTelno) {
		this.cmpnyTelno = cmpnyTelno;
	}

	public String getChargerNm() {
		return chargerNm;
	}

	public void setChargerNm(String chargerNm) {
		this.chargerNm = chargerNm;
	}

	public String getChargerTelno() {
		return chargerTelno;
	}

	public void setChargerTelno(String chargerTelno) {
		this.chargerTelno = chargerTelno;
	}

	public String getChargerEmail() {
		return chargerEmail;
	}

	public void setChargerEmail(String chargerEmail) {
		this.chargerEmail = chargerEmail;
	}

	public String getStdeSeCode() {
		return stdeSeCode;
	}

	public void setStdeSeCode(String stdeSeCode) {
		this.stdeSeCode = stdeSeCode;
	}

	public String getSplpcSeCode() {
		return splpcSeCode;
	}

	public void setSplpcSeCode(String splpcSeCode) {
		this.splpcSeCode = splpcSeCode;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getAcnutno() {
		return acnutno;
	}

	public void setAcnutno(String acnutno) {
		this.acnutno = acnutno;
	}

	public String getCmpnyLogoPath() {
		return cmpnyLogoPath;
	}

	public void setCmpnyLogoPath(String cmpnyLogoPath) {
		this.cmpnyLogoPath = cmpnyLogoPath;
	}

	public String getOpnngSttusCode() {
		return opnngSttusCode;
	}

	public void setOpnngSttusCode(String opnngSttusCode) {
		this.opnngSttusCode = opnngSttusCode;
	}

	public String getMngrMemo() {
		return mngrMemo;
	}

	public void setMngrMemo(String mngrMemo) {
		this.mngrMemo = mngrMemo;
	}

	public java.util.Date getFrstRegistPnttm() {
		return frstRegistPnttm;
	}

	public void setFrstRegistPnttm(java.util.Date frstRegistPnttm) {
		this.frstRegistPnttm = frstRegistPnttm;
	}

	public String getFrstRegisterId() {
		return frstRegisterId;
	}

	public void setFrstRegisterId(String frstRegisterId) {
		this.frstRegisterId = frstRegisterId;
	}

	public java.util.Date getLastUpdtPnttm() {
		return lastUpdtPnttm;
	}

	public void setLastUpdtPnttm(java.util.Date lastUpdtPnttm) {
		this.lastUpdtPnttm = lastUpdtPnttm;
	}

	public String getLastUpduserId() {
		return lastUpduserId;
	}

	public void setLastUpduserId(String lastUpduserId) {
		this.lastUpduserId = lastUpduserId;
	}

	public String getUseAt() {
		return useAt;
	}

	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}

	public String getCmpnyGoodsCo() {
		return cmpnyGoodsCo;
	}

	public void setCmpnyGoodsCo(String cmpnyGoodsCo) {
		this.cmpnyGoodsCo = cmpnyGoodsCo;
	}

	public String getCmpnyMberId() {
		return cmpnyMberId;
	}

	public void setCmpnyMberId(String cmpnyMberId) {
		this.cmpnyMberId = cmpnyMberId;
	}

	public String getCmpnyMberPassword() {
		return cmpnyMberPassword;
	}

	public void setCmpnyMberPassword(String cmpnyMberPassword) {
		this.cmpnyMberPassword = cmpnyMberPassword;
	}

	public String getCmpnyMberRePassword() {
		return cmpnyMberRePassword;
	}

	public void setCmpnyMberRePassword(String cmpnyMberRePassword) {
		this.cmpnyMberRePassword = cmpnyMberRePassword;
	}

	public List<HdryCmpnyVO> getHdryCmpnyList() {
		return hdryCmpnyList;
	}

	public void setHdryCmpnyList(List<HdryCmpnyVO> hdryCmpnyList) {
		this.hdryCmpnyList = hdryCmpnyList;
	}

	public String getHdryId() {
		return hdryId;
	}

	public void setHdryId(String hdryId) {
		this.hdryId = hdryId;
	}

	public String getPrtnrId() {
		return prtnrId;
	}

	public void setPrtnrId(String prtnrId) {
		this.prtnrId = prtnrId;
	}

	public List<PrtnrCmpnyVO> getPrtnrCmpnyList() {
		return prtnrCmpnyList;
	}

	public void setPrtnrCmpnyList(List<PrtnrCmpnyVO> prtnrCmpnyList) {
		this.prtnrCmpnyList = prtnrCmpnyList;
	}

	public String getCmpnyDlvyPolicyCn() {
		return cmpnyDlvyPolicyCn;
	}

	public void setCmpnyDlvyPolicyCn(String cmpnyDlvyPolicyCn) {
		this.cmpnyDlvyPolicyCn = cmpnyDlvyPolicyCn;
	}

	public String getBsnmAdres() {
		return bsnmAdres;
	}

	public void setBsnmAdres(String bsnmAdres) {
		this.bsnmAdres = bsnmAdres;
	}

	public String getCsChnnl() {
		return csChnnl;
	}

	public void setCsChnnl(String csChnnl) {
		this.csChnnl = csChnnl;
	}

	public String getSvcAdres() {
		return svcAdres;
	}

	public void setSvcAdres(String svcAdres) {
		this.svcAdres = svcAdres;
	}

	public String getSvcHdryNm() {
		return svcHdryNm;
	}

	public void setSvcHdryNm(String svcHdryNm) {
		this.svcHdryNm = svcHdryNm;
	}

	public java.math.BigDecimal getRtngudDlvyPc() {
		return rtngudDlvyPc;
	}

	public void setRtngudDlvyPc(java.math.BigDecimal rtngudDlvyPc) {
		this.rtngudDlvyPc = rtngudDlvyPc;
	}

	public java.math.BigDecimal getExchngDlvyPc() {
		return exchngDlvyPc;
	}

	public void setExchngDlvyPc(java.math.BigDecimal exchngDlvyPc) {
		this.exchngDlvyPc = exchngDlvyPc;
	}

	public String getSearchOpnngSttusCode() {
		return searchOpnngSttusCode;
	}

	public void setSearchOpnngSttusCode(String searchOpnngSttusCode) {
		this.searchOpnngSttusCode = searchOpnngSttusCode;
	}

}
