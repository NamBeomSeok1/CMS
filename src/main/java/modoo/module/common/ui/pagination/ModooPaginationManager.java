package modoo.module.common.ui.pagination;

import java.util.Map;

public class ModooPaginationManager implements PaginationManager {
	
	private Map<String, PaginationRenderer> rendererType;
	
	public void setRendererType(Map<String, PaginationRenderer> rendererType) {
		this.rendererType = rendererType;
	}

	@Override
	public PaginationRenderer getRendererType(String type) {
		return (rendererType != null && rendererType.containsKey(type)) ? (PaginationRenderer) rendererType.get(type) : new ModooPaginationRenderer();
	}

}
