package by.dk.training.items.webapp.app.renderer;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import by.dk.training.items.datamodel.Type;

public class TypeRenderer extends ChoiceRenderer<Type> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final TypeRenderer INSTANCE = new TypeRenderer();

	private TypeRenderer() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public Object getDisplayValue(Type object) {
		String display = object.getId() + " " + object.getTypeName();
		return display;
	}

	@Override
	public String getIdValue(Type object, int index) {
		// TODO Auto-generated method stub
		return String.valueOf(object.getId());
	}

}
