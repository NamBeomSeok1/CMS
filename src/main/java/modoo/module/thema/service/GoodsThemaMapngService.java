package modoo.module.thema.service;

import java.util.List;

import modoo.module.event.service.impl.GoodsEventMapngVO;
import modoo.module.thema.service.impl.GoodsThemaMapngVO;

public interface GoodsThemaMapngService {

	public void insertGoodsThemaMapng(GoodsThemaMapngVO goodsThemaMapngVO);

	public void deleteGoodsThemaMapngList(GoodsThemaMapngVO searchVO);

	public List<?> selectGoodsThemaMapngList(GoodsThemaMapngVO searchMapngVO);
	
	void registThemaGoods(GoodsThemaMapngVO searchVO, List<GoodsThemaMapngVO> goodsList);
}
