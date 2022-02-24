package modoo.module.api.service.impl;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import modoo.module.api.service.BbsService;
import modoo.module.api.service.BbsVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("bbsService")
public class BbsServiceImpl extends EgovAbstractServiceImpl implements BbsService {

    @Resource(name = "BbsMapper")
    private BbsMapper bbsMapper;


    @Override
    public List<BbsVO> selectBbsList(BbsVO searchVO) throws Exception {
        return bbsMapper.selectBbsList(searchVO);
    }

    @Override
    public void insertBbs(BbsVO searchVO) throws Exception {
        bbsMapper.insertBbs(searchVO);
    }

    @Override
    public void deleteBbs(BbsVO searchVO) throws Exception {
        bbsMapper.deleteBbs(searchVO);
    }
}
