package modoo.front.biztalk.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.rte.psl.dataaccess.util.EgovMap;
import modoo.module.biztalk.service.BiztalkService;
import modoo.module.biztalk.service.BiztalkVO;
import modoo.module.common.web.CommonDefaultController;

@Controller
public class BizTalkController extends CommonDefaultController {

	@Resource(name = "biztalkService")
	private BiztalkService biztalkService;
	
	//비즈톡 테스트용
	@RequestMapping(value = "/embed/biztalk/test.do")
	@ResponseBody
	public BiztalkVO test(@ModelAttribute("searchVO") BiztalkVO searchVO, Model model) throws Exception {
		/*
		 *  전화번호 정보는 본인이 사용 할 부분에서 추출해와야 함 - 회원 테이블에 미존재
		 *  테스트는 본인 폰번호로 확인
		 */
		//searchVO.setRecipient("010-9799-9373");
		searchVO.setRecipient("본인번호");
		searchVO.setTmplatCode("template_001");
		
		/* ****************************************************************************
		 * 메세지 방법 1 
		 * *****************************************************************************/
		/*템플릿에 등록 된 내용 가져와서 넣음
			
			[#{SHOPNAME}]
			안녕하세요. #{NAME}님!
			#{SHOPNAME}에
			회원가입 해주셔서
			진심으로 감사드립니다~
			
			************************
			* 변수들을 글자로 다 치환해줘야 함 
			* 엔터부분은 \n 처리하기
			************************
			* 아래처럼 변수를 제외 한 다른 부분에 글 작성해서 보내면 biztalk결과 값은 완료이나 실질적으로 알림톡으로는 안 감
			* ex.) searchVO.setMessage("[모두의구독]\n안녕하세요1111111. 문성진님!\n모두의구독에\n회원가입 해주셔서\n진심으로 감사드립니다~");
		*/
		searchVO.setMessage("[모두의구독]\n안녕하세요. 문성진님!\n모두의구독에\n회원가입 해주셔서\n진심으로 감사드립니다~");
		
		
		
		/* ****************************************************************************
		 * 메세지 방법 2 
		 * *****************************************************************************/
		/*
		 	등록 된 알림톡 템플릿을 가져와서 변수 치환
		 	현재(2020-11-12 기준) 등록 된 11개 템플릿은 디비에 등록되어져 있음
		 */
		//알림톡 템플릿 내용 조회
		BiztalkVO template = biztalkService.selectBizTalkTemplate(searchVO);
		String shopName = "모두의구독";
		String name = "문성진";
		String message = template.getTmplatCn();
		message = message.replaceAll("#\\{SHOPNAME\\}", shopName).replaceAll("#\\{NAME\\}", name);
		searchVO.setMessage(message);
		
		BiztalkVO result = biztalkService.sendAlimTalk(searchVO);
		//BiztalkVO result = null;
		return result;
	}
	
	//비즈톡 전송
	@RequestMapping(value = "/embed/biztalk/sendAlimTalk.json")
	@ResponseBody
	public EgovMap sendAlimTalk(@ModelAttribute("searchVO") BiztalkVO searchVO, Model model) throws Exception {
		String successYn = "N";
		String msg = "";
		int successCnt = 0;
		int failCnt = 0;
		EgovMap map = new EgovMap();
		
		if(searchVO.getRecipientList() != null && searchVO.getRecipientList().size() > 0 &&searchVO.getMessageList() != null && searchVO.getMessageList().size() > 0){
			for(int i = 0; i < searchVO.getRecipientList().size(); i++){
				searchVO.setRecipient(searchVO.getRecipientList().get(i).toString());
				searchVO.setMessage(searchVO.getMessageList().get(i).toString());
				
				BiztalkVO result = biztalkService.sendAlimTalk(searchVO);
				if("1000".equals(result.getBizResultCode())){
					successCnt++;
				}else{
					failCnt++;
				}
				
				successYn = "Y";
				msg = "성공";
			}
		}else{
			successYn = "N";
			msg = egovMessageSource.getMessage("NotEmpty.BiztalkVO");
		}
		
		map.put("successYn", successYn);
		map.put("msg", msg);
		map.put("successCnt", successCnt);
		map.put("failCnt", failCnt);
		
		return map;
	}
	
	//비즈톡 전송 - 파라미터에 정보 일괄로 
	@RequestMapping(value = "/embed/biztalk/sendAlimTalkParam.json")
	@ResponseBody
	public EgovMap sendAlimTalkParam(@RequestBody BiztalkVO searchVO) throws Exception {
		String successYn = "N";
		String msg = "";
		int successCnt = 0;
		int failCnt = 0;
		EgovMap map = new EgovMap();
		
		if(searchVO.getParamList() != null && searchVO.getParamList().size() > 0){
			for(int i = 0; i < searchVO.getParamList().size(); i++){
				String param = searchVO.getParamList().get(i).toString().replaceAll("\\{param=", "").replaceAll("\\}", "");
				String[] paramSplit =  param.split("@@@");
				searchVO.setRecipient(paramSplit[0]);
				searchVO.setMessage(paramSplit[1]);
				
				BiztalkVO result = biztalkService.sendAlimTalk(searchVO);
				if("1000".equals(result.getBizResultCode())){
					successCnt++;
				}else{
					failCnt++;
				}
				
				successYn = "Y";
				msg = "성공";
			}
		}else{
			successYn = "N";
			msg = egovMessageSource.getMessage("NotEmpty.BiztalkVO");
		}
		
		map.put("successYn", successYn);
		map.put("msg", msg);
		map.put("successCnt", successCnt);
		map.put("failCnt", failCnt);
		
		return map;
	}
	
}
