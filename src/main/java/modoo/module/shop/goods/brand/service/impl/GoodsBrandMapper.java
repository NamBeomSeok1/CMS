package modoo.module.shop.goods.brand.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import modoo.module.shop.goods.brand.service.GoodsBrandVO;

@Mapper("goodsBrandMapper")
public interface GoodsBrandMapper {
	
	/**
	 * 상품브랜드 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<GoodsBrandVO> selectGoodsBrandList(GoodsBrandVO searchVO) throws Exception;
	
	/**
	 * 상품브랜드 목록 카운트
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	int selectGoodsBrandListCnt(GoodsBrandVO searchVO) throws Exception;
	
	/**
	 * 상품브랜드 저장
	 * @param goodsBrand
	 * @throws Exception
	 */
	void insertGoodsBrand(GoodsBrandVO goodsBrand) throws Exception;
	
	/**
	 * 상품브랜드 상세
	 * @param goodsGrand
	 * @return
	 * @throws Exception
	 */
	GoodsBrandVO selectGoodsBrand(GoodsBrandVO goodsBrand) throws Exception;
	
	/**
	 * 상품브랜드 수정
	 * @param goodsBrand
	 * @throws Exception
	 */
	void updateGoodsBrand(GoodsBrandVO goodsBrand) throws Exception;
	
	/**
	 * 상품 브랜드ID NULL 처리 ( 삭제 시 처리함)
	 * @param goodsBrand
	 * @throws Exception
	 */
	void updateGoodsBrandIdNull(GoodsBrandVO goodsBrand) throws Exception;
	
	/**
	 * 상품브랜드 삭제
	 * @param goodsBrand
	 * @throws Exception
	 */
	void deleteGoodsBrand(GoodsBrandVO goodsBrand) throws Exception;
	
	/**
	 * 상품브랜드 메뉴 목록
	 * @param goodsBrand
	 * @return
	 * @throws Exception
	 */
	List<GoodsBrandVO> selectGoodsBrandMenuList() throws Exception;
	/**
	 * 브랜드관 목록 조회 (ㄱ~ㅎ, A~Z)
	 * @param searchVO
	 * @return
	 */
	List<GoodsBrandVO> selectGoodsBrandByChar(GoodsBrandVO searchVO);

}
