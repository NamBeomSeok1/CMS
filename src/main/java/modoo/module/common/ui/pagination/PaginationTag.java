package modoo.module.common.ui.pagination;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContextUtils;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

@SuppressWarnings("serial")
public class PaginationTag extends TagSupport {

	private PaginationInfo paginationInfo;
	private String type;
	private String jsFunction;
	private String pageUrl;
	private String pageCssClass;
	
	public int doEndTag() throws JspException {
		
		try {
			JspWriter out = pageContext.getOut();
			PaginationManager paginationManager;
			
			WebApplicationContext ctx = RequestContextUtils.findWebApplicationContext((HttpServletRequest) pageContext.getRequest(), pageContext.getServletContext());
			
			if(ctx.containsBean("paginationManager")) {
				paginationManager = (PaginationManager) ctx.getBean("paginationManager");
			}else {
				paginationManager = new ModooPaginationManager();
			}
			
			PaginationRenderer paginationRenderer = paginationManager.getRendererType(type);
			
			String contents = paginationRenderer.rederPagination(paginationInfo, type, jsFunction, pageUrl, pageCssClass);
			out.println(contents);
			
			return EVAL_PAGE;
		}catch(IOException e) {
			throw new JspException();
		}
	}

	public void setPaginationInfo(PaginationInfo paginationInfo) {
		this.paginationInfo = paginationInfo;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setJsFunction(String jsFunction) {
		this.jsFunction = jsFunction;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public void setPageCssClass(String pageCssClass) {
		this.pageCssClass = pageCssClass;
	}
}
