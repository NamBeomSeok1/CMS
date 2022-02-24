package modoo.module.sample.service;

import org.hibernate.validator.constraints.NotEmpty;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class SampleVO extends CommonDefaultSearchVO {

	/** 샘플고유ID */
	private String sampleId;
	/** 샘플제목 */
	@NotEmpty
	private String sampleSj;
	/** 샘플내용 */
	private String sampleCn;
	/** 첨부파일고유ID */
	private String atchFileId;
	/** 등록시점 */
	private java.util.Date registPnttm;
	/** 수정시점 */
	private java.util.Date updtPnttm;

	public String getSampleId() {
		return sampleId;
	}
	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
	}
	public String getSampleSj() {
		return sampleSj;
	}
	public void setSampleSj(String sampleSj) {
		this.sampleSj = sampleSj;
	}
	public String getSampleCn() {
		return sampleCn;
	}
	public void setSampleCn(String sampleCn) {
		this.sampleCn = sampleCn;
	}
	public String getAtchFileId() {
		return atchFileId;
	}
	public void setAtchFileId(String atchFileId) {
		this.atchFileId = atchFileId;
	}
	public java.util.Date getRegistPnttm() {
		return registPnttm;
	}
	public void setRegistPnttm(java.util.Date registPnttm) {
		this.registPnttm = registPnttm;
	}
	public java.util.Date getUpdtPnttm() {
		return updtPnttm;
	}
	public void setUpdtPnttm(java.util.Date updtPnttm) {
		this.updtPnttm = updtPnttm;
	}
}
