package modoo.module.thema.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import modoo.module.thema.service.GoodsThemaMapngService;

@Service("goodsThemaMapngService")
public class GoodsThemaMapngServiceImpl implements GoodsThemaMapngService {

	@Resource(name="goodsThemaMapngMapper")
	GoodsThemaMapngMapper goodsThemaMapngMapper;

	@Override
	public void insertGoodsThemaMapng(GoodsThemaMapngVO goodsThemaMapngVO) {
		goodsThemaMapngMapper.insertGoodsThemaMapng(goodsThemaMapngVO);
	}

	@Override
	public void deleteGoodsThemaMapngList(GoodsThemaMapngVO searchVO) {
		goodsThemaMapngMapper.deleteGoodsThemaMapngList(searchVO);
		
	}

	@Override
	public List<?> selectGoodsThemaMapngList(GoodsThemaMapngVO searchMapngVO) {
		return goodsThemaMapngMapper.selectGoodsThemaMapngList(searchMapngVO);
	}

	@Transactional
	@Override
	public void registThemaGoods(GoodsThemaMapngVO searchVO, List<GoodsThemaMapngVO> goodsList) {
		goodsThemaMapngMapper.deleteGoodsThemaMapngList(searchVO);
		for (GoodsThemaMapngVO goodsThemaMapngVO : goodsList) {
			goodsThemaMapngMapper.insertGoodsThemaMapng(goodsThemaMapngVO);
		}
	}
	
	

}
