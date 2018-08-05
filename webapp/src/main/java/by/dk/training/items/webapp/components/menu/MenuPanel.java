package by.dk.training.items.webapp.components.menu;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;

import by.dk.training.items.webapp.app.AuthorizedSession;
import by.dk.training.items.webapp.pages.home.HomePage;
import by.dk.training.items.webapp.pages.login.LoginPage;
import by.dk.training.items.webapp.pages.packages.PackagesPage;
import by.dk.training.items.webapp.pages.packages.formreg.PackRegPage;
import by.dk.training.items.webapp.pages.products.ProductPage;
import by.dk.training.items.webapp.pages.profilemenu.ProfileMenuPage;
import by.dk.training.items.webapp.pages.recipients.RecipientPage;
import by.dk.training.items.webapp.pages.types.TypePage;

public class MenuPanel extends Panel {

	private static final long serialVersionUID = 494069347178478071L;

	public MenuPanel(String id) {
		super(id);
		// setRenderBodyOnly(true);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		addLinkProduct();
		addLinkTypes();
		addLinkPackages();
		addLinkRecipient();
		addLinkNewPack();
		addLinkLogout();
		addLinkProfile();
		addLinkBack();
	}

	private void addLinkBack() {
		add(new Link<HomePage>("Back") {

			private static final long serialVersionUID = -3937787242251718764L;

			@Override
			public void onClick() {
				setResponsePage(new HomePage());
			}
		});
	}

	private void addLinkProfile() {
		add(new Link<Object>("profile") {

			private static final long serialVersionUID = 4170131416386490873L;

			@Override
			public void onClick() {
				setResponsePage(new ProfileMenuPage(AuthorizedSession.get().getUser()));
			}
		}.add(AttributeModifier.append("title", getString("menu.profile"))));
	}

	private void addLinkLogout() {
		add(new Link<Object>("logout") {

			private static final long serialVersionUID = -7942332893209732444L;

			@Override
			public void onClick() {
				getSession().invalidate();
				setResponsePage(LoginPage.class);
			}
		}.setVisible(AuthorizedSession.get().isSignedIn())
				.add(AttributeModifier.append("title", getString("menu.profile-logout"))));
	}

	private void addLinkNewPack() {
		add(new Link<Object>("newPack") {

			private static final long serialVersionUID = -8907418760740661737L;

			@Override
			public void onClick() {
				setResponsePage(new PackRegPage());
			}
		});
	}

	private void addLinkRecipient() {
		add(new Link<Object>("recipient") {

			private static final long serialVersionUID = 5554431846913550124L;

			@Override
			public void onClick() {
				setResponsePage(new RecipientPage());
			}
		}.add(AttributeModifier.append("title", getString("page.recipients.base.title"))));
	}

	private void addLinkPackages() {
		add(new Link<Object>("packages") {
			private static final long serialVersionUID = 6562157635959900485L;

			@Override
			public void onClick() {
				setResponsePage(new PackagesPage());
			}
		}.add(AttributeModifier.append("title", getString("page.packages.list.title"))));
	}

	private void addLinkTypes() {
		add(new Link<Object>("types") {

			private static final long serialVersionUID = -5436621072954935748L;

			@Override
			public void onClick() {
				setResponsePage(new TypePage());
			}
		}.add(AttributeModifier.append("title", getString("page.types.title"))));
	}

	private void addLinkProduct() {
		add(new Link<Object>("linkproduct") {

			private static final long serialVersionUID = 8775306617309812955L;

			@Override
			public void onClick() {
				setResponsePage(new ProductPage());
			}
		}.add(AttributeModifier.append("title", getString("page.products.title"))));
	}
}
