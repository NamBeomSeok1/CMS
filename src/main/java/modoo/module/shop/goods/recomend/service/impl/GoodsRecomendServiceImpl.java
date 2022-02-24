package modoo.module.shop.goods.recomend.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import modoo.module.shop.goods.recomend.service.GoodsRecomendService;
import modoo.module.shop.goods.recomend.service.GoodsRecomendVO;

@Service("goodsRecomendService")
public class GoodsRecomendServiceImpl extends EgovAbstractServiceImpl implements GoodsRecomendService {

	@Resource(name = "goodsRecomendMapper")
	private GoodsRecomendMapper goodsRecomendMapper;

	/**
	 * 추천상품 삭제
	 */
	@Override
	public void deleteGoodsRecomend(GoodsRecomendVO goodsRecomend) throws Exception {
		goodsRecomendMapper.deleteGoodsRecomend(goodsRecomend);
	}

}
