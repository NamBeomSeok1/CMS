package modoo.cms.bbs.web;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import modoo.module.api.service.BbsService;
import modoo.module.api.service.BbsVO;
import modoo.module.api.service.FilterVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import modoo.module.biztalk.service.BiztalkService;
import modoo.module.biztalk.service.BiztalkVO;
import modoo.module.common.service.JsonResult;
import modoo.module.common.util.CommonUtils;
import modoo.module.common.util.SiteDomainHelper;
import modoo.module.common.web.CommonDefaultController;
import modoo.module.mber.info.service.MberService;
import modoo.module.mber.info.service.MberVO;
import modoo.module.qainfo.service.QainfoService;
import modoo.module.qainfo.service.QainfoVO;
import modoo.module.shop.goods.info.service.GoodsService;
import modoo.module.shop.goods.order.service.OrderVO;
import egovframework.com.cmm.ComDefaultCodeVO;
import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.service.EgovCmmUseService;
import egovframework.com.cmm.service.EgovFileMngService;
import egovframework.com.cmm.service.FileVO;
import egovframework.com.cmm.service.impl.FileManageDAO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.com.utl.fcc.service.EgovDateUtil;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@Controller
public class CmsBbsController extends CommonDefaultController{


    @Resource(name="bbsService")
    private BbsService bbsService;
    /**
     * 순위 목록
     *
     * @param searchVO
     * @return
     */
    @RequestMapping(value = "/decms/bbs/bbsList.json", method = RequestMethod.GET)
    @ResponseBody
    public JsonResult bbsList(BbsVO searchVO) {
        JsonResult jsonResult = new JsonResult();

        try {
            PaginationInfo paginationInfo = new PaginationInfo();
            searchVO.setPageUnit(propertiesService.getInt("gridPageUnit"));
            this.setPagination(paginationInfo, searchVO);


            FilterVO filterVO = bbsService.selectFilter();
            if(filterVO!=null){
                searchVO.setSearchBgnde(filterVO.getFrstPnttm());
                searchVO.setSearchEndde(filterVO.getLastPnttm());
                searchVO.setDlpctAt(filterVO.getDplctAt());
            }
            if("Y".equals(searchVO.getDlpctAt())){
                List<?> resultList = bbsService.selectBbsList(searchVO);
                jsonResult.put("list", resultList);
            }else{
                List<?> resultList = bbsService.selectDupliBbsList(searchVO);
                jsonResult.put("list", resultList);
            }

         /*   int totalRecordCount = 10;
            paginationInfo.setTotalRecordCount(totalRecordCount);
            jsonResult.put("paginationInfo", paginationInfo);*/

            jsonResult.setSuccess(true);

        } catch (Exception e) {
            e.printStackTrace();
            jsonResult.setSuccess(false);
            jsonResult.setMessage(egovMessageSource.getMessage("fail.common.select")); //조회에 실패하였습니다.
        }

        return jsonResult;
    }
    /**
     * 검색 필터 저장
     *
     * @param qainfo
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/decms/bbs/writeFilter.json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult writeFilter(@Valid FilterVO filterVO, BindingResult bindingResult) {
        JsonResult jsonResult = new JsonResult();

        try {
            if (!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {

                System.out.println(filterVO.toString());
                filterVO.setFrstPnttm(String.valueOf(filterVO.getFrstPnttm()));
                filterVO.setLastPnttm(String.valueOf(filterVO.getLastPnttm()));
                bbsService.deleteFilter();
                bbsService.insertFilter(filterVO);

                jsonResult.setSuccess(true);
            }
        } catch (Exception e) {
            jsonResult.setSuccess(false);
            jsonResult.setMessage(egovMessageSource.getMessage("fail.common.insert")); //생성이 실패하였습니다.
        }

        return jsonResult;
    }

    /**
     * 질답 저장
     *
     * @param qainfo
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/decms/bbs/writeBbs.json", method = RequestMethod.POST)
    @ResponseBody
    public JsonResult writeQainfo(@Valid BbsVO bbs, BindingResult bindingResult) {
        JsonResult jsonResult = new JsonResult();
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        try {
            if (!this.isHasErrorsJson(bindingResult, jsonResult, "<br/>")) {

                int partcptnCo = 1;

                List<BbsVO> list = bbsService.selectBbsList(bbs);
                for(int i=0;i<list.size();i++){
                    if(list.get(i).getUsrNm().equals(bbs.getUsrNm())){
                        Integer maxPartcptnCo = bbsService.selectMaxPartcprnCo(list.get(i));
                        partcptnCo=maxPartcptnCo+1;
                    }
                }

                bbs.setPartcptnCo(partcptnCo);
                bbsService.insertBbs(bbs);

                jsonResult.setSuccess(true);
            }
        } catch (Exception e) {
            jsonResult.setSuccess(false);
            jsonResult.setMessage(egovMessageSource.getMessage("fail.common.insert")); //생성이 실패하였습니다.
        }

        return jsonResult;
    }


    /**
     * 질답 삭제
     *
     * @param qainfo
     * @return
     */
    @RequestMapping(value = "/decms/bbs/deleteBbs.json")
    @ResponseBody
    public JsonResult deleteQainfo(BbsVO bbs) {
        JsonResult jsonResult = new JsonResult();
        LoginVO user = (LoginVO) EgovUserDetailsHelper.getAuthenticatedUser();

        try {
            System.out.println(bbs.toString());

            bbsService.deleteBbs(bbs);

        } catch (Exception e) {
            jsonResult.setSuccess(false);
            jsonResult.setMessage(egovMessageSource.getMessage("fail.common.delete")); //삭제가 실패하였습니다.
        }

        return jsonResult;
    }

}
