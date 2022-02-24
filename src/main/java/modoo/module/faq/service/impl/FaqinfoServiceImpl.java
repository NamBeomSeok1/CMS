package modoo.module.faq.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import modoo.module.faq.service.FaqinfoService;
import modoo.module.faq.service.FaqinfoVO;
import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.fdl.idgnr.EgovIdGnrService;

@Service("faqinfoService")
public class FaqinfoServiceImpl extends EgovAbstractServiceImpl implements FaqinfoService {
	
	@Resource(name = "faqinfoMapper")
	private FaqinfoMapper faqinfoMapper;
	
	@Resource(name = "faqinfoIdGnrService")
	private EgovIdGnrService faqinfoIdGnrService;

	/**
	 * FAQ목록
	 */
	@Override
	public List<?> selectFaqinfoList(FaqinfoVO searchVO) throws Exception {
		return faqinfoMapper.selectFaqinfoList(searchVO);
	}

	/**
	 * FAQ목록 카운트
	 */
	@Override
	public int selectFaqinfoListCnt(FaqinfoVO searchVO) throws Exception {
		return faqinfoMapper.selectFaqinfoListCnt(searchVO);
	}

	/**
	 * FAQ저장
	 */
	@Override
	public void insertFaqinfo(FaqinfoVO faqinfo) throws Exception {
		String faqId = faqinfoIdGnrService.getNextStringId();
		faqinfo.setFaqId(faqId);
		faqinfoMapper.insertFaqinfo(faqinfo);
	}

	/**
	 * FAQ상세
	 */
	@Override
	public FaqinfoVO selectFaqinfo(FaqinfoVO faqinfo) throws Exception {
		return faqinfoMapper.selectFaqinfo(faqinfo);
	}

	/**
	 * FAQ수정
	 */
	@Override
	public void updateFaqinfo(FaqinfoVO faqinfo) throws Exception {
		faqinfoMapper.updateFaqinfo(faqinfo);
	}
	
	/**
	 * FAQ조회수 업데이트
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void updateFaqinfoRdcnt(FaqinfoVO faqinfo, HttpSession session) throws Exception {
		
		ArrayList<String> rdcntList = (ArrayList<String>)session.getAttribute("rdcnt");
		String faqId = faqinfo.getFaqId();
		if(rdcntList == null) {
			rdcntList = new ArrayList<String>();
			rdcntList.add(faqId);
			faqinfoMapper.updateFaqinfoRdcnt(faqinfo);
			session.setAttribute("rdcnt", rdcntList);
		}else {
			if(!rdcntList.contains(faqId)) {
				rdcntList.add(faqId);
				faqinfoMapper.updateFaqinfoRdcnt(faqinfo);
				session.setAttribute("rdcnt", rdcntList);
			}
		}
	}

	/**
	 * FAQ삭제
	 */
	@Override
	public void deleteFaqinfo(FaqinfoVO faqinfo) throws Exception {
		faqinfoMapper.deleteFaqinfo(faqinfo);
	}

	

}
