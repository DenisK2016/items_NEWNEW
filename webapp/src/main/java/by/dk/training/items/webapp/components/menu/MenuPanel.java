package by.dk.training.items.webapp.components.menu;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import by.dk.training.items.webapp.app.AuthorizedSession;
import by.dk.training.items.webapp.pages.home.HomePage;
import by.dk.training.items.webapp.pages.login.LoginPage;
import by.dk.training.items.webapp.pages.packages.PackagesPage;
import by.dk.training.items.webapp.pages.packages.formforreg.PackRegPage;
import by.dk.training.items.webapp.pages.products.ProductPage;
import by.dk.training.items.webapp.pages.profilemenu.ProfileMenuPage;
import by.dk.training.items.webapp.pages.recipients.RecipientPage;
import by.dk.training.items.webapp.pages.types.TypePage;

public class MenuPanel extends Panel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public MenuPanel(String id) {
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
		}.add(AttributeModifier.append("title", "База продуктов")));

		add(new Link<Object>("types") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new TypePage());
			}
		}.add(AttributeModifier.append("title", "База типов")));

		add(new Link<Object>("packages") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new PackagesPage());
			}
		}.add(AttributeModifier.append("title", "База посылок")));

		add(new Link<Object>("recipient") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new RecipientPage());
			}
		}.add(AttributeModifier.append("title", "База получателей")));

		add(new Link<Object>("newPack") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new PackRegPage());
			}
		});

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

		}.setVisible(AuthorizedSession.get().isSignedIn()).add(AttributeModifier.append("title", "Выйти из системы")));

		add(new Link<Object>("profile") {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new ProfileMenuPage(AuthorizedSession.get().getUser()));

			}

		}.add(AttributeModifier.append("title", "Информация о пользователе")));

		add(new Link<HomePage>("Back") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new HomePage());
			}
		});

	}

}
