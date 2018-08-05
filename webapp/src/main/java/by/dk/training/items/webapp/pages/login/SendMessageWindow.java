package by.dk.training.items.webapp.pages.login;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.googlecode.wicket.kendo.ui.widget.notification.Notification;

import by.dk.training.items.dataaccess.filters.UserFilter;
import by.dk.training.items.datamodel.UserProfile;
import by.dk.training.items.services.UserProfileService;
import by.dk.training.items.services.mail.Sender;
import by.dk.training.items.webapp.app.AuthorizedSession;

public class SendMessageWindow extends Panel {

	private static final long serialVersionUID = -4050360002339445270L;

	private static final String EMAIL = "sample1denis1@gmail.com";
	private static final String PASSWORD = "12345678qwertyui";

	@SuppressWarnings("unused")
	private ModalWindow modalWindow;

	@Inject
	private UserProfileService userProfileService;

	private String email;
	private UserFilter userFilter;
	private boolean banned = AuthorizedSession.get().getRoles().contains("BANNED");

	public SendMessageWindow(ModalWindow modalWindow) {
		super(modalWindow.getContentId());
		this.modalWindow = modalWindow;
		userFilter = new UserFilter();
		userFilter.setFetchCredentials(true);
	}

	@Override
	protected void onInitialize() {
		Form<Object> formSendWindow = new Form<>("formSend");

		addLabelToForm(formSendWindow);
		addEmailToForm(formSendWindow);
		addAjaxSubmitLinkToForm(formSendWindow);

		add(formSendWindow);

		super.onInitialize();
	}

	private void addAjaxSubmitLinkToForm(Form<Object> formSendWindow) {

		final Notification notification = new Notification("notification");
		this.add(notification);

		FeedbackPanel feedBackPanel = new FeedbackPanel("feedback");
		add(feedBackPanel);
		feedBackPanel.setVisible(false);

		AjaxSubmitLink link = new AjaxSubmitLink("send") {

			private static final long serialVersionUID = 7491960504543556605L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);

				userFilter.setEmail(email);

				List<UserProfile> userList = userProfileService.findUser(userFilter);

				if (userList.size() == 0 || email == null) {
					notification.error(target, getString("restore.error"));
				} else if (banned) {
					notification.error(target, getString("restore.error.ban"));
				} else {
					UserProfile user = userList.get(0);
					Long idUser = user.getId();
					BigDecimal key;
					key = keyMethod(idUser);
					PageParameters param = new PageParameters();
					param.add("foo", key);
					String text;
					text = getTextMessage(user, param);
					new Sender(EMAIL, PASSWORD).send(getString("send.message.title"), text,
							user.getUserCredentials().getEmail());
					notification.info(target, getString("send.message.info"));
				}
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				for (FeedbackMessage msg : feedBackPanel.getFeedbackMessagesModel().getObject()) {
					notification.error(target, msg.getMessage());
				}
				super.onError(target, form);
			}
		};
		link.add(AttributeModifier.append("title", getString("restore.button")));
		formSendWindow.add(link);
	}

	private void addEmailToForm(Form<Object> formSendWindow) {
		EmailTextField emailField = new EmailTextField("email", new PropertyModel<>(this, "email"));
		emailField.setRequired(true);
		emailField.add(new AjaxFormComponentUpdatingBehavior("change") {

			private static final long serialVersionUID = 8433134471515114392L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
			}
		});
		formSendWindow.add(emailField);
	}

	private void addLabelToForm(Form<Object> formSendWindow) {
		formSendWindow.add(new Label("title", getString("restore.fieldname")));
	}

	private BigDecimal keyMethod(Long idUser) {
		BigDecimal key;
		key = new BigDecimal(idUser).multiply(new BigDecimal("123")).multiply(new BigDecimal("23"))
				.multiply(new BigDecimal("3"));
		return key;
	}

	private String getTextMessage(UserProfile user, PageParameters param) {
		String text;
		String url = "http://localhost:8081"
				+ RequestCycle.get().urlFor(PageConfirmation.class, param).toString().substring(5);
		text = String.format("%s %s. %s %s", getString("send.message.text"), user.getLogin(),
				getString("send.message.text2"), url);
		return text;
	}
}
