package modoo.front.popup.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import modoo.module.common.web.CommonDefaultController;
import modoo.module.popup.service.PopupService;
import modoo.module.popup.service.PopupVO;

@Controller
public class PopupController extends CommonDefaultController {

	@Resource(name = "popupService")
	private PopupService popupService;
	
	/**
	 * 윈도우 팝업
	 * @param popup
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/popup/windowPopup.do")
	public String windowPopup(@ModelAttribute("searchVO") PopupVO searchVO, Model model) throws Exception {
		
		PopupVO popup = new PopupVO();
		popup = popupService.selectPopup(searchVO);
		model.addAttribute("popup", popup);
		
		
		return "modoo/front/popup/windowPopup";
	}
}
