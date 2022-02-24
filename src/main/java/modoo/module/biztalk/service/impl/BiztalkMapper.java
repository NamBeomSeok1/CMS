package modoo.module.biztalk.service.impl;

import modoo.module.biztalk.service.BiztalkVO;
import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("biztalkMapper")
public interface BiztalkMapper {
	
	//알림톡전송이력 등록
	void insertBizTalkHistory(BiztalkVO vo) throws Exception;
	
	//토큰 등록
	void insertBizTalkToken(BiztalkVO vo) throws Exception;
	
	//토큰 조회
	BiztalkVO selectBizTalkToken(BiztalkVO vo) throws Exception;
	
	//알림톡내용 조회
	BiztalkVO selectBizTalkTemplate(BiztalkVO vo) throws Exception;
	
}
