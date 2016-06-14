package by.dk.training.items.webapp.app.renderer;

import java.util.List;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

public class TypeChoiceRenderer extends ChoiceRenderer<String> {

	private static final long serialVersionUID = 1L;
	private List<String> list;

	public TypeChoiceRenderer(List<String> list) {
		super();
		this.list = list;
	}

	@Override
	public Object getDisplayValue(String object) {
		return object;
	}

	@Override
	public String getIdValue(String object, int index) {
		return String.valueOf(list.indexOf(object));
	}
}
