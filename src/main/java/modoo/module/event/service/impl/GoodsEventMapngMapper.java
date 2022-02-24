package modoo.module.event.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("goodsEventMapngMapper")
public interface GoodsEventMapngMapper {

	public void insertGoodsEventMapng(GoodsEventMapngVO goodsEventMapngVO);

	public void deleteGoodsEventMapngList(GoodsEventMapngVO searchVO);

	public List<?> selectGoodsEventMapngList(GoodsEventMapngVO searchMapngVO);
}
