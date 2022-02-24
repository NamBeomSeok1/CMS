package modoo.module.shop.goods.hist.service;

import java.util.List;

public interface OrderReqHistService {

	List<?> selectOrderReqList(OrderReqHistVO searchVO) throws Exception;

	int selectOrderReqListCnt(OrderReqHistVO searchVO);

	void insertOrderReqHist(OrderReqHistVO searchVO);

}
