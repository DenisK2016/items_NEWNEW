package by.dk.training.items.webapp.pages.login;

import java.math.BigDecimal;
import java.util.Arrays;

import javax.inject.Inject;
import javax.persistence.EntityExistsException;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;

import com.googlecode.wicket.kendo.ui.widget.notification.Notification;

import by.dk.training.items.dataaccess.filters.UserFilter;
import by.dk.training.items.datamodel.Ranks;
import by.dk.training.items.datamodel.UserCredentials;
import by.dk.training.items.datamodel.UserProfile;
import by.dk.training.items.services.UserProfileService;
import by.dk.training.items.services.mail.Sender;

public class NewLoginPanel extends Panel {

	private static final long serialVersionUID = 723212201477360672L;

	private static final String EMAIL = "sample1denis1@gmail.com";
	private static final String PASSWORD = "12345678qwertyui";

	private UserProfile userProfile;
	private UserCredentials userCredentials;
	private ModalWindow modalWindow;

	@Inject
	private UserProfileService userProfileService;

	public NewLoginPanel(ModalWindow modalWindow) {
		super(modalWindow.getContentId());
		userProfile = new UserProfile();
		userCredentials = new UserCredentials();
		this.modalWindow = modalWindow;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();

		final Notification notification = new Notification("notification");
		add(notification);

		Form<UserProfile> form = new Form<UserProfile>("formRegUser",
				new CompoundPropertyModel<UserProfile>(userProfile));

		FeedbackPanel feedBackPanel = new FeedbackPanel("feedback");
		form.add(feedBackPanel.setOutputMarkupId(true));
		feedBackPanel.setVisible(false);

		addLoginTextFieldToForm(form);
		addPasswordFieldToForm(form);
		addFirstNameFieldToForm(form);
		addLastNameToForm(form);
		addEmailFieldToForm(form);
		addPostFieldToForm(form);
		addRankDropDownToForm(form);
		addAjaxSubmitLinkToForm(notification, form, feedBackPanel);
		addCancelLinkToForm(form);

		add(form);
	}

	private void addCancelLinkToForm(Form<UserProfile> form) {
		AjaxLink<Object> cancelLink = new AjaxLink<Object>("cancel") {

			private static final long serialVersionUID = -2656632890712526950L;

			@Override
			public void onClick(AjaxRequestTarget target) {
				modalWindow.close(target);
			}
		};
		cancelLink.add(AttributeModifier.append("title", getString("newlogin.newcancel.title")));
		form.add(cancelLink);
	}

	private void addAjaxSubmitLinkToForm(final Notification notification, Form<UserProfile> form,
			FeedbackPanel feedBackPanel) {

		AjaxSubmitLink link = new AjaxSubmitLink("save") {

			private static final long serialVersionUID = 8855200967446420402L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);
				UserFilter filter = new UserFilter();
				String msg = "";
				try {
					filter.setLogin(userProfile.getLogin());
					if (!userProfileService.findUser(filter).isEmpty()) {
						msg = getString("newlogin.error2");
						throw new EntityExistsException();
					}

					filter.setLogin(null);

					filter.setEmail(userCredentials.getEmail());

					if (!userProfileService.findUser(filter).isEmpty()) {
						msg = getString("newlogin.error1");
						throw new EntityExistsException();
					}
					userProfileService.registerUser(userProfile, userCredentials);

					Long idUser = userProfileService
							.getUserByNameAndPassword(userProfile.getLogin(), userProfile.getPassword()).getId();

					BigDecimal key = new BigDecimal(idUser).multiply(new BigDecimal("123"))
							.multiply(new BigDecimal("23")).multiply(new BigDecimal("3"));

					PageParameters param = new PageParameters();
					param.add("foo", key);

					String url = "http://localhost:8081"
							+ RequestCycle.get().urlFor(PageConfirmation.class, param).toString().substring(5);

					String text = String.format("%s %s. %s %s", getString("send.message.text"), userProfile.getLogin(),
							getString("send.message.text2"), url);

					new Sender(EMAIL, PASSWORD).send(getString("send.message.title"), text, userCredentials.getEmail());

					modalWindow.close(target);
				} catch (EntityExistsException e) {
					notification.error(target, msg);
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

		link.add(AttributeModifier.append("title", getString("newlogin.newsave.title")));

		form.add(link);
	}

	private void addRankDropDownToForm(Form<UserProfile> form) {
		DropDownChoice<Ranks> rank = new DropDownChoice<Ranks>("rank",
				new PropertyModel<Ranks>(userCredentials, "rank"), Arrays.asList(Ranks.values()));
		rank.add(AttributeModifier.append("title", getString("newlogin.newrank.title")));
		rank.add(new AjaxFormComponentUpdatingBehavior("change") {

			private static final long serialVersionUID = -8295776566406146163L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {

			}
		});
		form.add(rank);
	}

	private void addPostFieldToForm(Form<UserProfile> form) {
		TextField<String> post = new TextField<String>("post", new PropertyModel<>(userCredentials, "post"));
		post.add(new AjaxFormComponentUpdatingBehavior("change") {

			private static final long serialVersionUID = -8295776566406146163L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
			}
		});
		post.add(AttributeModifier.append("title", getString("newlogin.newpost.title")));
		form.add(post);
	}

