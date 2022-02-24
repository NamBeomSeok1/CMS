package modoo.front.card.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.json.Json;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.sim.service.EgovFileScrty;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import modoo.module.biling.service.Biling;
import modoo.module.biling.service.CreditCardVaild;
import modoo.module.biling.service.Encryption;
import modoo.module.card.service.CreditCardVO;
import modoo.module.card.service.CreditCardService;
import modoo.module.common.service.JsonResult;
import modoo.module.common.util.CommonUtils;
import modoo.module.common.web.CommonDefaultController;

@Controller
public class CreditCardController extends CommonDefaultController{

	private static final Logger LOGGER = LoggerFactory.getLogger(CreditCardController.class); 
	
	@Resource(name="creditCardService")
	private CreditCardService creditCardService;
	
	//암호화,복호화
	Encryption encryption = new Encryption();
	
	
	//등록 폼
	@RequestMapping("/card/cardForm.do")
	public String cardForm(Model model,CreditCardVO card,@RequestParam("goodsId") String goodsId,HttpServletResponse res){
		
		Cookie cookie = new Cookie("goodsId", goodsId);
		cookie.setMaxAge(60*10);
		res.addCookie(cookie);
		
		Boolean isLogin = EgovUserDetailsHelper.isAuthenticated();
		if(isLogin){
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			model.addAttribute("user",user);
			model.addAttribute("card",new CreditCardVO());
			model.addAttribute("goodsId",goodsId);
		}
		
		return "modoo/front/shop/order/card/cardForm"; 
	}	
	
