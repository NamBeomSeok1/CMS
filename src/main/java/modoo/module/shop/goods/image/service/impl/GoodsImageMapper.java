package modoo.module.shop.goods.image.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import modoo.module.shop.goods.image.service.GoodsImageVO;
import modoo.module.shop.goods.info.service.GoodsVO;

@Mapper("goodsImageMapper")
public interface GoodsImageMapper {
	
	/**
	 * 상품 이미지 목록
	 * @param goods
	 * @return
	 * @throws Exception
	 */
	List<GoodsImageVO> selectGoodsImageList(GoodsImageVO goodsImage) throws Exception;
	
	/**
	 * 상품 이미지 저장
	 * @param goodsImage
	 * @throws Exception
	 */
	void insertGoodsImage(GoodsImageVO goodsImage) throws Exception;
	
	/**
	 * 상품 이미지 수정
	 * @param goodsImage
	 * @throws Exception
	 */
	void updateGoodsImage(GoodsImageVO goodsImage) throws Exception;
	
	/**
	 * 상품 이미지 삭제
	 * @param goodsImage
	 * @throws Exception
	 */
	void deleteGoodsImage(GoodsImageVO goodsImage) throws Exception;
	
}
