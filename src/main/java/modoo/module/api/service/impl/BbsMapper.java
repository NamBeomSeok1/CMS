package modoo.module.api.service.impl;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import modoo.module.api.service.BbsVO;
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
    List<BbsVO> selectBbsList(BbsVO searchVO) throws Exception;

    void insertBbs(BbsVO searchVO) throws Exception;

    void deleteBbs(BbsVO searchVO) throws Exception;
}
