package modoo.module.event.service;

import java.util.List;

import modoo.module.event.service.impl.GoodsEventMapngVO;

public interface GoodsEventMapngService {

	void insertGoodsEventMapng(GoodsEventMapngVO goodsEventMapngVO);

	void deleteGoodsEventMapngList(GoodsEventMapngVO searchVO);

	List<?> selectGoodsEventMapngList(GoodsEventMapngVO searchMapngVO);

	void registEventGoods(GoodsEventMapngVO searchVO, List<GoodsEventMapngVO> goodsList);

}
