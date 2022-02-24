package modoo.module.system.log.login.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import modoo.module.system.log.login.service.LoginLogService;
import modoo.module.system.log.login.service.LoginLogVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("loginLogService")
public class LoginLogServiceImpl extends EgovAbstractServiceImpl implements LoginLogService {

	@Resource(name = "loginLogMapper")
	private LoginLogMapper loginLogMapper;

	/**
	 * 접속로그 저장
	 */
	@Override
	public void insertLoginLog(LoginLogVO loginLog) throws Exception {
		loginLogMapper.insertLoginLog(loginLog);
	}

	/**
	 * 접속로그 목록
	 */
	@Override
	public List<?> selectLoginLogList(LoginLogVO searchVO) throws Exception {
		return loginLogMapper.selectLoginLogList(searchVO);
	}

	/**
	 * 접속로그 목록 카운트
	 */
	@Override
	public int selectLoginLogListCnt(LoginLogVO searchVO) throws Exception {
		return loginLogMapper.selectLoginLogListCnt(searchVO);
	}

}
