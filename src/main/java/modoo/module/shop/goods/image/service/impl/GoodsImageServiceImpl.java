package modoo.module.shop.goods.image.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import modoo.module.shop.goods.image.service.GoodsImageService;
import modoo.module.shop.goods.image.service.GoodsImageVO;

@Service("goodsImageService")
public class GoodsImageServiceImpl extends EgovAbstractServiceImpl implements GoodsImageService {
	
	@Resource(name = "goodsImageMapper")
	private GoodsImageMapper goodsImageMapper;

	/**
	 * 상품이미지 삭제 (설명, 대표, 모바일..)
	 */
	@Override
	public void deleteGoodsImage(GoodsImageVO goodsImage) throws Exception {
		goodsImageMapper.deleteGoodsImage(goodsImage);

	}

}
