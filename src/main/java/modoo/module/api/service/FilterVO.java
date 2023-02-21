package modoo.module.api.service;

import modoo.module.common.service.CommonDefaultSearchVO;

import java.util.Date;

/**
 * 프론트에 뿌려줄 검색 타입
 */
@SuppressWarnings("serial")
public class FilterVO extends CommonDefaultSearchVO {


    //첫번째 검색 기간
    private String frstPnttm;

    //두번째 검색 기간
    private String lastPnttm;

    //두번째 검색 기간
    private String dplctAt;

    private String dateUseAt;

    public String getFrstPnttm() {
        return frstPnttm;
    }

    public void setFrstPnttm(String frstPnttm) {
        this.frstPnttm = frstPnttm;
    }

    public String getLastPnttm() {
        return lastPnttm;
    }

    public void setLastPnttm(String lastPnttm) {
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

    public String getDateUseAt() {
        return dateUseAt;
    }

    public void setDateUseAt(String dateUseAt) {
        this.dateUseAt = dateUseAt;
    }
}
