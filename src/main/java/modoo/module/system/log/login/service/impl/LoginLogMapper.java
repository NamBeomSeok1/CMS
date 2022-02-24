package modoo.module.system.log.login.service.impl;

import java.util.List;

import modoo.module.system.log.login.service.LoginLogVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("loginLogMapper")
public interface LoginLogMapper {

	/**
	 * 접속로그 저장
	 * @param loginLog
	 * @throws Exception
	 */
	void insertLoginLog(LoginLogVO loginLog) throws Exception;

	/**
	 * 접속로그 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectLoginLogList(LoginLogVO searchVO) throws Exception;

	/**
	 * 접속로그 목록 카운트
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	int selectLoginLogListCnt(LoginLogVO searchVO) throws Exception;
}
