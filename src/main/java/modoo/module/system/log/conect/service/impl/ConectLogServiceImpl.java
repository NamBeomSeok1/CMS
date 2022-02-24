package modoo.module.system.log.conect.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import modoo.module.system.log.conect.service.ConectLogService;
import modoo.module.system.log.conect.service.ConectLogVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Service("conectLogService")
public class ConectLogServiceImpl extends EgovAbstractServiceImpl implements ConectLogService {
	
	@Resource(name = "conectLogMapper")
	private ConectLogMapper conectLogMapper;
	
	@Resource(name = "conectLogIdGnrService")
	private EgovIdGnrService conectLogIdGnrService;

	/**
	 * B2C접속로그 목록
	 */
	@Override
	public List<?> selectB2CConectCountList(ConectLogVO searchVO) throws Exception {
	return conectLogMapper.selectB2CConectCountList(searchVO);
	}
	
	/**
	 * B2B접속로그 목록
	 */
	@Override
	public List<?> selectB2BConectCountList(ConectLogVO searchVO) throws Exception {
		return conectLogMapper.selectB2BConectCountList(searchVO);
	}

	/**
	 * 접속로그 저장
	 */
	@Override
	public void insertConectLog(ConectLogVO conectLog) throws Exception {
		String conectId = conectLogIdGnrService.getNextStringId();
		conectLog.setConectId(conectId);
		conectLogMapper.insertConectLog(conectLog);
	}

	/**
	 * 접속카운트 목록
	 */
	@Override
	public List<ConectLogVO> selectConectCountList(ConectLogVO searchVO) throws Exception {
		return conectLogMapper.selectConectCountList(searchVO);
	}

}
