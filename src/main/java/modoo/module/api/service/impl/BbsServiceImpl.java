package modoo.module.api.service.impl;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import modoo.module.api.service.BbsService;
import modoo.module.api.service.BbsVO;
import modoo.module.api.service.FilterVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("bbsService")
public class BbsServiceImpl extends EgovAbstractServiceImpl implements BbsService {

    @Resource(name = "BbsMapper")
    private BbsMapper bbsMapper;

    /**
     * 관리자, 프론트 중복허용X 게시글 리스트
     * @param searchVO
     * @return
     * @throws Exception
     */

    @Override
    public List<BbsVO> selectBbsList(BbsVO searchVO) throws Exception {
        return bbsMapper.selectBbsList(searchVO);
    }

    @Override
    public Integer selectMaxPartcprnCo(BbsVO searchVO) throws Exception {
        return bbsMapper.selectMaxPartcprnCo(searchVO);
    }


    @Override
    public FilterVO selectFilter() throws Exception {
        return bbsMapper.selectFilter();
    }

    @Override
    public List<BbsVO> selectDupliBbsList(BbsVO searchVO) throws Exception {
        return bbsMapper.selectDupliBbsList(searchVO);
    }

    @Override
    public void insertBbs(BbsVO searchVO) throws Exception {
        bbsMapper.insertBbs(searchVO);
    }

    @Override
    public void deleteBbs(BbsVO searchVO) throws Exception {
        bbsMapper.deleteBbs(searchVO);
    }

    @Override
    public void insertFilter(FilterVO searchVO) throws Exception {
        bbsMapper.insertFilter(searchVO);
    }


    @Override
    public void deleteFilter() throws Exception {
        bbsMapper.deleteFilter();
    }


}
