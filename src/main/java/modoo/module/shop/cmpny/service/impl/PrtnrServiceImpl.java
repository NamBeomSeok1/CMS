package modoo.module.shop.cmpny.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import modoo.module.shop.cmpny.service.PrtnrService;
import modoo.module.shop.cmpny.service.PrtnrVO;

@Service("prtnrService")
public class PrtnrServiceImpl extends EgovAbstractServiceImpl implements PrtnrService {

	@Resource(name = "prtnrMapper")
	private PrtnrMapper prtnrMapper;
	
	/**
	 * 제휴사 목록
	 */
	@Override
	public List<PrtnrVO> selectPrtnrList(PrtnrVO prtnr) throws Exception {
		return prtnrMapper.selectPrtnrList(prtnr);
	}

}
