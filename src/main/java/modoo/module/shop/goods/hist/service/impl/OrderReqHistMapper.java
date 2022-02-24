package modoo.module.shop.goods.hist.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import modoo.module.shop.goods.hist.service.OrderReqHistVO;

@Mapper("orderReqHistMapper")
public interface OrderReqHistMapper {

	/**
	 * 주문 요청 이력 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectOrderReqList(OrderReqHistVO searchVO) throws Exception;
	
	/**
	 * 주문 요청 이력 목록 갯수
	 * @param searchVO
	 * @return
	 */
	int selectOrderReqListCnt(OrderReqHistVO searchVO);

	/**
	 * 주문 요청 이력 등록
	 * @param searchVO
	 */
	void insertOrderReqHist(OrderReqHistVO searchVO);
}
