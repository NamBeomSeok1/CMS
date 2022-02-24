package modoo.module.mber.agre.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import modoo.module.mber.agre.service.MberAgreService;
import modoo.module.mber.agre.service.MberAgreVO;

@Service("mberAgreService")
public class MberAgreServiceImpl extends EgovAbstractServiceImpl implements MberAgreService {
	
	@Resource(name = "mberAgreMapper")
	private MberAgreMapper mberAgreMapper;

	/**
	 * 회원약관동의 저장
	 */
	@Override
	public void insertMberAgre(MberAgreVO mberAgre) throws Exception {
		mberAgreMapper.insertMberAgre(mberAgre);
	}

	/**
	 * 회원약관동의 수정
	 */
	@Override
	public void updateMberAgre(MberAgreVO mberAgre) throws Exception {
		mberAgreMapper.updateMberAgre(mberAgre);
	}

}
