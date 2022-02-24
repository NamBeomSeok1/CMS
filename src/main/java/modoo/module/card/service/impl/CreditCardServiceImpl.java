package modoo.module.card.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import modoo.module.card.service.CreditCardVO;
import modoo.module.biling.service.Encryption;
import modoo.module.card.service.CreditCardService;

@Service("creditCardService")
public class CreditCardServiceImpl implements CreditCardService {
	
	@Resource(name="creditCardMapper")
	private CreditCardMapper creditCardMapper;
	
	@Resource(name="cardIdGnrService")
	private EgovIdGnrService cardIdGnrService;

	
	/*
	 * 카드 목록
	 */
	@Override
	public List<CreditCardVO> selectCardList(CreditCardVO searchVO) throws Exception {
		return creditCardMapper.selectCardList(searchVO);
	}
	/*
	 * 카드 목록 카운트
	 */
	@Override
	public int selectCardListCnt(CreditCardVO searchVO) throws Exception {
		return creditCardMapper.selectCardListCnt(searchVO);
	}
	/*
	 * 카드 상세
	 */
	@Override
	public CreditCardVO selectCard(CreditCardVO searchVO) throws Exception {
		return creditCardMapper.selectCard(searchVO);
	}
	/*
	 * 카드 저장
	 */
	@Override
	public void insertCard(CreditCardVO card) throws Exception {
		String cardId = cardIdGnrService.getNextStringId();
		card.setCardId(cardId);
		creditCardMapper.insertCard(card);
	}
	/*
	 * 카드 수정
	 */
	@Override
	public void updateCard(CreditCardVO card) throws Exception {
		creditCardMapper.updateCard(card);
	}
	/*
	 * 카드 삭제
	 */
	@Override
	public void deleteCard(CreditCardVO card) throws Exception {
		creditCardMapper.deleteCard(card);
	}
	/*
	 * 카드 기본설정 수정
	 */
	@Override
	public void updateBassUseAt(CreditCardVO card) throws Exception {
		creditCardMapper.updateBassUseAt(card);
	}

	/*
	 *주문 연동 카드 개수 
	 */
	@Override
	public List<EgovMap> selectOrderCardList(CreditCardVO card) throws Exception {
		return creditCardMapper.selectOrderCardList(card);
	}
}
