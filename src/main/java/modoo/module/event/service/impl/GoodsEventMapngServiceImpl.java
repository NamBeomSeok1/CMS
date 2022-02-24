package modoo.module.event.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import modoo.module.event.service.GoodsEventMapngService;

@Service("goodsEventMapngService")
public class GoodsEventMapngServiceImpl implements GoodsEventMapngService {

	@Resource(name="goodsEventMapngMapper")
	GoodsEventMapngMapper goodsEventMapngMapper;

	@Override
	public void insertGoodsEventMapng(GoodsEventMapngVO goodsEventMapngVO) {
		goodsEventMapngMapper.insertGoodsEventMapng(goodsEventMapngVO);
	}

	@Override
	public void deleteGoodsEventMapngList(GoodsEventMapngVO searchVO) {
		goodsEventMapngMapper.deleteGoodsEventMapngList(searchVO);
		
	}

	@Override
	public List<?> selectGoodsEventMapngList(GoodsEventMapngVO searchMapngVO) {
		return goodsEventMapngMapper.selectGoodsEventMapngList(searchMapngVO);
	}

	@Transactional
	@Override
	public void registEventGoods(GoodsEventMapngVO searchVO, List<GoodsEventMapngVO> goodsList) {
		goodsEventMapngMapper.deleteGoodsEventMapngList(searchVO);
		for (GoodsEventMapngVO goodsEventMapngVO : goodsList) {
			goodsEventMapngMapper.insertGoodsEventMapng(goodsEventMapngVO);
		}
		
	}

}
