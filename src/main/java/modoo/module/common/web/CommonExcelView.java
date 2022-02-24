package modoo.module.common.web;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ibm.icu.text.SimpleDateFormat;

import modoo.module.common.util.CommonUtils;
import egovframework.rte.fdl.excel.util.AbstractPOIExcelView;
import net.sf.jxls.transformer.XLSTransformer;

public class CommonExcelView extends AbstractPOIExcelView {
	
	private static SimpleDateFormat sf = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");

	@Override
	protected void buildExcelDocument(Map<String, Object> model, XSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String browser = CommonUtils.getBrowser(request);
		String templateFileName = (String) model.get("template");
		String destFileName = (String) model.get("fileName") + "_" + sf.format(new Date()) + ".xlsx";
		
		String excelTemplatPath = getServletContext().getRealPath("") 
				+ File.separator + "WEB-INF" + File.separator + "classes" + File.separator + "egovframework" + File.separator + "excel" + File.separator + templateFileName;
		
		XLSTransformer transformer = new XLSTransformer();
		Workbook resultWorkbook = transformer.transformXLS(new FileInputStream(excelTemplatPath), model);
		
		StringBuffer contentDisposition = new StringBuffer();
		String encodedFilename = null;
		
		if (browser.equals("MSIE")) {
			contentDisposition.append("attachment;fileName=");
			encodedFilename = URLEncoder.encode(destFileName, "UTF-8").replaceAll("\\+", "%20");
		} else {
			contentDisposition.append("attachment;fileName*=UTF-8''");
			encodedFilename = URLEncoder.encode(destFileName, "UTF-8").replaceAll("\\+", "%20");
		}
		
		contentDisposition.append(encodedFilename);		
		contentDisposition.append(";");

		response.setHeader("Content-Disposition", contentDisposition.toString());
		response.setContentType("application/x-msexcel");
		resultWorkbook.write(response.getOutputStream());

	}

}
