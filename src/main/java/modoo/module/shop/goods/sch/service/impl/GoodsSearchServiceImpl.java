package modoo.module.shop.goods.sch.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import modoo.module.shop.goods.sch.service.GoodsSearchService;
import modoo.module.shop.goods.sch.service.GoodsSearchVO;

@Service("goodsSearchService")
public class GoodsSearchServiceImpl extends EgovAbstractServiceImpl implements GoodsSearchService {
	
	@Resource(name = "goodsSearchMapper")
	private GoodsSearchMapper goodsSearchMapper;

	/**
	 * 상품검색 목록
	 */
	@Override
	public List<GoodsSearchVO> selectGoodsSearchList(GoodsSearchVO searchVO) throws Exception {
		return goodsSearchMapper.selectGoodsSearchList(searchVO);
	}

	/**
	 * 상품검색 저장
	 */
	@Override
	public void insertGoodsSearch(GoodsSearchVO goodsSearch) throws Exception {
		goodsSearchMapper.insertGoodsSearch(goodsSearch);
	}

	/**
	 * 사용자 검색어 목록
	 */
	@Override
	public List<GoodsSearchVO> selectUserGoodsSearchWrdList(GoodsSearchVO searchVO) throws Exception {
		return goodsSearchMapper.selectUserGoodsSearchWrdList(searchVO);
	}

	/**
	 * 사용자 검색어 기록 삭제
	 */
	@Override
	public void deleteUserGoodsSearchList(GoodsSearchVO searchVO) throws Exception {
		goodsSearchMapper.deleteUserGoodsSearchList(searchVO);
	}

	/**
	 * 인기 검색어 카운트 목록
	 */
	@Override
	public List<GoodsSearchVO> selectHotGoodsSearchWrdList(int day) throws Exception {
		return goodsSearchMapper.selectHotGoodsSearchWrdList(day);
	}
	
	

}
