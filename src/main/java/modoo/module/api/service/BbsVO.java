package modoo.module.api.service;

import modoo.module.common.service.CommonDefaultSearchVO;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigInteger;
import java.util.Date;

@SuppressWarnings("serial")
public class BbsVO extends CommonDefaultSearchVO {

    /** 배너고유ID */
    private java.math.BigInteger bbsNo;

    private String usrNm;

    private Date regstPnttm;

    /** 배너구분코드 */
    private String rcord;

    private Integer partcptnCo;


    @Override
    public String toString() {
        return "BbsVO{" +
                "bbsNo=" + bbsNo +
                ", usrNm='" + usrNm + '\'' +
                ", regstPnttm=" + regstPnttm +
                ", rcord='" + rcord + '\'' +
                ", partcptnCo=" + partcptnCo +
                '}';
    }

    public BigInteger getBbsNo() {
        return bbsNo;
    }

    public void setBbsNo(BigInteger bbsNo) {
        this.bbsNo = bbsNo;
    }

    public String getUsrNm() {
        return usrNm;
    }

    public void setUsrNm(String usrNm) {
        this.usrNm = usrNm;
    }

    public Date getRegstPnttm() {
        return regstPnttm;
    }

    public void setRegstPnttm(Date regstPnttm) {
        this.regstPnttm = regstPnttm;
    }

    public String getRcord() {
        return rcord;
    }

    public void setRcord(String rcord) {
        this.rcord = rcord;
    }

    public Integer getPartcptnCo(Integer bigInteger) {
        return partcptnCo;
    }

    public void setPartcptnCo(Integer partcptnCo) {
        this.partcptnCo = partcptnCo;
    }
}
