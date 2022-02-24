package modoo.module.site.service;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class SiteDomainVO extends CommonDefaultSearchVO {
	
	/** 사이트 고유ID */
	private String siteId;
	
	/** 도메인명 */
	private String domainNm;
	
	/** 도메인 순번 */
	private Integer domainSn;

	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}

	public String getDomainNm() {
		return domainNm;
	}

	public void setDomainNm(String domainNm) {
		this.domainNm = domainNm;
	}

	public Integer getDomainSn() {
		return domainSn;
	}

	public void setDomainSn(Integer domainSn) {
		this.domainSn = domainSn;
	}
	
}
