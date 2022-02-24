package modoo.module.shop.cmpny.service;

import java.util.List;

public interface PrtnrCmpnyService {

	/**
	 * 제휴사매핑 목록
	 * @param prtnrCmpny
	 * @return
	 * @throws Exception
	 */
	List<PrtnrCmpnyVO> selectPrtnrCmpnyList(PrtnrCmpnyVO prtnrCmpny) throws Exception;
}
