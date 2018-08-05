package by.dk.training.items.webapp.app.renderer;

import org.apache.wicket.markup.html.form.ChoiceRenderer;

import by.dk.training.items.datamodel.UserProfile;

public class UserProfileRenderer extends ChoiceRenderer<UserProfile> {

	private static final long serialVersionUID = 6533429038169241627L;
	public static final UserProfileRenderer INSTANCE = new UserProfileRenderer();

	private UserProfileRenderer() {
		super();
	}

	@Override
	public Object getDisplayValue(UserProfile object) {
		String display = String.format("%s %s", object.getId(), object.getLogin());
		return display;
	}

	@Override
	public String getIdValue(UserProfile object, int index) {
		return String.valueOf(object.getId());
	}

}
