package modoo.module.qainfo.service.impl;

import java.util.List;

import modoo.module.qainfo.service.QainfoVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("qainfoMapper")
public interface QainfoMapper {

	/**
	 * 질답목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectQainfoList(QainfoVO searchVO) throws Exception;
	
	/**
	 * 이미지목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectImageList(QainfoVO searchVO) throws Exception;
	
	/**
	 * 질답목록 카운트
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	int selectQainfoListCnt(QainfoVO searchVO) throws Exception;
	
	/**
	 * 질답저장
	 * @param qainfo
	 * @throws Exception
	 */
	void insertQainfo(QainfoVO qainfo) throws Exception;
	
	/**
	 * 질답상세
	 * @param qainfo
	 * @return
	 * @throws Exception
	 */
	QainfoVO selectQainfo(QainfoVO qainfo) throws Exception;
	
	/**
	 * 질답수정
	 * @param qainfo
	 * @throws Exception
	 */
	void updateQainfo(QainfoVO qainfo) throws Exception;
	
	/**
	 * 질답 답변 수정
	 * @param qainfo
	 * @throws Exception
	 */
	void updateQainfoReply(QainfoVO qainfo) throws Exception;
	
	/**
	 * 질문수정
	 * @param qainfo
	 * @throws Exception
	 */
	void updateQainfoQestn(QainfoVO qainfo) throws Exception;

	/**
	 * 답변수정
	 * @param qainfo
	 * @throws Exception
	 */
	void updateQainfoAnswer(QainfoVO qainfo) throws Exception;
	
	/**
	 * 진행상태 수정
	 * @param qainfo
	 * @throws Exception
	 */
	void updateQainfoProcessSttus(QainfoVO qainfo) throws Exception;
	
	/**
	 * 질답삭제
	 * @param qainfo
	 * @throws Exception
	 */
	void deleteQainfo(QainfoVO qainfo) throws Exception;
	
	/**
	 *  CP > 교환, 반품 > 교환사유, 반품사유
	 * @param qaInfo
	 */
	QainfoVO selectReason(QainfoVO qaInfo);
	/**
	 * 교환/환불 > 상세정보 > 반품사유
	 * @param qainfo
	 * @return
	 */
	QainfoVO selectRefundInfo(QainfoVO qainfo);
}
