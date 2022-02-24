package modoo.front.user.sign.web;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.scribejava.core.model.OAuth2AccessToken;
import com.google.gson.Gson;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.config.EgovLoginConfig;
import egovframework.com.cmm.service.EgovProperties;
import egovframework.com.cmm.service.Globals;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.uat.uia.service.AppleService;
import egovframework.com.uat.uia.service.EgovLoginService;
import egovframework.com.uat.uia.service.NaverLoginService;
import egovframework.com.uat.uia.service.Payload;
import egovframework.com.uat.uia.service.TokenResponse;
import egovframework.com.utl.fcc.service.EgovStringUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import modoo.module.biztalk.service.BiztalkService;
import modoo.module.biztalk.service.BiztalkVO;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.mber.info.service.MberService;
import modoo.module.mber.info.service.MberVO;
import modoo.module.shop.cmpny.service.CmpnyService;
import modoo.module.shop.cmpny.service.CmpnyVO;

@Controller
public class LoginController extends CommonDefaultController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
	
	/** EgovLoginService */
	@Resource(name = "loginService")
	private EgovLoginService loginService;
	
	@Resource(name = "mberService")
	private MberService mberService;
	
	@Resource(name = "egovLoginConfig")
	EgovLoginConfig egovLoginConfig;
	
	@Resource(name = "appleService")
	private AppleService appleService;
	
	@Resource(name = "naverLoginService")
    private NaverLoginService naverLoginService;
	
	@Resource(name = "biztalkService")
    private BiztalkService biztalkService;
	
	@Resource(name = "cmpnyService")
    private CmpnyService cmpnyService;
	
	/**
	 * 로그인 페이지
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/sign/loginUser.do")
	public String loginUser(@ModelAttribute("loginVO") LoginVO loginVO, 
			@RequestParam(name="loginMode", required=false) String loginMode, 
			HttpServletRequest request, HttpServletResponse response,  Model model, HttpSession session) throws Exception {
		
		String retUrl = "";
		String referrer = request.getHeader("Referer");
		if(!EgovStringUtil.isEmpty(referrer)){
			if(referrer.indexOf("portal") > -1) {
				String prevPage = (String) session.getAttribute("prevPage");
				if(EgovStringUtil.isEmpty(prevPage)) {
					retUrl = "/index.do";
				}else {
					retUrl = prevPage;
				}
			}
			request.getSession().setAttribute("prevPage", referrer);
		}
		
		if("id".equals(loginVO.getLoginType())){
			retUrl = "modoo/front/user/sign/loginUser";
		}else {
			retUrl = "redirect:" + Globals.FOX_MEMBER_LOGINURL;
		}
		
		return retUrl;
		/*
		//Apple
		Map<String, String> metaInfo = appleService.getLoginMetaInfo();

        model.addAttribute("client_id", metaInfo.get("CLIENT_ID"));
        model.addAttribute("redirect_uri", metaInfo.get("REDIRECT_URI"));
        model.addAttribute("nonce", metaInfo.get("NONCE"));
		
        //Naver
        String domain = request.getServerName();
        String naverAuthUrl = naverLoginService.getAuthorizationUrl(session, domain);
        model.addAttribute("naverAuthUrl", naverAuthUrl);
        
		//개발모드 로그인
		if("DEV".equals(EgovProperties.getProperty("CMS.mode")) && ("127.0.0.1".equals(request.getRemoteAddr()) || "0:0:0:0:0:0:0:1".equals(request.getRemoteAddr()))) {
			// Eclise -> Run -> Run Configurations -> Tomcat Server -> VM arguments -> -Djava.net.preferIPv4Stack=true
			LOGGER.info("============================ DEV MODE ===============================");
			model.addAttribute("dev_user", EgovProperties.getProperty("CMS.user"));
			model.addAttribute("dev_password", EgovProperties.getProperty("CMS.password"));
		}
		
		model.addAttribute("CMS_MODE", EgovProperties.getProperty("CMS.mode"));
		
		return "modoo/front/user/sign/loginUser";
		*/
	}
	
	/**
	 * 로그인 페이지 javascript
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/user/sign/loginUserScript.do")
	public String loginUserScript(HttpServletResponse response, Model model) {
		
		response.setContentType("application/javascript");
		return "modoo/front/user/sign/loginUserScript";
	}
	
	/**
	 * CP & ADMIN 로그인 페이지 URL 간소화
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cpmng/index.do")
	public String manager(Model model) throws Exception {
		if(EgovUserDetailsHelper.getAuthorities().contains("ROLE_EMPLOYEE")) {
			return "redirect:/decms/index.do";
		}
		LoginVO loginVO = new LoginVO();
		loginVO.setLoginType("id");
		model.addAttribute("loginVO", loginVO);
		return "modoo/front/user/sign/loginUser";
		
	}
	
	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/sign/main.do")
	public String actionMain() throws Exception {
		return "redirect:" + Globals.MAIN_PAGE;
	}
	
	/**
	 * 세션 로그인 처리
	 * @param loginVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/sign/actionLogin.do", method = RequestMethod.POST)
	public String actionLogin(@ModelAttribute("loginVO") LoginVO loginVO,
			@RequestParam(value = "refUrl", required = false) String refUrl, HttpServletRequest request, Model model) throws Exception {
		
		// 1. 로그인인증제한 활성화시 
		if( egovLoginConfig.isLock()){
			Map<?,?> mapLockUserInfo = (EgovMap)loginService.selectLoginIncorrect(loginVO);
			if(mapLockUserInfo != null){			
				//2.1 로그인인증제한 처리
				String sLoginIncorrectCode = loginService.processLoginIncorrect(loginVO, mapLockUserInfo);
				if(!sLoginIncorrectCode.equals("E")){
					if(sLoginIncorrectCode.equals("L")){

						System.out.println("================잠긴계정정보:" + mapLockUserInfo.toString());
						
						/* 업체 계정이 잠길 시 관리자에게 알림톡 */
						CmpnyVO cmpny = new CmpnyVO();
						cmpny.setCmpnyId((String) mapLockUserInfo.get("cmpnyId"));
						System.out.println(loginVO.getCmpnyId());
						CmpnyVO cmpnyInfo = cmpnyService.selectCmpny(cmpny);
						if (cmpnyInfo != null) {
							BiztalkVO bizTalk = new BiztalkVO();
							bizTalk.setRecipient("010-4891-7141"); // TODO 관리자-이채빈
							bizTalk.setTmplatCode("template_028");
							/*
							 	비밀번호 5회 오류로 인해 CP 관리자 계정이 잠금처리되었습니다.
								확인 후 신규 비밀번호 발급을 부탁드립니다.
							* 업체 계정 : #{ID}
							* 업체명 : #{CPNAME}
							* 잠김 일시 : #{LOCKDATETIME}
							 */
							BiztalkVO template = biztalkService.selectBizTalkTemplate(bizTalk);
							String message = template.getTmplatCn();
							message = message.replaceAll("#\\{ID\\}", loginVO.getId());
							message = message.replaceAll("#\\{CPNAME\\}", cmpnyInfo.getCmpnyNm());
							SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							Date today = new Date();
							message = message.replaceAll("#\\{LOCKDATETIME\\}", format.format(today));
							
							bizTalk.setMessage(message);
							
							BiztalkVO result = biztalkService.sendAlimTalk(bizTalk);

						}
						model.addAttribute("message", egovMessageSource.getMessageArgs("fail.common.loginIncorrect", new Object[] {egovLoginConfig.getLockCount(),request.getLocale()}));
					}else if(sLoginIncorrectCode.equals("C")){
						model.addAttribute("message", egovMessageSource.getMessage("fail.common.login",request.getLocale()));
					}
					return "redirect:/user/sign/loginUser.do?loginType=id";
				}
			}else{
				model.addAttribute("message", egovMessageSource.getMessage("fail.common.login",request.getLocale()));
				//return "egovframework/com/uat/uia/EgovLoginUsr";
				return "redirect:/user/sign/loginUser.do?loginType=id";
			}
		}
		
		// 2. 로그인 처리
		LoginVO resultVO = loginService.actionLogin(loginVO);
		
		// 3. 일반 로그인 처리
		if (resultVO != null && resultVO.getId() != null && !resultVO.getId().equals("")) {

			// 3-1. 로그인 정보를 세션에 저장
			request.getSession().setAttribute("loginVO", resultVO);
			// 2019.10.01 로그인 인증세션 추가
			request.getSession().setAttribute("accessUser", resultVO.getUserSe().concat(resultVO.getId()));

			//return "redirect:/uat/uia/actionMain.do";
			if(StringUtils.isNotBlank(refUrl)) {
				return "redirect:" + refUrl;
			}else {
				List<String> roleList = EgovUserDetailsHelper.getAuthorities();
				//로그인 후 첫페이지 분리
				if(roleList.contains("ROLE_SHOP")) { // ROLE_ADMIN, ROLE_EMPLOYEE 포함
					return "redirect:/decms/index.do";
				}else {
					return "redirect:/index.do";
				}
			}

		} else {
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login",request.getLocale()));
			//return "egovframework/com/uat/uia/EgovLoginUsr";
			return "modoo/front/user/sign/loginUser";
		}
	}
	
	/**
	 * 소셜 로그인 처리
	 * @param loginVO
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/user/sign/snsActionLogin.do", method = RequestMethod.POST)
	public String snsActionLogin(@ModelAttribute("loginVO") LoginVO loginVO,
			@RequestParam(value = "refUrl", required = false) String refUrl, HttpServletRequest request, Model model) throws Exception {
		
		String sexdstn = "";
		String agrde = "";
		if("KAKAO".equals(loginVO.getClientCd())){
			//성별
			if("male".equals(loginVO.getSexdstn())){
				sexdstn = "M";
			}else if("female".equals(loginVO.getSexdstn())){
				sexdstn = "F";
			}else{
				sexdstn = "E";
			}
			
			loginVO.setId("KAKAO" + loginVO.getId());
			loginVO.setPassword("KAKAO-" + loginVO.getId());
			loginVO.setSexdstn(sexdstn);
			if(!EgovStringUtil.isEmpty(loginVO.getAgrde())){
				loginVO.setAgrde(loginVO.getAgrde().substring(0,2));
			}
		}else if("GOOGLE".equals(loginVO.getClientCd())){
			loginVO.setId(loginVO.getEmail());
			loginVO.setPassword("GOOGLE-" + loginVO.getId());
		}
		
		//로그인 처리
		LoginVO resultVO = loginService.actionLogin(loginVO);
		
		// 3. 일반 로그인 처리
		if(resultVO != null && resultVO.getId() != null && !resultVO.getId().equals("")) {
			//sns에서 받은 정보를 기준으로 성별, 연령대 체크 후 다르면 업데이트
			sexdstn = "";
			agrde = "";
			if(!EgovStringUtil.isEmpty(resultVO.getSexdstn()) && !EgovStringUtil.isEmpty(loginVO.getSexdstn())){
				if(!resultVO.getSexdstn().equals(loginVO.getSexdstn())){
					sexdstn = loginVO.getSexdstn();
				}
			}
			if(!EgovStringUtil.isEmpty(resultVO.getAgrde()) && !EgovStringUtil.isEmpty(loginVO.getAgrde())){
				if(!resultVO.getAgrde().equals(loginVO.getAgrde())){
					agrde = loginVO.getAgrde();
				}
			}
			if(!EgovStringUtil.isEmpty(sexdstn) || !EgovStringUtil.isEmpty(agrde)){
				MberVO mber = new MberVO();
				mber.setEsntlId(resultVO.getUniqId());
				mber.setSexdstn(sexdstn);
				mber.setAgrde(agrde);
				
				mberService.updateMber(mber);
			}
			
			// 3-1. 로그인 정보를 세션에 저장
			request.getSession().setAttribute("loginVO", resultVO);
			request.getSession().setAttribute("accessUser", resultVO.getUserSe().concat(resultVO.getId()));
			String prevPage = "";
			if(request.getSession().getAttribute("prevPage") != null){
				prevPage = request.getSession().getAttribute("prevPage").toString();
			}
			
			if(!EgovStringUtil.isEmpty(prevPage)){
				return "redirect:" + prevPage;
			}
			else{
				return "redirect:/index.do";
			}
		}else if("KAKAO".equals(loginVO.getClientCd()) || "GOOGLE".equals(loginVO.getClientCd())){
			request.getSession().setAttribute("memberVO", loginVO);
			//return "forward:/prtnr/sso/mberAgre.do";
			return "redirect:/prtnr/sso/mberAgre.do";
		}else{
			model.addAttribute("message", egovMessageSource.getMessage("fail.common.login",request.getLocale()));
			return "modoo/front/user/sign/loginUser";
		}
	}
	
	//apple Auth
	/*
	@RequestMapping(value = "/user/sign/appleAuth.do")
	public String appleAuth(@ModelAttribute("loginVO") LoginVO loginVO, HttpServletRequest request, Model model) throws Exception {

        Map<String, String> metaInfo = appleService.getLoginMetaInfo();

        model.addAttribute("client_id", metaInfo.get("CLIENT_ID"));
        model.addAttribute("redirect_uri", metaInfo.get("REDIRECT_URI"));
        model.addAttribute("nonce", metaInfo.get("NONCE"));
        model.addAttribute("response_type", "code id_token");
        model.addAttribute("scope", "name email");
        model.addAttribute("response_mode", "form_post");

        return "redirect:https://appleid.apple.com/auth/authorize";
    }
	
	//apple로그인
	@RequestMapping(value = "/user/sign/appleLogin.do")
	public String appleLogin(@ModelAttribute("loginVO") LoginVO loginVO, HttpServletRequest request, Model model) throws Exception {
		
		Payload payload = appleService.getPayload(loginVO.getId_token());
		loginVO.setClientCd("APPLE");
		loginVO.setId("APPLE" + payload.getSub());
		loginVO.setPassword("APPLE-" + payload.getSub());
		loginVO.setEmail(payload.getEmail());
		loginVO.setUserKey(payload.getSub());
		
		//로그인 처리
		LoginVO resultVO = loginService.actionLogin(loginVO);
		
		if(resultVO != null && resultVO.getId() != null && !resultVO.getId().equals("")) {
			request.getSession().setAttribute("loginVO", resultVO);
			request.getSession().setAttribute("accessUser", resultVO.getUserSe().concat(resultVO.getId()));
			String prevPage = "";
			if(request.getSession().getAttribute("prevPage") != null){
				prevPage = request.getSession().getAttribute("prevPage").toString();
			}
			
			if(!EgovStringUtil.isEmpty(prevPage) && prevPage.indexOf("modoo") > -1){
				return "redirect:" + prevPage;
			}else{
				return "redirect:/index.do";
			}
		}else{
			//사용자 정보는 최초 1회에만 옴
			//String user = "{&quot;name&quot;:{&quot;firstName&quot;:&quot;youngseok&quot;,&quot;lastName&quot;:&quot;oh&quot;},&quot;email&quot;:&quot;iswr8vmdgn@privaterelay.appleid.com&quot;}";
			//loginVO.setUser(user);
			String user = loginVO.getUser();
			if(!EgovStringUtil.isEmpty(user)){
				String userInfoParse = user.replaceAll("&quot;", "\\\"");
				JSONParser parser = new JSONParser();
				JSONObject jsonObj = (JSONObject) parser.parse(userInfoParse);
				JSONObject nameObj = (JSONObject) jsonObj.get("name");
				
				String email = (String) jsonObj.get("email");
				String lastName = (String) nameObj.get("lastName");
				String firstName = (String) nameObj.get("firstName");
				
				loginVO.setName(lastName + firstName);
				loginVO.setEmail(email);
			}else{
				loginVO.setName(loginVO.getEmail());
			}
			
			request.getSession().setAttribute("memberVO", loginVO);
			return "redirect:/prtnr/sso/mberAgre.do";
		}
	}
	
	//네이버로그인 CallBack
	@RequestMapping(value="/user/sign/naverLogin.do")
	public String naverLogin(@ModelAttribute("loginVO") LoginVO loginVO, @RequestParam String code, @RequestParam String state, HttpSession session, HttpServletRequest request,HttpServletResponse response,Model model)throws Exception {
		String domain = request.getServerName();
        OAuth2AccessToken oauthToken;
        oauthToken = naverLoginService.getAccessToken(session, code, state, domain);
        
        //로그인 사용자 정보를 읽어온다.
        String apiResult = naverLoginService.getUserProfile(oauthToken);
        //System.out.println(apiResult);
        
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(apiResult);
		JSONObject jsonObj = (JSONObject) obj;
		JSONObject result = (JSONObject) jsonObj.get("response");
		
		loginVO.setClientCd("NAVER");
		loginVO.setId("NAVER" + result.get("id").toString());
		loginVO.setPassword("NAVER-" + result.get("id").toString());
		if(result.get("gender")!=null){
			loginVO.setSexdstn(result.get("gender").toString());
		}else{
			loginVO.setSexdstn("E");
		}
		
		//생일
		if(result.get("birthday")!=null){
			loginVO.setBirthday(result.get("birthday").toString());
		}
		//핸드폰번호
		if(result.get("mobile")!=null){
			loginVO.setMoblphon(result.get("mobile").toString());
		}
		
		if(result.get("email")!=null){
			loginVO.setEmail(result.get("email").toString());
		}
		if(result.get("name")!=null){
			loginVO.setName(result.get("name").toString());
		}else{
			loginVO.setName("NAVER" + result.get("id").toString());
		}
		loginVO.setUserKey(result.get("id").toString());
		
        //로그인 처리
  		LoginVO resultVO = loginService.actionLogin(loginVO);
  		
  		// 3. 일반 로그인 처리
  		if(resultVO != null && resultVO.getId() != null && !resultVO.getId().equals("")) {
  			//sns에서 받은 정보를 기준으로 성별, 연령대 체크 후 다르면 업데이트
  			String sexdstn = "";
  			String agrde = "";
  			String birthday = "";
  			String moblphon = "";
  			
  			if(!EgovStringUtil.isEmpty(resultVO.getSexdstn()) && !EgovStringUtil.isEmpty(loginVO.getSexdstn())){
  				if(!resultVO.getSexdstn().equals(loginVO.getSexdstn())){
  					sexdstn = loginVO.getSexdstn();
  				}
  			}
  			if(!EgovStringUtil.isEmpty(resultVO.getAgrde()) && !EgovStringUtil.isEmpty(loginVO.getAgrde())){
  				if(!resultVO.getAgrde().equals(loginVO.getAgrde())){
  					agrde = loginVO.getAgrde();
  				}
  			}
  			if(!EgovStringUtil.isEmpty(resultVO.getBirthday()) && !EgovStringUtil.isEmpty(loginVO.getBirthday())){
  				if(!resultVO.getBirthday().equals(loginVO.getBirthday())){
  					birthday = loginVO.getBirthday();
  				}
  			}
  			if(!EgovStringUtil.isEmpty(resultVO.getMoblphon()) && !EgovStringUtil.isEmpty(loginVO.getMoblphon())){
  				if(!resultVO.getMoblphon().equals(loginVO.getMoblphon())){
  					moblphon = loginVO.getMoblphon();
  				}
  			}
  			
  			if(!EgovStringUtil.isEmpty(sexdstn) || !EgovStringUtil.isEmpty(agrde) || !EgovStringUtil.isEmpty(birthday) || !EgovStringUtil.isEmpty(moblphon)){
  				MberVO mber = new MberVO();
  				mber.setEsntlId(resultVO.getUniqId());
  				mber.setSexdstn(sexdstn);
  				mber.setAgrde(agrde);
  				mber.setBirthday(birthday);
  				mber.setMoblphon(moblphon);
  				
  				mberService.updateMber(mber);
  			}
  			
  			// 3-1. 로그인 정보를 세션에 저장
  			request.getSession().setAttribute("loginVO", resultVO);
  			request.getSession().setAttribute("accessUser", resultVO.getUserSe().concat(resultVO.getId()));
  			String prevPage = "";
  			if(request.getSession().getAttribute("prevPage") != null){
  				prevPage = request.getSession().getAttribute("prevPage").toString();
  			}
  			
  			if(!EgovStringUtil.isEmpty(prevPage)){
  				return "redirect:" + prevPage;
  			}else{
  				return "redirect:/index.do";
  			}
  		}else{
  			request.getSession().setAttribute("memberVO", loginVO);
  			return "redirect:/prtnr/sso/mberAgre.do";
  		}
  		
	}
	*/
	/**
	 * 로그아웃 페이지
	 * @param session
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/sign/logout.do")
	public String logout(HttpSession session, Model model) throws Exception {
		session.setAttribute("loginVO", null);
		session.setAttribute("accessUser", null);
		return "redirect:" + Globals.MAIN_PAGE;
	}
	
	/**
	 * 카드사 테스트 아이디 강제 로그인
	 * @param request
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shop/cardUserLogin.do")
	public String cardUserLogin(HttpServletRequest request, Model model) throws Exception {
		// 테스트 USER : nacard / skzkem123!
		// USRCNFRM_00000000074
		
		
		LoginVO vo = new LoginVO();
		vo.setUniqId("USRCNFRM_00000000074");
		LoginVO user = loginService.actionLoginByEsntlId(vo);
		
		request.getSession().setAttribute("loginVO", user);
		request.getSession().setAttribute("accessUser", user.getUserSe().concat(user.getId()));
		return "redirect:/index.do";
	}
	
	
	/**
	 * 권한제한
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/user/accessDenied.do")
	public String accessDenied() throws Exception {
		return "modoo/common/error/accessDenied";
	}
	
	//약관 동의
	@RequestMapping(value = "/user/sign/agree.do")
	public String agree(@ModelAttribute("loginVO") LoginVO loginVO, HttpServletRequest request, HttpServletResponse response,  Model model) throws Exception {
		
		return "modoo/front/user/sign/agree";
	}
	
	//oAuth 콜백URL
	@RequestMapping(value = "/user/sign/oauth.do")
	public String oauth(@ModelAttribute("loginVO") LoginVO loginVO, HttpServletRequest request, HttpServletResponse response,  Model model) throws Exception {
		
		return "modoo/front/user/sign/oauth";
	}
}
