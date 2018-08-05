package by.dk.training.items.webapp.pages.recipients.formforreg;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeAction;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;

import com.googlecode.wicket.jquery.ui.markup.html.link.AjaxSubmitLink;
import com.googlecode.wicket.kendo.ui.widget.notification.Notification;

import by.dk.training.items.dataaccess.filters.RecipientFilter;
import by.dk.training.items.datamodel.Recipient;
import by.dk.training.items.services.RecipientService;
import by.dk.training.items.webapp.app.AuthorizedSession;
import by.dk.training.items.webapp.pages.recipients.RecipientPage;

@AuthorizeAction(roles = { "ADMIN", "COMMANDER", "OFFICER" }, action = Action.RENDER)
public class RegistryRecipientPanel extends Panel {

	private static final long serialVersionUID = 1L;

	private Recipient recipient;
	@Inject
	private RecipientService recipientService;
	@SuppressWarnings("unused")
	private ModalWindow modalWindow;

	public RegistryRecipientPanel(ModalWindow modalWindow) {
		super(modalWindow.getContentId());
		this.modalWindow = modalWindow;
		recipient = new Recipient();
	}

	public RegistryRecipientPanel(ModalWindow modalWindow, Recipient recipient) {
		super(modalWindow.getContentId());
		this.modalWindow = modalWindow;
		this.recipient = recipient;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		final Notification notification = new Notification("notification");
		this.add(notification);
		Form<?> form = new Form<>("formRegRec", new CompoundPropertyModel<Recipient>(recipient));
		FeedbackPanel feedBackPanel = new FeedbackPanel("feedback");
		feedBackPanel.setVisible(false);
		form.add(feedBackPanel);

		TextField<String> name = new TextField<String>("name");
		name.setRequired(true);
		name.add(StringValidator.maximumLength(100));
		name.add(StringValidator.minimumLength(5));
		name.add(new PatternValidator("[А-Я а-я]+"));
		form.add(name);

		TextField<String> city = new TextField<String>("city");
		city.setRequired(true);
		city.add(StringValidator.maximumLength(100));
		city.add(StringValidator.minimumLength(2));
		form.add(city);

		TextField<String> address = new TextField<String>("address");
		address.setRequired(true);
		address.add(StringValidator.maximumLength(100));
		address.add(StringValidator.minimumLength(2));
		form.add(address);

		form.add(new AjaxSubmitLink("sendButton") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {

				if (recipient.getId() == null) {
					RecipientFilter filter = new RecipientFilter();
					filter.setAddress(recipient.getAddress());
					filter.setCity(recipient.getCity());
					filter.setName(recipient.getName());
					if (recipientService.getDuplicate(filter).isEmpty()) {
						recipient.setIdUser(AuthorizedSession.get().getUser());
						recipientService.registerRecipient(recipient);
						setResponsePage(new RecipientPage());
					} else {
						notification.error(target, getString("page.recipients.create.error"));
					}
				} else {
					recipientService.update(recipient);
					setResponsePage(new RecipientPage());
				}

			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				for (FeedbackMessage msg : feedBackPanel.getFeedbackMessagesModel().getObject()) {
					notification.error(target, msg.getMessage());
				}
				super.onError(target, form);
			}
		});

		add(form);

		form.add(new Link<RecipientPage>("BackToRecipients") {

			private static final long serialVersionUID = 1L;

			@Override
			public void onClick() {
				setResponsePage(new RecipientPage());
			}
		});

		if (recipient.getId() == null) {
			form.add(new Label("regOrUpdate", getString("page.recipients.create.title1")));
		} else {
			form.add(new Label("regOrUpdate", getString("page.recipients.create.title2")));
		}

	}

}
