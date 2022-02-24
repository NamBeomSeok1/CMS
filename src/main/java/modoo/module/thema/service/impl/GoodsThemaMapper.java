package modoo.module.thema.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("goodsThemaMapper")
public interface GoodsThemaMapper {
	
	/**
	 * 테마목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	 List<GoodsThemaVO> selectGoodsThemaList(GoodsThemaVO searchVO) throws Exception;

	 /**
	  * 테마목록개수
	  * @param searchVO
	  * @return
	  * @throws Exception
	  */
	 int selectGoodsThemaListCnt(GoodsThemaVO searchVO) throws Exception;

	 /**
	  * 테마상세
	  * @param searchVO
	  * @return
	  * @throws Exception
	  */
	 GoodsThemaVO selectGoodsThema(GoodsThemaVO searchVO) throws Exception;
	 
	 /**
	  * 관리자 -> 테마다음순서
	  * @param searchVO
	  * @return
	  * @throws Exception
	  */
	 int selectNextThemaSn(GoodsThemaVO searchVO) throws Exception;

	 /**
	  * 관리자 -> 테마다음번호
	  * @param searchVO
	  * @return
	  * @throws Exception
	  */
	 int selectNextThemaNo() throws Exception;

	 /**
	  * 테마저장
	  * @param goodsThemaVO
	  * @throws Exception
	  */
	 void insertGoodsThema(GoodsThemaVO goodsThema) throws Exception;

	 /**
	  * 테마수정
	  * @param goodsThemaVO
	  * @throws Exception
	  */
	 void updateGoodsThema(GoodsThemaVO goodsThema) throws Exception;
	 
	 /**
	  * 테마삭제
	  * @param goodsThema
	  * @throws Exception
	  */
	 void deleteGoodsThema(GoodsThemaVO goodsThema) throws Exception;

	 /**
	 * 관리자 > 테마 이미지 삭제
	 * @param goodsEvent
	 */
	void deleteThemaImg(GoodsThemaVO goodsThema);
}
