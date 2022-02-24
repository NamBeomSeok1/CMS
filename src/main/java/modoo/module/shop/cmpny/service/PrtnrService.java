package modoo.module.shop.cmpny.service;

import java.util.List;

public interface PrtnrService {

	/**
	 * 제휴사 목록
	 * @param prtnr
	 * @return
	 * @throws Exception
	 */
	List<PrtnrVO> selectPrtnrList(PrtnrVO prtnr) throws Exception;
}
