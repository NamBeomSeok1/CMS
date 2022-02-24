package modoo.module.shop.goods.info.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import modoo.module.shop.goods.info.service.GoodsItemVO;
import modoo.module.shop.goods.info.service.GoodsVO;

@Mapper("goodsItemMapper")
public interface GoodsItemMapper {
	
	/**
	 * 상품항목 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<GoodsItemVO> selectGoodsItemList(GoodsItemVO searchVO) throws Exception;

	/**
	 * 상품항목 상세
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	GoodsItemVO selectGoodsItem(GoodsItemVO searchVO) throws Exception;
	
	/**
	 * 상품항목 저장
	 * @param goodsItem
	 * @throws Exception
	 */
	void insertGoodsItem(GoodsItemVO goodsItem) throws Exception;
	
	/**
	 * 상품항목 수정
	 * @param goodsItem
	 * @throws Exception
	 */
	void updateGoodsItem(GoodsItemVO goodsItem) throws Exception;
	
	/**
	 * 상품항목 삭제
	 * @param goodsItem
	 * @throws Exception
	 */
	void deleteGoodsItem(GoodsItemVO goodsItem) throws Exception;

	/**
	 * 상품항목 목록 삭제
	 * @param goodsItem
	 * @throws Exception
	 */
	void deleteGoodsItemList(GoodsItemVO goodsItem) throws Exception;
}
