package modoo.module.mber.author.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import modoo.module.mber.author.service.MberAuthorService;
import modoo.module.mber.author.service.MberAuthorVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("mberAuthorService")
public class MberAuthorServiceImpl extends EgovAbstractServiceImpl implements MberAuthorService {

	@Resource(name = "mberAuthorMapper")
	private MberAuthorMapper mberAuthorMapper;

	/**
	 * 권한 목록
	 */
	@Override
	public List<MberAuthorVO> selectMberAuthorList(MberAuthorVO searchVO) throws Exception {
		return mberAuthorMapper.selectMberAuthorList(searchVO);
	}

	/**
	 * 사용된 권한 목록
	 */
	@Override
	public List<MberAuthorVO> selectUsedAuthorList(MberAuthorVO searchVO) throws Exception {
		return mberAuthorMapper.selectUsedAuthorList(searchVO);
	}

}
