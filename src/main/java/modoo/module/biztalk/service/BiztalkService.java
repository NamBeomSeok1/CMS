package modoo.module.biztalk.service;

public interface BiztalkService {
	
	//비즈톡 전송
	BiztalkVO sendAlimTalk(BiztalkVO vo) throws Exception;
	
	//비즈톡 토큰생성
	String bizTalkGetToken() throws Exception;
	
	//알림톡내용 조회
	BiztalkVO selectBizTalkTemplate(BiztalkVO vo) throws Exception;
	
}
