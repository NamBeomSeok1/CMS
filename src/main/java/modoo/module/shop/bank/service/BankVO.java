package modoo.module.shop.bank.service;

import org.hibernate.validator.constraints.NotEmpty;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class BankVO extends CommonDefaultSearchVO {

	/** 은행고유ID */
	private String bankId;
	
	/** 은행명 */
	@NotEmpty
	private String bankNm;
	
	/** 사용여부 */
	private String useAt;

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBankNm() {
		return bankNm;
	}

	public void setBankNm(String bankNm) {
		this.bankNm = bankNm;
	}

	public String getUseAt() {
		return useAt;
	}

	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}
	
	
}
