package by.dk.training.items.webapp.pages.login;

import javax.inject.Inject;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.EmailAddressValidator;

import com.googlecode.wicket.kendo.ui.widget.notification.Notification;

import by.dk.training.items.dataaccess.filters.UserFilter;
import by.dk.training.items.datamodel.UserProfile;
import by.dk.training.items.services.UserProfileService;
import by.dk.training.items.services.mail.Sender;

public class PasswordRestorePanel extends Panel {

	private static final long serialVersionUID = 4463263609129122149L;

	private static final String EMAIL = "sample1denis1@gmail.com";
	private static final String PASSWORD = "12345678qwertyui";

	private String emailStr;
	private ModalWindow modalWindow;
	private UserFilter userFilter;
	private String text;

	@Inject
	private UserProfileService userProfileService;

	public PasswordRestorePanel(ModalWindow modalWindow) {
		super(modalWindow.getContentId());
		this.modalWindow = modalWindow;
		userFilter = new UserFilter();
		userFilter.setFetchCredentials(true);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		Form<UserProfile> form = new Form<UserProfile>("formRestorePass");

		addEmailFieldToForm(form);
		addSubmitLinkToForm(form);
		addCancelLinkToForm(form);

		add(form);
	}

	private void addCancelLinkToForm(Form<UserProfile> form) {
		form.add(new AjaxLink<Object>("cancel") {

			private static final long serialVersionUID = -8722237129605316386L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				modalWindow.close(target);
			}
		});
	}

	private void addSubmitLinkToForm(Form<UserProfile> form) {

		FeedbackPanel feedBackPanel = new FeedbackPanel("feedback");
		form.add(feedBackPanel.setOutputMarkupId(true));
		feedBackPanel.setVisible(false);

		Notification notification = new Notification("notification");
		add(notification);

		form.add(new AjaxSubmitLink("request") {

			private static final long serialVersionUID = -4688438991243598737L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);

				userFilter.setEmail(emailStr);

				if (!userProfileService.findUser(userFilter).isEmpty()) {
					initTextMessage();
					new Sender(EMAIL, PASSWORD).send(getString("restore.title"), text, emailStr);
					notification.info(target, getString("send.message.info"));
				} else {
					notification.error(target, getString("restore.error"));
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
	}

	private void addEmailFieldToForm(Form<UserProfile> form) {
		TextField<String> email = new TextField<String>("email", new PropertyModel<String>(this, "emailStr"));
		email.add(AttributeModifier.append("title", getString("restore.title.field")));
		email.add(EmailAddressValidator.getInstance());
		email.setRequired(true);
		form.add(email);
	}

	private void initTextMessage() {
		text = String.format("%s: %s \n%s: %s", getString("newlogin.login"),
				userProfileService.findUser(userFilter).get(0).getLogin(), getString("newlogin.password"),
				userProfileService.findUser(userFilter).get(0).getPassword());
	}
}
