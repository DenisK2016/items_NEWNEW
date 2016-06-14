package by.dk.training.items.webapp.pages.packages;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.markup.html.link.Link;

import by.dk.training.items.webapp.app.AuthorizedSession;
import by.dk.training.items.webapp.pages.AbstractPage;
import by.dk.training.items.webapp.pages.packages.formreg.PackRegPage;
import by.dk.training.items.webapp.pages.packages.panelpackages.ListPackagesOfficer;
import by.dk.training.items.webapp.pages.packages.panelpackages.ListPackagesPanel;

@AuthorizeInstantiation(value = { "ADMIN", "OFFICER", "COMMANDER" })
public class PackagesPage extends AbstractPage {

	private static final long serialVersionUID = 1L;
	boolean admin = AuthorizedSession.get().getRoles().contains("ADMIN");
	boolean commander = AuthorizedSession.get().getRoles().contains("COMMANDER");

	public PackagesPage() {
		super();

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		if (admin || commander) {
			add(new ListPackagesPanel("list-panel"));
		} else {
			add(new ListPackagesOfficer("list-panel"));
		}
		Link<PackRegPage> createLink = new Link<PackRegPage>("CreatePack") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new PackRegPage());
			}
		};
		add(createLink);
		if (admin) {
			createLink.setVisible(false);
		}
	}
}
