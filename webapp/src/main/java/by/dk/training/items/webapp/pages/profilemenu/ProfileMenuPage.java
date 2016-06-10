package by.dk.training.items.webapp.pages.profilemenu;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.WindowClosedCallback;

import by.dk.training.items.datamodel.UserProfile;
import by.dk.training.items.webapp.app.AuthorizedSession;
import by.dk.training.items.webapp.pages.AbstractPage;
import by.dk.training.items.webapp.pages.packages.setting.SystemSettings;

@AuthorizeInstantiation(value = { "ADMIN", "OFFICER", "COMMANDER" })
public class ProfileMenuPage extends AbstractPage {

	boolean commander = AuthorizedSession.get().getRoles().contains("COMMANDER");
	boolean officer = AuthorizedSession.get().getRoles().contains("OFFICER");

	private static final long serialVersionUID = 1L;
	private UserProfile userProfile;

	public ProfileMenuPage(UserProfile userProfile) {
		super();
		this.userProfile = userProfile;

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		add(new ProfileMenuPanel("profileMenu", userProfile));
		final ModalWindow modalSettings = new ModalWindow("modalSettings");
		modalSettings.setCssClassName("modal_window");
		modalSettings.setInitialHeight(500);
		modalSettings.setResizable(false);
		modalSettings.setWindowClosedCallback(new WindowClosedCallback() {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClose(AjaxRequestTarget target) {
				target.add(ProfileMenuPage.this);

			}
		});
		this.setOutputMarkupId(true);
		add(modalSettings);

		AjaxLink<Void> linkSettings = new AjaxLink<Void>("settings") {
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				modalSettings.setContent(new SystemSettings(modalSettings));
				modalSettings.show(target);
			}
		};
		linkSettings.setVisible(false);
		if (commander || officer) {
			linkSettings.setVisible(true);
		}
		add(linkSettings);
	}
}
