package modoo.module.sale.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("saleMapper")
public interface SaleMapper {

	void deleteSaleGoods(SaleVO searchVO);

	void insertSaleGoods(SaleVO saleGoods);

	List<?> selectSaleGoodsList(SaleVO searchVO);

}
