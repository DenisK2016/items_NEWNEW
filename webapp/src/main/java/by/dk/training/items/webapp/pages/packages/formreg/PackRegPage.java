package by.dk.training.items.webapp.pages.packages.formreg;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;

import by.dk.training.items.datamodel.Package;
import by.dk.training.items.webapp.pages.AbstractPage;

@AuthorizeInstantiation(value = { "ADMIN", "OFFICER", "COMMANDER" })
public class PackRegPage extends AbstractPage {

	private static final long serialVersionUID = 1L;
	private Package pack;

	public PackRegPage() {
		super();

	}

	public PackRegPage(Package pack) {
		super();
		this.pack = pack;

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		addRegistrationPackagePanel();
	}

	private void addRegistrationPackagePanel() {
		if (pack != null) {
			add(new RegistryPackPanel("regPanPack", pack));
		} else {
			add(new RegistryPackPanel("regPanPack"));
		}
	}
}
