package modoo.module.shop.goods.hist.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import modoo.module.shop.goods.hist.service.OrderReqHistService;
import modoo.module.shop.goods.hist.service.OrderReqHistVO;

@Service("orderReqHistService")
public class OrderReqHistServiceImpl extends EgovAbstractServiceImpl implements OrderReqHistService {

	@Resource(name = "orderReqHistMapper")
	private OrderReqHistMapper orderReqHistMapper;

	@Override
	public List<?> selectOrderReqList(OrderReqHistVO searchVO) throws Exception {
		return orderReqHistMapper.selectOrderReqList(searchVO);
	}

	@Override
	public int selectOrderReqListCnt(OrderReqHistVO searchVO) {
		return orderReqHistMapper.selectOrderReqListCnt(searchVO);
	}

	@Override
	public void insertOrderReqHist(OrderReqHistVO searchVO) {
		orderReqHistMapper.insertOrderReqHist(searchVO);
	}

}
