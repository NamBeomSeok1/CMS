package modoo.module.api.service;

import java.util.Date;

/**
 * 프론트에 뿌려줄 검색 타입
 */
@SuppressWarnings("serial")
public class FilterVO {


    //첫번째 검색 기간
    private java.util.Date frstPnttm;

    //두번째 검색 기간
    private java.util.Date lastPnttm;

    //두번째 검색 기간
    private String dplctAt;

    public FilterVO(Date frstPnttm, Date lastPnttm, String dplctAt) {
        this.frstPnttm = frstPnttm;
        this.lastPnttm = lastPnttm;
        this.dplctAt = dplctAt;
    }

    public Date getFrstPnttm() {
        return frstPnttm;
    }

    public void setFrstPnttm(Date frstPnttm) {
        this.frstPnttm = frstPnttm;
    }

    public Date getLastPnttm() {
        return lastPnttm;
    }

    public void setLastPnttm(Date lastPnttm) {
        this.lastPnttm = lastPnttm;
    }

    public String getDplctAt() {
        return dplctAt;
    }

    public void setDplctAt(String dplctAt) {
        this.dplctAt = dplctAt;
    }

    @Override
    public String toString() {
        return "filterVO{" +
                "frstPnttm=" + frstPnttm +
                ", lastPnttm=" + lastPnttm +
                ", dplctAt='" + dplctAt + '\'' +
                '}';
    }
}
