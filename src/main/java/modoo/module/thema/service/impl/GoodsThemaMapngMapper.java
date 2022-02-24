package modoo.module.thema.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("goodsThemaMapngMapper")
public interface GoodsThemaMapngMapper {

	public void insertGoodsThemaMapng(GoodsThemaMapngVO goodsThemaMapngVO);

	public void deleteGoodsThemaMapngList(GoodsThemaMapngVO searchVO);

	public List<?> selectGoodsThemaMapngList(GoodsThemaMapngVO searchMapngVO);
}
