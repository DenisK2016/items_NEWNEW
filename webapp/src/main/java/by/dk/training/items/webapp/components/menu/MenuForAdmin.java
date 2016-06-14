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

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MenuForAdmin(String id) {
		super(id);
		// setRenderBodyOnly(true);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new Link<Object>("linkproduct") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new ProductPage());
			}
		}.add(AttributeModifier.append("title", getString("page.products.title"))));
		add(new Link<Object>("types") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new TypePage());
			}
		}.add(AttributeModifier.append("title", getString("page.types.title"))));

		add(new Link<Object>("user") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new UserPage());
			}
		}.add(AttributeModifier.append("title", getString("page.users.base.title"))));

		add(new Link<Object>("packages") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new PackagesPage());
			}
		}.add(AttributeModifier.append("title", getString("page.packages.list.title"))));

		add(new Link<Object>("recipient") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new RecipientPage());
			}
		}.add(AttributeModifier.append("title", getString("page.recipients.base.title"))));

		add(new Link<Object>("logout") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				getSession().invalidate();
				setResponsePage(LoginPage.class);

			}

		}.setVisible(AuthorizedSession.get().isSignedIn())
				.add(AttributeModifier.append("title", getString("menu.profile-logout"))));

		add(new Link<Object>("profile") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new ProfileMenuPage(AuthorizedSession.get().getUser()));

			}

		}.add(AttributeModifier.append("title", getString("menu.profile"))));

		add(new Link<HomePage>("Back") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new HomePage());
			}
		});
	}
}
