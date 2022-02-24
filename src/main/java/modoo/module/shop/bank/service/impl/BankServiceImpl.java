package modoo.module.shop.bank.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import modoo.module.shop.bank.service.BankService;
import modoo.module.shop.bank.service.BankVO;

@Service("bankService")
public class BankServiceImpl extends EgovAbstractServiceImpl implements BankService {

	@Resource(name = "bankMapper")
	private BankMapper bankMapper;
	
	@Resource(name = "bankIdGnrService")
	private EgovIdGnrService bankIdGnrService;
	
	/**
	 * 은행목록
	 */
	@Override
	public List<BankVO> selectBankList(BankVO searchVO) throws Exception {
		return bankMapper.selectBankList(searchVO);
	}

	/**
	 * 은행목록 카운트
	 */
	@Override
	public int selectBankListCnt(BankVO searchVO) throws Exception {
		return bankMapper.selectBankListCnt(searchVO);
	}

	/**
	 * 은행저장
	 */
	@Override
	public void insertBank(BankVO bank) throws Exception {
		String bankId = bankIdGnrService.getNextStringId();
		bank.setBankId(bankId);
		bankMapper.insertBank(bank);
	}

	/**
	 * 은행상세
	 */
	@Override
	public BankVO selectBank(BankVO bank) throws Exception {
		return bankMapper.selectBank(bank);
	}

	/**
	 * 은행수정
	 */
	@Override
	public void updateBank(BankVO bank) throws Exception {
		bankMapper.updateBank(bank);
	}

	/**
	 * 은행삭제
	 */
	@Override
	public void deleteBank(BankVO bank) throws Exception {
		bankMapper.deleteBank(bank);
	}

	/**
	 * 은행명 체크 카운트
	 */
	@Override
	public int selectBankNameCheckCnt(BankVO bank) throws Exception {
		return bankMapper.selectBankNameCheckCnt(bank);
	}

}
