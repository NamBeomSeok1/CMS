package modoo.module.mber.author.service;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class MberAuthorVO extends CommonDefaultSearchVO {
	
	/** 권한 코드 */
	private String authorCode;
	
	/** 권한 코드명 */
	private String authorNm;
	
	/** 권한 설명 */
	private String authorDc;
	
	/** 권한 생성일 */
	private java.util.Date authorCreatDe;

	public String getAuthorCode() {
		return authorCode;
	}

	public void setAuthorCode(String authorCode) {
		this.authorCode = authorCode;
	}

	public String getAuthorNm() {
		return authorNm;
	}

	public void setAuthorNm(String authorNm) {
		this.authorNm = authorNm;
	}

	public String getAuthorDc() {
		return authorDc;
	}

	public void setAuthorDc(String authorDc) {
		this.authorDc = authorDc;
	}

	public java.util.Date getAuthorCreatDe() {
		return authorCreatDe;
	}

	public void setAuthorCreatDe(java.util.Date authorCreatDe) {
		this.authorCreatDe = authorCreatDe;
	}

}
