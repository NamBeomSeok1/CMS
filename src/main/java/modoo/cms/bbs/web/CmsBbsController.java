package modoo.cms.bbs.web;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.enterprise.inject.Model;
import javax.validation.Valid;

import modoo.module.api.service.BbsService;
import modoo.module.api.service.BbsVO;
import modoo.module.api.service.FilterVO;
import modoo.module.shop.goods.info.service.GoodsVO;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
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
import org.springframework.web.servlet.ModelAndView;

import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

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
            List<EgovMap> resultList = new ArrayList<>();

            searchVO.setDlpctAt("Y");
            if("Y".equals(searchVO.getDlpctAt())){
                 resultList = bbsService.selectBbsList(searchVO);
            }else{
                 resultList = bbsService.selectDupliBbsList(searchVO);
            }

            FilterVO filterVO = bbsService.selectFilter();
           /* if(filterVO!=null){
                searchVO.setSearchBgnde(filterVO.getFrstPnttm());
                searchVO.setSearchEndde(filterVO.getLastPnttm());
                searchVO.setDlpctAt(filterVO.getDplctAt());
                searchVO.setSearchKeyword("ten");

                List<Object> frontNoList = bbsService.selectBbsList(searchVO).stream()
                        .map(m -> String.valueOf(m.get("bbsNo")))
                        .collect(toList());

                for(EgovMap e : resultList){
                    for(Object a : frontNoList){
                        if(String.valueOf(e.get("bbsNo")).equals(String.valueOf(a))){
                            e.put("front","Y");
                            e.put("cnt",resultList.size()-frontNoList.size());
                        }
                    }
                }
            }*/


            jsonResult.put("list", resultList);



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
                if("Y".equals(filterVO.getDateUseAt())){
                    filterVO.setFrstPnttm(String.valueOf(filterVO.getFrstPnttm()));
                    filterVO.setLastPnttm(String.valueOf(filterVO.getLastPnttm()));
                }
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
                List<EgovMap> list = bbsService.selectBbsList(bbs);
                for(int i=0;i<list.size();i++){
                    if(list.get(i).get("usrNm").equals(bbs.getUsrNm())){
                        BbsVO searBbsVO = new BbsVO();
                        searBbsVO.setUsrNm(String.valueOf(list.get(i).get("usrNm")));
                        Integer maxPartcptnCo = bbsService.selectMaxPartcprnCo(searBbsVO);
                        partcptnCo=maxPartcptnCo+1;
                    }
                }

                bbs.setPartcptnCo(partcptnCo);
                bbsService.insertBbs(bbs);
                bbsService.updateBbsPartcptnCo(bbs);

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

    @RequestMapping(value = "/decms/bbs/bbsExcelDownload.do")
    public ModelAndView bbsListExcel(@ModelAttribute("searchVO") BbsVO searchVO
                                       ) throws Exception {
        LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
        Map<String, Object> map = new HashMap<String, Object>();

        FilterVO filterVO = bbsService.selectFilter();

       /* if(filterVO!=null){
            searchVO.setSearchBgnde(filterVO.getFrstPnttm());
            searchVO.setSearchEndde(filterVO.getLastPnttm());
            searchVO.setDlpctAt(filterVO.getDplctAt())
        }*/
        searchVO.setDlpctAt("Y");
        if("Y".equals(searchVO.getDlpctAt())){
            List<?> resultList = bbsService.selectBbsList(searchVO);
            map.put("dataList", resultList);
        }else{
            List<?> resultList = bbsService.selectDupliBbsList(searchVO);
            map.put("dataList", resultList);
        }

        map.put("template", "deepRacer.xlsx");
        map.put("fileName", "딥레이서기록");

        return new ModelAndView("commonExcelView", map);
    }

}
