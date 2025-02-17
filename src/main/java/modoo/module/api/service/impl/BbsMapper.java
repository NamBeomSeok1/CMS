package modoo.module.api.service.impl;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import modoo.module.api.service.BbsVO;
import modoo.module.api.service.FilterVO;
import modoo.module.banner.service.BannerVO;

import java.util.List;

@Mapper("BbsMapper")
public interface BbsMapper {

    /**
     * 작성글 목록
     * @param searchVO
     * @return
     * @throws Exception
     */
    List<EgovMap> selectBbsList(BbsVO searchVO) throws Exception;

    BbsVO selectBbs(BbsVO searchVO) throws Exception;

    Integer selectMaxPartcprnCo(BbsVO seBbsVO) throws Exception;

    List<EgovMap> selectDupliBbsList(BbsVO searchVO) throws Exception;

    FilterVO selectFilter() throws Exception;

    void insertFilter(FilterVO searchVO) throws Exception;

    void deleteFilter() throws Exception;

    void insertBbs(BbsVO searchVO) throws Exception;

    void deleteBbs(BbsVO searchVO) throws Exception;
    void updateBbsPartcptnCo(BbsVO searchVO) throws Exception;
}
