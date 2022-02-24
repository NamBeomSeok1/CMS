package modoo.module.shop.cmpny.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import modoo.module.mber.info.service.MberVO;
import modoo.module.shop.cmpny.service.CmpnyVO;
import modoo.module.shop.hdry.service.HdryCmpnyVO;

@Mapper("cmpnyMapper")
public interface CmpnyMapper {

	/**
	 * 업체목록 
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectCmpnyList(CmpnyVO searchVO) throws Exception;
	
	/**
	 * 업체목록 카운트
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	int selectCmpnyListCnt(CmpnyVO searchVO) throws Exception;
	
	/**
	 * 업체전체 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectCmpnyAllList(CmpnyVO searchVO) throws Exception;
	
	/**
	 * 업체목록저장
	 * @param cmpny
	 * @throws Exception
	 */
	void insertCmpny(CmpnyVO cmpny) throws Exception;
	
	/**
	 * 업체상세
	 * @param cmpny
	 * @return
	 * @throws Exception
	 */
	CmpnyVO selectCmpny(CmpnyVO cmpny) throws Exception;
	
	/**
	 * 업체수정
	 * @param cmpny
	 * @throws Exception
	 */
	void updateCmpny(CmpnyVO cmpny) throws Exception;
	
	/**
	 * 업체삭제
	 * @param cmpny
	 * @throws Exception
	 */
	void deleteCmpny(CmpnyVO cmpny) throws Exception;
	
	/**
	 * 사업자등록 조회 카운트
	 * @param bizrno
	 * @return
	 * @throws Exception
	 */
	int selectCmpnyBizrnoCheckCnt(CmpnyVO cmpny) throws Exception;
	
	/**
	 * 업체 사용자 검색
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectCmpnyMberList(MberVO searchVO) throws Exception;
	
	/**
	 * 업체 사용자 목록 카운트
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	int selectCmpnyMberListCnt(MberVO searchVO) throws Exception;
	
	/**
	 * 업체 사용자 패스워드 수정
	 * @param cmpny
	 * @throws Exception
	 */
	void updateCmpnyMberPassword(CmpnyVO cmpny) throws Exception;
	
	/**
	 * 업체 택배사 목록
	 * @param cmpny
	 * @return
	 * @throws Exception
	 */
	List<HdryCmpnyVO> selectHdryCmpnyMapngList(CmpnyVO cmpny) throws Exception;
	
	/**
	 * 업체택배매핑 저장
	 * @param cmpny
	 * @throws Exception
	 */
	void insertHdryCmpnyMapng(CmpnyVO cmpny) throws Exception;
	
	/**
	 * 업체택배매핑 삭제
	 * @param cmpny
	 * @throws Exception
	 */
	void deleteHdryCmpnyMapng(CmpnyVO cmpny) throws Exception;
	
	/**
	 * 업체상태코드 수정
	 * @param cmpny
	 * @throws Exception
	 */
	void updateHdryCmpnySttusCode(CmpnyVO cmpny) throws Exception;

	/**
	 * 주문상품 업체 번호
	 * @param egovMap
	 * @throws Exception
	 */
	CmpnyVO selectOrderCmpnyTelno(EgovMap map) throws Exception;
	/**
	 * 공지사항 알림톡 발송할 CP 담당자 전화번호 목록
	 * @return
	 */
	List<CmpnyVO> selectContactList();
}
