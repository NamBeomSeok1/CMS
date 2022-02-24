package modoo.module.qainfo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import modoo.module.qainfo.service.QainfoService;
import modoo.module.qainfo.service.QainfoVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Service("qainfoService")
public class QainfoServiceImpl extends EgovAbstractServiceImpl implements QainfoService {
	
	@Resource(name = "qainfoMapper")
	private QainfoMapper qainfoMapper;
	
	@Resource(name = "qainfoIdGnrService")
	private EgovIdGnrService qainfoIdGnrService;

	/**
	 * 질답목록
	 */
	@Override
	public List<?> selectQainfoList(QainfoVO searchVO) throws Exception {
		return qainfoMapper.selectQainfoList(searchVO);
	}

	/**
	 * 이미지목록
	 */
	@Override
	public List<?> selectImageList(QainfoVO searchVO) throws Exception {
		return qainfoMapper.selectImageList(searchVO);
	}
	
	/**
	 * 질답목록 카운트
	 */
	@Override
	public int selectQainfoListCnt(QainfoVO searchVO) throws Exception {
		return qainfoMapper.selectQainfoListCnt(searchVO);
	}

	/**
	 * 질답 저장
	 */
	@Override
	public void insertQainfo(QainfoVO qainfo) throws Exception {
		String qaId = qainfoIdGnrService.getNextStringId();
		qainfo.setQaId(qaId);
		qainfoMapper.insertQainfo(qainfo);

	}

	/**
	 * 질답 상세
	 */
	@Override
	public QainfoVO selectQainfo(QainfoVO qainfo) throws Exception {
		return qainfoMapper.selectQainfo(qainfo);
	}

	/**
	 * 질답 수정
	 */
	@Override
	public void updateQainfo(QainfoVO qainfo) throws Exception {
		qainfoMapper.updateQainfo(qainfo);
	}
	
	/**
	 * 질답 수정
	 */
	@Override
	public void updateQainfoQestn(QainfoVO qainfo) throws Exception {
		qainfoMapper.updateQainfoQestn(qainfo);
	}

	/**
	 * 답변 수정
	 */
	@Override
	public void updateQainfoAnswer(QainfoVO qainfo) throws Exception {
		qainfoMapper.updateQainfoAnswer(qainfo);
	}
	
	/**
	 * 질답 삭제
	 */
	@Override
	public void deleteQainfo(QainfoVO qainfo) throws Exception {
		qainfoMapper.deleteQainfo(qainfo);
	}

	/**
	 * CP > 교환, 반품 > 교환사유, 반품사유
	 * @return 
	 */
	@Override
	public QainfoVO selectReason(QainfoVO qaInfo) {
		return qainfoMapper.selectReason(qaInfo);
	}
	
	/**
	 * 교환/환불 > 상세정보 > 반품사유
	 */
	@Override
	public QainfoVO selectRefundInfo(QainfoVO qainfo) {
		return qainfoMapper.selectRefundInfo(qainfo);
	}

}
