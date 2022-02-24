package modoo.module.shop.goods.info.service;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.util.EgovUserDetailsHelper;
import egovframework.rte.fdl.excel.EgovExcelMapping;
import egovframework.rte.fdl.excel.util.EgovExcelUtil;

public class GoodsCouponExcelMapping extends EgovExcelMapping {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(GoodsCouponExcelMapping.class);

	@Override
	public Object mappingColumn(Row row) throws Exception {
		LoginVO user = (LoginVO)EgovUserDetailsHelper.getAuthenticatedUser();
		String uploadGroupId = user.getUniqId(); //(StringUtils.isEmpty(user.getCmpnyId())?"SYSTEM":user.getCmpnyId()) + user.getUniqId();
		
		Cell cell = row.getCell(0);
		
		GoodsCouponVO vo = new GoodsCouponVO();
		vo.setCouponNo(EgovExcelUtil.getValue(cell).trim());
		vo.setRegisterId(user.getUniqId());
		vo.setUploadGroupId(uploadGroupId);

		return vo;
	}

}
