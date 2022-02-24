package modoo.module.faq.service.impl;

import java.util.List;

import modoo.module.faq.service.FaqinfoVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("faqinfoMapper")
public interface FaqinfoMapper {

	/**
	 * FAQ목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectFaqinfoList(FaqinfoVO searchVO) throws Exception;
	
	/**
	 * FAQ목록 카운트
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	int selectFaqinfoListCnt(FaqinfoVO searchVO) throws Exception;
	
	/**
	 * FAQ저장
	 * @param faqinfo
	 * @throws Exception
	 */
	void insertFaqinfo(FaqinfoVO faqinfo) throws Exception;
	
	/**
	 * FAQ상세
	 * @param faqinfo
	 * @return
	 * @throws Exception
	 */
	FaqinfoVO selectFaqinfo(FaqinfoVO faqinfo) throws Exception;
	
	/**
	 * FAQ수정
	 * @param faqinfo
	 * @throws Exception
	 */
	void updateFaqinfo(FaqinfoVO faqinfo) throws Exception;
	
	/**
	 * FAQ 조회수 업데이트
	 * @param faqinfo
	 * @throws Exception
	 */
	void updateFaqinfoRdcnt(FaqinfoVO faqinfo) throws Exception;
	
	/**
	 * FAQ삭제
	 * @param faqinfo
	 * @throws Exception
	 */
	void deleteFaqinfo(FaqinfoVO faqinfo) throws Exception;
}
