package modoo.module.shop.user.service.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;
import modoo.module.shop.user.service.DlvyAdresService;
import modoo.module.shop.user.service.DlvyAdresVO;

@Service("dlvyAdresService")
public class DlvyAdresServiceImpl extends EgovAbstractServiceImpl implements DlvyAdresService {
	
	@Resource(name = "dlvyAdresMapper")
	private DlvyAdresMapper dlvyAdresMapper;
	
	@Resource(name = "dlvyAdresIdGnrService")
	private EgovIdGnrService dlvyAdresIdGnrService;

	/**
	 * 배송지 목록
	 */
	@Override
	public List<DlvyAdresVO> selectDlvyAdresList(DlvyAdresVO searchVO) throws Exception {
		return dlvyAdresMapper.selectDlvyAdresList(searchVO);
	}

	/**
	 * 배송지 목록 카운트
	 */
	@Override
	public int selectDlvyAdresListCnt(DlvyAdresVO searchVO) throws Exception {
		return dlvyAdresMapper.selectDlvyAdresListCnt(searchVO);
	}

	/**
	 * 배송지 저장
	 */
	@Override
	public void insertDlvyAdres(DlvyAdresVO dlvyAdres) throws Exception {
		java.math.BigDecimal dadresNo = dlvyAdresIdGnrService.getNextBigDecimalId();
		dlvyAdres.setDadresNo(dadresNo);
		dlvyAdresMapper.insertDlvyAdres(dlvyAdres);
	}

	/**
	 * 배송지 상세
	 */
	@Override
	public DlvyAdresVO selectDlvyAdres(DlvyAdresVO dlvyAdres) throws Exception {
		DlvyAdresVO vo = dlvyAdresMapper.selectDlvyAdres(dlvyAdres);
		String[] telnoArr = vo.getTelno().split("-");
		if(telnoArr.length > 0) {
			vo.setTelno1(telnoArr[0]);
			if(telnoArr.length > 1) {
				vo.setTelno2(telnoArr[1]);
				if(telnoArr.length > 2) {
					vo.setTelno3(telnoArr[2]);
				}
			}
		}
		return vo;
	}

	/**
	 * 배송지 수정
	 */
	@Override
	public void updateDlvyAdres(DlvyAdresVO dlvyAdres) throws Exception {
		dlvyAdresMapper.updateDlvyAdres(dlvyAdres);
	}

	/**
	 * 배송지 삭제
	 */
	@Override
	public void deleteDlvyAdres(DlvyAdresVO dlvyAdres) throws Exception {
		dlvyAdresMapper.deleteDlvyAdres(dlvyAdres);
	}

	/**
	 * 최근사용 배송지 5개
	 */
	@Override
	public List<DlvyAdresVO> selectRecentUseDlvyAdres(DlvyAdresVO searchVO) throws Exception {
		return dlvyAdresMapper.selectRecentUseDlvyAdres(searchVO);
	}

	/**
	 * 배송지 사용시점 수정
	 */
	@Override
	public void updateUseDlvyAdres(DlvyAdresVO dlvyAdres) throws Exception {
		dlvyAdresMapper.updateUseDlvyAdres(dlvyAdres);
	}

	/**
	 * 기본배송지 정보
	 */
	@Override
	public DlvyAdresVO selectBassDlvyAdres(DlvyAdresVO dlvyAdres) throws Exception {
		return dlvyAdresMapper.selectBassDlvyAdres(dlvyAdres);
	}
	
	/**
	 * 기본 배송지 수정
	 */

	@Transactional
	@Override
	public void updateBassDlvyAdres(DlvyAdresVO searchVO) throws Exception {
		dlvyAdresMapper.updateRelisBassDlvyAdres(searchVO);
		dlvyAdresMapper.updateSetBassDlvyAdres(searchVO);
	}

}
