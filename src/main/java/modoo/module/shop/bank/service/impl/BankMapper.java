package modoo.module.shop.bank.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import modoo.module.shop.bank.service.BankVO;

@Mapper("bankMapper")
public interface BankMapper {
	
	/**
	 * 은행목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<BankVO> selectBankList(BankVO searchVO) throws Exception;
	
	/**
	 * 은행목록 카운트
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	int selectBankListCnt(BankVO searchVO) throws Exception;
	
	/**
	 * 은행저장
	 * @param bank
	 * @throws Exception
	 */
	void insertBank(BankVO bank) throws Exception;
	
	/**
	 * 은행상세
	 * @param bank
	 * @return
	 * @throws Exception
	 */
	BankVO selectBank(BankVO bank) throws Exception;
	
	/**
	 * 은행수정
	 * @param bank
	 * @throws Exception
	 */
	void updateBank(BankVO bank) throws Exception;
	
	/**
	 * 은행삭제
	 * @param bank
	 * @throws Exception
	 */
	void deleteBank(BankVO bank) throws Exception;
	
	/**
	 * 은행명 카운트
	 * @param bank
	 * @return
	 * @throws Exception
	 */
	int selectBankNameCheckCnt(BankVO bank) throws Exception;
}
