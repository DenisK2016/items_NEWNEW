package by.dk.training.items.webapp.pages.profilemenu.modalwindows;

import java.util.Arrays;

import javax.inject.Inject;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.ajax.markup.html.form.AjaxSubmitLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;
import org.apache.wicket.feedback.FeedbackMessage;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.PatternValidator;
import org.apache.wicket.validation.validator.StringValidator;

import com.googlecode.wicket.kendo.ui.widget.notification.Notification;

import by.dk.training.items.datamodel.Ranks;
import by.dk.training.items.datamodel.UserCredentials;
import by.dk.training.items.datamodel.UserProfile;
import by.dk.training.items.services.UserProfileService;

public class EditInfoPanel extends Panel {

	private static final long serialVersionUID = 1L;
	@Inject
	private UserProfileService userProfileService;
	private ModalWindow modalWindow;
	private UserProfile userProfile;
	private UserCredentials userCredentials;
	private TextField<String> password;
	private TextField<String> firstName;
	private TextField<String> lastName;
	private TextField<String> post;
	private DropDownChoice<Ranks> ranks;
	private String fieldName;

	public EditInfoPanel(ModalWindow modalWindow, UserProfile userProfile, String filedName) {
		super(modalWindow.getContentId());
		this.modalWindow = modalWindow;
		this.userProfile = userProfile;
		this.userCredentials = userProfile.getUserCredentials();
		this.fieldName = filedName;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		Form<UserProfile> form = new Form<UserProfile>("formEdit", new CompoundPropertyModel<UserProfile>(userProfile));
		FeedbackPanel feedBackPanel = new FeedbackPanel("feedback");
		feedBackPanel.setVisible(false);
		add(feedBackPanel);
		final Notification notification = new Notification("notification");
		this.add(notification);
		add(form);
		form.add(password = new TextField<>("password"));
		password.setVisible(false);
		password.setRequired(true);
		password.add(StringValidator.maximumLength(100));
		password.add(StringValidator.minimumLength(6));
		password.add(new PatternValidator("[A-Za-z0-9]+"));
		if (fieldName.equals("password")) {
			password.setVisible(true);
		}
		form.add(firstName = new TextField<>("firstName", new PropertyModel<>(userCredentials, "firstName")));
		firstName.setRequired(true);
		firstName.add(StringValidator.maximumLength(100));
		firstName.add(StringValidator.minimumLength(2));
		firstName.add(new PatternValidator("[А-Яа-я]+"));
		firstName.setVisible(false);
		if (fieldName.equals("firstName")) {
			firstName.setVisible(true);
		}
		form.add(lastName = new TextField<>("lastName", new PropertyModel<>(userCredentials, "lastName")));
		lastName.setRequired(true);
		lastName.add(StringValidator.maximumLength(100));
		lastName.add(StringValidator.minimumLength(2));
		firstName.add(new PatternValidator("[А-Яа-я]+"));
		lastName.setVisible(false);
		if (fieldName.equals("lastName")) {
			lastName.setVisible(true);
		}
		form.add(post = new TextField<>("post", new PropertyModel<>(userCredentials, "post")));
		post.setVisible(false);
		if (fieldName.equals("post")) {
			post.setVisible(true);
		}
		form.add(ranks = new DropDownChoice<Ranks>("rank", new PropertyModel<>(userCredentials, "rank"),
				Arrays.asList(Ranks.values())));
		ranks.setVisible(false);
		if (fieldName.equals("ranks")) {
			ranks.setVisible(true);
		}
		form.add(new AjaxSubmitLink("save") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
				super.onSubmit(target, form);
				userProfileService.updateUserProfile(userProfile);
				userProfileService.updateUserCredentials(userCredentials);
				modalWindow.close(target);
			}

			@Override
			protected void onError(AjaxRequestTarget target, Form<?> form) {
				for (FeedbackMessage msg : feedBackPanel.getFeedbackMessagesModel().getObject()) {
					notification.error(target, msg.getMessage());
				}
				super.onError(target, form);
			}
		});

		form.add(new AjaxLink<Object>("cancel") {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void onClick(AjaxRequestTarget target) {

				modalWindow.close(target);
			}
		});
	}
}
