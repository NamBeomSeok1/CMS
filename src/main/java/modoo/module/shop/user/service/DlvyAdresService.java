package modoo.module.shop.user.service;

import java.util.List;

public interface DlvyAdresService {
	
	/**
	 * 배송지 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<DlvyAdresVO> selectDlvyAdresList(DlvyAdresVO searchVO) throws Exception;
	
	/**
	 * 배송지 목록 카운트
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	int selectDlvyAdresListCnt(DlvyAdresVO searchVO) throws Exception;
	
	/**
	 * 배송지 저장
	 * @param dlvyAdres
	 * @throws Exception
	 */
	void insertDlvyAdres(DlvyAdresVO dlvyAdres) throws Exception;
	
	/**
	 * 배송지 상세
	 * @param dlvyAdres
	 * @return
	 * @throws Exception
	 */
	DlvyAdresVO selectDlvyAdres(DlvyAdresVO dlvyAdres) throws Exception;
	
	/**
	 * 배송지 수정
	 * @param dlvyAdres
	 * @throws Exception
	 */
	void updateDlvyAdres(DlvyAdresVO dlvyAdres) throws Exception;
	
	/**
	 * 배송지 삭제
	 * @param dlvyAdres
	 * @throws Exception
	 */
	void deleteDlvyAdres(DlvyAdresVO dlvyAdres) throws Exception;
	
	/**
	 * 최근사용 배송지 5개
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<DlvyAdresVO> selectRecentUseDlvyAdres(DlvyAdresVO searchVO) throws Exception;
	
	/**
	 * 배송지 사용시점 수정
	 * @param dlvyAdres
	 * @throws Exception
	 */
	void updateUseDlvyAdres(DlvyAdresVO dlvyAdres) throws Exception;
	
	/**
	 * 기본배송지 정보
	 * @param dlvyAdres
	 * @return
	 * @throws Exception
	 */
	DlvyAdresVO selectBassDlvyAdres(DlvyAdresVO dlvyAdres) throws Exception;
	
	/**
	 * 기본 배송지 수정
	 * @param searchVO
	 * @throws Exception
	 */

	void updateBassDlvyAdres(DlvyAdresVO searchVO) throws Exception;

}
