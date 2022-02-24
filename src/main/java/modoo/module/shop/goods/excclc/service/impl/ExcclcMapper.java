package modoo.module.shop.goods.excclc.service.impl;

import java.util.List;

import egovframework.rte.psl.dataaccess.mapper.Mapper;
import egovframework.rte.psl.dataaccess.util.EgovMap;
import modoo.module.shop.goods.excclc.service.ExcclcResult;
import modoo.module.shop.goods.excclc.service.ExcclcVO;

@Mapper("excclcMapper")
public interface ExcclcMapper {

	/**
	 * 정산목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<ExcclcResult> selectExcclcList(ExcclcVO searchVO) throws Exception;
	
	/**
	 * 정산목록 카운트
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	int selectExcclcListCnt(ExcclcVO searchVO) throws Exception;
	
	/**
	 * 정산목록 계
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	EgovMap selectExcclcListTotalSum(ExcclcVO searchVO) throws Exception;
	
	/**
	 * 정산상태 변경
	 * @param excclc
	 * @throws Exception
	 */
	void updateExcclcSttus(ExcclcVO excclc) throws Exception;
	
	/**
	 * 정산예정일 변경
	 * @param excclc
	 * @throws Exception
	 */
	void updateExcclcPrarnde(ExcclcVO excclc) throws Exception;
	
	/**
	 * 이지웰 정산 목록
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	List<?> selectEzwelExcclcList(ExcclcVO searchVO) throws Exception;
	
	/**
	 * 이지웰 정산 목록 카운트
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	int selectEzwelExcclcListCnt(ExcclcVO searchVO) throws Exception;
	
	/**
	 * 이지웰 정산 목록 합계
	 * @param searchVO
	 * @return
	 * @throws Exception
	 */
	EgovMap selectEzwelExcclcListTotalSum(ExcclcVO searchVO) throws Exception;
}
