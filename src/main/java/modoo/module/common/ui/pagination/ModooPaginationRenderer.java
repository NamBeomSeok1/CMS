package modoo.module.common.ui.pagination;

import egovframework.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

public class ModooPaginationRenderer extends AbstractPaginationRenderer {


	public ModooPaginationRenderer() {
		/* 	{0} : jsFunction
			{1} : pageIndex
			{2} : pageUrl
			{3} : pageCssClass
		*/
		firstPageLabel 		= "<li class=\"ppv\"><a href=\"{2}{1}\" title=\"처음으로\" class=\"page-link\" data-page-index=\"{1}\"><span class=\"txt-hide\"></span></a></li>";
		previousPageLabel 	= "<li class=\"pv\"><a href=\"{2}{1}\" title=\"이전\" class=\"page-link\" data-page-index=\"{1}\"><span class=\"txt-hide\"></span></a></li>";
		currentPageLabel 	= "<li class=\"is-active\"><a href=\"#\" title=\"to {1} page\" class=\"page-link\" data-page-index=\"{1}\">{1}</a></li>";
		otherPageLabel 		= "<li class=\"\"><a href=\"{2}{1}\" title=\"to {1} page\" class=\"page-link\" data-page-index=\"{1}\">{1}</a></li>";
		nextPageLabel 		= "<li class=\"fw\"><a href=\"{2}{1}\" title=\"다음\" class=\"page-link\" data-page-index=\"{1}\"><span class=\"txt-hide\"></span></a></li>";
		lastPageLabel 		= "<li class=\"ffw\"><a href=\"{2}{1}\" title=\"끝으로\" class=\"page-link\" data-page-index=\"{1}\"><span class=\"txt-hide\"></span></a></li>";
	}

	
	public String rederPagination(PaginationInfo paginationInfo, String type,
			String jsFunction, String pageUrl, String pageCssClass) {
		return super.rederPagination(paginationInfo, type, jsFunction, pageUrl, pageCssClass);
	}

}
