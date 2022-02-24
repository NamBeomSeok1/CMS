package modoo.module.card.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import modoo.module.card.service.CreditCardVO;

@Mapper("creditCardMapper")
public interface CreditCardMapper{

	/*
	 * 카드 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	
	List<CreditCardVO> selectCardList(CreditCardVO searchVO) throws Exception;
	
	/*
	*카드 개수
	*@param searchVO
	*@return Exception
	*/
	int selectCardListCnt(CreditCardVO searchVO) throws Exception;
	
	/*
	 *카드 상세
	 *@param searchVO
	 *@return
	 *@thorws Exception
	 */
	CreditCardVO selectCard(CreditCardVO searchVO) throws Exception;
	
	
	/*
	 * 카드 정보 저장
	 * @param card
	 * @return
	 * @throws Exception
	 */
	void insertCard(CreditCardVO card) throws Exception;

	/*
	 * 카드 정보 수정
	 * @param card
	 * @return
	 * @throws Exception
	 */
	void updateCard(CreditCardVO card) throws Exception;
	
	/*
	 * 카드 정보 삭제
	 * @param card
	 * @return
	 * @thrwos Exception
	 */
	void deleteCard(CreditCardVO card)throws Exception;

	
	/*
	 * 카드 기본설정 수정
	 * @param card
	 * @return
	 * @thrwos Exception
	 */
	void updateBassUseAt(CreditCardVO card)throws Exception;
	
	/*
	 * 주문 연동 카드 개수
	 * @param card
	 * @return
	 * @thrwos Exception
	 */
	List<EgovMap> selectOrderCardList(CreditCardVO card)throws Exception;
}
