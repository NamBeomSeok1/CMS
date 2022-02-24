package modoo.module.common.code.service;

import java.util.List;

public interface CmmnCodeService {

	/**
	 * 공통코드 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<CmmnCodeVO> selectCmmnCodeList(CmmnCodeVO searchVO) throws Exception;
	
	/**
	 * 공통코드 목록 카운트
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	int selectCmmnCodeListCnt(CmmnCodeVO searchVO) throws Exception;
	
	/**
	 * 공통코드 저장
	 * @param code
	 * @throws Exception
	 */
	void insertCmmnCode(CmmnCodeVO code) throws Exception;
	
	/**
	 * 다은 공통코드ID
	 * @param code
	 * @return
	 * @throws Exception
	 */
	String getNextCmmnCodeId(CmmnCodeVO code) throws Exception;
	
	/**
	 * 공통코드ID 체크 카운트
	 * @param code
	 * @return
	 * @throws Exception
	 */
	int selectCheckCmmnCodeId(CmmnCodeVO code) throws Exception;
	
	/**
	 * 공통코드 상세
	 * @param code
	 * @return
	 * @throws Exception
	 */
	CmmnCodeVO selectCmmnCode(CmmnCodeVO code) throws Exception;
	
	/**
	 * 공통코드 수정
	 * @param code
	 * @throws Exception
	 */
	void updateCmmnCode(CmmnCodeVO code) throws Exception;
	
	/**
	 * 공통코드 삭제
	 * @param code
	 * @throws Exception
	 */
	void deleteCmmnCode(CmmnCodeVO code) throws Exception;
	
	/**
	 * 공통코드상세 확인 카운트
	 * @param code
	 * @return
	 * @throws Exception
	 */
	int selectCmmnDetailCodeCheckCnt(CmmnCodeVO code) throws Exception;
	
	/**
	 * 공통코드상세 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<CmmnDetailCodeVO> selectCmmnDetailCodeList(CmmnDetailCodeVO searchVO) throws Exception;
	
	/**
	 * 공통코드상세순서 최대값 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	int selectCmmnDetailCodeSnMaxCnt(CmmnDetailCodeVO searchVO) throws Exception;
	
	/**
	 * 공통코드상세 목록 카운트
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	int selectCmmnDetailCodeListCnt(CmmnDetailCodeVO searchVO) throws Exception;
	
	/**
	 * 공통코드상세 체크카운트
	 * @param codeDetail
	 * @return
	 * @throws Exception
	 */
	int selectCheckCmmnCodeDetailCnt(CmmnDetailCodeVO codeDetail) throws Exception;
	
	/**
	 * 공통코드상세 저장
	 * @param codeDetail
	 * @throws Exception
	 */
	void insertCmmnCodeDetail(CmmnDetailCodeVO codeDetail) throws Exception;
	
	/**
	 * 공통코드상세
	 * @param codeDetail
	 * @return
	 * @throws Exception
	 */
	CmmnDetailCodeVO selectCmmnCodeDetail(CmmnDetailCodeVO codeDetail) throws Exception;
	
	/**
	 * 공통코드상세 수정
	 * @param codeDetail
	 * @throws Exception
	 */
	void updateCmmnCodeDetail(CmmnDetailCodeVO codeDetail) throws Exception;
	
	/**
	 * 공통코드상세 삭제
	 * @param codeDetail
	 * @throws Exception
	 */
	void deleteCmmnCodeDetail(CmmnDetailCodeVO codeDetail) throws Exception;
}
