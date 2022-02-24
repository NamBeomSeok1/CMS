package modoo.module.sale.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import modoo.module.best.service.BestService;
import modoo.module.best.service.impl.BestVO;
import modoo.module.sale.service.SaleService;

@Service("saleService")
public class SaleServiceImpl implements SaleService {
	
	@Resource(name = "saleMapper")
	private SaleMapper saleMapper;

	@Transactional
	@Override
	public void saveSaleGoods(SaleVO searchVO, List<SaleVO> saleList) {
		saleMapper.deleteSaleGoods(searchVO);
		for (SaleVO saleGoods : saleList) {
			saleMapper.insertSaleGoods(saleGoods);
		}
	}

	@Override
	public List<?> selectSaleGoodsList(SaleVO searchVO) {
		return saleMapper.selectSaleGoodsList(searchVO);
	}

}
