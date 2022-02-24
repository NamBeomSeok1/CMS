package modoo.module.api.service;

import java.util.List;

public interface BbsService {

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
