package modoo.module.shop.cmpny.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import modoo.module.shop.cmpny.service.PrtnrVO;

@Mapper("prtnrMapper")
public interface PrtnrMapper {

	/**
	 * 제휴사 목록
	 * @param prtnr
	 * @return
	 * @throws Exception
	 */
	List<PrtnrVO> selectPrtnrList(PrtnrVO prtnr) throws Exception;
}
