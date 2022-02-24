package modoo.module.common.ui.out;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CrlfTag extends SimpleTagSupport {

	private static final Logger LOGGER = LoggerFactory.getLogger(CrlfTag.class);

	private String content;

	@Override
	public void doTag() throws JspException, IOException {
		try {
			JspWriter out = getJspContext().getOut();
			out.write(this.content);
		} catch(Exception e) {
			LOGGER.error("Exception : ",e);
			throw new JspException();
		}
		super.doTag();
	}
	
	public void setContent(String content) {
		content = StringEscapeUtils.escapeHtml(content);
		this.content = content.replaceAll("(\r\n|\n)", "<br />");
	}

	
}
