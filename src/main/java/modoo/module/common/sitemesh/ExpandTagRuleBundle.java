package modoo.module.common.sitemesh;

import org.sitemesh.SiteMeshContext;
import org.sitemesh.content.ContentProperty;
import org.sitemesh.content.tagrules.TagRuleBundle;
import org.sitemesh.content.tagrules.html.ExportTagToContentRule;
import org.sitemesh.tagprocessor.State;

public class ExpandTagRuleBundle implements TagRuleBundle {

	@Override
	public void install(State defaultState, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {
		defaultState.addRule("metatag", new ExportTagToContentRule(siteMeshContext, contentProperty.getChild("metatag"), false));
		defaultState.addRule("javascript", new ExportTagToContentRule(siteMeshContext, contentProperty.getChild("javascript"), false));
		defaultState.addRule("template", new ExportTagToContentRule(siteMeshContext, contentProperty.getChild("template"), false));

	}

	@Override
	public void cleanUp(State defaultState, ContentProperty contentProperty, SiteMeshContext siteMeshContext) {
		// TODO Auto-generated method stub

	}

}
