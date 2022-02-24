package modoo.module.common.ui.pagination;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

public interface PaginationRenderer {

	String rederPagination(PaginationInfo paginationInfo, String type, String jsFunction, String pageUrl, String pageCssClass);
}
