package modoo.module.shop.cmpny.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import modoo.module.shop.cmpny.service.PrtnrCmpnyService;
import modoo.module.shop.cmpny.service.PrtnrCmpnyVO;

@Service("prtnrCmpnyService")
public class PrtnrCmpnyServiceImpl extends EgovAbstractServiceImpl implements PrtnrCmpnyService {
	
	@Resource(name = "prtnrCmpnyMapper")
	private PrtnrCmpnyMapper prtnrCmpnyMapper;


	/**
	 * 제휴사매핑 목록
	 */
	@Override
	public List<PrtnrCmpnyVO> selectPrtnrCmpnyList(PrtnrCmpnyVO prtnrCmpny) throws Exception {
		return prtnrCmpnyMapper.selectPrtnrCmpnyList(prtnrCmpny);
	}

}
