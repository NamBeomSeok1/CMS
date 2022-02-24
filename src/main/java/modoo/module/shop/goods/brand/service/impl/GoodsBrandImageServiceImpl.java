package modoo.module.shop.goods.brand.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import modoo.module.shop.goods.brand.service.GoodsBrandImageService;
import modoo.module.shop.goods.brand.service.GoodsBrandImageVO;

@Service("goodsBrandImageService")
public class GoodsBrandImageServiceImpl extends EgovAbstractServiceImpl implements GoodsBrandImageService {
	
	@Resource(name = "goodsBrandImageMapper")
	private GoodsBrandImageMapper goodsBrandImageMapper;

	/**
	 * 브랜드이미지 삭제
	 */
	@Override
	public void deleteGoodsBrandImage(GoodsBrandImageVO brandImage) throws Exception {
		goodsBrandImageMapper.deleteGoodsBrandImage(brandImage);
	}

	/**
	 * 브랜드이미지 소속업체ID
	 */
	@Override
	public String selectCheckGoodsBrandCmpnyId(GoodsBrandImageVO brandImage) throws Exception {
		return goodsBrandImageMapper.selectCheckGoodsBrandCmpnyId(brandImage);
	}

}
