package modoo.module.shop.goods.setle.service;

import java.util.HashMap;
import java.util.List;

public interface OrderSetleService {
	
	/**
	 * 결제 목록
	 * @param searchVO
	 * @return
	 */
	List<?> selectOrderSetleList(OrderSetleVO searchVO);
	
	/**
	 * 결제 목록 갯수
	 * @param searchVO
	 * @return
	 */
	int selectOrderSetleListCnt(OrderSetleVO searchVO);

	/**
	 * 결제 등록
	 * @param orderSetle
	 * @return
	 */
	void insertOrderSetle(OrderSetleVO orderSetle);
	
	
	/**
	 * 결제 내역 수정
	 * @param orderSetle
	 */
	void updateOrderSetle(OrderSetleVO orderSetle);


	/**
	 * 결제 프로세스
	 * @param billingInfo
	 * @return
	 * @throws Exception
	 */
	HashMap<String, Object> billingProcess(HashMap<String, Object> billingMap) throws Exception;
	
	/**
	 * 결제 배송지 등록 
	 * @param map
	 * @return
	 */
	void insertNextSTN_ORDER_DLVY(HashMap<String, Object> map);
}
