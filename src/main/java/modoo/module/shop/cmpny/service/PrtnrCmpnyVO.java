package modoo.module.shop.cmpny.service;

public class PrtnrCmpnyVO extends PrtnrVO {
	
	/** 제휴사매핑고유ID */
	private String pcmapngId;
	
	/** 제휴사고유ID */
	private String prtnrId;
	
	/** 업체고유ID */
	private String cmpnyId;
	
	/** 사용여부 */
	private String useAt;

	public String getPcmapngId() {
		return pcmapngId;
	}

	public void setPcmapngId(String pcmapngId) {
		this.pcmapngId = pcmapngId;
	}

	public String getPrtnrId() {
		return prtnrId;
	}

	public void setPrtnrId(String prtnrId) {
		this.prtnrId = prtnrId;
	}

	public String getCmpnyId() {
		return cmpnyId;
	}

	public void setCmpnyId(String cmpnyId) {
		this.cmpnyId = cmpnyId;
	}

	public String getUseAt() {
		return useAt;
	}

	public void setUseAt(String useAt) {
		this.useAt = useAt;
	}
	

}
