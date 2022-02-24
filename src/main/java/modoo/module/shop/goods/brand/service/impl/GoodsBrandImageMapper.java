package modoo.module.shop.goods.brand.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import modoo.module.shop.goods.brand.service.GoodsBrandImageVO;

@Mapper("goodsBrandImageMapper")
public interface GoodsBrandImageMapper {

	/**
	 * 브랜드이미지 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<GoodsBrandImageVO> selectGoodsBrandImageList(GoodsBrandImageVO searchVO) throws Exception;
	
	/**
	 * 브랜드이미지 저장
	 * @param brandImage
	 * @throws Exception
	 */
	void insertGoodsBrandImage(GoodsBrandImageVO brandImage) throws Exception;
	
	/**
	 * 브랜드이미지 수정
	 * @param brandImage
	 * @throws Exception
	 */
	void updateGoodsBrandImage(GoodsBrandImageVO brandImage) throws Exception;
	
	/**
	 * 브랜드이미지 최대 순번 + 1
	 * @param brandImage
	 * @return
	 * @throws Exception
	 */
	int selectGoodsBrandImageMaxSn(GoodsBrandImageVO brandImage) throws Exception;
	
	/**
	 * 브랜드이미지 삭제
	 * @param brandImage
	 * @throws Exception
	 */
	void deleteGoodsBrandImage(GoodsBrandImageVO brandImage) throws Exception;
	
	/**
	 * 브랜드ID에 속한 브랜드이미지 삭제
	 * @param brandImage
	 * @throws Exception
	 */
	void deleteGoodsBrandImageBrandId(GoodsBrandImageVO brandImage) throws Exception;
	
	/**
	 * 브랜드이미지 소속업체ID
	 * @param brandImage
	 * @return
	 * @throws Exception
	 */
	String selectCheckGoodsBrandCmpnyId(GoodsBrandImageVO brandImage) throws Exception;
	
	/**
	 * 브랜드이미지 목록 삭제
	 * @param brandImage
	 * @throws Exception
	 */
	void deleteGoodsBrandImageList(GoodsBrandImageVO brandImage) throws Exception;
}
