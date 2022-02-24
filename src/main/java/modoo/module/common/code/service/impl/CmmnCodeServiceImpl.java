package modoo.module.common.code.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import modoo.module.common.code.service.CmmnCodeService;
import modoo.module.common.code.service.CmmnCodeVO;
import modoo.module.common.code.service.CmmnDetailCodeVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;

@Service("cmmnCodeService")
public class CmmnCodeServiceImpl extends EgovAbstractServiceImpl implements CmmnCodeService {
	
	@Resource(name = "cmmnCodeMapper")
	private CmmnCodeMapper cmmnCodeMapper;

	/**
	 * 공통코드 목록
	 */
	@Override
	public List<CmmnCodeVO> selectCmmnCodeList(CmmnCodeVO searchVO) throws Exception {
		return cmmnCodeMapper.selectCmmnCodeList(searchVO);
	}

	/**
	 * 공통코드 목록 카운트
	 */
	@Override
	public int selectCmmnCodeListCnt(CmmnCodeVO searchVO) throws Exception {
		return cmmnCodeMapper.selectCmmnCodeListCnt(searchVO);
	}

	/**
	 * 공통코드 저장
	 */
	@Override
	public void insertCmmnCode(CmmnCodeVO code) throws Exception {
		cmmnCodeMapper.insertCmmnCode(code);
	}

	/**
	 * 다음 공통코드ID
	 */
	@Override
	public String getNextCmmnCodeId(CmmnCodeVO code) throws Exception {
		return cmmnCodeMapper.getNextCmmnCodeId(code);
	}
	
	/**
	 * 공통코드ID 체크카운트
	 */
	@Override
	public int selectCheckCmmnCodeId(CmmnCodeVO code) throws Exception {
		return cmmnCodeMapper.selectCheckCmmnCodeId(code);
	}
	
	/**
	 * 공통코드상세
	 */
	@Override
	public CmmnCodeVO selectCmmnCode(CmmnCodeVO code) throws Exception {
		return cmmnCodeMapper.selectCmmnCode(code);
	}
	
	/**
	 * 공통코드 수정
	 */
	@Override
	public void updateCmmnCode(CmmnCodeVO code) throws Exception {
		cmmnCodeMapper.updateCmmnCode(code);
	}
	
	/**
	 * 공통코드 삭제
	 */
	@Override
	public void deleteCmmnCode(CmmnCodeVO code) throws Exception {
		cmmnCodeMapper.deleteCmmnCode(code);
	}
	
	/**
	 * 공통코드상세 확인 카운트
	 */
	@Override
	public int selectCmmnDetailCodeCheckCnt(CmmnCodeVO code) throws Exception {
		return cmmnCodeMapper.selectCmmnDetailCodeCheckCnt(code);
	}
	
	/**
	 * 공통코드상세 목록
	 */
	@Override
	public List<CmmnDetailCodeVO> selectCmmnDetailCodeList(CmmnDetailCodeVO searchVO) throws Exception {
		return cmmnCodeMapper.selectCmmnDetailCodeList(searchVO);
	}
	/**
	 * 공통코드상세순서 최대값
	 */
	@Override
	public int selectCmmnDetailCodeSnMaxCnt(CmmnDetailCodeVO searchVO) throws Exception {
		return cmmnCodeMapper.selectCmmnDetailCodeSnMaxCnt(searchVO);
	}
	
	/**
	 * 공통코드상세 목록 카운트
	 */
	@Override
	public int selectCmmnDetailCodeListCnt(CmmnDetailCodeVO searchVO) throws Exception {
		return cmmnCodeMapper.selectCmmnCodeListCnt(searchVO);
	}
	
	/**
	 * 공통코드상세 체크카운트
	 */
	@Override
	public int selectCheckCmmnCodeDetailCnt(CmmnDetailCodeVO codeDetail) throws Exception {
		return cmmnCodeMapper.selectCheckCmmnCodeDetailCnt(codeDetail);
	}

	/**
	 * 공통코드상세 저장
	 */
	@Override
	public void insertCmmnCodeDetail(CmmnDetailCodeVO codeDetail) throws Exception {
		cmmnCodeMapper.insertCmmnCodeDetail(codeDetail);
	}

	/**
	 * 공통코드상세
	 */
	@Override
	public CmmnDetailCodeVO selectCmmnCodeDetail(CmmnDetailCodeVO codeDetail) throws Exception {
		return cmmnCodeMapper.selectCmmnCodeDetail(codeDetail);
	}

	/**
	 * 공통코드상세 수정
	 */
	@Override
	public void updateCmmnCodeDetail(CmmnDetailCodeVO codeDetail) throws Exception {
		cmmnCodeMapper.updateCmmnCodeDetail(codeDetail);
	}

	/**
	 * 공통코드상세 삭제
	 */
	@Override
	public void deleteCmmnCodeDetail(CmmnDetailCodeVO codeDetail) throws Exception {
		cmmnCodeMapper.deleteCmmnCodeDetail(codeDetail);
	}

}
