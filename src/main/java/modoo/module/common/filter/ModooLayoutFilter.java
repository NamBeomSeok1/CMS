package modoo.module.common.filter;

import org.sitemesh.builder.SiteMeshFilterBuilder;
import org.sitemesh.config.ConfigurableSiteMeshFilter;

public class ModooLayoutFilter extends ConfigurableSiteMeshFilter {

	@Override
	protected void applyCustomConfiguration(SiteMeshFilterBuilder builder) { 
		builder.addDecoratorPath("/*", "/WEB-INF/jsp/modoo/front/cmm/layout/decorator.jsp")
				.addDecoratorPath("/decms/*", "/WEB-INF/jsp/modoo/cms/cmm/layout/cmsDecorator.jsp")
				.addExcludedPath("/html/*")
				.addExcludedPath("/resources/*")
				.addExcludedPath("/decms/ajax/*")
				.addExcludedPath("/decms/embed/*")
				.addExcludedPath("/index.do")
				.addExcludedPath("/embed/*")
				.addExcludedPath("/ajax/*")
				.addExcludedPath("/popup/*")
				.addExcludedPath("/prtnr/*")
				.addExcludedPath("/fms/*")
				.addExcludedPath("/user/sign/*")
				.addExcludedPath("/cpmng/index.do")
				;
	}
}
