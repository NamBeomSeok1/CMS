package modoo.module.api.service;

import egovframework.rte.psl.dataaccess.util.EgovMap;

import java.util.List;

public interface BbsService {

    /**
     * 작성글 목록
     * @param searchVO
     * @return
     * @throws Exception
     */
    List<EgovMap> selectBbsList(BbsVO searchVO) throws Exception;

    Integer selectMaxPartcprnCo(BbsVO searchVO) throws Exception;

    List<EgovMap> selectDupliBbsList(BbsVO searchVO) throws Exception;

    FilterVO selectFilter() throws Exception;

    void insertFilter(FilterVO searchVO) throws Exception;

    void deleteFilter() throws Exception;

    void insertBbs(BbsVO searchVO) throws Exception;

    void deleteBbs(BbsVO searchVO) throws Exception;
    void updateBbsPartcptnCo(BbsVO searchVO) throws Exception;
}
