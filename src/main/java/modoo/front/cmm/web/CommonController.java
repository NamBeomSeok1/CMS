package modoo.front.cmm.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CommonController {
	
	/**
	 * 이미지뷰어
	 * @param imageUrl
	 * @param model
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/embed/common/imageView.do")
	public String imageView(@RequestParam("imageUrl") String imageUrl, Model model) throws Exception {
		
		imageUrl = java.net.URLDecoder.decode(imageUrl, "utf-8");
		model.addAttribute("imageUrl", imageUrl);
		return "modoo/front/cmm/etc/imageView";
	}

}
