package modoo.module.mber.agre.service.impl;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import modoo.module.mber.agre.service.MberAgreVO;

@Mapper("mberAgreMapper")
public interface MberAgreMapper {

	/**
	 * 회원약관동의 저장
	 * @param mberAgre
	 * @throws Exception
	 */
	void insertMberAgre(MberAgreVO mberAgre) throws Exception;
	
	/**
	 * 회원약관동의 수정
	 * @param mberAgre
	 * @throws Exception
	 */
	void updateMberAgre(MberAgreVO mberAgre) throws Exception;
}