	private void addEmailFieldToForm(Form<UserProfile> form) {
		TextField<String> email = new TextField<String>("email", new PropertyModel<>(userCredentials, "email"));
		email.add(new AjaxFormComponentUpdatingBehavior("change") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
			}
		});
		email.setRequired(true);
		email.add(StringValidator.maximumLength(100));
		email.add(StringValidator.minimumLength(2));
		email.add(EmailAddressValidator.getInstance());
		email.add(AttributeModifier.append("title", getString("newlogin.newemail.title")));
		form.add(email);
	}

	private void addLastNameToForm(Form<UserProfile> form) {
		TextField<String> lastName = new TextField<String>("lastName",
				new PropertyModel<>(userCredentials, "lastName"));
		lastName.add(new AjaxFormComponentUpdatingBehavior("change") {

			private static final long serialVersionUID = -6790584607931786435L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {

			}
		});
		lastName.setRequired(true);
		lastName.add(StringValidator.maximumLength(100));
		lastName.add(StringValidator.minimumLength(2));
		lastName.add(new PatternValidator("[А-Яа-я]+"));
		lastName.add(AttributeModifier.append("title", getString("newlogin.newlname.title")));
		form.add(lastName);
	}

	private void addFirstNameFieldToForm(Form<UserProfile> form) {
		TextField<String> firstName = new TextField<String>("firstName",
				new PropertyModel<>(userCredentials, "firstName"));
		firstName.add(new AjaxFormComponentUpdatingBehavior("change") {

			private static final long serialVersionUID = -8295776566406146163L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
			}
		});
		firstName.setRequired(true);
		firstName.add(StringValidator.maximumLength(100));
		firstName.add(StringValidator.minimumLength(2));
		firstName.add(new PatternValidator("[А-Яа-я]+"));
		firstName.add(AttributeModifier.append("title", getString("newlogin.newfname.title")));
		form.add(firstName);
	}

	private void addPasswordFieldToForm(Form<UserProfile> form) {
		PasswordTextField password = new PasswordTextField("password");
		password.add(new AjaxFormComponentUpdatingBehavior("change") {

			private static final long serialVersionUID = -8295776566406146163L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
			}
		});
		password.add(StringValidator.maximumLength(100));
		password.add(StringValidator.minimumLength(6));
		password.add(new PatternValidator("[A-Za-z0-9]+"));
		password.add(AttributeModifier.append("title", getString("newlogin.newpass.title")));
		form.add(password);
	}

	private void addLoginTextFieldToForm(Form<UserProfile> form) {
		TextField<String> login = new TextField<String>("login");
		login.add(new AjaxFormComponentUpdatingBehavior("change") {

			private static final long serialVersionUID = -8295776566406146163L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
			}
		});
		login.setRequired(true);
		login.add(StringValidator.maximumLength(100));
		login.add(StringValidator.minimumLength(2));
		login.add(AttributeModifier.append("title", getString("newlogin.newuser.title")));
		form.add(login);
	}
}
