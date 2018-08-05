package by.dk.training.items.webapp.components.menu;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import by.dk.training.items.webapp.app.AuthorizedSession;
import by.dk.training.items.webapp.pages.home.HomePage;
import by.dk.training.items.webapp.pages.login.LoginPage;
import by.dk.training.items.webapp.pages.packages.PackagesPage;
import by.dk.training.items.webapp.pages.products.ProductPage;
import by.dk.training.items.webapp.pages.profilemenu.ProfileMenuPage;
import by.dk.training.items.webapp.pages.recipients.RecipientPage;
import by.dk.training.items.webapp.pages.types.TypePage;
import by.dk.training.items.webapp.pages.users.UserPage;

public class MenuForAdmin extends Panel {

	private static final long serialVersionUID = 8747670052866621682L;

	public MenuForAdmin(String id) {
		super(id);
		// setRenderBodyOnly(true);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		addLinkProduct();

		addLinkTypes();

		addLinkUser();

		addLinkPackages();

		addLinkRecipient();

		addLinkLogout();

		addLinkProfile();

		addLinkBack();
	}

	private void addLinkBack() {
		add(new Link<HomePage>("Back") {

			private static final long serialVersionUID = 8184218358289727907L;

			@Override
			public void onClick() {
				setResponsePage(new HomePage());
			}
		});
	}

	private void addLinkProfile() {
		add(new Link<Object>("profile") {

			private static final long serialVersionUID = -5931801570555899627L;

			@Override
			public void onClick() {
				setResponsePage(new ProfileMenuPage(AuthorizedSession.get().getUser()));

			}

		}.add(AttributeModifier.append("title", getString("menu.profile"))));
	}

	private void addLinkLogout() {
		add(new Link<Object>("logout") {

			private static final long serialVersionUID = 7260244438883151793L;

			@Override
			public void onClick() {
				getSession().invalidate();
				setResponsePage(LoginPage.class);

			}

		}.setVisible(AuthorizedSession.get().isSignedIn())
				.add(AttributeModifier.append("title", getString("menu.profile-logout"))));
	}

	private void addLinkRecipient() {
		add(new Link<Object>("recipient") {

			private static final long serialVersionUID = -5950804462525877248L;

			@Override
			public void onClick() {
				setResponsePage(new RecipientPage());
			}
		}.add(AttributeModifier.append("title", getString("page.recipients.base.title"))));
	}

	private void addLinkPackages() {
		add(new Link<Object>("packages") {

			private static final long serialVersionUID = -4888470632646656041L;

			@Override
			public void onClick() {
				setResponsePage(new PackagesPage());
			}
		}.add(AttributeModifier.append("title", getString("page.packages.list.title"))));
	}

	private void addLinkUser() {
		add(new Link<Object>("user") {

			private static final long serialVersionUID = -2826650665460809159L;

			@Override
			public void onClick() {
				setResponsePage(new UserPage());
			}
		}.add(AttributeModifier.append("title", getString("page.users.base.title"))));
	}

	private void addLinkTypes() {
		add(new Link<Object>("types") {

			private static final long serialVersionUID = -7515901495815932773L;

			@Override
			public void onClick() {
				setResponsePage(new TypePage());
			}
		}.add(AttributeModifier.append("title", getString("page.types.title"))));
	}

	private void addLinkProduct() {
		add(new Link<Object>("linkproduct") {

			private static final long serialVersionUID = 3721907603803460289L;

			@Override
			public void onClick() {
				setResponsePage(new ProductPage());
			}
		}.add(AttributeModifier.append("title", getString("page.products.title"))));
	}
}
