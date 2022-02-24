package modoo.module.mber.info.service;

import java.util.List;

public interface MberService {
	
	/**
	 * 회원 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectMberList(MberVO searchVO) throws Exception;
	
	/**
	 * 회원 목록 카운트
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	int selectMberListCnt(MberVO searchVO) throws Exception;
	
	/**
	 * 회원 저장
	 * @param mber
	 * @throws Exception
	 */
	void insertMber(MberVO mber) throws Exception;
	
	/**
	 * SSO 회원 저장
	 * @param mber
	 * @throws Exception
	 */
	String insertSsoMber(MberVO mber) throws Exception;
	
	/**
	 * PORTAL 회원 저장 
	 */
	void insertPortalMber(MberVO mber) throws Exception;
	
	/**
	 * 회원 상세 
	 * @param mber
	 * @return
	 * @throws Exception
	 */
	MberVO selectMber(MberVO mber) throws Exception;
	
	/**
	 * 회원 수정
	 * @param mber
	 * @throws Exception
	 */
	void updateMber(MberVO mber) throws Exception;
	
	/**
	 * 회원 상태 수정
	 * @param mber
	 * @throws Exception
	 */
	void updateMberSttus(MberVO mber) throws Exception;
	
	/**
	 * 회원 삭제
	 * @param mber
	 * @throws Exception
	 */
	void deleteMber(MberVO mber) throws Exception;
	
	/**
	 * 회원 ID 중복 카운트
	 * @param mber
	 * @return
	 * @throws Exception
	 */
	int selectCheckDuplMberIdCnt(MberVO mber) throws Exception;
	
	/**
	 * 비밀번호 수정
	 * @param mber
	 * @throws Exception
	 */
	void updatePassword(MberVO mber) throws Exception;
	
	/**
	 * 잠김 해제
	 * @param mber
	 * @throws Exception
	 */
	void updateLockIncorrect(MberVO mber) throws Exception;
	
	/**
	 * SSO 회원가입체크
	 * @param mber
	 * @return
	 * @throws Exception
	 */
	int selectSsoMemberCheck(MberVO mber) throws Exception;
	
	/**
	 * SSO 회원상세
	 * @param mber
	 * @return
	 * @throws Exception
	 */
	MberVO selectSsoMember(MberVO mber) throws Exception;
	
	/**
	 * 회원권한 목록
	 * @param mber
	 * @return
	 * @throws Exception
	 */
	List<MberVO> selectMberAuthList() throws Exception;
	
	/**
	 * 이지웰 상태 수정
	 * @param mber
	 * @throws Exception
	 */
	void updateEzwelMember(MberVO mber) throws Exception;
	
	void addMberRole(MberVO mber) throws Exception;
}
