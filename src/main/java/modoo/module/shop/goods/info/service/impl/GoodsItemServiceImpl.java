package modoo.module.shop.goods.info.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import modoo.module.shop.goods.info.service.GoodsItemService;
import modoo.module.shop.goods.info.service.GoodsItemVO;

@Service("goodsItemService")
public class GoodsItemServiceImpl extends EgovAbstractServiceImpl implements GoodsItemService {

	@Resource(name = "goodsItemMapper")
	private GoodsItemMapper goodsItemMapper;
	
	/**
	 * 상품항목 목록
	 */
	@Override
	public List<GoodsItemVO> selectGoodsItemList(GoodsItemVO searchVO) throws Exception {
		return goodsItemMapper.selectGoodsItemList(searchVO);
	}
	
	/**
	 * 상품항목 삭제
	 */
	@Override
	public void deleteGoodsItem(GoodsItemVO goodsItem) throws Exception {
		goodsItemMapper.deleteGoodsItem(goodsItem);
	}

}
