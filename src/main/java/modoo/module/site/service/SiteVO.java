package modoo.module.site.service;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class SiteVO extends CommonDefaultSearchVO {

	/** 사이트고유ID */
	private String siteId;
	/** 사이트명 */
	@NotEmpty
	private String siteNm;
	/** 사이트관리자명 */
	private String siteMngrNm;
	/** 사이트이메일 */
	private String siteEmail;
	/** 사이트주소 */
	private String siteAdres;
	/** 사이트전화번호 */
	private String siteTelno;
	/** 사이트팩스번호 */
	private String siteFaxno;
	/** 사이트카피라이트 */
	private String siteCopyright;
	/** 사이트로고경로 */
	@NotNull
	private String siteLogoPath;
	/** 사이트순번 */
	private Integer siteSn = 0;
	/** 이용약관 */
	private String termsCond;
	/** 개인정보보호정책 */
	private String privInfo;
	/** 이벤트마케팅 */
	private String eventMarkt;

	/** 등록시점 */
	private java.util.Date registPnttm;
	/** 등록자ID */
	private String registerId;
	/** 활성여부 */
	private String actvtyAt = "N";
	
	/** 도메인 목록 */
	private List<SiteDomainVO> siteDomainList;

	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public String getSiteNm() {
		return siteNm;
	}
	public void setSiteNm(String siteNm) {
		this.siteNm = siteNm;
	}
	public String getSiteMngrNm() {
		return siteMngrNm;
	}
	public void setSiteMngrNm(String siteMngrNm) {
		this.siteMngrNm = siteMngrNm;
	}
	public String getSiteEmail() {
		return siteEmail;
	}
	public void setSiteEmail(String siteEmail) {
		this.siteEmail = siteEmail;
	}
	public String getSiteAdres() {
		return siteAdres;
	}
	public void setSiteAdres(String siteAdres) {
		this.siteAdres = siteAdres;
	}
	public String getSiteTelno() {
		return siteTelno;
	}
	public void setSiteTelno(String siteTelno) {
		this.siteTelno = siteTelno;
	}
	public String getSiteFaxno() {
		return siteFaxno;
	}
	public void setSiteFaxno(String siteFaxno) {
		this.siteFaxno = siteFaxno;
	}
	public String getSiteCopyright() {
		return siteCopyright;
	}
	public void setSiteCopyright(String siteCopyright) {
		this.siteCopyright = siteCopyright;
	}
	public String getSiteLogoPath() {
		return siteLogoPath;
	}
	public void setSiteLogoPath(String siteLogoPath) {
		this.siteLogoPath = siteLogoPath;
	}
	public Integer getSiteSn() {
		return siteSn;
	}
	public void setSiteSn(Integer siteSn) {
		this.siteSn = siteSn;
	}
	public String getTermsCond() {
		return termsCond;
	}
	public void setTermsCond(String termsCond) {
		this.termsCond = termsCond;
	}
	public String getPrivInfo() {
		return privInfo;
	}
	public void setPrivInfo(String privInfo) {
		this.privInfo = privInfo;
	}
	public String getEventMarkt() {
		return eventMarkt;
	}
	public void setEventMarkt(String eventMarkt) {
		this.eventMarkt = eventMarkt;
	}

	public java.util.Date getRegistPnttm() {
		return registPnttm;
	}
	public void setRegistPnttm(java.util.Date registPnttm) {
		this.registPnttm = registPnttm;
	}
	public String getRegisterId() {
		return registerId;
	}
	public void setRegisterId(String registerId) {
		this.registerId = registerId;
	}
	public String getActvtyAt() {
		return actvtyAt;
	}
	public void setActvtyAt(String actvtyAt) {
		this.actvtyAt = actvtyAt;
	}
	public List<SiteDomainVO> getSiteDomainList() {
		return siteDomainList;
	}
	public void setSiteDomainList(List<SiteDomainVO> siteDomainList) {
		this.siteDomainList = siteDomainList;
	}
	
	public void initSiteDoaminList(int len) {
		if(this.siteDomainList == null) {
			this.siteDomainList = new ArrayList<SiteDomainVO>();
		}
		
		int tot = len - this.getSiteDomainList().size();
		
		for(int i = 0; i < tot; i++) {
			SiteDomainVO siteDomain = new SiteDomainVO();
			siteDomain.setSiteId(this.siteId);
			this.siteDomainList.add(siteDomain);
		}
	}
	
}
