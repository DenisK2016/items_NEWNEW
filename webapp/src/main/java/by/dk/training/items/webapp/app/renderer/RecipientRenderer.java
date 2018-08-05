package by.dk.training.items.webapp.app.renderer;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import by.dk.training.items.datamodel.Recipient;

public class RecipientRenderer extends ChoiceRenderer<Recipient> {

	private static final long serialVersionUID = -4021606940356196752L;
	public static final RecipientRenderer INSTANCE = new RecipientRenderer();

	private RecipientRenderer() {
		super();
	}

	@Override
	public Object getDisplayValue(Recipient object) {
		String display = String.format("%s %s %s %s", object.getId(), object.getName(), object.getCity(),
				object.getAddress());
		return display;
	}

	@Override
	public String getIdValue(Recipient object, int index) {
		return String.valueOf(object.getId());
	}

}
