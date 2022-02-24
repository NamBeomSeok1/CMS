package modoo.module.system.log.login.service;

import java.util.List;

public interface LoginLogService {

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