	/*
	 * 카드 목록
	 *@param searchVO,userId 
	 *@return 
	 */
	@RequestMapping("/card/cardList.json")
	@ResponseBody
	public JsonResult cardList(CreditCardVO searchVO){
		
		JsonResult res = new JsonResult();

		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		try {
			searchVO.setEsntlId(user.getUniqId());
			searchVO.setUseAt("Y");
			List<CreditCardVO> list = creditCardService.selectCardList(searchVO);
			for(CreditCardVO cvo : list){
				String decCardNoList = encryption.decryption(cvo.getCardNo());
				if(decCardNoList.length()==15){
					cvo.setLastCardNo(decCardNoList.substring(12,15));
				}else{
					cvo.setLastCardNo(decCardNoList.substring(12,16));
				}
			}
			if(list.size()>0){
				res.put("cardList", list);
				res.setSuccess(true);
			}else{
				res.setMessage("등록 된 카드가 없습니다.");
				res.setSuccess(false);
			}
		} catch (Exception e) {
			loggerError(LOGGER, e);
		}
		return res;
	}
	/*
	 * 카드 저장
	 * @param card
	 * @param goodsId
	 * @return
	 */
	@RequestMapping("/card/cardReg.json")
	@ResponseBody
	public JsonResult cardReg(@Valid CreditCardVO card, BindingResult bindingResult,@CookieValue(value="goodsId", required=false) String goodsId) throws Exception{
		
		JsonResult res = new JsonResult();
		
		EgovFileScrty scrty = new EgovFileScrty();

		Biling cardValid =  new Biling();
		//지원 카드사 번호
		HashMap<String, String> cardCodeMap = new HashMap<>();
		cardCodeMap.put("11","비씨카드");
		cardCodeMap.put("12","삼성카드");
		cardCodeMap.put("34","하나카드");
		cardCodeMap.put("17","하나카드");
		cardCodeMap.put("01","하나카드");
		cardCodeMap.put("16","농협카드");
		cardCodeMap.put("44","우리카드");
		cardCodeMap.put("03","롯데카드");
		cardCodeMap.put("04","현대카드");
		cardCodeMap.put("06","국민카드");
		
		
		try {
			
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			
			if(!"GROUP_00000000000001".equals(user.getGroupId())){
				cardCodeMap.put("14","신한카드");
			}

			if(!this.isHasErrorsJson(bindingResult, res,"<br/>")){
				
				card.setCardNo(card.getCardNo().replace(",", ""));
				
				if(card.getCardUsgpd().contains(",")==true){
					String[] cardUsgPdarr = card.getCardUsgpd().split(",");
					String cardUsgPd = cardUsgPdarr[1]+cardUsgPdarr[0];
					card.setCardUsgpd(cardUsgPd);	
				}
				
				List<String> enList = new ArrayList<String>();
				String[] enListName = {"cardNo","cardUsgpd","cardPassword","brthdy"};
				enList.add(CommonUtils.unscript(card.getCardNo().trim()));
				enList.add(CommonUtils.unscript(card.getCardUsgpd().trim()));
				enList.add(CommonUtils.unscript(card.getCardPassword().trim()));
				enList.add(CommonUtils.unscript(card.getBrthdy().trim()));
				HashMap<String, String>  encMap = encryption.encryption(enList, enListName);
				
				//카드 빌링키 검증	
				HashMap<String, Object> resultMap = cardValid.CardbilingKey(encMap);
				
				int cardCodeCnt = 0; 
				String cardCodeNm = null;
				//카드 번호
				if((String)resultMap.get("cardCode")!=null){
					String cardCode = (String)resultMap.get("cardCode");
					System.out.println(cardCode+"@@");
					if(cardCodeMap.containsKey(cardCode)){
						cardCodeCnt=+1;
						cardCodeNm=cardCodeMap.get(cardCode);
					}
					/*for(String cc : cardCodeArr){
						if(cc.equals(cardCode)){
							cardCodeCnt=+1;
						}
					}*/
				}
				if(cardCodeCnt>0){
					if((Boolean)resultMap.get("success")){
						//카드 유효성 체크
							String password = scrty.encryptPassword(card.getPassword().trim(), user.getUniqId());
							card.setEsntlId(user.getUniqId());
							if(StringUtils.isNotEmpty(card.getCardNm())){
								card.setCardNm(CommonUtils.unscript(card.getCardNm()));
							}else{
								card.setUseAt("Y");
								int cardCnt = creditCardService.selectCardListCnt(card);
								card.setCardNm(cardCodeNm);
							}
							card.setCardNo(encMap.get("cardNo"));
							card.setCardUsgpd(encMap.get("cardUsgpd"));
							card.setCardPassword(encMap.get("cardPassword"));
							card.setBrthdy(encMap.get("brthdy"));
							card.setPassword(password);
							CreditCardVO searchVO = new CreditCardVO();
							searchVO.setEsntlId(user.getUniqId());
							List<CreditCardVO> cardList = creditCardService.selectCardList(searchVO);
							int existCardCnt = 0;
							Boolean isUseAt = false;
							String cardId = null;
							for(CreditCardVO cvo : cardList){
								if(cvo.getCardNo().equals(card.getCardNo())){
									existCardCnt++;
									isUseAt = cvo.getUseAt().equals("N")?true:false;
									cardId = cvo.getCardId();
								}
							}
							if(existCardCnt==0){
								if(("Y").equals(card.getBassUseAt())){
									creditCardService.updateBassUseAt(card);
								}else if(card.getBassUseAt()==null || card.getBassUseAt()=="N"){
									card.setBassUseAt("N");
								}
								card.setUseAt("Y");
								creditCardService.insertCard(card);
								res.setMessage("카드가 정상적으로 등록되었습니다.");
								res.setSuccess(true);
							}else if(existCardCnt>=1){
								if(isUseAt){
									card.setBassUseAt("N");
									card.setCardId(cardId);
									card.setPassword(password);
									creditCardService.updateCard(card);
									res.setMessage("카드가 정상적으로 등록되었습니다.");
									res.setSuccess(true);
								}else{
									res.setMessage("이미 등록된 카드 입니다.");
									res.setSuccess(false);
								}
							}
					}else{
						String msg = (String)resultMap.get("resultMsg");
						msg.replace("\"","");
						msg=msg.substring(msg.lastIndexOf("|")+1,msg.length()-1);
						res.setMessage(msg);
						res.setSuccess(false);
						LOGGER.error("카드 유효성 검사 실패");
					}
				}else{
					res.setSuccess(false);
					res.setMessage("지원하지 않는 카드사입니다.");
					LOGGER.error("지원하지 않는 카드사입니다.");
			}	
		}
		} catch (Exception e) {
			loggerError(LOGGER, e);
			res.setSuccess(false);
		}
		return res;
	}
	/*
	 * 카드 수정
	 * @param card
	 * @return
	 */
	@RequestMapping("/card/cardUpdate.json")
	@ResponseBody
	public JsonResult cardUpdate(CreditCardVO card) throws Exception{
		
		JsonResult res = new JsonResult();
		EgovFileScrty scrty = new EgovFileScrty();
		try {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			
			card.setEsntlId(user.getUniqId());
			card.setCardNm(CommonUtils.unscript(card.getCardNm()));
			if(StringUtils.isNotEmpty(card.getPassword())){
				String password = scrty.encryptPassword(card.getPassword().trim(), user.getUniqId());
				card.setPassword(password);
			}
			if(("Y").equals(card.getBassUseAt())){
				creditCardService.updateBassUseAt(card);
			}else if(card.getBassUseAt()==null){
				card.setBassUseAt("N");
			}
			creditCardService.updateCard(card);
			res.setMessage("정상적으로 수정되었습니다.");
			res.setSuccess(true);
		} catch (Exception e) {
			loggerError(LOGGER, e);
			res.setSuccess(false);
		}
		return res;
	}
	/**
	 * 카드 비밀번호 변경
	 * @param password
	 * @param cardId
	 * @return
	 */
	@RequestMapping("/card/cardPwdUpdate.json")
	@ResponseBody
	public JsonResult cardPwdUpdate(@RequestParam("password") String password,@RequestParam("cardId") String cardId){
		
		JsonResult jsonResult = new JsonResult();
		
		try {
			LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
			EgovFileScrty scrty = new EgovFileScrty();
			CreditCardVO card = new CreditCardVO();
			
			String enpassword = scrty.encryptPassword(password.trim(), user.getUniqId());
			card.setPassword(enpassword);
			card.setCardId(cardId);
			creditCardService.updateCard(card);
			jsonResult.setSuccess(true);
			
		} catch (Exception e) {
			loggerError(LOGGER, e);
			jsonResult.setSuccess(false);
		}
		return jsonResult;
	}
	
	/*
	 * 카드 삭제
	 *@param card
	 *@param cardId
	 *@return 
	 */
	@RequestMapping("/card/cardDelete.json")
	@ResponseBody
	public JsonResult cardDelete(CreditCardVO card,@RequestParam("cardId") String cardId){
		JsonResult res = new JsonResult();
		
		try {
			if(StringUtils.isNotEmpty(cardId)){
				card.setCardId(cardId);
				
				List<EgovMap> orderCardChk = creditCardService.selectOrderCardList(card);
				List<String> orderNoList = new ArrayList<>();
				for(EgovMap cardItem : orderCardChk){
					orderNoList.add((String) cardItem.get("orderNo"));
				}
				if(orderNoList.size()==0){
					creditCardService.deleteCard(card);
					res.setMessage("삭제되었습니다.");
					res.setSuccess(true);
				}else{
					res.setMessage("삭제할 카드로 "+orderNoList.size()+" 개의 구독을 진행중입니다. '마이페이지-구독중-구독변경' 에서 카드를 변경해주세요.");
					res.setSuccess(false);
				}
			}
			
		} catch (Exception e) {
			loggerError(LOGGER, e);
			res.setSuccess(false);
		}
		return res;
	}
}
