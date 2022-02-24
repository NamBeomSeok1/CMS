package modoo.module.shop.cmpny.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import modoo.module.shop.cmpny.service.PrtnrCmpnyVO;

@Mapper("prtnrCmpnyMapper")
public interface PrtnrCmpnyMapper {
	
	/**
	 * 제휴사매핑 목록
	 * @param prtnrCmpny
	 * @return
	 * @throws Exception
	 */
	List<PrtnrCmpnyVO> selectPrtnrCmpnyList(PrtnrCmpnyVO prtnrCmpny) throws Exception;
	
	/**
	 * 재휴사매핑 체크 카운트
	 * @param prtnrCmpny
	 * @return
	 */
	int selectPrtnrCmpnyCheckCnt(PrtnrCmpnyVO prtnrCmpny) throws Exception;
	
	/**
	 * 제휴사매핑 저장
	 * @param prtnrCmpny
	 */
	void insertPrtnrCmpny(PrtnrCmpnyVO prtnrCmpny) throws Exception;
	
	/**
	 * 제휴사매핑 수정
	 * @param prtnrCmpny
	 * @throws Exception
	 */
	void updatePrtnrCmpny(PrtnrCmpnyVO prtnrCmpny) throws Exception;
	
	/**
	 * 제휴사매핑 삭제
	 * @param prtnrCmpny
	 */
	void deletePrtnrCmpny(PrtnrCmpnyVO prtnrCmpny) throws Exception;

	
}
