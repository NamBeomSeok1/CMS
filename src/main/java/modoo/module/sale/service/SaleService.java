package modoo.module.sale.service;

import java.util.List;

import modoo.module.best.service.impl.BestVO;
import modoo.module.sale.service.impl.SaleVO;

public interface SaleService {

	void saveSaleGoods(SaleVO searchVO, List<SaleVO> bestList);

	List<?> selectSaleGoodsList(SaleVO searchVO);

}
