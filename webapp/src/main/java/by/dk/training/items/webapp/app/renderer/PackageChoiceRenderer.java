package by.dk.training.items.webapp.app.renderer;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import by.dk.training.items.datamodel.Package;

public class PackageChoiceRenderer extends ChoiceRenderer<Package> {

	private static final long serialVersionUID = 5914991141613751751L;
	public static final PackageChoiceRenderer INSTANCE = new PackageChoiceRenderer();

	private PackageChoiceRenderer() {
		super();
	}

	@Override
	public Object getDisplayValue(Package object) {
		return object.getId();
	}

	@Override
	public String getIdValue(Package object, int index) {
		return String.valueOf(object.getId());
	}

}
