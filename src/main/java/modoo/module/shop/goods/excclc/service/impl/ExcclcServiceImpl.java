package modoo.module.shop.goods.excclc.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import egovframework.rte.fdl.cmmn.EgovAbstractServiceImpl;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import modoo.module.shop.goods.excclc.service.ExcclcResult;
import modoo.module.shop.goods.excclc.service.ExcclcService;
import modoo.module.shop.goods.excclc.service.ExcclcVO;

@Service("excclcService")
public class ExcclcServiceImpl extends EgovAbstractServiceImpl implements ExcclcService {
	
	@Resource(name = "excclcMapper")
	private ExcclcMapper excclcMapper;

	/**
	 * 정산목록
	 */
	@Override
	public List<ExcclcResult> selectExcclcList(ExcclcVO searchVO) throws Exception {
		List<ExcclcResult> resultList = excclcMapper.selectExcclcList(searchVO);
		for(ExcclcResult obj : resultList) {
			Map<String, Object> attributes = new HashMap<String, Object>();
			Map<String, Object> className = new HashMap<String, Object>();
			List<String> row = new ArrayList<String>();
			String excclcSttusCode = obj.getExcclcSttusCode();
			if("CPE01".equals(excclcSttusCode)) { //대기
				row.add("cpe01");
			}else if("CPE02".equals(excclcSttusCode)) { // 보류
				row.add("cpe02");
			}else if("CPE03".equals(excclcSttusCode)) { // 완료
				row.add("cpe03");
			}
			className.put("row", row);
			attributes.put("className", className);
			obj.set_attributes(attributes);
			//map.put("_attributes", attributes);
		}
		return resultList;
	}

	/**
	 * 정산목록 카운트
	 */
	@Override
	public int selectExcclcListCnt(ExcclcVO searchVO) throws Exception {
		return excclcMapper.selectExcclcListCnt(searchVO);
	}

	/**
	 * 정산목록 계
	 */
	@Override
	public EgovMap selectExcclcListTotalSum(ExcclcVO searchVO) throws Exception {
		return excclcMapper.selectExcclcListTotalSum(searchVO);
	}

	/**
	 * 정산상태 변경
	 */
	@Override
	public void updateExcclcSttus(ExcclcVO excclc) throws Exception {
		excclcMapper.updateExcclcSttus(excclc);
	}

	/**
	 * 정산예정일 변경
	 */
	@Override
	public void updateExcclcPrarnde(ExcclcVO excclc) throws Exception {
		excclcMapper.updateExcclcPrarnde(excclc);
	}

	/**
	 * 이지웰 정산 목록
	 */
	@Override
	public List<?> selectEzwelExcclcList(ExcclcVO searchVO) throws Exception {
		return excclcMapper.selectEzwelExcclcList(searchVO);
	}

	/**
	 * 이지웰 정산 목록 카운트
	 */
	@Override
	public int selectEzwelExcclcListCnt(ExcclcVO searchVO) throws Exception {
		return excclcMapper.selectEzwelExcclcListCnt(searchVO);
	}

	/**
	 * 이지웰 정산 합계
	 */
	@Override
	public EgovMap selectEzwelExcclcListTotalSum(ExcclcVO searchVO) throws Exception {
		return excclcMapper.selectEzwelExcclcListTotalSum(searchVO);
	}

}
