package modoo.module.shop.idsrts.service;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class IdsrtsVO extends CommonDefaultSearchVO {

	//우편번호
	private String zip;
	
	//조소
	private String adres;
	
	//사용여부
	private String useAt;

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getAdres() {
		return adres;
	}

	public void setAdres(String adres) {
		this.adres = adres;
	}

	public String getUseAt() {
		return useAt;
	}

	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}

}
