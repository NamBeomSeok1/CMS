package modoo.module.shop.goods.brand.service;

public interface GoodsBrandImageService {

	/**
	 * 브랜드이미지 삭제
	 * @param brandImage
	 * @throws Exception
	 */
	void deleteGoodsBrandImage(GoodsBrandImageVO brandImage) throws Exception;
	
	/**
	 * 브랜드이미지 소속업체ID
	 * @param brandImage
	 * @return
	 * @throws Exception
	 */
	String selectCheckGoodsBrandCmpnyId(GoodsBrandImageVO brandImage) throws Exception;
}
