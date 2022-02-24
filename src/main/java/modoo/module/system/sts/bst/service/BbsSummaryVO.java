package modoo.module.system.sts.bst.service;

import modoo.module.common.service.CommonDefaultSearchVO;

@SuppressWarnings("serial")
public class BbsSummaryVO extends CommonDefaultSearchVO {
	
	/** 발생일 */
	private String occrrncDe;
	/** 통계구분 */
	private String statsSe;
	/** 생성수 */
	private int creatCo;
	/** 총조회수 */
	private int totRdcnt;
	/** 평균조회수 */
	private int avrgRdcnt;
	/** 총첨부파일수 */
	private int totAtchFileCo;
	/** 총첨부파일크기 */
	private long totAtchFileSize;
	
	public String getOccrrncDe() {
		return occrrncDe;
	}
	public void setOccrrncDe(String occrrncDe) {
		this.occrrncDe = occrrncDe;
	}
	public String getStatsSe() {
		return statsSe;
	}
	public void setStatsSe(String statsSe) {
		this.statsSe = statsSe;
	}
	public int getCreatCo() {
		return creatCo;
	}
	public void setCreatCo(int creatCo) {
		this.creatCo = creatCo;
	}
	public int getTotRdcnt() {
		return totRdcnt;
	}
	public void setTotRdcnt(int totRdcnt) {
		this.totRdcnt = totRdcnt;
	}
	public int getAvrgRdcnt() {
		return avrgRdcnt;
	}
	public void setAvrgRdcnt(int avrgRdcnt) {
		this.avrgRdcnt = avrgRdcnt;
	}
	public int getTotAtchFileCo() {
		return totAtchFileCo;
	}
	public void setTotAtchFileCo(int totAtchFileCo) {
		this.totAtchFileCo = totAtchFileCo;
	}
	public long getTotAtchFileSize() {
		return totAtchFileSize;
	}
	public void setTotAtchFileSize(long totAtchFileSize) {
		this.totAtchFileSize = totAtchFileSize;
	}
	
	

}
