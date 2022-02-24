package modoo.module.common.filter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class HttpHeaderFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletResponse rep = (HttpServletResponse) response;
		rep.setHeader("X-Frame-Options", "");
		rep.setHeader("Expires", "0" );
        
		Collection<String> em = (List<String>) rep.getHeaderNames();
		for (String name : em) {
			Collection<String> values = rep.getHeaders(name);
			
			System.out.println(name + " : ==> " + values);
		}
        
        
        chain.doFilter(request, response);

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub

	}

}
