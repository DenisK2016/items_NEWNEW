package by.dk.training.items.webapp.pages.users;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow.WindowClosedCallback;

import by.dk.training.items.webapp.pages.AbstractPage;
import by.dk.training.items.webapp.pages.users.formreg.RegistryUserPanel;
import by.dk.training.items.webapp.pages.users.panelusers.ListUsersPanel;

@AuthorizeInstantiation(value = { "ADMIN" })
public class UserPage extends AbstractPage {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserPage() {
		super();

	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(new ListUsersPanel("list-panel"));
		final ModalWindow modalCreate = new ModalWindow("modalCreate");
		modalCreate.setCssClassName("modal_window");
		modalCreate.setResizable(false);
		modalCreate.setWindowClosedCallback(new WindowClosedCallback() {

			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClose(AjaxRequestTarget target) {
				target.add(UserPage.this);

			}
		});
		this.setOutputMarkupId(true);
		add(modalCreate);
		AjaxLink<Void> create = new AjaxLink<Void>("createUser") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				modalCreate.setContent(new RegistryUserPanel(modalCreate));
				modalCreate.show(target);
			}
		};
		create.add(AttributeModifier.append("title", getString("page.users.create")));
		add(create);
	}
}
