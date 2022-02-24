package modoo.module.common.code.service;

import org.hibernate.validator.constraints.NotEmpty;

@SuppressWarnings("serial")
public class CmmnDetailCodeVO extends CmmnCodeVO {

	/** 코드 */
	@NotEmpty
	private String code;

	/** 코드명 */
	@NotEmpty
	private String codeNm;

	/** 코드설명 */
	private String codeDc;

	/** 코드순번 */
	private Integer codeSn;
	
	/** 사용여부 */
	private String useAt;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCodeNm() {
		return codeNm;
	}
	public void setCodeNm(String codeNm) {
		this.codeNm = codeNm;
	}
	public String getCodeDc() {
		return codeDc;
	}
	public void setCodeDc(String codeDc) {
		this.codeDc = codeDc;
	}
	public Integer getCodeSn() {
		return codeSn;
	}
	public void setCodeSn(Integer codeSn) {
		this.codeSn = codeSn;
	}
	public String getUseAt() {
		return useAt;
	}
	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}
	
}
