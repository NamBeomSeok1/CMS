package modoo.module.shop.goods.keyword.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import modoo.module.shop.goods.info.service.GoodsVO;
import modoo.module.shop.goods.keyword.service.GoodsKeywordService;
import modoo.module.shop.goods.keyword.service.GoodsKeywordVO;

@Service("goodsKeywordService")
public class GoodsKeywordServiceImpl extends EgovAbstractServiceImpl implements GoodsKeywordService {
	
	@Resource(name = "goodsKeywordMapper")
	private GoodsKeywordMapper goodsKeywordMapper;

	/**
	 * 상품키워드 목록
	 */
	@Override
	public List<GoodsKeywordVO> selectGoodsKeywordList(GoodsVO goods) throws Exception {
		return goodsKeywordMapper.selectGoodsKeywordList(goods);
	}

	/**
	 * 상품키워드 저장
	 */
	@Override
	public void insertGoodsKeyword(GoodsKeywordVO goodsKeyword) throws Exception {
		goodsKeywordMapper.insertGoodsKeyword(goodsKeyword);
	}

	/**
	 * 상품키워드 삭제
	 */
	@Override
	public void deleteGoodsKeyword(GoodsKeywordVO goodsKeyword) throws Exception {
		goodsKeywordMapper.deleteGoodsKeyword(goodsKeyword);
	}

	/**
	 * 상품연관 키워드 목록
	 */
	@Override
	public List<?> selectRelationGoodsKeywordList(GoodsVO goods) throws Exception {
		return goodsKeywordMapper.selectRelationGoodsKeywordList(goods);
	}

}
